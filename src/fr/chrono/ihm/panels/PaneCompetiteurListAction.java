package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.RunControler;
import fr.chrono.ihm.dialogs.ExceptionDialog;
import fr.chrono.ihm.fields.CategoryField;
import fr.chrono.ihm.fields.NameField;
import fr.chrono.ihm.fields.TimeField;
import fr.chrono.model.exceptions.ModelFormatException;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class PaneCompetiteurListAction extends BorderPane{

	/*
	 * add competiteur 
	 */
	private Label labelName;

	private NameField textFieldName;

	private Label labelCategory;

	private CategoryField textFieldCategory;

	private Button buttonAddCompetiteur;

	/*
	 * set start time
	 */
	private Label labelStartTime;

	private TimeField fieldStartTime;

	private Label labelDeltaCompetiteur;

	private TimeField fieldDeltaCompetiteur;
	
	private Label labelDeltaCategory;

	private TimeField fieldDeltaCategory;

	private Button buttonInitStartTime;

	private Button buttonInitStartOrder;
	
	/*
	 * set start run
	 */
	
	private Button buttonStartRun;

	public PaneCompetiteurListAction() {
		GridPane gridPane = new GridPane();
		gridPane.add(getLabelName(), 0, 0);
		gridPane.add(getTextFieldName(), 1, 0);
		gridPane.add(getLabelCategory(), 2, 0);
		gridPane.add(getTextFieldCategory(), 3, 0);
		gridPane.add(getButtonAddCompetiteur(), 4, 0);

		gridPane.add(getLabelStartTime(), 0, 1);
		gridPane.add(getFieldStartTime(), 1, 1);
		gridPane.add(getButtonInitStartTime(), 2, 1);
		GridPane.setRowSpan(getButtonInitStartTime(), 3);
		gridPane.add(getButtonInitStartOrder(), 3, 1);
		GridPane.setRowSpan(getButtonInitStartOrder(), 3);
		
		gridPane.add(getLabelDelta(), 0, 2);
		gridPane.add(getFieldDelta(), 1, 2);
		gridPane.add(getLabelDeltaCategory(), 0, 3);
		gridPane.add(getFieldDeltaCategory(), 1, 3);
		
		gridPane.add(getButtonStartRun(), 5, 0);
		GridPane.setRowSpan(getButtonStartRun(), GridPane.REMAINING);
		
		
		GridPane.setFillHeight(getButtonInitStartTime(), true);
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setMinWidth(80);
		ColumnConstraints col2 = new ColumnConstraints();
		col2.setMinWidth(80);
		col2.setMaxWidth(150);
		ColumnConstraints col3 = new ColumnConstraints();
//		col3.setPercentWidth(25);
		ColumnConstraints col4 = new ColumnConstraints();
//		col2.setPercentWidth(50);
		ColumnConstraints col5 = new ColumnConstraints();
//		col3.setPercentWidth(25);
		gridPane.getColumnConstraints().addAll(col1,col2,col3,col4,col5);
		this.setCenter(gridPane);
	}

	/**
	 * @return the textFieldName
	 */
	private NameField getTextFieldName() {
		if(textFieldName == null) {
			textFieldName = new NameField();
			textFieldName.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, 
						String newValue) {
					checkAddCompetiteurAvailable();
				}
			});
		}
		return textFieldName;
	}

	/**
	 * @return the textFieldCategory
	 */
	private CategoryField getTextFieldCategory() {
		if(textFieldCategory == null) {
			textFieldCategory = new CategoryField();
		}
		return textFieldCategory;
	}

	private Button getButtonAddCompetiteur() {
		if(buttonAddCompetiteur == null) {
			buttonAddCompetiteur = new Button();
			buttonAddCompetiteur.setText("Ajouter");
			buttonAddCompetiteur.setOnAction(new EventHandler<ActionEvent>() {

				@Override public void handle(ActionEvent e) {
					addCompetiteur();
				}

			});
		}
		return buttonAddCompetiteur;
	}

	/**
	 * @return the labelCategory
	 */
	private Label getLabelCategory() {
		if(labelCategory == null) {
			labelCategory = new Label("Categorie:");
		}
		return labelCategory;
	}

	private Label getLabelName() {
		if(labelName == null) {
			labelName = new Label("Nom:");
		}
		return labelName;
	}

	private void addCompetiteur() {
		String name = getTextFieldName().getText();
		String category = getTextFieldCategory().getSelectedCategory();
		ICompetiteur competiteur = null;
		try {
			competiteur = CompetiteurControler.createCompetiteur(name, category);
		}catch( ModelFormatException e) {
			e.printStackTrace();
			ExceptionDialog alert = new ExceptionDialog(e);
			alert.showAndWait();
			return;
		}
		
		if(competiteur == null) {
			System.err.println("competiteur non ajouté");
		}else {
			System.out.println("competiteur ajouté");
			getTextFieldName().setText("");
			getTextFieldCategory().getSelectionModel().select(0);
		}

	}

	/**
	 * @return the labelStartTime
	 */
	private Label getLabelStartTime() {
		if(labelStartTime == null) {
			labelStartTime = new Label("Heure de départ:");
		}
		return labelStartTime;
	}

	/**
	 * @return the textStartTime
	 */
	private TimeField getFieldStartTime() {
		if(fieldStartTime == null) {
			fieldStartTime = new TimeField();
		}
		return fieldStartTime;
	}

	/**
	 * @return the labelDelta
	 */
	private Label getLabelDelta() {
		if(labelDeltaCompetiteur == null) {
			labelDeltaCompetiteur = new Label("Ecart competiteurs:");
		}
		return labelDeltaCompetiteur;
	}

	/**
	 * @return the textFieldDelta
	 */
	private TimeField getFieldDelta() {
		if(fieldDeltaCompetiteur == null) {
			fieldDeltaCompetiteur = new TimeField();
		}
		return fieldDeltaCompetiteur;
	}

	/**
	 * @return the buttonSetStartTime
	 */
	private Button getButtonInitStartTime() {
		if(buttonInitStartTime == null) {
			buttonInitStartTime = new Button();
			buttonInitStartTime.setText("Init heure\nde départ");
			buttonInitStartTime.setMaxHeight(Integer.MAX_VALUE);
			buttonInitStartTime.setMaxWidth(Integer.MAX_VALUE);
			buttonInitStartTime.setOnAction(new EventHandler<ActionEvent>() {

				@Override public void handle(ActionEvent e) {
					CompetiteurControler.initStartTime(
							getFieldStartTime().getTime(),
							getFieldDelta().getTime(),
							getFieldDeltaCategory().getTime());
				}

			});
		}
		return buttonInitStartTime;
	}

	private Button getButtonInitStartOrder() {
		if(buttonInitStartOrder == null) {
			buttonInitStartOrder = new Button();
			buttonInitStartOrder.setText("Init Ordre\nde départ");
			buttonInitStartOrder.setMaxHeight(Integer.MAX_VALUE);
			buttonInitStartOrder.setMaxWidth(Integer.MAX_VALUE);
			buttonInitStartOrder.setOnAction(new EventHandler<ActionEvent>() {

				@Override public void handle(ActionEvent e) {
					CompetiteurControler.initStartOrder();
				}

			});
		}
		return buttonInitStartOrder;
	}
	
	public void checkAddCompetiteurAvailable() {
		if(getTextFieldName().nameIsRight()) {
			getButtonAddCompetiteur().setDisable(false);
		}else {
			getButtonAddCompetiteur().setDisable(true);
		}
	}

	/**
	 * @return the labelDeltaCategory
	 */
	private Label getLabelDeltaCategory() {
		if(labelDeltaCategory == null) {
			labelDeltaCategory = new Label("Ecart categories:");
		}
		return labelDeltaCategory;
	}

	/**
	 * @return the fieldDeltaCategory
	 */
	private TimeField getFieldDeltaCategory() {
		if(fieldDeltaCategory == null) {
			fieldDeltaCategory = new TimeField();
		}
		return fieldDeltaCategory;
	}

	public Button getButtonStartRun() {
		if(buttonStartRun == null) {
			buttonStartRun = new Button();
			buttonStartRun.setText("Démarer\nla\ncourse");
			buttonStartRun.setMaxHeight(Integer.MAX_VALUE);
			buttonStartRun.setMaxWidth(Integer.MAX_VALUE);
			buttonStartRun.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					RunControler.startRun();
				}
			});
		}
		return buttonStartRun;
	}

}
