package fr.chrono.ihm;

import java.io.File;
import java.io.IOException;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.RunControler;
import fr.chrono.controlers.listeners.RunListener;
import fr.chrono.ihm.panels.TabCompetiteurList;
import fr.chrono.ihm.panels.TabRun;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application{

	private TableView<ICompetiteur> table;
	
	private TabPane mainTabPane;
	
	private TabCompetiteurList tabCompetiteurList;
	
	private TabRun tabRun;
	
	private MainMenuBar mainMenuBar;
	
	private RunListener runListener;
	
	public MainApplication() {
		super();
		RunControler.addRunListener(getRunListener());
	}
	
	public static void main(String[] args) {
		if(args.length>0) {
			try {
				CompetiteurControler.load(new File(args[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		MainApplication.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(getMainMenuBar());
		borderPane.setCenter(getMainTabPane());
		Scene scene = new Scene(borderPane, 550,400);

		primaryStage.setTitle("ChronoManager");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@SuppressWarnings("unchecked")
	public TableView<ICompetiteur> getTable() {
		if(table == null) {
			table = new TableView<ICompetiteur>();

			TableColumn<ICompetiteur,String> nameColumn = new TableColumn<ICompetiteur,String>("Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,String>("name"));
			TableColumn<ICompetiteur,String> categoryColumn = new TableColumn<ICompetiteur,String>("Category");
			categoryColumn.setCellValueFactory(new PropertyValueFactory<ICompetiteur,String>("category"));


			table.getColumns().addAll(nameColumn,categoryColumn);
			try {
				CompetiteurControler.load(new File("resources/liste_depart.csv"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			final ObservableList<ICompetiteur> data = FXCollections.observableArrayList(
					CompetiteurControler.getCompetiteurs());
			table.setItems(data);
		}
		return table;
	}

	public TabPane getMainTabPane() {
		if(mainTabPane == null) {
			mainTabPane = new TabPane();
			mainTabPane.getTabs().add(getTabCompetiteurList());
		}
		return mainTabPane;
	}

	public TabCompetiteurList getTabCompetiteurList() {
		if(tabCompetiteurList == null) {
			tabCompetiteurList = new TabCompetiteurList();
		}
		return tabCompetiteurList;
	}

	public TabRun getTabRun() {
		if(tabRun == null) {
			tabRun = new TabRun();
		}
		return tabRun;
	}

	public MainMenuBar getMainMenuBar() {
		if(mainMenuBar == null) {
			mainMenuBar = new MainMenuBar();
		}
		return mainMenuBar;
	}

	private RunListener getRunListener() {
		if(runListener == null) {
			runListener = new RunListener() {
				
				@Override
				public void runStoped() {
					mainTabPane.getTabs().remove(getTabRun());
				}
				
				@Override
				public void runStarted() {
					getTabRun().refresh();
					mainTabPane.getTabs().add(getTabRun());
					mainTabPane.getSelectionModel().select(getTabRun());
				}

				@Override
				public void stateChange() {
					// TODO Auto-generated method stub
					
				}
			};
		}
		return runListener;
	}

}
