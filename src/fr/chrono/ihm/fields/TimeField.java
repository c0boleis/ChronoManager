package fr.chrono.ihm.fields;

import fr.chrono.controlers.TimeControler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TimeField extends GridPane{

	private TextField textFieldHour;

	private TextField textFieldMinute;

	private TextField textFieldSecond;

	private TextField textFieldMilliSecond;
	
	public TimeField() {
		this.add(getTextFieldHour(), 1, 1);
		Label label1 = new Label(":");
		this.add(label1, 2, 1);
		this.add(getTextFieldMinute(), 3, 1);
		Label label2 = new Label(":");
		this.add(label2, 4, 1);
		this.add(getTextFieldSecond(), 5, 1);
		Label label3 = new Label(",");
		this.add(label3, 6, 1);
		this.add(getTextFieldMilliSecond(), 7, 1);
		initText(0);
	}
	
	public void initText(long time) {
		String[] tab = TimeControler.parseTimeToStringTab(time);
		getTextFieldHour().setText(tab[0]);
		getTextFieldMinute().setText(tab[1]);
		getTextFieldSecond().setText(tab[2]);
		getTextFieldMilliSecond().setText(tab[3]);
	}


	/**
	 * @return the textFieldHour
	 */
	private TextField getTextFieldHour() {
		if(textFieldHour == null) {
			textFieldHour = new TextField();
			textFieldHour.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	textFieldHour.setText(newValue.replaceAll("[^\\d]", ""));
			        	return;
			        }
			        if(newValue.length()>2) {
			        	int l = newValue.length();
			        	textFieldHour.setText(newValue.substring(l-2, l));
			        }
			        if(newValue.length()==0) {
			        	textFieldHour.setText("00");
			        }
			        if(newValue.length()==1) {
			        	textFieldHour.setText("0"+newValue);
			        }
			    }
			});
		}
		return textFieldHour;
	}

	/**
	 * @return the textFieldMinute
	 */
	private TextField getTextFieldMinute() {
		if(textFieldMinute == null) {
			textFieldMinute = new TextField();
			textFieldMinute.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	textFieldMinute.setText(newValue.replaceAll("[^\\d]", ""));
			        	return;
			        }
			        if(newValue.length()>2) {
			        	int l = newValue.length();
			        	String text = newValue.substring(l-2, l);
			        	int firstNumber = Integer.parseInt(String.valueOf(text.charAt(0)));
			        	if(firstNumber>5) {
			        		firstNumber=5;
			        	}
			        	text = String.valueOf(firstNumber)+text.charAt(1);
			        	textFieldMinute.setText(text);
			        }
			        if(newValue.length()==0) {
			        	textFieldMinute.setText("00");
			        }
			        if(newValue.length()==1) {
			        	textFieldMinute.setText("0"+newValue);
			        }
			    }
			});
		}
		return textFieldMinute;
	}

	/**
	 * @return the textFieldSecond
	 */
	private TextField getTextFieldSecond() {
		if(textFieldSecond == null) {
			textFieldSecond = new TextField();
			textFieldSecond.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	textFieldSecond.setText(newValue.replaceAll("[^\\d]", ""));
			        	return;
			        }
			        if(newValue.length()>2) {
			        	int l = newValue.length();
			        	String text = newValue.substring(l-2, l);
			        	int firstNumber = Integer.parseInt(String.valueOf(text.charAt(0)));
			        	if(firstNumber>5) {
			        		firstNumber=5;
			        	}
			        	text = String.valueOf(firstNumber)+text.charAt(1);
			        	textFieldSecond.setText(text);
			        }
			        if(newValue.length()==0) {
			        	textFieldSecond.setText("00");
			        }
			        if(newValue.length()==1) {
			        	textFieldSecond.setText("0"+newValue);
			        }
			    }
			});
		}
		return textFieldSecond;
	}

	/**
	 * @return the textFieldMilliSecond
	 */
	private TextField getTextFieldMilliSecond() {
		if(textFieldMilliSecond == null) {
			textFieldMilliSecond = new TextField();
			textFieldMilliSecond.textProperty().addListener(new ChangeListener<String>() {
			    @Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, 
			        String newValue) {
			        if (!newValue.matches("\\d*")) {
			        	textFieldMilliSecond.setText(newValue.replaceAll("[^\\d]", ""));
			        	return;
			        }
			        if(newValue.length()>3) {
			        	int l = newValue.length();
			        	textFieldMilliSecond.setText(newValue.substring(l-3, l));
			        }
			        if(newValue.length()==0) {
			        	textFieldMilliSecond.setText("000");
			        }
			        if(newValue.length()==1) {
			        	textFieldMilliSecond.setText("00"+newValue);
			        }
			        if(newValue.length()==2) {
			        	textFieldMilliSecond.setText("0"+newValue);
			        }
			    }
			});
		}
		return textFieldMilliSecond;
	}
	
	public String getTimeToString() {
		return getTextFieldHour().getText()+":"
				+ getTextFieldMinute().getText()+":"
				+ getTextFieldSecond().getText()+","
				+ getTextFieldMilliSecond().getText();
	}

	public long getTime() {
		return TimeControler.parseTimeTolong(getTimeToString());
	}
}
