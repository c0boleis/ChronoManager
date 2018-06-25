package fr.chrono.ihm.panels;

import fr.chrono.controlers.RunControler;
import fr.chrono.controlers.TimeControler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PanelRunInfo extends BorderPane{
	
	private Label labelCurrentTime;
	
	private Button buttonStopRun;
	
	public PanelRunInfo() {
		super();
		this.setCenter(getLabelCurrentTime());
		this.setLeft(getButtonStopRun());
	}

	public Label getLabelCurrentTime() {
		if(labelCurrentTime == null) {
			labelCurrentTime = new Label();
			labelCurrentTime.setMaxHeight(Integer.MAX_VALUE);
			labelCurrentTime.setMaxWidth(Integer.MAX_VALUE);
			labelCurrentTime.setAlignment(Pos.BASELINE_RIGHT);
			labelCurrentTime.setFont(new Font("Arial",80));
			labelCurrentTime.setText(TimeControler.getCurrentTimeString());
			new Thread() {
			    public void run() {
			        //Do some stuff in another thread
			    	while(true) {
				    	try {
							Thread.sleep(33);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				        Platform.runLater(new Runnable() {
				            public void run() {
				            	labelCurrentTime.setText(TimeControler.getCurrentTimeString());
				            }
				        });
			    	}

			    }
			}.start();
		}
		return labelCurrentTime;
	}

	private Button getButtonStopRun() {
		if(buttonStopRun == null) {
			buttonStopRun = new Button();
			buttonStopRun.setText("Stoper\nla\ncourse");
			buttonStopRun.setMaxHeight(Integer.MAX_VALUE);
			buttonStopRun.setMaxWidth(Integer.MAX_VALUE);
//			buttonStopRun.setAlignment(Pos.BASELINE_RIGHT);
			buttonStopRun.setFont(new Font("Arial",16));
			buttonStopRun.setBackground(new Background(
					new BackgroundFill(
							Color.RED, 
							CornerRadii.EMPTY,
							Insets.EMPTY)));
			buttonStopRun.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					RunControler.stopRun();
				}
			});
		}
		return buttonStopRun;
	}

}
