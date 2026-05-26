package iut.gon.tp4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GrilleController implements Initializable {

    private GrilleModel modele;
    private Scores scores;

    private @FXML GridPane grille;
    private @FXML HBox statut;
    private @FXML Label joueur;
    private @FXML MenusController menusController;

    public GrilleController(Scores scores) {
        this.modele = new GrilleModel();
        this.scores = scores;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille.setStyle("-fx-background-color: seashell");
        for (int l = 0; l < 3; ++l)
            for (int c = 0; c < 3; ++c) {
                Label label = new Label();
                label.textProperty().bind(modele.getCase(l, c));
                grille.add(label, c, l);
                int lg = l;
                int col = c;
                label.setOnMouseClicked(event -> this.joueCase(lg, col));
                label.setMaxSize(1000, 1000);
                label.setAlignment(Pos.CENTER);
                label.setFont(Font.font(24));
            }
        joueur.textProperty().bind(modele.texteJoueur);

        // Passe le modèle et les scores au MenusController
        menusController.setParams(modele, scores, this);
    }

    public void joueCase(int lg, int col) {
        if (modele.estFinie()) return;
        try {
            modele.joueCase(lg, col);
        } catch (IllegalStateException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            return;
        }
        if (modele.estGagne(modele.JOUEUR_X))
            onGagne(modele.JOUEUR_X);
        else if (modele.estGagne(modele.JOUEUR_O))
            onGagne(modele.JOUEUR_O);
        else if (modele.estFinie())
            onGagne(null);
    }

    public void onGagne(String joueur) {
        if (joueur != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Victoire !");
            dialog.setHeaderText("Le joueur " + joueur + " a gagné !");
            dialog.setContentText("Entrez votre nom :");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(nom -> scores.ajouteVictoire(nom));
        } else {
            scores.ajouteNulle();
        }
        afficherTable();
    }

    public void afficherTable() {
        try {
            FXMLLoader loader = new FXMLLoader(Morpion.class.getResource("table.fxml"));
            Parent tableRoot = loader.load();
            TableController tableController = loader.getController();
            tableController.setScores(scores);
            grille.getScene().setRoot(tableRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}