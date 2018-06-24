package fr.chrono.controlers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.chrono.controlers.listeners.CatergoyListener;
import fr.chrono.model.Category;
import fr.chrono.model.interfaces.ICategory;
import fr.chrono.model.interfaces.ICompetiteur;

public class CategoryControler {

	private static List<String> categories;

	public static final String CATEGORY_FORBIDDEN_CHAR = "[^\\w&&[^-]]*";

	public static final boolean CATEGORY_UPPER_CASE_ONLY = true;

	private static List<CatergoyListener> listeners;

	static {
		categories = new ArrayList<String>();
		listeners = new ArrayList<CatergoyListener>();
	}

	public static String[] getCategories() {
		ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteurs();
		for(ICompetiteur competiteur : competiteurs) {
			if(!categories.contains(competiteur.getCategory())) {
				categories.add(competiteur.getCategory());
			}
		}
		return categories.toArray(new String[0]);
	}
	
	public static ICategory[] generateCategories() {
		List<ICategory> categories = new ArrayList<ICategory>();
		ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteurs();
		for(ICompetiteur competiteur : competiteurs) {
			ICategory category = getCategories(categories, competiteur.getCategory());
			if(category==null) {
				category = new Category(competiteur.getCategory());
				categories.add(category);
			}
			category.addCompetiteur(competiteur);
		}
		return categories.toArray(new ICategory[0]);
	}
	
	public static ICategory getCategories(List<ICategory> categories,String nameCategory) {
		for(ICategory category : categories) {
			if(category.getName().equals(nameCategory)) {
				return category;
			}
		}
		return null;
	}

	public static boolean addCategory(String category) {
		String categoryChecked = checkCategory(category);
		if(categoryChecked==null) {
			return false;
		}
		if(!categories.contains(categoryChecked)) {
			categories.add(categoryChecked);
			fireCategoryAdded(categoryChecked);
		}
		return true;
	}

	public static String checkCategory(String category) {
		if(category == null) {
			return null;
		}
		Pattern pattern = Pattern.compile(CATEGORY_FORBIDDEN_CHAR);
		Matcher match = pattern.matcher(category);
		while(match.find()) {
			if(match.group().length()>0) {
				/*
				 * there is forbidden char
				 */
				System.err.println("\""+match.group()+"\"");
				return null;
			}
		}
		if(CATEGORY_UPPER_CASE_ONLY) {
			return category.trim().toUpperCase();
		}else {
			return category.trim();
		}

	}
	
	public static boolean addCatergoyListener(CatergoyListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
			return true;
		}
		return false;
	}

	public static boolean removeCatergoyListener(CatergoyListener listener) {
		return listeners.remove(listener);
	}

	public static void removeAllCatergoyListeners() {
		listeners.clear();
	}

	public static CatergoyListener[] getCatergoyListeners() {
		return listeners.toArray(new CatergoyListener[0]);
	}
	
	private static void fireCategoryAdded(String category) {
		CatergoyListener[] listenersTmp = getCatergoyListeners();
		for(CatergoyListener listener : listenersTmp) {
			listener.categoryAdded(category);
		}
	}

}
