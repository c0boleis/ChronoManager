package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.TimeControler;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class PanelCompetiteurRun extends GridPane{
	
	private ICompetiteur competiteur;
	
	private PanelCompetiteurInfo panelCompetiteurInfo;
	
	private Button buttonFinish;
	
	private Label labelStartOrder;
	
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
		}
		return labelStartOrder;
	}
	
	public void applyArriving() {
		CompetiteurControler.setArrivalTime(competiteur,TimeControler.getCurrentTime());
		getPanelCompetiteurInfo().refreshText();
	}

}
