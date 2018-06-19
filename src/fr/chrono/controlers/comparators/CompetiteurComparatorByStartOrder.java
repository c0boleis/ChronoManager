package fr.chrono.controlers.comparators;

import java.util.Comparator;

import fr.chrono.model.interfaces.ICompetiteur;

public class CompetiteurComparatorByStartOrder implements Comparator<ICompetiteur> {

	@Override
	public int compare(ICompetiteur arg0, ICompetiteur arg1) {
		/*
		 * sort first by category
		 */
		int result = Integer.compare(arg0.getStartOrder(), arg1.getStartOrder());
		if(result != 0) {
			return result;
		}
		/*
		 * sort second by name 
		 */
		return arg0.getName().compareTo(arg1.getName());
	}

}
