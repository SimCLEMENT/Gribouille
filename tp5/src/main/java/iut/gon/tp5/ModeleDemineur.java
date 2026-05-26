package iut.gon.tp5;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Arrays;
import java.util.Random;

/** Le modèle d'un démineur */
public class ModeleDemineur {
  /** Autorise la révélation automatique des zones sûres */
  public final SimpleBooleanProperty autoZero = new SimpleBooleanProperty(false);
  private final ReadOnlyIntegerWrapper nbInconnues = new ReadOnlyIntegerWrapper();
  private final ReadOnlyIntegerWrapper nbMarques = new ReadOnlyIntegerWrapper();
  private final ReadOnlyBooleanWrapper perdu = new ReadOnlyBooleanWrapper();
  private ReadOnlyBooleanWrapper[][] revelees;
  private ReadOnlyBooleanWrapper[][] marquees;
  private int[][] textes;
  private boolean[][] mines;

  /** Crée une zone à déminer avec une taille et un nombre de mines données */
  public ModeleDemineur(int tailleY, int tailleX, int nbMines) {
    setTaille(tailleY, tailleX, nbMines);
  }

  /** Crée une zone à déminer avec une taille et un nombre de mines données */
  public void setTaille(int tailleY, int tailleX, int nbMines) {
    mines = new boolean[tailleY][tailleX];
    revelees = new ReadOnlyBooleanWrapper[tailleY][tailleX];
    marquees = new ReadOnlyBooleanWrapper[tailleY][tailleX];
    textes = new int[tailleY][tailleX];
    int nb = nbMines;
    Random alea = new Random();
    while (nb > 0) {
      int y = alea.nextInt(tailleY);
      int x = alea.nextInt(tailleX);
      if (! mines[y][x]) {
        mines[y][x] = true;
        textes[y][x] = -1;
        nb--;
        for (int dx=-1; dx<=1; ++dx)
          if (x+dx>=0 && x+dx<tailleX)
            for (int dy=-1; dy<=1; ++dy)
              if (y+dy>=0 && y+dy<tailleY)
                if (!mines[y+dy][x+dx])
                  ++textes[y+dy][x+dx];
      } // if ! mine
    } // while nb>0
    for (int y=0; y<tailleY; ++y) {
      for (int x=0; x<tailleX; ++x) {
        revelees[y][x] = new ReadOnlyBooleanWrapper(false);
        marquees[y][x] = new ReadOnlyBooleanWrapper(false);
      }
    }
    nbInconnues.set(tailleX*tailleY);
    perdu.set(false);
  } // void setTaille(...)

  /** Teste si la case spécifiée est révélée */
  public boolean estRevelee(int y, int x) {
    return revelees[y][x].get();
  }
  /** Indique si la case spécifiée est révélée */
  public BooleanExpression estReveleeProperty(int y, int x) {
    return revelees[y][x].getReadOnlyProperty();
  }
  /** Teste si la case spécifiée est marquée */
  public boolean estMarquee(int y, int x) {
    return marquees[y][x].get();
  }
  /** Indique si la case spécifiée est marquée */
  public BooleanExpression estMarqueeProperty(int y, int x) {
    return marquees[y][x].getReadOnlyProperty();
  }
  /** Retourne le texte à afficher dans la case spécifiée */
  public String getText(int y, int x) {
    if (revelees[y][x].get()) {
      return mines[y][x]?"X":String.valueOf(textes[y][x]);
    } else {
      return marquees[y][x].get()?"P":"?";
    }
  }
  /** Indique le texte à afficher dans la case spécifiée */
  public StringExpression texteProperty(int y, int x) {
    return Bindings.when(revelees[y][x])
        .then(mines[y][x]?"X":String.valueOf(textes[y][x]))
        .otherwise(Bindings.when(marquees[y][x]).then("P").otherwise("?"));
  }

  /** Retourne le nombre de cases marquées */
  public int getNbMarques() {
    return nbMarques.get();
  }

  /* Indique le nombre de cases marquées */
  public ReadOnlyIntegerProperty nbMarquesProperty() {
    return nbMarques.getReadOnlyProperty();
  }

  /** Retourne le nombre de cases non révélées */
  public int getNbInconnues() {
    return nbInconnues.get();
  }

  /** Indique le nombre de cases non révélées */
  public ReadOnlyIntegerProperty nbInconnuesProperty() {
    return nbInconnues.getReadOnlyProperty();
  }

  /** Marque/démarque la case spécifiée. */
  public void marque(int y, int x) {
    if (!perdu.get())
    if (! revelees[y][x].get())
      if (marquees[y][x].get()) {
        marquees[y][x].set(false);
        nbMarques.set(nbMarques.get()-1);
      } else {
        marquees[y][x].set(true);
        nbMarques.set(nbMarques.get()+1);
      }
  }

  /** Révèle la case spécifiée.
      Si elle était marquée, retire la marque.
   */
  public void revele(int y, int x) {
    if (!perdu.get())
    if (! revelees[y][x].get()) {
      if (marquees[y][x].get()) {
        marquees[y][x].set(false);
        nbMarques.set(nbMarques.get()-1);
      }
      revelees[y][x].set(true);
      nbInconnues.set(nbInconnues.get()-1);
      if (mines[y][x])
        perdu.set(true);
      else if (autoZero.get() && textes[y][x] == 0) {
        for (int dy=-1; dy<=1; ++dy) {
          int ny = y + dy;
          if (ny >=0 && ny <textes.length)
            for (int dx=-1; dx<=1; ++dx) {
              int nx = x + dx;
              if (nx >=0 && nx <textes[ny].length)
                if (!estMarquee(ny, nx) && !estRevelee(ny, nx))
                  revele(ny, nx);
            } // for dx
        } // for dy
      } // if propage
    } // if pas déjà révélé
  } // void revele(y,x)

  /** Teste si l'on a révélé une mine */
  public boolean estPerdu() { return perdu.get(); }

  /** Indique si l'on a révélé une mine */
  public BooleanExpression perduProperty() {return perdu.getReadOnlyProperty(); }

  /** Retourne le nombre de mines à trouver */
  public int getNbMines() {
    int cpt = 0;
    for (boolean[] ligne : mines)
      for (boolean cas : ligne)
        if (cas) ++cpt;
    return cpt;
  }

  /** Traduit un userData "String" en un tableau de 3 entiers. */
  public static int[] parseUserData(String userData) {
    return Arrays.stream(userData.split("; *")).mapToInt(Integer::parseInt).toArray();
  }
} // public class ModeleDemineur
