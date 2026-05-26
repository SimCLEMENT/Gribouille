package iut.gon.gribouille.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class CouleursController {

    @FXML public ColorPicker colorPicker;
    @FXML public Button btnRouge, btnVert, btnBleu, btnCyan;
    @FXML public Button btnMagenta, btnJaune, btnNoir, btnBlanc;

    private Controleur controleur;

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;

        // Lier le ColorPicker à la propriété couleur
        colorPicker.valueProperty().bindBidirectional(controleur.couleur);

        // Boutons de couleur rapide
        btnRouge.setOnAction(e -> controleur.couleur.set(Color.RED));
        btnVert.setOnAction(e -> controleur.couleur.set(Color.LIME));
        btnBleu.setOnAction(e -> controleur.couleur.set(Color.BLUE));
        btnCyan.setOnAction(e -> controleur.couleur.set(Color.CYAN));
        btnMagenta.setOnAction(e -> controleur.couleur.set(Color.MAGENTA));
        btnJaune.setOnAction(e -> controleur.couleur.set(Color.YELLOW));
        btnNoir.setOnAction(e -> controleur.couleur.set(Color.BLACK));
        btnBlanc.setOnAction(e -> controleur.couleur.set(Color.WHITE));
    }
}