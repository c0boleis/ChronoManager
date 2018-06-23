package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class TableRunArriving extends BorderPane{
	
	private ScrollPane scrollPane;
	
	private VBox table;
	
	public TableRunArriving() {
		super();
		this.setCenter(getScrollPane());
	}

	/**
	 * @return the scrollPane
	 */
	private ScrollPane getScrollPane() {
		if(scrollPane == null) {
			scrollPane =new ScrollPane(getTable());
		}
		return scrollPane;
	}

	/**
	 * @return the table
	 */
	private VBox getTable() {
		if(table == null) {
			table = new VBox();
			ICompetiteur[] competiteurs = CompetiteurControler.getCompetiteurs();
			for(ICompetiteur competiteur : competiteurs) {
				PanelCompetiteurRun panel = new PanelCompetiteurRun(competiteur);
				table.getChildren().add(panel);
			}
		}
		return table;
	}

}
