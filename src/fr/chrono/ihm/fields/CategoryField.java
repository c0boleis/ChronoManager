package fr.chrono.ihm.fields;

import fr.chrono.controlers.CategoryControler;
import fr.chrono.controlers.listeners.CatergoyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CategoryField extends ComboBox<String>{
	
	public static final String STRING_NEW_CATEGORY = "...";
	
	private CatergoyListener catergoyListener;
	
	private String defaultValue;

	public CategoryField() {
		this(null);
	}
	
	public CategoryField(String defaultValueIn) {
		this.defaultValue = defaultValueIn;
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
	}
	
	public String getSelectedCategory() {
		return this.getSelectionModel().getSelectedItem();
	}
	
	public void setSelectedCategory(String category) {
		this.getSelectionModel().select(category);
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
}
