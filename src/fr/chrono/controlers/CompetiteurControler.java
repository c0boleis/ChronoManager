package fr.chrono.controlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.chrono.controlers.comparators.CompetiteurComparatorByStartOrder;
import fr.chrono.controlers.comparators.CompetiteurComparatorByCategory;
import fr.chrono.controlers.listeners.CompetiteurListener;
import fr.chrono.ihm.panels.PanelCompetiteurRun;
import fr.chrono.model.Competiteur;
import fr.chrono.model.exceptions.IllegalCategoryException;
import fr.chrono.model.exceptions.IllegalNameException;
import fr.chrono.model.interfaces.ICategory;
import fr.chrono.model.interfaces.ICompetiteur;

public class CompetiteurControler {

	private static List<ICompetiteur> competiteurs;

	private static List<CompetiteurListener> listeners;

	private static boolean activeListener = true;

	static {
		competiteurs = new ArrayList<ICompetiteur>();
		listeners = new ArrayList<CompetiteurListener>();
	}

	public static ICompetiteur createCompetiteur(String newName,String newCategory) {
		String newNameChecked = checkName(newName);
		String newCategoryChecked = CategoryControler.checkCategory(newCategory);
		if(newNameChecked == null || newCategoryChecked==null) {
			/*
			 * name null, means the string is malformed.
			 */
			throw new IllegalNameException(newName);
		}
		if(containsCompetiteur(newNameChecked, newCategoryChecked)) {
			/*
			 * competiteur already exist so we return it.
			 */
			throw new IllegalCategoryException(newCategory);
		}
		ICompetiteur newCompetiteur = new Competiteur(newNameChecked, newCategoryChecked);
		competiteurs.add(newCompetiteur);
		fireCompetiteurAdded(newCompetiteur);
		RunControler.checkIfReadyToStart();
		return newCompetiteur;
	}

	public static boolean removeCompetiteur(ICompetiteur competiteur) {
		if(competiteurs.remove(competiteur)) {
			fireCompetiteurRemoved(competiteur);
			return true;
		}
		return false;
	}

	public static ICompetiteur removeCompetiteur(String name, String category) {
		ICompetiteur competiteur = getCompetiteur(name, category);
		if(competiteur==null) {
			return null;
		}
		if(competiteurs.remove(competiteur)) {
			fireCompetiteurRemoved(competiteur);
			return competiteur;
		}
		return null;
	}

	public static boolean setStartTime(ICompetiteur competiteur, long newStartTime) {
		// check if the property need to be change
		long oldStartTime = competiteur.getStartTime();
		if(oldStartTime == newStartTime) {
			//nothing to do
			return false;
		}
		competiteur.setStartTime(newStartTime);
		fireCompetiteurChanged(competiteur);
		RunControler.checkIfReadyToStart();
		return true;
	}

	public static boolean setArrivalTime(ICompetiteur competiteur, long newArrivalTime) {
		// check if the property need to be change
		long oldArrivalTime = competiteur.getArrivalTime();
		if(oldArrivalTime == newArrivalTime) {
			//nothing to do
			return false;
		}
		competiteur.setArrivalTime(newArrivalTime);
		fireCompetiteurChanged(competiteur);
		return true;
	}

	public static boolean setName(ICompetiteur competiteur, String newName) {
		String newNameChecked = checkName(newName);
		// check if the property need to be change
		String oldName = competiteur.getName();
		if(oldName.equals(newNameChecked)) {
			//nothing to do
			return false;
		}
		if(containsCompetiteur(newNameChecked, competiteur.getCategory())) {
			/*
			 * Impossible because an other competiteur 
			 * have the same name and category.
			 */
			return false;
		}
		competiteur.setName(newNameChecked);
		fireCompetiteurChanged(competiteur);
		return true;
	}

	public static boolean setCategory(ICompetiteur competiteur, String newCategory) {
		String newCategoryChecked = CategoryControler.checkCategory(newCategory);
		// check if the property need to be change
		String oldCategory = competiteur.getName();
		if(oldCategory.equals(newCategoryChecked)) {
			//nothing to do
			return false;
		}
		if(containsCompetiteur(competiteur.getName(), newCategoryChecked)) {
			/*
			 * Impossible because an other competiteur 
			 * have the same name and category.
			 */
			return false;
		}
		competiteur.setCategory(newCategoryChecked);
		fireCompetiteurChanged(competiteur);
		return true;
	}

