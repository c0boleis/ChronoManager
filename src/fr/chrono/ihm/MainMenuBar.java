package fr.chrono.ihm;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.print.ResultPrinter;
import fr.chrono.ihm.dialogs.ExceptionDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.stage.FileChooser;

public class MainMenuBar extends MenuBar{
	
	private Menu menuFile;
	
	private MenuItem menuItemOpen;
	
	private MenuItem menuItemSave;
	
	private MenuItem menuItemClose;
	
	private MenuItem menuItemStartOrderToPdf;
	
	private MenuItem menuItemResultToPdf;
	
	private Menu menuOptions;
	
	private MenuItem menuItemAPropos;
	
	public MainMenuBar() {
		getMenus().add(getMenuFile());
		getMenus().add(getMenuOptions());
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
			menuFile.getItems().add(getMenuItemStartOrderToPdf());
			menuFile.getItems().add(getMenuItemResultToPdf());
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
			menuItemOpen.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					File file = fileChooser.showOpenDialog(null);
					if(file==null) {
						return;
					}
					try {
						CompetiteurControler.load(file);
					} catch (IOException e) {
						ExceptionDialog dialog = new ExceptionDialog(e);
						dialog.showAndWait();
					}
				}
			});
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

	/**
	 * @return the menuOptions
	 */
	private Menu getMenuOptions() {
		if(menuOptions == null) {
			menuOptions = new Menu();
			menuOptions.setText("Options");
			menuOptions.getItems().add(getMenuItemAPropos());
		}
		return menuOptions;
	}

	/**
	 * @return the menuItemAPropos
	 */
	private MenuItem getMenuItemAPropos() {
		if(menuItemAPropos == null) {
			menuItemAPropos = new MenuItem();
			menuItemAPropos.setText("A propos");
		}
		return menuItemAPropos;
	}

	public MenuItem getMenuItemStartOrderToPdf() {
		if(menuItemStartOrderToPdf == null) {
			menuItemStartOrderToPdf = new MenuItem();
			menuItemStartOrderToPdf.setText("Liste de départ en pdf");
			menuItemStartOrderToPdf.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					File file = fileChooser.showSaveDialog(null);
					if(file==null) {
						return;
					}
					try {
						ResultPrinter.exportStartOrderToPdf(file);
					} catch (FOPException | TransformerException | IOException e) {
						ExceptionDialog dialog = new ExceptionDialog(e);
						dialog.showAndWait();
					}	
				}
			});
		}
		return menuItemStartOrderToPdf;
	}

	public MenuItem getMenuItemResultToPdf() {
		if(menuItemResultToPdf == null) {
			menuItemResultToPdf = new MenuItem();
			menuItemResultToPdf.setText("Résultat en pdf");
			menuItemResultToPdf.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					File file = fileChooser.showSaveDialog(null);
					if(file==null) {
						return;
					}
					try {
						ResultPrinter.exportResultToPdf(file);
					} catch (FOPException | TransformerException | IOException e) {
						ExceptionDialog dialog = new ExceptionDialog(e);
						dialog.showAndWait();
					}	
				}
			});
		}
		return menuItemResultToPdf;
	}

}
