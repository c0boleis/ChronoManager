package fr.chrono.controlers.listeners;

import fr.chrono.model.interfaces.ICompetiteur;

public interface CompetiteurListener {
	
	public void competiteurChange(ICompetiteur competiteur);
	
	public void competiteurAdded(ICompetiteur competiteur);
	
	public void competiteurRemoved(ICompetiteur competiteur);

}
