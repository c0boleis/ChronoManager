package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.TimeControler;
import fr.chrono.controlers.listeners.CompetiteurListener;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PanelCompetiteurRun extends GridPane{

	private ICompetiteur competiteur;

	private PanelCompetiteurInfo panelCompetiteurInfo;

	private Button buttonFinish;

	private Label labelStartOrder;

	private Background backgroundCompetiteurStatus;

	private BackgroundFill backgroundFillStatus;

	private CompetiteurListener competiteurListener;

	public PanelCompetiteurRun(ICompetiteur competiteurIn) {
		super();
		this.competiteur = competiteurIn;
		this.add(getLabelStartOrder(),0,0);
		this.add(getPanelCompetiteurInfo(),2,0);
		this.add(getButtonFinish(),3,0);
	}

	public Button getButtonFinish() {
		if(buttonFinish == null) {
			buttonFinish = new Button("Arrivé");
			buttonFinish.setMaxHeight(Integer.MAX_VALUE);
			buttonFinish.setMaxWidth(Integer.MAX_VALUE);
			buttonFinish.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					applyArriving();
				}
			});
		}
		return buttonFinish;
	}

	public PanelCompetiteurInfo getPanelCompetiteurInfo() {
		if(panelCompetiteurInfo == null) {
			panelCompetiteurInfo = new PanelCompetiteurInfo(this.competiteur);
		}
		return panelCompetiteurInfo;
	}

	public Label getLabelStartOrder() {
		if(labelStartOrder == null) {
			labelStartOrder = new Label();
			labelStartOrder.setMaxHeight(Integer.MAX_VALUE);
			labelStartOrder.setMaxWidth(Integer.MAX_VALUE);
			//			labelStartOrder.setContentDisplay(ContentDisplay.TOP);
			labelStartOrder.setAlignment(Pos.BASELINE_CENTER);
			labelStartOrder.setFont(new Font("Arial",80));
			labelStartOrder.setText(String.valueOf(competiteur.getStartOrder()));
			labelStartOrder.setBackground(getBackgroundCompetiteurStatus());
			CompetiteurControler.addCompetiteurListener(getCompetiteurListener());
		}
		return labelStartOrder;
	}

	public void applyArriving() {
		CompetiteurControler.setArrivalTime(competiteur,TimeControler.getCurrentTime());
		getPanelCompetiteurInfo().refreshText();
	}


	private CompetiteurListener getCompetiteurListener() {
		if(competiteurListener == null) {
			competiteurListener = new CompetiteurListener() {

				@Override
				public void competiteurRemoved(ICompetiteur competiteur) {
					// TODO Auto-generated method stub

				}

				@Override
				public void competiteurChange(ICompetiteur competiteur) {
					if(competiteur.equals(PanelCompetiteurRun.this.competiteur)) {
						labelStartOrder.setBackground(getBackgroundCompetiteurStatus());
					}
				}

				@Override
				public void competiteurAdded(ICompetiteur competiteur) {
					// TODO Auto-generated method stub

				}
			};
		}
		return competiteurListener;
	}

	private Background getBackgroundCompetiteurStatus() {
		backgroundCompetiteurStatus = null;
		if(backgroundCompetiteurStatus == null) {
			backgroundCompetiteurStatus = new Background(getBackgroundFillStatus());
		}
		return backgroundCompetiteurStatus;
	}

	private BackgroundFill getBackgroundFillStatus() {
		backgroundFillStatus = null;
		if(backgroundFillStatus == null) {
			Color col = Color.CORNFLOWERBLUE;
			switch (competiteur.getRunStatus()) {
			case notSart:
				col = Color.CORNFLOWERBLUE;
				break;
			case start:
				col = Color.ORANGE;
				break;
			case finish:
				col = Color.GREENYELLOW;
				break;
			default:
				break;
			}
			backgroundFillStatus = new BackgroundFill(col,
					CornerRadii.EMPTY, Insets.EMPTY);
		}
		return backgroundFillStatus;
	}

}
