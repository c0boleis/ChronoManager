package fr.chrono.ihm.panels.cells;

import java.util.Optional;

import fr.chrono.controlers.CategoryControler;
import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.ihm.fields.CategoryField;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextInputDialog;

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
		CompetiteurControler.setCategory(competiteur, newValue);
		setText(newValue);
		setGraphic(null);
	}

	private CategoryField getCategoryField() {
		if(categoryField == null) {
			categoryField = new CategoryField();
			categoryField.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					String text = categoryField.getSelectedCategory();
					if(text.equals(CategoryField.STRING_NEW_CATEGORY)) {
						TextInputDialog dialog = new TextInputDialog();
						dialog.setContentText("Entrez la nouvelle categorie:");
						dialog.setTitle("Nouvelle categorie");
						Optional<String> result = dialog.showAndWait();
						if (result.isPresent()){
							if(CategoryControler.addCategory(result.get())) {
								commitEdit(result.get());
							}
						}
					}else {
						commitEdit(text);
					}
					
				}
			});
		}
		return categoryField;
	}
}