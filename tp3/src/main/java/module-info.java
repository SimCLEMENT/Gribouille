module iut.gon.tp3 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires java.desktop;

    opens iut.gon.tp3 to javafx.fxml;
    exports iut.gon.tp3;
}
