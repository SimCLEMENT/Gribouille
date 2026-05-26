package fr.iutgon.tp6.modele;

import javafx.beans.property.SimpleFloatProperty;

/** Représente un produit */
public class Produit {
  private final String nom;
  private SimpleFloatProperty prix;
  private float tva;

  /** Construit un produit
   @param nom son nom
   @param prix son prix
   @param tva sa TVA (1.33 ou 0.33 pour 33%)
   @throws IllegalArgumentException si le taux n'est pas entre 0 et 100%.
   */
  public Produit(String nom, float prix, float tva) {
    this.nom = nom;
    this.prix = new SimpleFloatProperty(prix);
    if (tva<0 || tva >=2)
      throw new IllegalArgumentException("Taux de TVA incorrect !");
    if (tva < 1)
      this.tva = 1 + tva;
    else
      this.tva = tva;
  }

  /** Retourne le nom du produit */
  public String getNom() {
    return nom;
  }

  /** Le prix du produit */
  public SimpleFloatProperty prixProperty() {
    return prix;
  }

  /** Retourne le prix du produit */
  public float getPrix() {
    return prix.get();
  }

  /** Modifie le prix du produit */
  public void setPrix(float prix) {
    this.prix.set(prix);
  }

  /** Retourne la TVA du produit.
   @return un nombre entre 1 et 2. (1.33 = +33%)
   */
  public float getTva() {
    return tva;
  }

  @Override
  public String toString() {
    return String.format("Produit{nom='%s', prix=%s, tva=%s}", nom, prix, tva);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Produit produit = (Produit) o;

    if (Float.compare(produit.prix.get(), prix.get()) != 0) return false;
    if (Float.compare(produit.tva, tva) != 0) return false;
    return nom.equals(produit.nom);
  }

  @Override
  public int hashCode() {
    int result = nom.hashCode();
    result = 31 * result + (prix.get() != +0.0f ? Float.floatToIntBits(prix.get()) : 0);
    result = 31 * result + (tva != +0.0f ? Float.floatToIntBits(tva) : 0);
    return result;
  }
} 
