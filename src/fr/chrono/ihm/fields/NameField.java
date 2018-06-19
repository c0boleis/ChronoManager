package fr.chrono.ihm.fields;

import fr.chrono.controlers.CompetiteurControler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NameField extends TextField{

	public NameField() {
		this.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        //TODO
		    }
		});
	}
}
