package iut.gon.tp5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader= new FXMLLoader(App.class.getResource("vueApplication.fxml"));
		BorderPane contenu = loader.load();
		primaryStage.setScene(new Scene(contenu));
		primaryStage.show();
	}
}
