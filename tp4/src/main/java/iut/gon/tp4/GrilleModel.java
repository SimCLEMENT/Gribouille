package iut.gon.tp4;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class GrilleModel {

  public static final String VIDE = " ";
  public static final String JOUEUR_X = "X";
  public static final String JOUEUR_O = "O";

  private SimpleBooleanProperty joueurCourantX = new SimpleBooleanProperty(true);
  private int nbJoue = 0;

  private SimpleStringProperty[][] contenu = new SimpleStringProperty[][] {
      {new SimpleStringProperty(" "),new SimpleStringProperty(" "),new SimpleStringProperty(" ")},
      {new SimpleStringProperty(" "),new SimpleStringProperty(" "),new SimpleStringProperty(" ")},
      {new SimpleStringProperty(" "),new SimpleStringProperty(" "),new SimpleStringProperty(VIDE)}
  };
  public StringExpression texteJoueur = Bindings.when(joueurCourantX).then(JOUEUR_X).otherwise(JOUEUR_O);

  public StringProperty getCase(int lg, int col) { return contenu[lg][col]; }

  public void joueCase(int lg, int col) {
    if (contenu[lg][col].get() == VIDE)
      contenu[lg][col].set(texteJoueur.get());
    else
      throw new IllegalStateException("Case déjà occupée !");
    ++nbJoue;
    joueurCourantX.set(! joueurCourantX.get());
  }

  public void nouvellePartie() {
    for (int l=0; l<3; ++l)
      for (int c=0; c<3; ++c)
        contenu[l][c].set(VIDE);
    nbJoue = 0;
    joueurCourantX.set(true);
  }

  public boolean estGagne(String joueur) {
    return gagneLigne(joueur, 0) || gagneLigne(joueur, 1) || gagneLigne(joueur, 2) ||
        gagneColonne(joueur, 0) || gagneColonne(joueur, 1) || gagneColonne(joueur, 2) ||
        gagneDiago1(joueur) || gagneDiago2(joueur);
  }

  public boolean estNulle() {
    return nbJoue == 9 && ! estGagne(JOUEUR_O) && ! estGagne(JOUEUR_X);
  }

  public boolean estFinie() {
    return estGagne(JOUEUR_X) || estGagne(JOUEUR_O) || nbJoue==9;
  }

  private boolean gagneDiago1(String joueur) {
    return contenu[0][0].get()==joueur && contenu[1][1].get()==joueur && contenu[2][2].get()==joueur;
  }
  private boolean gagneDiago2(String joueur) {
    return contenu[0][2].get()==joueur && contenu[1][1].get()==joueur && contenu[2][0].get()==joueur;
  }

  private boolean gagneColonne(String joueur, int idx) {
    return contenu[0][idx].get()==joueur && contenu[1][idx].get()==joueur && contenu[2][idx].get()==joueur;
  }

  private boolean gagneLigne(String joueur, int idx) {
    return contenu[idx][0].get()==joueur && contenu[idx][1].get()==joueur && contenu[idx][2].get()==joueur;
  }
}
