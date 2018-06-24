package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TableRunArriving extends BorderPane{
	
	private PanelRunInfo panelRunInfo;

	private TableView<PanelCompetiteurRun> table;

	private TableColumn<PanelCompetiteurRun,Label> startOrderColumn;

	private TableColumn<PanelCompetiteurRun,GridPane> infoColumn;

	private TableColumn<PanelCompetiteurRun,Button> btnArrivingColumn;

	public TableRunArriving() {
		super();
		this.setCenter(getTable());
		this.setTop(getPanelRunInfo());
	}

	/**
	 * @return the table
	 */
	@SuppressWarnings("unchecked")
	private TableView<PanelCompetiteurRun> getTable() {
		if(table == null) {
			table = new TableView<PanelCompetiteurRun>();
			table.getColumns().addAll(getStartOrderColumn(),getInfoColumn(),getBtnArrivingColumn());
			ObservableList<PanelCompetiteurRun> data = FXCollections.observableArrayList(
					CompetiteurControler.getPanelCompetiteurRuns());
			table.setItems(data);
			table.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					if(event.getCode()==KeyCode.ENTER) {
						PanelCompetiteurRun panelCompetiteurRun = 
								table.getSelectionModel().getSelectedItem();
						if(panelCompetiteurRun != null) {
							panelCompetiteurRun.applyArriving();
						}
					}
				}
			});
			table.getColumns().get(2).setMinWidth(200);
		}
		return table;
	}

	/**
	 * @return the nameColumn
	 */
	private TableColumn<PanelCompetiteurRun, Label> getStartOrderColumn() {
		if(startOrderColumn == null) {
			startOrderColumn = new TableColumn<PanelCompetiteurRun,Label>("Ordre de départ");
			startOrderColumn.setCellValueFactory(new PropertyValueFactory<PanelCompetiteurRun, Label>("labelStartOrder"));
		}
		return startOrderColumn;
	}

	/**²
	 * @return the categoryColumn
	 */
	private TableColumn<PanelCompetiteurRun, GridPane> getInfoColumn() {
		if(infoColumn == null) {
			infoColumn = new TableColumn<PanelCompetiteurRun,GridPane>("Information");
			infoColumn.setCellValueFactory(new PropertyValueFactory<PanelCompetiteurRun, GridPane>("panelCompetiteurInfo"));
		}
		return infoColumn;
	}

	/**
	 * @return the startOrderColumn
	 */
	private TableColumn<PanelCompetiteurRun, Button> getBtnArrivingColumn() {
		if(btnArrivingColumn == null) {
			btnArrivingColumn = new TableColumn<PanelCompetiteurRun,Button>("Ordre de départ");
			btnArrivingColumn.setCellValueFactory(new PropertyValueFactory<PanelCompetiteurRun, Button>("buttonFinish"));
		}
		return btnArrivingColumn;
	}

	public PanelRunInfo getPanelRunInfo() {
		if(panelRunInfo == null) {
			panelRunInfo = new PanelRunInfo();
		}
		return panelRunInfo;
	}

	public void refresh() {
		getTable().refresh();
	}

}
