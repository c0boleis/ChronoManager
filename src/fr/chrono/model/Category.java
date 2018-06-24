package fr.chrono.model;

import java.util.ArrayList;
import java.util.List;

import fr.chrono.model.interfaces.ICategory;
import fr.chrono.model.interfaces.ICompetiteur;

public class Category implements ICategory{
	
	private String name;
	
	private List<ICompetiteur> competiteurs;
	
	public Category(String nameIn) {
		this.name = nameIn;
		this.competiteurs = new ArrayList<ICompetiteur>();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public ICompetiteur[] getCompetiteurs() {
		return this.competiteurs.toArray(new ICompetiteur[0]);
	}

	@Override
	public boolean addCompetiteur(ICompetiteur competiteur) {
		if(!this.competiteurs.contains(competiteur)) {
			this.competiteurs.add(competiteur);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeCompetiteur(ICompetiteur competiteur) {
		return this.removeCompetiteur(competiteur);
	}

	@Override
	public void removeAllCompetiteurs() {
		this.competiteurs.clear();
	}

}
