package fr.chrono.ihm.panels.cells;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.listeners.FieldListener;
import fr.chrono.ihm.fields.CategoryField;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.scene.control.TableCell;

public class EditingCategoryCell extends TableCell<ICompetiteur, String> {

	private CategoryField categoryField;

	public EditingCategoryCell() {
	}

	@Override
	public void startEdit() {
		if (!isEmpty()) {
			super.startEdit();
			setText(null);
			getCategoryField().setSelectedCategory(getItem());
			setGraphic(getCategoryField());
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
				getCategoryField().setSelectedCategory(getItem());
				setText(null);
				setGraphic(getCategoryField());
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
		if(!CompetiteurControler.setCategory(competiteur, newValue)) {
			cancelEdit();
			return;
		}
		setText(newValue);
		setGraphic(null);
	}

	private CategoryField getCategoryField() {
		if(categoryField == null) {
			categoryField = new CategoryField(getItem());
			categoryField.addFieldListener(new FieldListener<String>() {
				
				@Override
				public void valueChanged(String newValue) {
					commitEdit(newValue);
				}
			});
		}
		return categoryField;
	}
}