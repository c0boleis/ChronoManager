package fr.chrono.ihm.panels;

import fr.chrono.model.interfaces.IAuxiliare;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class PanelAuxiliaireStatus extends GridPane{

	private Label labelName;
	
	private IAuxiliare auxiliare;
	
	public PanelAuxiliaireStatus(IAuxiliare auxiliare) {
		super();
		this.auxiliare = auxiliare;
		init();
	}

	public void init() {

		this.add(getLabelName(), 1, 0);

	}

	/**
	 * @return the labelName
	 * 
	 * 
	 */
	private Label getLabelName() {
		if(labelName == null) {
			labelName = new Label();
			labelName.setText(this.auxiliare.getDomainType().toString());
		}
		return labelName;
	}
}
