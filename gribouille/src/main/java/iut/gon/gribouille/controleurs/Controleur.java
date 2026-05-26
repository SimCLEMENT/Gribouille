package iut.gon.gribouille.controleurs;

import iut.gon.gribouille.Dialogues;
import iut.gon.gribouille.modele.Dessin;
import iut.gon.gribouille.modele.Etoile;
import iut.gon.gribouille.modele.Figure;
import iut.gon.gribouille.modele.Point;
import iut.gon.gribouille.modele.Trace;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    public final Dessin dessin = new Dessin();
    public Figure figureCourante;

    public final SimpleDoubleProperty precX = new SimpleDoubleProperty(0);
    public final SimpleDoubleProperty precY = new SimpleDoubleProperty(0);
    public final SimpleObjectProperty<Color> couleur = new SimpleObjectProperty<>(Color.BLACK);
    public final SimpleIntegerProperty epaisseur = new SimpleIntegerProperty(1);

    @FXML private MenusController menusController;
    @FXML private StatusController statutController;
    @FXML private CouleursController couleursController;
    @FXML public DessinController dessinController;

    private Outil outil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menusController.setControleur(this);
        statutController.setControleur(this);
        couleursController.setControleur(this);
        dessinController.setControleur(this);

        outil = new OutilCrayon(this);

        statutController.labelX.textProperty().bind(precX.asString("X : %.0f"));
        statutController.labelY.textProperty().bind(precY.asString("Y : %.0f"));
        statutController.labelEpaisseur.textProperty().bind(
            Bindings.createStringBinding(
                () -> "Epaisseur : " + epaisseur.get(), epaisseur));
        statutController.labelCouleur.textProperty().bind(
            Bindings.createStringBinding(
                () -> toColorString(couleur.get()), couleur));
        statutController.labelOutil.setText("Crayon");
        
        epaisseur.addListener((obs, old, nv) -> dessinController.setEpaisseur(nv.doubleValue()));
        couleur.addListener((obs, old, nv) -> dessinController.setCouleur(nv));
    }

    public void onMousePressed(double x, double y) {
        precX.set(x);
        precY.set(y);
        outil.onMousePress(x, y);
    }

    public void onMouseDragged(double x, double y) {
        outil.onMouseDrag(x, y);
        precX.set(x);
        precY.set(y);
    }

    public void onMouseMoved(double x, double y) {
        precX.set(x);
        precY.set(y);
    }

    public void dessine() {
        dessinController.efface();
        GraphicsContext gc = dessinController.dessin.getGraphicsContext2D();
        for (Figure figure : dessin.getFigures()) {
            gc.setLineWidth(figure.getEpaisseur());
            gc.setStroke(Color.web(figure.getCouleur()));
            if (figure instanceof Trace) {
                List<Point> pts = figure.getPoints();
                for (int i = 0; i < pts.size() - 1; i++) {
                    dessinController.trace(
                        pts.get(i).getX(), pts.get(i).getY(),
                        pts.get(i + 1).getX(), pts.get(i + 1).getY());
                }
            } else if (figure instanceof Etoile) {
                Etoile e = (Etoile) figure;
                Point centre = e.getCentre();
                for (Point p : e.getPoints()) {
                    dessinController.trace(centre.getX(), centre.getY(), p.getX(), p.getY());
                }
            }
        }
    }

    public boolean onQuitter() {
        if (!dessin.getEstModifie()) return true;

        ButtonType sansSauvegarder = new ButtonType("Quitter sans sauvegarder");
        ButtonType avecSauvegarder = new ButtonType("Quitter en sauvegardant");
        ButtonType annuler = new ButtonType("Ne pas quitter");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Le dessin a été modifié.");
        alert.setContentText("Que voulez-vous faire ?");
        alert.getButtonTypes().setAll(sansSauvegarder, avecSauvegarder, annuler);

        java.util.Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == sansSauvegarder) return true;
            if (result.get() == avecSauvegarder) {
                try {
                    sauvegarde(dessinController.pane.getScene().getWindow());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

    public void onCrayon() {
        outil = new OutilCrayon(this);
        statutController.labelOutil.setText("Crayon");
    }

    public void onEtoile() {
        outil = new OutilEtoile(this);
        statutController.labelOutil.setText("Etoile");
    }

    public static String toColorString(Color c) {
        return String.format("#%02x%02x%02x",
            (int) Math.round(c.getRed() * 255),
            (int) Math.round(c.getGreen() * 255),
            (int) Math.round(c.getBlue() * 255));
    }
    
    public void setEpaisseur(int e) {
        epaisseur.set(e);
    }
    
    public void onKeyPressed(KeyCode code) {
        switch (code) {
            case ADD:
            case PLUS:
            case EQUALS:
                setEpaisseur(Math.min(epaisseur.get() + 1, 9));
                break;
            case SUBTRACT:
            case MINUS:
            case UNDERSCORE:
            case DIGIT6:
                setEpaisseur(Math.max(epaisseur.get() - 1, 1));
                break;
            case R:
                couleur.set(Color.RED);
                break;
            case V:
                couleur.set(Color.GREEN);
                break;
            case B:
                couleur.set(Color.BLUE);
                break;
            case N:
                couleur.set(Color.BLACK);
                break;
            case W:
                couleur.set(Color.WHITE);
                break;
            default:
                break;
        }
    }
    
    public void sauvegarde(Window window) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Sauvegarder le dessin");
        fc.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers Gribouille", "*.grib")
        );
        java.io.File fichier = fc.showSaveDialog(window);
        if (fichier != null) {
            try {
                dessin.sauveSous(fichier.getAbsolutePath());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void charge(Window window) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Charger un dessin");
        fc.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Fichiers Gribouille", "*.grib")
        );
        java.io.File fichier = fc.showOpenDialog(window);
        if (fichier != null) {
            try {
                dessin.charge(fichier.getAbsolutePath());
                dessinController.efface();
                dessine();
            } catch (java.io.IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
