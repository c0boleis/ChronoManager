package fr.chrono.ihm.panels;

import fr.chrono.controlers.TimeControler;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PanelCompetiteurRun extends GridPane{
	
	private ICompetiteur competiteur;
	
	private PanelCompetiteurInfo panelCompetiteurInfo;
	
	private Button buttonFinish;
	
	public PanelCompetiteurRun(ICompetiteur competiteurIn) {
		super();
		this.competiteur = competiteurIn;
		this.add(getPanelCompetiteurInfo(),0,0);
		this.add(getButtonFinish(),1,0);
	}

	private Button getButtonFinish() {
		if(buttonFinish == null) {
			buttonFinish = new Button("Arrivé");
			buttonFinish.setMaxHeight(Integer.MAX_VALUE);
			buttonFinish.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					competiteur.setArrivalTime(TimeControler.getCurrentTime());
					getPanelCompetiteurInfo().refreshText();
				}
			});
		}
		return buttonFinish;
	}

	private PanelCompetiteurInfo getPanelCompetiteurInfo() {
		if(panelCompetiteurInfo == null) {
			panelCompetiteurInfo = new PanelCompetiteurInfo(this.competiteur);
		}
		return panelCompetiteurInfo;
	}

}
