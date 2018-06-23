package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.listeners.CompetiteurListener;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TableRunArrivingV2 extends BorderPane{
	
//	private ScrollPane scrollPane;
	
	private TableView<ICompetiteur> table;
	
	private TableColumn<ICompetiteur,Label> startOrderColumn;

	private TableColumn<ICompetiteur,GridPane> infoColumn;

	private TableColumn<ICompetiteur,Button> btnArrivingColumn;

	private CompetiteurListener competiteurListener;
	
	public TableRunArrivingV2() {
		super();
		this.setCenter(getTable());
		CompetiteurControler.addCompetiteurListener(getCompetiteurListener());
	}
	
	private CompetiteurListener getCompetiteurListener() {
		if(competiteurListener == null) {
			competiteurListener = new CompetiteurListener() {

				@Override
				public void competiteurRemoved(ICompetiteur competiteur) {
					//TODO
				}

				@Override
				public void competiteurChange(ICompetiteur competiteur) {
					getTable().refresh();
				} 

				@Override
				public void competiteurAdded(ICompetiteur competiteur) {
					//TODO
				}
			};
		}
		return competiteurListener;
	}

//	/**
//	 * @return the scrollPane
//	 */
//	private ScrollPane getScrollPane() {
//		if(scrollPane == null) {
//			scrollPane =new ScrollPane(getTable());
//		}
//		return scrollPane;
//	}

	/**
	 * @return the table
	 */
	@SuppressWarnings("unchecked")
	private TableView<ICompetiteur> getTable() {
		if(table == null) {
			table = new TableView<ICompetiteur>();
			table.getColumns().addAll(getStartOrderColumn(),getInfoColumn(),getBtnArrivingColumn());
			ObservableList<ICompetiteur> data = FXCollections.observableArrayList(
					CompetiteurControler.getCompetiteurs());
			table.setItems(data);
		}
		return table;
	}
	
	/**
	 * @return the nameColumn
	 */
	private TableColumn<ICompetiteur, Label> getStartOrderColumn() {
		if(startOrderColumn == null) {
			startOrderColumn = new TableColumn<ICompetiteur,Label>("Ordre de départ");
//			nameColumn.setEditable(true);
			startOrderColumn.setCellValueFactory(new CallbackCompetiteurStartOrder());
//			nameColumn.setEditable(true);
//			nameColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, String>, TableCell<ICompetiteur, String>>() {
//				
//				public TableCell<ICompetiteur, String> call(TableColumn<ICompetiteur, String> col) {
//					return new EditingNameCell();
//				}
//			});
		}
		return startOrderColumn;
	}

	/**²
	 * @return the categoryColumn
	 */
	private TableColumn<ICompetiteur, GridPane> getInfoColumn() {
		if(infoColumn == null) {
			infoColumn = new TableColumn<ICompetiteur,GridPane>("Information");
			infoColumn.setCellValueFactory(new CallbackCompetiteurInfo());
//			categoryColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,String>("category"));
//			categoryColumn.setEditable(true);
//			categoryColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, String>, TableCell<ICompetiteur, String>>() {
//				
//				public TableCell<ICompetiteur, String> call(TableColumn<ICompetiteur, String> col) {
//					return new EditingCategoryCell();
//				}
//			});
		}
		return infoColumn;
	}

	/**
	 * @return the startOrderColumn
	 */
	private TableColumn<ICompetiteur, Button> getBtnArrivingColumn() {
		if(btnArrivingColumn == null) {
			btnArrivingColumn = new TableColumn<ICompetiteur,Button>("Ordre de départ");
			btnArrivingColumn.setCellValueFactory(new CallbackBtnArriving());

		}
		return btnArrivingColumn;
	}

}
