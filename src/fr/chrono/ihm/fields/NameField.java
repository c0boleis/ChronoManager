package fr.chrono.ihm.fields;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.model.exceptions.ModelFormatException;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NameField extends TextField{
	
	
	private String initStyle = "";

	public NameField() {
		//		System.out.
		this.initStyle = getStyle();
		this.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) {
				Pattern pattern = Pattern.compile(ICompetiteur.NAME_FORBIDDEN_CHAR);
				Matcher match = pattern.matcher(newValue);
				if(match.find()) {
					setText(newValue.replaceAll(ICompetiteur.NAME_FORBIDDEN_CHAR, ""));
				}
				try {
					CompetiteurControler.formatName(newValue);
					setStyle(initStyle);
				}catch(ModelFormatException e) {
					setStyle("-fx-border-color: red");
				}
			}
		});
	}
	
	public boolean nameIsRight() {
		try {
			if(CompetiteurControler.checkName(this.getText())==null){
				setStyle("-fx-border-color: red");
				return false;
			}else {
				setStyle(initStyle);
				return true;
			}
			
		}catch(ModelFormatException e) {
			setStyle("-fx-border-color: red");
			return false;
		}
	}
	
	

}
