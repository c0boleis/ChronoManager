package fr.chrono.controlers.comparators;

import java.util.Comparator;

import fr.chrono.model.interfaces.ICompetiteur;

public class CompetiteurComparatorByRunTime implements Comparator<ICompetiteur> {

	@Override
	public int compare(ICompetiteur arg0, ICompetiteur arg1) {
		/*
		 * sort first by category
		 */
		int result = Long.compare(arg0.getRunTime(), arg1.getRunTime());
		if(result != 0) {
			return result;
		}
		/*
		 * sort second by start order priority to superieur startorder
		 */
		return Integer.compare(arg1.getStartOrder(), arg0.getStartOrder());
	}

}
