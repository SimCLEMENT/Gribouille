package fr.iutgon.tp6.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

  private Produit produit;

  @BeforeEach
  public void prepare() {
    produit = new Produit("produit", 12.f, 1.1f);
  }

  @Test
  public void newTest() {
    assertSame("produit", produit.getNom());
    assertEquals(12.f, produit.getPrix());
    assertEquals(12.f, produit.prixProperty().get());
    assertEquals(1.1f, produit.getTva());
  }
  @Test
  public void newAreIndependantTest() {
    Produit produit1 = new Produit("Burger", 2.75f, 1.15f);
    assertSame("Burger", produit1.getNom());
    assertEquals(2.75f, produit1.getPrix());
    assertEquals(2.75f, produit1.prixProperty().get());
    assertEquals(1.15f, produit1.getTva());
    assertSame("produit", produit.getNom());
    assertEquals(12.f, produit.getPrix());
    assertEquals(12.f, produit.prixProperty().get());
    assertEquals(1.1f, produit.getTva());
  }
  @Test
  public void newTestThrows() {
    assertThrows(IllegalArgumentException.class, ()->new Produit("abc", 1f, -1f));
    assertThrows(IllegalArgumentException.class, ()->new Produit("abc", 1f, 2f));
  }

  @Test
  public void setTest() {
    produit.setPrix(15);
    assertEquals(15, produit.getPrix());
    assertEquals(15.f, produit.prixProperty().get());
    Produit produit2 = new Produit("produit2", 12.f, 1.1f);
    assertEquals(15, produit.getPrix());
  }

  @Test
  public void equalsTest() {
    Produit produit2 = new Produit("produit", 12.f, 1.1f);
    assertEquals(produit2, produit);
    assertNotSame(produit2, produit);
    produit2.setPrix(12.1f);
    assertNotEquals(produit, produit2);
    produit2 = new Produit("produit2", 12.f, 1.1f);
    assertNotEquals(produit, produit2);
  }
}
