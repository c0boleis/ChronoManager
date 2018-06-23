package fr.chrono.ihm.panels;

import fr.chrono.model.interfaces.ICompetiteur;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class CallbackCompetiteurStartOrder implements Callback<TableColumn.CellDataFeatures<ICompetiteur,Label>,ObservableValue<Label>> {

	@Override
	public ObservableValue<Label> call(CellDataFeatures<ICompetiteur, Label> param) {
		ObsInfo n = new ObsInfo(param.getValue());
		return n;
	}
	
	class ObsInfo implements ObservableValue<Label>{
		
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
		public void addListener(ChangeListener<? super Label> arg0) {
			
		}

		@Override
		public Label getValue() {
			Label label = new Label();
			label.setFont(new Font("Arial", 32));
			label.setText(String.valueOf(this.competiteur.getStartOrder()));
			return label;
		}

		@Override
		public void removeListener(ChangeListener<? super Label> arg0) {
			
		}
		
	}

}
