package fr.chrono.model;

import fr.chrono.model.interfaces.ICompetiteur;

public class Competiteur implements ICompetiteur{
	
	private long arrivalTime = 0l;
	
	private String category = null;
	
	private long deltaTime = 0l;
	
	private String name = null;
	
	private int startOrder = 1;
	
	private long startTime = 0l;

	/**
	 * @param newCategory
	 * @param newName
	 */
	public Competiteur(String newName, String newCategory) {
		super();
		this.category = newCategory;
		this.name = newName;
	}

	@Override
	public long getArrivalTime() {
		return arrivalTime;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public long getDeltaTime() {
		return deltaTime;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getStartOrder() {
		return startOrder;
	}

	@Override
	public long getStartTime() {
		return startTime;
	}

	@Override
	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public void setDeltaTime(long deltaTime) {
		this.deltaTime = deltaTime;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setStartOrder(int startOrder) {
		this.startOrder = startOrder;
	}

	@Override
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public long getRunTime() {
		return getArrivalTime()-getStartTime()+getDeltaTime();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Competiteur)) {
			return false;
		}
		Competiteur other = (Competiteur) obj;
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	
}
