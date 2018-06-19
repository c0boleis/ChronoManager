package fr.chrono.ihm.panels.cells;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.TimeControler;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class EditingStartTimeCell extends TableCell<ICompetiteur, Long> {

	private GridPane gridPaneEdit;

	private TextField textFieldHour;

	private TextField textFieldMinute;

	private TextField textFieldSecond;

	private TextField textFieldMilliSecond;

	public EditingStartTimeCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			setText(null);
			initText(getItem());
			setGraphic(getGridPaneEdit());
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		String text = TimeControler.parseTimeToString((Long) getItem());
		setText(text);
		setGraphic(null);
	}

	@Override
	public void updateItem(Long item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				initText(item);
				setText(null);
				setGraphic(getGridPaneEdit());
			} else {
				setText(getString());
				setGraphic(null);
			}
		}
	}

	private String getString() {
		if(getItem()==null) {
			return "";
		}else {
			String text = TimeControler.parseTimeToString((Long) getItem());
			return text;
		}
	}

	public GridPane getGridPaneEdit() {
		if(gridPaneEdit == null) {
			gridPaneEdit = new GridPane();
			gridPaneEdit.setMinWidth(150);
			gridPaneEdit.add(getTextFieldHour(), 1, 1);
			Label label1 = new Label(":");
			gridPaneEdit.add(label1, 2, 1);
			gridPaneEdit.add(getTextFieldMinute(), 3, 1);
			Label label2 = new Label(":");
			gridPaneEdit.add(label2, 4, 1);
			gridPaneEdit.add(getTextFieldSecond(), 5, 1);
			Label label3 = new Label(",");
			gridPaneEdit.add(label3, 6, 1);
			gridPaneEdit.add(getTextFieldMilliSecond(), 7, 1);
		}
		return gridPaneEdit;
	}

	private void initText(long time) {
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
//			textFieldHour.setMinWidth(20);
			textFieldHour.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if(ke.getCode()== KeyCode.ENTER) {
						long l = TimeControler.parseTimeTolong(getResult());
						commitEdit(l);
					}
				}
			});
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
//			textFieldMinute.setMinWidth(20);
			textFieldMinute.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if(ke.getCode()== KeyCode.ENTER) {
						long l = TimeControler.parseTimeTolong(getResult());
						commitEdit(l);
					}
				}
			});
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
//			textFieldSecond.setMinWidth(20);
			textFieldSecond.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if(ke.getCode()== KeyCode.ENTER) {
						long l = TimeControler.parseTimeTolong(getResult());
						commitEdit(l);
					}
				}
			});
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
//			textFieldMilliSecond.setMinWidth(25);
			textFieldMilliSecond.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if(ke.getCode()== KeyCode.ENTER) {
						long l = TimeControler.parseTimeTolong(getResult());
						commitEdit(l);
					}
				}
			});
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

	/* (non-Javadoc)
	 * @see javafx.scene.control.TableCell#commitEdit(java.lang.Object)
	 */
	@Override
	public void commitEdit(Long newValue) {
		ICompetiteur competiteur = (ICompetiteur) getTableRow().getItem();
		CompetiteurControler.setStartTime(competiteur, newValue);
		String text = TimeControler.parseTimeToString((Long) newValue);
		setText(text);
		setGraphic(null);
	}

	private String getResult() {
		return getTextFieldHour().getText()+":"
				+ getTextFieldMinute().getText()+":"
				+ getTextFieldSecond().getText()+","
				+ getTextFieldMilliSecond().getText();
	}
}