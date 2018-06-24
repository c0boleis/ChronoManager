package fr.chrono.controlers;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.core.IsSame;

import com.sun.javafx.image.impl.ByteIndexed.Getter;

import fr.chrono.controlers.listeners.RunListener;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.application.Platform;

public class RunControler {
	
	private static List<RunListener> listeners;
	
	private static boolean isReadyToStart = false;
	
	private static final String DEFAULT_START_STATUS = "N/A";
	
	private static String startStatus = DEFAULT_START_STATUS;
	
	private static boolean iStart = false;
	
	private static Thread timerRefresh;
	
	static {
		listeners = new ArrayList<RunListener>();
		isReadyToStart = false;
	}
	
	public static void startRun() {
		if(iStart) {
			return;
		}
		getTimerRefresh().start();
		fireRunStarted();
		iStart = true;
	}
	
	public static void stopRun() {
		if(!iStart) {
			return;
		}
		getTimerRefresh().interrupt();
		timerRefresh = null;
		fireRunStoped();
		iStart = false;
	}
	
	public static boolean isReadyToStart() {
		return isReadyToStart;
	}
	
	private static void setIsReadyToStart(boolean b) {
		if(isReadyToStart == b) {
			return;
		}
		isReadyToStart = b;
		fireStateChanged();
	}
	
	/**
	 * check competiteurs list
	 * @return true if the run is ready to start.
	 */
	public static boolean checkIfReadyToStart() {
		ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteursByStartOrder();
		if(competiteurs.length <= 0) {
			startStatus = "Il n'y a aucun competiteur";
			setIsReadyToStart(false);
			return false;
		}
		int startOrderTest = 1;
		long timeOrderTest = competiteurs[0].getStartTime()-1;
		for(ICompetiteur competiteur : competiteurs) {
			if(competiteur.getStartOrder()!=startOrderTest) {
				startStatus = "L'ordre de départ de "+competiteur.getName()
				+"["+competiteur.getCategory()+"] ne suit pas le précédents";
				setIsReadyToStart(false);
				return false;
			}
			if(competiteur.getStartTime()<=timeOrderTest) {
				startStatus = "Le temps de départ de "+competiteur.getName()
				+"["+competiteur.getCategory()+"] est en avance sur le précédents";
				setIsReadyToStart(false);
				return false;
			}
			timeOrderTest = competiteur.getStartTime();
			startOrderTest++;
		}
		startStatus="La course peut démarer!";
		setIsReadyToStart(true);
		return true;
	}
	
	public static boolean addRunListener(RunListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
			return true;
		}
		return false;
	}

	public static boolean removeRunListener(RunListener listener) {
		return listeners.remove(listener);
	}

	public static void removeAllRunListeners() {
		listeners.clear();
	}

	public static RunListener[] getRunListeners() {
		return listeners.toArray(new RunListener[0]);
	}
	
	private static void fireRunStarted() {
		RunListener[] listenersTmp = getRunListeners();
		for(RunListener listener : listenersTmp) {
			listener.runStarted();
		}
	}
	
	private static void fireRunStoped() {
		RunListener[] listenersTmp = getRunListeners();
		for(RunListener listener : listenersTmp) {
			listener.runStoped();
		}
	}
	
	private static void fireStateChanged() {
		RunListener[] listenersTmp = getRunListeners();
		for(RunListener listener : listenersTmp) {
			listener.stateChange();
		}
	}

	public static String getStartStatus() {
		if(startStatus.equals(DEFAULT_START_STATUS)) {
			checkIfReadyToStart();
		}
		return startStatus;
	}

	public static Thread getTimerRefresh() {
		if(timerRefresh == null) {
			timerRefresh = new Thread() {
			    public void run() {
			        //Do some stuff in another thread
			    	while(true) {
				    	try {
							Thread.sleep(500);
							Thread.yield();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				    	ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteurs();
				    	long currentTime = TimeControler.getCurrentTime();
				    	for(ICompetiteur competiteur : competiteurs) {
				    		if(competiteur.getStartTime()<currentTime) {

						        Platform.runLater(new Runnable() {
						            public void run() {
						            	CompetiteurControler.fireCompetiteurChanged(competiteur);
						            }
						        });
				    		}
				    	}
			    	}

			    }
			};
		}
		return timerRefresh;
	}

}