	public static boolean setStartOrder(ICompetiteur competiteur, int newStartOrder) {
		// check if the property need to be change
		int oldStartOrder = competiteur.getStartOrder();
		if(oldStartOrder == newStartOrder) {
			//nothing to do
			return false;
		}
		if(newStartOrder<=0 || newStartOrder> competiteurs.size()) {
			/*
			 * newStartOrder have to be > 0
			 * and <= number of competiteurs
			 */
			return false;
		}
		competiteur.setStartOrder(newStartOrder);
		fireCompetiteurChanged(competiteur);
		//change other start order;
		ICompetiteur[] competiteursTmp = getCompetiteurs();
		for(ICompetiteur competiteurTmp : competiteursTmp) {
			if(competiteur.equals(competiteurTmp)) {
				/*
				 * we don't modify the competiteur who
				 * was set
				 */
				continue;
			}
			int startOrderTmp = competiteurTmp.getStartOrder();
			if(startOrderTmp>oldStartOrder) {
				if(startOrderTmp>oldStartOrder && startOrderTmp<=newStartOrder) {
					startOrderTmp--;
					competiteurTmp.setStartOrder(startOrderTmp);
					fireCompetiteurChanged(competiteurTmp);
				}
			}else if(startOrderTmp<oldStartOrder){
				if(startOrderTmp<oldStartOrder && startOrderTmp>=newStartOrder) {
					startOrderTmp++;
					competiteurTmp.setStartOrder(startOrderTmp);
					fireCompetiteurChanged(competiteurTmp);
				}
			}else if(startOrderTmp==oldStartOrder){
				if(startOrderTmp>=newStartOrder) {
					startOrderTmp++;
					competiteurTmp.setStartOrder(startOrderTmp);
					fireCompetiteurChanged(competiteurTmp);
				}
			}

		}
		RunControler.checkIfReadyToStart();
		return true;
	}

	public static ICompetiteur[] getCompetiteurs() {
		return competiteurs.toArray(new ICompetiteur[0]);
	}

	public static ICompetiteur[] getCompetiteursByStartOrder() {
		Collections.sort(competiteurs, new CompetiteurComparatorByStartOrder());
		return competiteurs.toArray(new ICompetiteur[0]);
	}

	public static PanelCompetiteurRun[] getPanelCompetiteurRuns() {
		ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteurs();
		List<PanelCompetiteurRun> listOut = new ArrayList<PanelCompetiteurRun>();
		for(ICompetiteur competiteur : competiteurs) {
			PanelCompetiteurRun panel = new PanelCompetiteurRun(competiteur);
			listOut.add(panel);
		}
		return listOut.toArray(new PanelCompetiteurRun[0]);
	}

	public static boolean containsCompetiteur(ICompetiteur competiteur) {
		return containsCompetiteur(competiteur.getName(), competiteur.getCategory());
	}

	public static boolean containsCompetiteur(String name,String category) {
		ICompetiteur[] competiteursTmp = getCompetiteurs();
		for(ICompetiteur competiteur : competiteursTmp) {
			if(!name.equalsIgnoreCase(competiteur.getName())) {
				continue;
			}
			if(!category.equalsIgnoreCase(competiteur.getCategory())) {
				continue;
			}
			return true;
		}
		return false;
	}

