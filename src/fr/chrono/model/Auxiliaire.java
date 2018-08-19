package fr.chrono.model;

import fr.chrono.model.interfaces.DomainType;
import fr.chrono.model.interfaces.IAuxiliare;

public class Auxiliaire implements IAuxiliare{
	
	private DomainType domainType;
	
	private int id;
	
	public Auxiliaire(DomainType typeIn, int idIn) {
		this.domainType = typeIn;
		this.id = idIn;
	}

	@Override
	public int getID() {
		return this.id;
	}

	@Override
	public DomainType getDomainType() {
		return this.domainType;
	}

}
