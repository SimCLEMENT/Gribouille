package iut.gon.tp4;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.ListIterator;

public class Scores {
  public class Entree {
    private String joueur;
    private int victoires;

    public Entree(String joueur, int victoires) {
      this.joueur = joueur;
      this.victoires = victoires;
    }

    public String getJoueur() {
      return joueur;
    }

    public int getVictoires() {
      return victoires;
    }

    public void ajouteVictoire() {
      ++victoires;
    }
  }
  private ObservableList<Entree> scores;
  private int nulles;

  public Scores() {
    scores = FXCollections.observableList(new ArrayList<>());
    nulles = 0;
    scores.add(new Entree("Laurent",42));
  }

  public void ajouteVictoire(String joueur) {
    for (ListIterator<Entree> iterator = scores.listIterator(); iterator.hasNext(); ) {
      Entree e = iterator.next();
      if (e.joueur.equals(joueur)) {
        e.ajouteVictoire();
        iterator.set(e); // Pour modifier la liste
        return;
      }
    }
    // Si on arrive là, c'est un nouveau gagnant !
    scores.add(new Entree(joueur, 1));
  }

  public void ajouteNulle() {
    nulles++;
  }

  public ObservableList<Entree> getScores() {
    return scores;
  }

  public int getNulles() {
    return nulles;
  }

  public void reinitialiser() {
    scores.clear();
    nulles = 0;
  }

}
