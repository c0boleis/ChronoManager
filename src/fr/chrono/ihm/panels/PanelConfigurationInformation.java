package fr.chrono.ihm.panels;

import fr.chrono.controlers.ConfigurationControler;
import fr.chrono.model.interfaces.IAuxiliare;
import javafx.scene.layout.GridPane;

public class PanelConfigurationInformation extends GridPane{
	
	public PanelConfigurationInformation() {
		init();
	}
	
	private void init() {
		IAuxiliare[] auxiliares = ConfigurationControler.getAuxiliares();
		int columnIndex = 0;
		for(IAuxiliare auxiliare : auxiliares) {
			add(new PanelAuxiliaireStatus(auxiliare), columnIndex, 0);
			columnIndex++;
		}
	}

}
