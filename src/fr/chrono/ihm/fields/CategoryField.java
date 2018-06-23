package fr.chrono.ihm.fields;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.chrono.controlers.CategoryControler;
import fr.chrono.controlers.listeners.CatergoyListener;
import fr.chrono.controlers.listeners.FieldListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;

public class CategoryField extends ComboBox<String>{
	
	public static final String STRING_NEW_CATEGORY = "...";
	
	private CatergoyListener catergoyListener;
	
	private String defaultValue;
	
	private List<FieldListener<String>> listeners;

	public CategoryField() {
		this(null);
	}
	
	public CategoryField(String defaultValueIn) {
		this.defaultValue = defaultValueIn;
		this.listeners = new ArrayList<FieldListener<String>>();
		init();
		CategoryControler.addCatergoyListener(getCatergoyListener());
	}
	
	private void init() {
		ObservableList<String> list = FXCollections.observableArrayList(
				CategoryControler.getCategories());
		list.add(STRING_NEW_CATEGORY);
		this.setItems(list);
		if(defaultValue==null) {
			defaultValue = list.get(0);
		}
		if(list.size()>1) {
			this.setSelectedCategory(defaultValue);
		}
		this.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println(event.getClass().getSimpleName());
				String text = getSelectedCategory();
				if(text.equals(CategoryField.STRING_NEW_CATEGORY)) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setContentText("Entrez la nouvelle categorie:");
					dialog.setTitle("Nouvelle categorie");
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()){
						String newCategory = result.get();
						if(CategoryControler.addCategory(newCategory)) {
							setSelectedCategory(newCategory);
						}
					}
				}
				
			}
		});
	}
	
	public String getSelectedCategory() {
		return this.getSelectionModel().getSelectedItem();
	}
	
	public void setSelectedCategory(String newCategory) {
		String oldCategory = this.getSelectedCategory();
		if(oldCategory!=null) {
			if(oldCategory.equals(newCategory)) {
				return;
			}
		}
		this.getSelectionModel().select(newCategory);
		if(!newCategory.equals(STRING_NEW_CATEGORY)) {
			fireSelectedCategoryChanged(newCategory);
		}
	}

	private CatergoyListener getCatergoyListener() {
		if(catergoyListener == null) {
			catergoyListener = new CatergoyListener() {
				
				@Override
				public void categoryAdded(String category) {
					init();
				}
			};
		}
		return catergoyListener;
	}
	
	public boolean addFieldListener(FieldListener<String> listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
			return true;
		}
		return false;
	}
	
	public boolean removeFieldListener(FieldListener<String> listener) {
		return listeners.remove(listener);
	}
	
	public void removeAllFieldListener() {
		listeners.clear();
	}
	
	@SuppressWarnings("unchecked")
	public FieldListener<String>[] getFieldListeners(){
		return listeners.toArray(new FieldListener[0]);
	}
	
	private void fireSelectedCategoryChanged(String newCategory) {
		FieldListener<String>[] listenersTmp = getFieldListeners();
		for(FieldListener<String> listener : listenersTmp) {
			listener.valueChanged(newCategory);
		}
	}
}
