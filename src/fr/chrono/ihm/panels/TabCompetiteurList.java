package fr.chrono.ihm.panels;

import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class TabCompetiteurList extends Tab{
	
	private SplitPane splitPane;

	private TableCompetiteurList table;
	
	private PaneCompetiteurListAction competiteurListAction;

	public TabCompetiteurList() {
		this.setText("Liste");
		this.setClosable(false);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(getSplitPane());
		this.setContent(borderPane);
	}

	public SplitPane getSplitPane() {
		if(splitPane == null) {
			splitPane = new SplitPane();
			splitPane.setOrientation(Orientation.VERTICAL);
			splitPane.setDividerPositions(0.75);
			BorderPane borderPaneSup = new BorderPane();
			borderPaneSup.setCenter(getTable());
			splitPane.getItems().add(borderPaneSup);
			splitPane.getItems().add(getCompetiteurListAction());
		}
		return splitPane;
	}
	
	public TableCompetiteurList getTable() {
		if(table == null) {
			table = new TableCompetiteurList();
		}
		return table;
	}

	private PaneCompetiteurListAction getCompetiteurListAction() {
		if(competiteurListAction == null) {
			competiteurListAction = new PaneCompetiteurListAction();
		}
		return competiteurListAction;
	}
}
