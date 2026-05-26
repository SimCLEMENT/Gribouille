package fr.iutgon.tp6.modele;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

/** Représente une ligne de facture */
public class Ligne {
	private SimpleIntegerProperty qte;
	private SimpleObjectProperty<Produit> produit;
	private NumberExpression totalHT;
	private NumberExpression totalTTC;

	/** Construit une ligne de facture */
	public Ligne(int qte, Produit produit) {
		this.qte = new SimpleIntegerProperty(qte);
		this.produit = new SimpleObjectProperty<>(produit);

		NumberExpression prixBinding = Bindings.selectFloat(this.produit, "prix");
		this.totalHT = Bindings.multiply(this.qte, prixBinding);

		NumberExpression tvaBinding = Bindings.selectFloat(this.produit, "tva");
		this.totalTTC = Bindings.multiply(this.totalHT, tvaBinding);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (Ligne) obj;
		return this.qte.get() == that.qte.get() && Objects.equals(this.produit.get(), that.produit.get());
	}

	/** Retourne le produit concerné par la ligne */
	public Produit getProduit() {
		return produit.get();
	}

	/** Modifie le produit concerné par la ligne */
	public void setProduit(Produit produit) {
		this.produit.set(produit);
	}

	/** Retourne la quantité de produits concernés par la ligne */
	public int getQte() {
		return qte.get();
	}

	/** Modifie la quantité de produits concernés par la ligne */
	public void setQte(int qte) {
		this.qte.set(qte);
	}

	/** La valeur totale Hors-Taxes de la ligne */
	public Number getTotalHT() {
		return totalHT.getValue();
	}

	/** La valeur totale Toutes-Taxes-Comprises de la ligne */
	public Number getTotalTTC() {
		return totalTTC.getValue();
	}

	@Override
	public int hashCode() {
		return Objects.hash(qte, produit);
	}

	/** Le produit concerné par la ligne */
	public SimpleObjectProperty<Produit> produitProperty() {
		return produit;
	}

	/** La quantité de produits concernés par la ligne */
	public SimpleIntegerProperty qteProperty() {
		return qte;
	}

	@Override
	public String toString() {
		return String.format("Ligne[qte=%s, produit=%s, totalHT=%s, totalTTC=%s]", qte, produit.get(), totalHT.floatValue(), totalTTC.floatValue());
	}

	/** Le total Hors-Taxes de la ligne */
	public NumberExpression totalHTProperty() {
		return totalHT;
	}

	/** Le total Toutes-Taxes-Comprises de la ligne */
	public NumberExpression totalTTCProperty() {
		return totalTTC;
	}

} 
