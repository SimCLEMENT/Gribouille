package fr.iutgon.tp6;

import fr.iutgon.tp6.modele.FabriqueProduits;
import fr.iutgon.tp6.modele.Ligne;
import fr.iutgon.tp6.modele.Produit;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class FactureController implements Initializable {
	public TableView<Ligne> table;
	public TableColumn<Ligne, Integer> qte;
	public TableColumn<Ligne, Produit> produit;
	public TableColumn<Ligne, Number> prixUnitaire;
	public TableColumn<Ligne, Number> totalHT;
	public TableColumn<Ligne, Number> totalTTC;
	public TextField sommeFacture;

	/**
   Called to initialize a controller after its root element has been completely processed.

   @param location  The location used to resolve relative paths for the root object, or
   {@code null} if the location is not known.
   @param resources The resources used to localize the root object, or {@code null} if
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prixUnitaire.setCellFactory(col -> new MoneyTableCell<>());
		totalHT.setCellFactory(col -> new MoneyTableCell<>());
		totalTTC.setCellFactory(col -> new MoneyTableCell<>());

		qte.setCellValueFactory(new PropertyValueFactory<>("qte"));

		produit.setCellValueFactory(
				cellData -> cellData.getValue().produitProperty()
				);

		prixUnitaire.setCellValueFactory(
				cellData -> Bindings.selectFloat(
						cellData.getValue().produitProperty(), "prix"
						)
				);

		totalHT.setCellValueFactory(
				cellData -> cellData.getValue().totalHTProperty()
				);
		totalTTC.setCellValueFactory(
				cellData -> cellData.getValue().totalTTCProperty()
				);

		qte.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		qte.setOnEditCommit(event -> event.getRowValue().setQte(event.getNewValue()));

		StringConverter<Produit> produitConverter = new StringConverter<>() {
			@Override
			public String toString(Produit p) {
				return p == null ? "" : p.getNom();
			}
			@Override
			public Produit fromString(String nom) {
				return FabriqueProduits.getProduits().stream()
						.filter(p -> p.getNom().equals(nom))
						.findFirst()
						.orElse(null);
			}
		};

		produit.setCellFactory(
				ChoiceBoxTableCell.forTableColumn(
						produitConverter,
						FXCollections.observableList(FabriqueProduits.getProduits())
						)
				);
		produit.setOnEditCommit(event -> event.getRowValue().setProduit(event.getNewValue()));
	}

	public void onAjouter(ActionEvent actionEvent) {
		List<Produit> produits = FabriqueProduits.getProduits();
		Produit produitAleatoire = produits.get(new Random().nextInt(produits.size()));
		int qteAleatoire = new Random().nextInt(10) + 1;
		Ligne ligne = new Ligne(qteAleatoire, produitAleatoire);
		table.getItems().add(ligne);

		NumberExpression somme = table.getItems().get(0).totalTTCProperty();
		for (int i = 1; i < table.getItems().size(); i++) {
			somme = Bindings.add(somme, table.getItems().get(i).totalTTCProperty());
		}
		sommeFacture.textProperty().bind(Bindings.format("%.2f", somme));
	}
}
