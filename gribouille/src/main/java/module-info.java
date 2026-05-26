module iut.gon.gribouille {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

	opens iut.gon.gribouille to javafx.fxml;
	opens iut.gon.gribouille.controleurs to javafx.fxml;
    exports iut.gon.gribouille;
}
