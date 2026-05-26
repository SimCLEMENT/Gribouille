package iut.gon.gribouille.controleurs;

import iut.gon.gribouille.modele.Etoile;

public class OutilEtoile extends Outil {

    private Etoile etoileEnCours;

    public OutilEtoile(Controleur controleur) {
        super(controleur);
    }

    @Override
    public void onMousePress(double x, double y) {
        etoileEnCours = new Etoile(
            controleur.epaisseur.get(),
            Controleur.toColorString(controleur.couleur.get()),
            x, y
        );
        controleur.dessin.addFigure(etoileEnCours);
    }

    @Override
    public void onMouseDrag(double x, double y) {
        etoileEnCours.addPoint(x, y);
        controleur.dessine();
    }
}
