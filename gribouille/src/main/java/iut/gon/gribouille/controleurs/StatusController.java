package iut.gon.gribouille.controleurs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class StatusController {
    @FXML public Label labelX, labelY, labelEpaisseur, labelOutil, labelCouleur;

    private Controleur controleur;
    public void setControleur(Controleur controleur) { this.controleur = controleur; }
}