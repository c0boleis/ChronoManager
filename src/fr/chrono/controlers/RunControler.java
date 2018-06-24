package fr.chrono.controlers;

import java.util.ArrayList;
import java.util.List;

import fr.chrono.controlers.listeners.RunListener;

public class RunControler {
	
	private static List<RunListener> listeners;
	
	static {
		listeners = new ArrayList<RunListener>();
	}
	
	public static void startRun() {
		fireRunStarted();
	}
	
	public static void stopRun() {
		fireRunStoped();
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

}
