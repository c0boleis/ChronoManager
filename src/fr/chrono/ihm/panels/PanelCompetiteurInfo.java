package fr.chrono.ihm.panels;

import fr.chrono.controlers.TimeControler;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PanelCompetiteurInfo extends GridPane{

	private ICompetiteur competiteur;

	public static final String DISPLAY_NAME = "Name: ";

	public static final String DISPLAY_CATGEORY = "Category: ";

	public static final String DISPLAY_START_TIME = "Heure de départ: ";

	public static final String DISPLAY_ARRIVAL_TIME = "Heure d'arrivée: ";

	public static final String DISPLAY_RUN_TIME = "Temps: ";

	private Label labelName;

	private Label labelCategory;

	private Label labelStartTime;

	private Label labelArrivalTime;

	private Label labelRunTime;
	
	public PanelCompetiteurInfo(ICompetiteur competiteurIn) {
		super();
		this.competiteur = competiteurIn;
		init();
	}

	public void init() {

		this.add(getLabelName(), 1, 0);
		this.add(getLabelCategory(), 1, 1);
		this.add(getLabelStartTime(), 1, 2);
		this.add(getLabelArrivalTime(), 1, 3);
		this.add(getLabelRunTime(), 1, 4);


		//		GridPane.setFillHeight(getButtonInitStartTime(), true);
//		ColumnConstraints col1 = new ColumnConstraints();
//		col1.setMinWidth(80);
		//		ColumnConstraints col2 = new ColumnConstraints();
		//		col2.setMinWidth(80);
		//		col2.setMaxWidth(150);
		//		ColumnConstraints col3 = new ColumnConstraints();
		////		col3.setPercentWidth(25);
		//		ColumnConstraints col4 = new ColumnConstraints();
		////		col2.setPercentWidth(50);
		//		ColumnConstraints col5 = new ColumnConstraints();
		////		col3.setPercentWidth(25);
//		this.getColumnConstraints().addAll(col1);//,col2,col3,col4,col5);
		//		this.setCenter(gridPane);
		refreshText();
	}

	public void refreshText() {
		getLabelName().setText(DISPLAY_NAME+this.competiteur.getName());
		getLabelCategory().setText(DISPLAY_CATGEORY+
				this.competiteur.getCategory());
		getLabelStartTime().setText(DISPLAY_START_TIME+
				TimeControler.parseTimeToString(this.competiteur.getStartTime()));
		getLabelArrivalTime().setText(DISPLAY_ARRIVAL_TIME+
				TimeControler.parseTimeToString(this.competiteur.getArrivalTime()));
		getLabelRunTime().setText(DISPLAY_RUN_TIME+
				TimeControler.parseTimeToString(this.competiteur.getRunTime()));
	}

	/**
	 * @return the labelName
	 */
	private Label getLabelName() {
		if(labelName == null) {
			labelName = new Label();
		}
		return labelName;
	}

	/**
	 * @return the labelCategory
	 */
	private Label getLabelCategory() {
		if(labelCategory == null) {
			labelCategory = new Label();
		}
		return labelCategory;
	}

	/**
	 * @return the labelStartTime
	 */
	private Label getLabelStartTime() {
		if(labelStartTime == null) {
			labelStartTime = new Label();
		}
		return labelStartTime;
	}

	/**
	 * @return the labelArrivalTime
	 */
	private Label getLabelArrivalTime() {
		if(labelArrivalTime == null) {
			labelArrivalTime = new Label();
		}
		return labelArrivalTime;
	}

	/**
	 * @return the labelRunTime
	 */
	private Label getLabelRunTime() {
		if(labelRunTime == null) {
			labelRunTime = new Label();
		}
		return labelRunTime;
	}
}
