package fr.chrono.ihm.panels;

import javafx.scene.control.Tab;

public class TabRun extends Tab{
	
	private TableRunArriving tableRunArriving;

	public TabRun() {
		this.setText("Course");
		this.setContent(getTableRunArriving());
		this.setClosable(false);
	}

	public TableRunArriving getTableRunArriving() {
		if(tableRunArriving == null) {
			tableRunArriving = new TableRunArriving();
		}
		return tableRunArriving;
	}


}
