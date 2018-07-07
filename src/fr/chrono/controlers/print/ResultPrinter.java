package fr.chrono.controlers.print;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fr.chrono.controlers.CategoryControler;
import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.model.interfaces.ICategory;
import fr.chrono.model.interfaces.ICompetiteur;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ResultPrinter extends Application{

	private WebView webView;

	public static void main(String[] args) {
		if(args.length>0) {
			try {
				CompetiteurControler.load(new File(args[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		launch(args);
	}

	public String printResult() {
		String result  ="";
		result+=getHead()+"\n";
		result+=getBody();
//		FopFactory fopFactory = FopFactory.newInstance();
		return result;
	}

	public WebView getWebView() {
		if(webView == null) {
			webView = new WebView();
		}
		return webView;
	}

	public String printCategory(ICategory category) {

		return null;
	}

	public String getHead() {
		String head = "";

		return head;
	}

	public String getBody() {
		String body = "";
		body+="<body>\r\n"+
				"<table style=\"width:100%\">\r\n";
		ICategory[] categories = CategoryControler.generateCategories();
		for(ICategory category : categories) {
			body += "		<tr>\r\n" + 
					"			<th colspan=\"3\" style=\"text-align: left\">"+category.getName()+"</th>\r\n" + 
					"		</tr>\r\n";
			ICompetiteur[] competiteurs = category.getCompetiteurs();
			int rang = 1;
			for(ICompetiteur competiteur : competiteurs) {
				body+= getCompetiteurResult(competiteur,rang)+"\r\n";
				rang++;
			}

		}
		body+="</table>\r\n"+
				"</body>";
		return body;
	}

	public String getCompetiteurResult(ICompetiteur competiteur,int rang) {
		String competiteurText = "";
		competiteurText+="<tr>\r\n" + 
				"			<td>"+rang+"</td>\r\n" + 
				"			<td>"+competiteur.getStartOrder()+"\t"+competiteur.getName()+"</td>\r\n" + 
				"			<td>"+competiteur.getStartTimeString()+"</td>\r\n" +
				"		</tr>";
		return competiteurText;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn = new Button();
		btn.setText("Print");
		WebView wb = new WebView();
		WebEngine webEngine = wb.getEngine();
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(btn);
		borderPane.setCenter(wb);
		Scene scene = new Scene(borderPane,125, 125);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				WritableImage img = new WritableImage((int)scene.getWidth(),
						(int)scene.getHeight());
				scene.snapshot(img);

				File file = new File("CanvasImage.png");

				try {
					ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
				}
				catch (Exception s) {
				}   
			}
		});
		webEngine.loadContent(printResult());

		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
