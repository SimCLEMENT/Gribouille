package iut.gon.gribouille.controleurs;

import iut.gon.gribouille.modele.Trace;

public class OutilCrayon extends Outil {

    private Trace traceEnCours;

    public OutilCrayon(Controleur controleur) {
        super(controleur);
    }

    @Override
    public void onMousePress(double x, double y) {
        traceEnCours = new Trace(
            controleur.epaisseur.get(),
            Controleur.toColorString(controleur.couleur.get()),
            x, y
        );
        controleur.dessin.addFigure(traceEnCours);
    }

    @Override
    public void onMouseDrag(double x, double y) {
        controleur.dessinController.trace(controleur.precX.get(), controleur.precY.get(), x, y);
        traceEnCours.addPoint(x, y);
    }
}
