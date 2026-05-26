package iut.gon.tp5;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class ControleurDemineur implements Initializable {
	
	@FXML private TextField tfInconnues;
	@FXML private TextField tfMarques;
	@FXML private ToggleGroup toggleDifficulte;
	@FXML private GridPane grille;

	private ModeleDemineur modele = new ModeleDemineur(10, 10, 10);
	
	private final Background bgInconnu = new Background(new BackgroundFill(Color.AQUA, new CornerRadii(20, true), Insets.EMPTY));
	private final Background bgLibre = new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));
	private final Background bgEchec = new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY));
	private final Background bgMarquee = new Background(new BackgroundFill(Color.LEMONCHIFFON, CornerRadii.EMPTY, Insets.EMPTY));

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		tfInconnues.textProperty().bind(modele.nbInconnuesProperty().asString());
		tfMarques.textProperty().bind(modele.nbMarquesProperty().asString());

		toggleDifficulte.selectedToggleProperty().addListener((obs, ancien, nouveau) -> {
			if (nouveau != null) {
				initGrille((String) nouveau.getUserData());
			}
		});
	}

	private void initGrille(String userData) {
		int[] params = ModeleDemineur.parseUserData(userData);
		int nbColonnes = params[0];
		int nbLignes = params[1];
		int nbMines = params[2];
		
		grille.getColumnConstraints().clear();
		grille.getRowConstraints().clear();
		
		modele = new ModeleDemineur(nbLignes, nbColonnes, nbMines);
		
		tfInconnues.textProperty().bind(modele.nbInconnuesProperty().asString());
		tfMarques.textProperty().bind(modele.nbMarquesProperty().asString());
		
		for (int col = 0 ; col < nbColonnes ; col++) {
			ColumnConstraints cc = new ColumnConstraints(32);
			grille.getColumnConstraints().add(cc);
		}
		
		for (int lig = 0 ; lig < nbLignes ; lig ++) {
			RowConstraints rc = new RowConstraints(32);
			grille.getRowConstraints().add(rc);
		}
		grille.getChildren().clear();
		
		for (int lg = 0 ; lg < nbLignes ; lg++) {
			for (int col = 0 ; col < nbColonnes ; col++) {
				javafx.scene.control.Label label = new Label();
				label.setPrefSize(31, 31);
				label.setAlignment(Pos.CENTER);
				label.setTextAlignment(TextAlignment.CENTER);
				label.setBackground(bgInconnu);
				
				label.textProperty().bind(modele.texteProperty(lg, col));
				
				final int finalLg = lg;
				final int finalCol = col;
				
				label.setOnMouseClicked((MouseEvent evt) -> {
		            if (evt.getButton() == MouseButton.PRIMARY) {
		                modele.revele(finalLg, finalCol);
		            } else if (evt.getButton() == MouseButton.SECONDARY) {
		                modele.marque(finalLg, finalCol);
		            }
		            
		            if (modele.estRevelee(finalLg, finalCol)) {
		                if (modele.getText(finalLg, finalCol).equals("X")) {
		                    label.setBackground(bgEchec);
		                } else {
		                    label.setBackground(bgLibre);
		                }
		            } else if (modele.estMarquee(finalLg, finalCol)) {
		                label.setBackground(bgMarquee);
		            } else {
		                label.setBackground(bgInconnu);
		            }
		        });

		        grille.add(label, col, lg);
			}
		}
	}
}











































































