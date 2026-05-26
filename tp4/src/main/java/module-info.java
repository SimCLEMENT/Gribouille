module iut.gon.tp4 {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;


  opens iut.gon.tp4 to javafx.fxml;
  exports iut.gon.tp4;
}
