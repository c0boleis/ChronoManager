package fr.chrono.ihm.panels;

import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.controlers.TimeControler;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class CallbackBtnArriving implements Callback<TableColumn.CellDataFeatures<ICompetiteur,Button>,ObservableValue<Button>> {

	@Override
	public ObservableValue<Button> call(CellDataFeatures<ICompetiteur, Button> param) {
		ObsInfo n = new ObsInfo(param.getValue());
		return n;
	}
	
	class ObsInfo implements ObservableValue<Button>{
		
		private ICompetiteur competiteur;

		public ObsInfo(ICompetiteur value) {
			this.competiteur = value;
		}

		@Override
		public void addListener(InvalidationListener arg0) {
			
		}

		@Override
		public void removeListener(InvalidationListener arg0) {
			
		}

		@Override
		public void addListener(ChangeListener<? super Button> arg0) {
			
		}

		@Override
		public Button getValue() {
			Button buttonFinish = new Button("Arrivé");
			buttonFinish.setMaxHeight(Integer.MAX_VALUE);
			buttonFinish.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					CompetiteurControler.setArrivalTime(competiteur,TimeControler.getCurrentTime());
				}
			});
			return buttonFinish;
		}

		@Override
		public void removeListener(ChangeListener<? super Button> arg0) {
			
		}
		
	}

}
