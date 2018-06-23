package fr.chrono.ihm.panels;

import javafx.scene.control.Tab;

public class TabRun extends Tab{
	
	private TableRunArrivingV2 tableRunArriving;

	public TabRun() {
		this.setText("Course");
		this.setContent(getTableRunArriving());
		this.setClosable(false);
	}

	public TableRunArrivingV2 getTableRunArriving() {
		if(tableRunArriving == null) {
			tableRunArriving = new TableRunArrivingV2();
		}
		return tableRunArriving;
	}


}
