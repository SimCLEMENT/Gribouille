package iut.gon.gribouille.controleurs;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class DessinController {

    @FXML public AnchorPane pane;
    @FXML public Canvas dessin;

    private Controleur controleur;

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
        dessin.widthProperty().bind(pane.widthProperty());
        dessin.heightProperty().bind(pane.heightProperty());
        dessin.widthProperty().addListener(e -> controleur.dessine());
        dessin.heightProperty().addListener(e -> controleur.dessine());

        // Rendre le canvas focusable et écouter le clavier
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(evt -> controleur.onKeyPressed(evt.getCode()));
    }

    public void efface() {
        GraphicsContext gc = dessin.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, dessin.getWidth(), dessin.getHeight());
    }

    public void trace(double x1, double y1, double x2, double y2) {
        dessin.getGraphicsContext2D().strokeLine(x1, y1, x2, y2);
    }

    @FXML
    public void onMousePressed(MouseEvent evt) {
        controleur.onMousePressed(evt.getX(), evt.getY());
    }

    @FXML
    public void onMouseDragged(MouseEvent evt) {
        controleur.onMouseDragged(evt.getX(), evt.getY());
    }

    @FXML
    public void onMouseMoved(MouseEvent evt) {
        controleur.onMouseMoved(evt.getX(), evt.getY());
    }
    
    public void setEpaisseur(double epaisseur) {
        dessin.getGraphicsContext2D().setLineWidth(epaisseur);
    }

    public void setCouleur(Color couleur) {
        dessin.getGraphicsContext2D().setStroke(couleur);
    }
}
