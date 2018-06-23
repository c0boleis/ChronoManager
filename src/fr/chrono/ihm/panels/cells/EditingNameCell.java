package fr.chrono.ihm.panels.cells;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.ihm.dialogs.ExceptionDialog;
import fr.chrono.ihm.fields.NameField;
import fr.chrono.model.exceptions.ModelFormatException;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditingNameCell extends TableCell<ICompetiteur, String> {

	private NameField nameField;

	public EditingNameCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			setText(null);
			getNameField().setText(getItem());
			setGraphic(getNameField());
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(getItem());
		setGraphic(null);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				getNameField().setText(getItem());
				setText(null);
				setGraphic(getNameField());
			} else {
				setText(getItem());
				setGraphic(null);
			}
		}
	}

	/* (non-Javadoc)
	 * @see javafx.scene.control.TableCell#commitEdit(java.lang.Object)
	 */
	@Override
	public void commitEdit(String newValue) {
		ICompetiteur competiteur = (ICompetiteur) getTableRow().getItem();
		try {
			if(!CompetiteurControler.setName(competiteur, newValue)) {
				cancelEdit();
				return;
			}
		}catch(ModelFormatException e) {
			ExceptionDialog dialog = new ExceptionDialog(e);
			dialog.showAndWait();
			cancelEdit();
			return;
		}

		setText(newValue);
		setGraphic(null);
	}

	private NameField getNameField() {
		if(nameField == null) {
			nameField = new NameField();
			nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if(ke.getCode()== KeyCode.ENTER) {
						commitEdit(nameField.getText());
					}
				}
			});
		}
		return nameField;
	}
}