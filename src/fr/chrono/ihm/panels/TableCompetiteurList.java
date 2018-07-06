package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.comparators.CompetiteurComparatorByStartOrder;
import fr.chrono.controlers.listeners.CompetiteurListener;
import fr.chrono.ihm.panels.cells.EditingCategoryCell;
import fr.chrono.ihm.panels.cells.EditingNameCell;
import fr.chrono.ihm.panels.cells.EditingStartOrderCell;
import fr.chrono.ihm.panels.cells.EditingStartTimeCell;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TableCompetiteurList extends TableView<ICompetiteur>{

	private TableColumn<ICompetiteur,String> nameColumn;

	private TableColumn<ICompetiteur,String> categoryColumn;

	private TableColumn<ICompetiteur,Integer> startOrderColumn;

	private TableColumn<ICompetiteur,Long> startTimeColumn;

	private TableColumn<ICompetiteur,Long> deltaTimeColumn;
	
	private TableColumn<ICompetiteur,Long> arrivalTimeColumn;

	private TableColumn<ICompetiteur,Long> runTimeColumn;

	private ObservableList<ICompetiteur> data;

	private CompetiteurListener competiteurListener;

//	private Callback<TableColumn<ICompetiteur, String>, TableCell<ICompetiteur, String>> cellFactory;
	//        = (TableColumn<ICompetiteur, String> p) -> new EditingCell();

	@SuppressWarnings("unchecked")
	public TableCompetiteurList() {
		this.getColumns().addAll(
				getNameColumn(),
				getCategoryColumn(),
				getStartOrderColumn(),
				getStartTimeColumn(),
				getArrivalTimeColumn(),
				getDeltaTimeColumn(),
				getRunTimeColumn());
		data = FXCollections.observableArrayList(
				CompetiteurControler.getCompetiteurs());
		this.setEditable(true);
		this.setItems(data);
		CompetiteurControler.addCompetiteurListener(getCompetiteurListener());

	}

	/**
	 * @return the nameColumn
	 */
	private TableColumn<ICompetiteur, String> getNameColumn() {
		if(nameColumn == null) {
			nameColumn = new TableColumn<ICompetiteur,String>("Name");
			nameColumn.setEditable(true);
			nameColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,String>("name"));
			nameColumn.setEditable(true);
			nameColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, String>, TableCell<ICompetiteur, String>>() {
				
				public TableCell<ICompetiteur, String> call(TableColumn<ICompetiteur, String> col) {
					return new EditingNameCell();
				}
			});
		}
		return nameColumn;
	}

	/**²
	 * @return the categoryColumn
	 */
	private TableColumn<ICompetiteur, String> getCategoryColumn() {
		if(categoryColumn == null) {
			categoryColumn = new TableColumn<ICompetiteur,String>("Categorie");
			categoryColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,String>("category"));
			categoryColumn.setEditable(true);
			categoryColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, String>, TableCell<ICompetiteur, String>>() {
				
				public TableCell<ICompetiteur, String> call(TableColumn<ICompetiteur, String> col) {
					return new EditingCategoryCell();
				}
			});
		}
		return categoryColumn;
	}

	/**
	 * @return the startOrderColumn
	 */
	private TableColumn<ICompetiteur, Integer> getStartOrderColumn() {
		if(startOrderColumn == null) {
			startOrderColumn = new TableColumn<ICompetiteur,Integer>("Ordre de départ");
			startOrderColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,Integer>("startOrder"));
			startOrderColumn.setEditable(true);
			startOrderColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, Integer>, TableCell<ICompetiteur, Integer>>() {
				
				public TableCell<ICompetiteur, Integer> call(TableColumn<ICompetiteur, Integer> col) {
					return new EditingStartOrderCell();
				}
			});
		}
		return startOrderColumn;
	}

	/**
	 * @return the startTimeColumn
	 */
	private TableColumn<ICompetiteur, Long> getStartTimeColumn() {
		if(startTimeColumn == null) {
			startTimeColumn= new TableColumn<ICompetiteur,Long>("Temps de départ");
			startTimeColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,Long>("startTime"));
			startTimeColumn.setEditable(true);
			startTimeColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, Long>, TableCell<ICompetiteur, Long>>() {
				@Override
				public TableCell<ICompetiteur, Long> call(TableColumn<ICompetiteur, Long> col) {
					return new EditingStartTimeCell();
				}
			});
		}
		return startTimeColumn;
	}

	private CompetiteurListener getCompetiteurListener() {
		if(competiteurListener == null) {
			competiteurListener = new CompetiteurListener() {

				@Override
				public void competiteurRemoved(ICompetiteur competiteur) {
					data.remove(competiteur);
				}

				@Override
				public void competiteurChange(ICompetiteur competiteur) {
					data.sort(new CompetiteurComparatorByStartOrder());
					refresh();
				} 

				@Override
				public void competiteurAdded(ICompetiteur competiteur) {
					data.add(competiteur);
				}
			};
		}
		return competiteurListener;
	}

	private TableColumn<ICompetiteur,Long> getDeltaTimeColumn() {
		if(deltaTimeColumn == null) {
			deltaTimeColumn= new TableColumn<ICompetiteur,Long>("Temps de synchro.");
			deltaTimeColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,Long>("deltaTime"));
			deltaTimeColumn.setEditable(true);
			deltaTimeColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, Long>, TableCell<ICompetiteur, Long>>() {
				@Override
				public TableCell<ICompetiteur, Long> call(TableColumn<ICompetiteur, Long> col) {
					return new EditingStartTimeCell();
				}
			});
		}
		return deltaTimeColumn;
	}

	private TableColumn<ICompetiteur,Long> getRunTimeColumn() {
		if(runTimeColumn == null) {
			runTimeColumn= new TableColumn<ICompetiteur,Long>("Temps");
			runTimeColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,Long>("runTime"));
			runTimeColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, Long>, TableCell<ICompetiteur, Long>>() {
				@Override
				public TableCell<ICompetiteur, Long> call(TableColumn<ICompetiteur, Long> col) {
					return new EditingStartTimeCell();
				}
			});
		}
		return runTimeColumn;
	}

	public TableColumn<ICompetiteur,Long> getArrivalTimeColumn() {
		if(arrivalTimeColumn == null) {
			arrivalTimeColumn= new TableColumn<ICompetiteur,Long>("Temps d'arrivée");
			arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,Long>("arrivalTime"));
			arrivalTimeColumn.setCellFactory(new Callback<TableColumn<ICompetiteur, Long>, TableCell<ICompetiteur, Long>>() {
				@Override
				public TableCell<ICompetiteur, Long> call(TableColumn<ICompetiteur, Long> col) {
					return new EditingStartTimeCell();
				}
			});
		}
		return arrivalTimeColumn;
	}

}
