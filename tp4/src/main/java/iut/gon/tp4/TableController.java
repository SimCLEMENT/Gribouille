package iut.gon.tp4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.io.IOException;

public class TableController {
  public Label nbNulles;
  private Scores scores;

  @FXML public TableView<Scores.Entree> table;


  public void onFermer(ActionEvent event) {
	  try {
	        FXMLLoader loader = new FXMLLoader(Morpion.class.getResource("grille.fxml"));
	        Scores s = this.scores;
	        GrilleController grilleController = new GrilleController(s);
	        loader.setController(grilleController);
	        Parent grilleRoot = loader.load();
	        table.getScene().setRoot(grilleRoot);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
  }

  public void onReinit(ActionEvent event) {
    scores.reinitialiser();
  }

  public void setScores(Scores scores) {
    this.scores = scores;
    table.setItems(scores.getScores());
    table.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("joueur"));
    table.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("victoires"));
    nbNulles.textProperty().set(String.valueOf(scores.getNulles()));
  }
}
