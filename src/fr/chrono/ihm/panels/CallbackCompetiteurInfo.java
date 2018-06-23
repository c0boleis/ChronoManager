package fr.chrono.ihm.panels;

import fr.chrono.model.interfaces.ICompetiteur;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class CallbackCompetiteurInfo implements Callback<TableColumn.CellDataFeatures<ICompetiteur,GridPane>,ObservableValue<GridPane>> {

	@Override
	public ObservableValue<GridPane> call(CellDataFeatures<ICompetiteur, GridPane> param) {
		ObsInfo n = new ObsInfo(param.getValue());
		return n;
	}
	
	class ObsInfo implements ObservableValue<GridPane>{
		
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
		public void addListener(ChangeListener<? super GridPane> arg0) {
			
		}

		@Override
		public GridPane getValue() {
			return new PanelCompetiteurInfo(this.competiteur);
		}

		@Override
		public void removeListener(ChangeListener<? super GridPane> arg0) {
			
		}
		
	}

}
