module iut.gon.tp2 {
  requires javafx.controls;
  requires javafx.fxml;
requires java.desktop;

  opens iut.gon.tp2 to javafx.fxml;
  exports iut.gon.tp2;
}
