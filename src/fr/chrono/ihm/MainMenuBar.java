package fr.chrono.ihm;

import java.io.IOException;

import fr.chrono.controlers.CompetiteurControler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenuBar extends MenuBar{
	
	private Menu menuFile;
	
	private MenuItem menuItemOpen;
	
	private MenuItem menuItemSave;
	
	private MenuItem menuItemClose;
	
	public MainMenuBar() {
		getMenus().add(getMenuFile());
	}

	/**
	 * @return the menuFile
	 */
	public Menu getMenuFile() {
		if(menuFile == null) {
			menuFile = new Menu();
			menuFile.setText("Fichier");
			menuFile.getItems().add(getMenuItemOpen());
			menuFile.getItems().add(getMenuItemSave());
			menuFile.getItems().add(getMenuItemClose());
		}
		return menuFile;
	}

	/**
	 * @return the menuItemOpen
	 */
	public MenuItem getMenuItemOpen() {
		if(menuItemOpen == null) {
			menuItemOpen = new MenuItem();
			menuItemOpen.setText("Open");
		}
		return menuItemOpen;
	}

	/**
	 * @return the menuItemSave
	 */
	public MenuItem getMenuItemSave() {
		if(menuItemSave == null) {
			menuItemSave = new MenuItem();
			menuItemSave.setText("Enregistrer");
			menuItemSave.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					try {
						CompetiteurControler.save();
						System.out.println("Save");
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			});
		}
		return menuItemSave;
	}

	/**
	 * @return the menuItemClose
	 */
	public MenuItem getMenuItemClose() {
		if(menuItemClose == null) {
			menuItemClose = new MenuItem();
			menuItemClose.setText("Fermer");
		}
		return menuItemClose;
	}

}