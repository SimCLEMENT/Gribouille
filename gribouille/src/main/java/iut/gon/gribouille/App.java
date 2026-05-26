package iut.gon.gribouille;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import iut.gon.gribouille.controleurs.Controleur;
import javafx.beans.binding.Bindings;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource("/iut/gon/gribouille/Gribouille.fxml")
        );
        Parent root = fxmlLoader.load();

        Controleur controleur = fxmlLoader.getController();

        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.titleProperty().bind(
        	    Bindings.when(controleur.dessin.estModifieProperty())
        	        .then(Bindings.concat(controleur.dessin.nomDuFichierProperty(), " *"))
        	        .otherwise(controleur.dessin.nomDuFichierProperty())
        	);
        stage.show();

        stage.setOnCloseRequest(event -> {
            if (!controleur.onQuitter()) {
                event.consume();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}