	public static boolean addCompetiteurListener(CompetiteurListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
			return true;
		}
		return false;
	}

	public static boolean removeCompetiteurListener(CompetiteurListener listener) {
		return listeners.remove(listener);
	}

	public static void removeAllCompetiteurListeners() {
		listeners.clear();
	}

	public static CompetiteurListener[] getCompetiteurListeners() {
		return listeners.toArray(new CompetiteurListener[0]);
	}

	protected static void fireCompetiteurChanged(ICompetiteur competiteur) {
		if(!activeListener) {
			return;
		}
		CompetiteurListener[] listenersTmp = getCompetiteurListeners();
		for(CompetiteurListener listener : listenersTmp) {
			listener.competiteurChange(competiteur);
		}
	}

	private static void fireCompetiteurAdded(ICompetiteur competiteur) {
		if(!activeListener) {
			return;
		}
		CompetiteurListener[] listenersTmp = getCompetiteurListeners();
		for(CompetiteurListener listener : listenersTmp) {
			listener.competiteurAdded(competiteur);
		}
	}

	private static void fireCompetiteurRemoved(ICompetiteur competiteur) {
		if(!activeListener) {
			return;
		}
		CompetiteurListener[] listenersTmp = getCompetiteurListeners();
		for(CompetiteurListener listener : listenersTmp) {
			listener.competiteurRemoved(competiteur);
		}
	}

	public static String checkName(String name) {
		if(name == null) {
			return null;
		}
		Pattern pattern = Pattern.compile(ICompetiteur.NAME_FORBIDDEN_CHAR);
		Matcher match = pattern.matcher(name);
		while(match.find()) {
			if(match.group().length()>0) {
				/*
				 * there is forbidden char
				 */
				System.err.println("\""+match.group()+"\"");
				return null;
			}
		}
		return formatName(name.trim());
	}

	public static String formatName(String name) {
		String nameOut = name.trim();
		if(nameOut.endsWith("/")) {
			throw new IllegalNameException(name);
		}
		if(Util.numberOfChar(name, '/')>1) {
			throw new IllegalNameException(name);
		}
		String[] info =nameOut.split("/");
		if(info.length<=0) {
			throw new IllegalNameException(name);
		}else if(info.length==1) {
			return formatNameFirsCharToUpperCase(info[0]);
		}else if(info.length==2) {
			return formatNameFirsCharToUpperCase(info[0])+"/"+
					formatNameFirsCharToUpperCase(info[1]);
		}else {
			throw new IllegalNameException(name);
		}

	}

	private static String formatNameFirsCharToUpperCase(String name) {
		String nameOut = name.trim();
		String[] info =nameOut.split(" ");
		nameOut = "";
		for(String st : info) {
			if(st.length()==0) {
				throw new IllegalNameException(name);
			}
			String text = st.toLowerCase();
			nameOut += text.replaceFirst(String.valueOf(text.charAt(0)),
					String.valueOf(text.charAt(0)).toUpperCase());
			nameOut+= " ";
		}
		return nameOut.trim();
	}

	public static ICompetiteur getCompetiteur(String name, String category) {
		ICompetiteur[] competiteursTmp = getCompetiteurs();
		for(ICompetiteur competiteur : competiteursTmp) {
			if(!name.equalsIgnoreCase(competiteur.getName())) {
				continue;
			}
			if(!category.equalsIgnoreCase(competiteur.getCategory())) {
				continue;
			}
			return competiteur;
		}
		return null;
	}

	public static final boolean save() throws IOException {
		File fileOut = new File("resources/fileOut.csv");//TODO
		BufferedWriter buf = new BufferedWriter(new FileWriter(fileOut));
		ICompetiteur[] competiteursTmp = getCompetiteurs();
		boolean first = true;
		for(ICompetiteur competiteur : competiteursTmp) {
			if(!first) {
				buf.write("\n");
			}else {
				first = false;
			}
			buf.write(getCompetiteurStringOut(competiteur));
		}
		buf.close();
		return false;
	}

	public static String getCompetiteurStringOut(ICompetiteur competiteur) {
		return competiteur.getName()+";"
				+ competiteur.getCategory()+";"
				+ competiteur.getStartOrder()+";"
				+ TimeControler.parseTimeToString(competiteur.getStartTime())+";"
				+ TimeControler.parseTimeToString(competiteur.getArrivalTime())+";"
				+ TimeControler.parseTimeToString(competiteur.getDeltaTime())+";"
				+ TimeControler.parseTimeToString(competiteur.getRunTime());
	}

	@SuppressWarnings("resource")
	public static final boolean load(File file) throws IOException {
		/*
		 * Deactivate listener
		 */
		activeListener = true; 
		BufferedReader buf = new BufferedReader(new FileReader(file));
		String line = buf.readLine();
		while(line!=null) {
			String[] info = line.split(";");
			if(info.length<2) {
				throw new IOException("not enough argument in line: "+line);
			}
			String name = info[0];
			String category = info[1];
			ICompetiteur competiteur = createCompetiteur(name, category);
			if(competiteur==null) {
				throw new IOException("Competiteur[name: \""+name+"\", category: \""+competiteur+"\"]"
						+ " wasn't created from line: "+line);
			}
			if(info.length>=3) {
				int startOrder = Integer.parseInt(info[2].trim());
				/*
				 * use controler function in case of
				 * to same startOrder
				 */
				competiteur.setStartOrder(startOrder);
			}
			if(info.length>=4) {
				String startTime = info[3].trim();
				long startTimeL = TimeControler.parseTimeTolong(startTime);
				setStartTime(competiteur, startTimeL);
			}
			line = buf.readLine();
		}
		buf.close();
		/*
		 * Activate listener
		 */
		activeListener = true;
		return true;
	}

	/**
	 * the function is a default initialize startOrder
	 * sort by category first and name second
	 */
	public static void initStartOrder() {
		Collections.sort(competiteurs, new CompetiteurComparatorByCategory());
		ICompetiteur[] competiteurs = getCompetiteurs();
		int startOrder = 1;
		for(ICompetiteur competiteur : competiteurs) {
			setStartOrder(competiteur, startOrder);
			startOrder++;
		}
	}

	public static void initStartTime(long startTime, long deltaCompetiteurs, long deltaCategorires) {
		Collections.sort(competiteurs, new CompetiteurComparatorByStartOrder());
		ICategory[] categories = CategoryControler.generateCategories();
		for(ICategory category : categories) {
			//init startTime
			startTime-=deltaCompetiteurs;
			ICompetiteur[] competiteurs = category.getCompetiteurs();
			for(ICompetiteur competiteur : competiteurs) {
				startTime+=deltaCompetiteurs;
				setStartTime(competiteur, startTime);
			}
			startTime+=deltaCategorires;
		}
	}

}
