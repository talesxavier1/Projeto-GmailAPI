package Interface;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class ShowEmailWebView extends Application
{

	private String data;
	
	WebEngine engine;
	
	public void start(Stage primaryStage)
	{
		WebView view = new WebView();
		engine = view.getEngine();
		engine.loadContent(data);
		engine.setJavaScriptEnabled(true);
		VBox box = new VBox();
		box.getChildren().addAll(view);
		Scene scene = new Scene(box,700,1000);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void runLaunch(String data_)
	{
		launch();
	}
	
	public void setBodyData(String data)
	{
		engine.loadContent(data);
	}
	



}



