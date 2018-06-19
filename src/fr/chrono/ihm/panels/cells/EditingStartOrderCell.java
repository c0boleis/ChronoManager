package fr.chrono.ihm.panels.cells;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.ihm.fields.StartOrderField;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditingStartOrderCell extends TableCell<ICompetiteur, Integer> {

	private StartOrderField categoryField;

	public EditingStartOrderCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			setText(null);
			getCategoryField().setStartOrder(getItem());
			setGraphic(getCategoryField());
		}
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText(String.valueOf(getItem()));
		setGraphic(null);
	}

	@Override
	public void updateItem(Integer item, boolean empty) {
		super.updateItem(item, empty);

		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				getCategoryField().setStartOrder(item);
				setText(null);
				setGraphic(getCategoryField());
			} else {
				setText(String.valueOf(getItem()));
				setGraphic(null);
			}
		}
	}

	/* (non-Javadoc)
	 * @see javafx.scene.control.TableCell#commitEdit(java.lang.Object)
	 */
	@Override
	public void commitEdit(Integer newValue) {
		ICompetiteur competiteur = (ICompetiteur) getTableRow().getItem();
		CompetiteurControler.setStartOrder(competiteur, newValue);
		setText(String.valueOf(newValue));
		setGraphic(null);
	}

	private StartOrderField getCategoryField() {
		if(categoryField == null) {
			categoryField = new StartOrderField();
			categoryField.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					commitEdit(categoryField.getStartOrder());
				}
			});
			categoryField.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if(ke.getCode()== KeyCode.ENTER) {
						commitEdit(categoryField.getStartOrder());
					}
				}
			});
		}
		return categoryField;
	}
}