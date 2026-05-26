package iut.gon.tp3;

import javafx.beans.property.SimpleStringProperty;

public class GrilleModel {
	private SimpleStringProperty[][] cases = new SimpleStringProperty[3][3];
    
    public GrilleModel() {
        for (int lg = 0; lg < 3; lg++) {
            for (int col = 0; col < 3; col++) {
            	cases[lg][col] = new SimpleStringProperty(String.format("L%dC%d", lg, col));
            }
        }
    }
    
    public SimpleStringProperty getCase(int lg, int col) {
    	return cases[lg][col];
    }
    
    public void setCase(int lg, int col, String texte) {
    	cases[lg][col].set(texte);
    }
}
