package iut.gon.gribouille.controleurs;

public abstract class Outil {

    protected Controleur controleur;

    public Outil(Controleur controleur) {
        this.controleur = controleur;
    }

    public abstract void onMousePress(double x, double y);
    public abstract void onMouseDrag(double x, double y);
}
