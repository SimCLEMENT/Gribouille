package iut.gon.tp3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        GrilleModel modele = new GrilleModel();
        GrilleController controleur = new GrilleController(modele);

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Grille.fxml"));
        fxmlLoader.setController(controleur);
        Parent root = fxmlLoader.load();

        // On utilise le root chargé avec le bon contrôleur !
        scene = new Scene(root, 640, 480);
        stage.setTitle("TP3 - Grille");
        stage.setScene(scene);
        stage.show();

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getText()) {
                case "1": modele.setCase(2, 0, "Touche"); break;
                case "2": modele.setCase(2, 1, "Touche"); break;
                case "3": modele.setCase(2, 2, "Touche"); break;
                case "4": modele.setCase(1, 0, "Touche"); break;
                case "5": modele.setCase(1, 1, "Touche"); break;
                case "6": modele.setCase(1, 2, "Touche"); break;
                case "7": modele.setCase(0, 0, "Touche"); break;
                case "8": modele.setCase(0, 1, "Touche"); break;
                case "9": modele.setCase(0, 2, "Touche"); break;
            }
        });
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}