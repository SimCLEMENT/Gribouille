package fr.iutgon.tp6;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;

public class MoneyTableCell<T> extends TableCell<T, Number> {

    private static final PseudoClass NEGATIF_CSS = PseudoClass.getPseudoClass("negatif");

    public MoneyTableCell() {
        setAlignment(Pos.CENTER_RIGHT);
    }

    @Override
    protected void updateItem(Number value, boolean empty) {
        super.updateItem(value, empty);

        if (empty || value == null) {
            setText(null);
            setGraphic(null);
            pseudoClassStateChanged(NEGATIF_CSS, false);
        } else {
            setText(String.format("%.2f €", value.floatValue()));
            pseudoClassStateChanged(NEGATIF_CSS, value.floatValue() < 0);
        }
    }
}