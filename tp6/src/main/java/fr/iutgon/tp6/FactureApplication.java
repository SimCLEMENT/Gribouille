package fr.iutgon.tp6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FactureApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(FactureApplication.class.getResource("facture.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 240);
    stage.setTitle("Une simple facture");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
