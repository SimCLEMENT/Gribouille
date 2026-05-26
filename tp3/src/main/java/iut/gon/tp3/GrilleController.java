package iut.gon.tp3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class GrilleController implements Initializable {

    @FXML
    private GridPane grille;

    private Label[][] labels = new Label[3][3];
    private GrilleModel modele;

    public GrilleController(GrilleModel modele) {
        this.modele = modele;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        grille.setStyle("-fx-background-color: seashell");

        for (int lg = 0; lg < 3; lg++) {
            for (int col = 0; col < 3; col++) {
                Label label = new Label();

                label.setMaxSize(1000, 1000);
                label.setAlignment(javafx.geometry.Pos.CENTER);
                label.setTextAlignment(TextAlignment.CENTER);
                
                label.textProperty().bind(modele.getCase(lg, col));

                final int finalLg = lg;
                final int finalCol = col;
                label.setOnMouseClicked(event -> {
                    modele.setCase(finalLg, finalCol, "bonjour");
                });

                grille.add(label, col, lg);
                labels[lg][col] = label;
            }
        }
    }
}