package fr.chrono.ihm.panels;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class TabRun extends Tab{
	
	private SplitPane splitPane;

	public TabRun() {
		this.setText("Course");
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(getSplitPane());
		this.setContent(borderPane);
	}

	public SplitPane getSplitPane() {
		if(splitPane == null) {
			splitPane = new SplitPane();
//			BorderPane borderPaneSup = new BorderPane();
//			borderPaneSup.setCenter(getTable());
//			splitPane.getItems().add(borderPaneSup);
		}
		return splitPane;
	}
}
