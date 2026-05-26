package iut.gon.gribouille.controleurs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class MenusController implements Initializable {

    @FXML private RadioMenuItem menuCrayon;
    @FXML private RadioMenuItem menuEtoile;
    @FXML private ToggleGroup groupeOutil;
    @FXML private ToggleGroup groupeEpaisseur;

    private Controleur controleur;

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupeOutil.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (controleur == null) return;
            if (nv == menuCrayon) controleur.onCrayon();
            else if (nv == menuEtoile) controleur.onEtoile();
        });

        groupeEpaisseur.selectedToggleProperty().addListener((obs, old, nv) -> {
            if (controleur == null || nv == null) return;
            String texte = ((RadioMenuItem) nv).getText();
            controleur.setEpaisseur(Integer.parseInt(texte));
        });
    }

    @FXML
    public void onQuitte() {
        if (controleur.onQuitter()) {
            Platform.exit();
        }
    }
    
    @FXML
    public void onSauvegarder() {
        controleur.sauvegarde(menuCrayon.getParentPopup().getOwnerWindow());
    }
    
    @FXML
    public void onCharger() {
        controleur.charge(menuCrayon.getParentPopup().getOwnerWindow());
    }
}