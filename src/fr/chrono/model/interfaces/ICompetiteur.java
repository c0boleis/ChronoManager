package fr.chrono.model.interfaces;

public interface ICompetiteur {
	
	public static final String NAME_FORBIDDEN_CHAR = "[^\\w&&[^ ]&&[^/]&&[^-]]*";
	
	public String getName();

	public void setName(String newName);
	
	public String getCategory();

	public void setCategory(String newName);
	
	public long getStartTime();
	
	public void setStartTime(long newStartTime);
	
	public long getArrivalTime();
	
	public void setArrivalTime(long newArrivalTime);
	
	public long getDeltaTime();
	
	public void setDeltaTime(long newDeltaTime);
	
	public int getStartOrder();
	
	public void setStartOrder(int newStartOrder);
	
	public long getRunTime();

}
