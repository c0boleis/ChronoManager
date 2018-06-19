package fr.chrono.ihm.fields;

import fr.chrono.controlers.CompetiteurControler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class StartOrderField extends TextField{

	public StartOrderField() {
		super();
		this.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	setText(newValue.replaceAll("[^\\d]", ""));
		        	return;
		        }
		        int intValue = Integer.valueOf(newValue);
		        if(intValue>CompetiteurControler.getCompetiteurs().length) {
		        	intValue = CompetiteurControler.getCompetiteurs().length;
		        }
		        setText(String.valueOf(intValue));
		    }
		});
	}

	public void setStartOrder(int value) {
		setText(String.valueOf(value));
	}

	public int getStartOrder() {
		return Integer.valueOf(getText());
	}
}
