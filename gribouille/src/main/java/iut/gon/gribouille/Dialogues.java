package iut.gon.gribouille;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Dialogues {

	public static boolean confirmation() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Fermeture");
		alert.setContentText("Voulez-vous vraiment quitter ?");
		alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		ButtonType reponse = alert.showAndWait().orElse(ButtonType.NO);
		return reponse == ButtonType.YES;
	}
}