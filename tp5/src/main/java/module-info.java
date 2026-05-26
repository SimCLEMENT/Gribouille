module iut.gon.tp5 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    exports iut.gon.tp5;
    opens iut.gon.tp5 to javafx.fxml;
}