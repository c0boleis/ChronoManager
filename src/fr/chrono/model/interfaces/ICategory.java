package fr.chrono.model.interfaces;

public interface ICategory {
	
	public String getName();
	
	public ICompetiteur[] getCompetiteurs();
	
	public boolean addCompetiteur(ICompetiteur competiteur);
	
	public boolean removeCompetiteur(ICompetiteur competiteur);
	
	public void removeAllCompetiteurs();

}
