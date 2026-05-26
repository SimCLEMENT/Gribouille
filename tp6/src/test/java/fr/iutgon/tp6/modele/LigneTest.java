package fr.iutgon.tp6.modele;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LigneTest {

  private Produit produit;
  private Ligne ligne;

  @BeforeEach
  public void prepare() {
    produit = new Produit("produit", 12.f, 1.1f);
    ligne = new Ligne(3, produit);
  }

  @Test
  public void newTest() {
    assertEquals(3, ligne.getQte());
    assertEquals(3, ligne.qteProperty().get());
    assertSame(produit, ligne.getProduit());
    assertSame(produit, ligne.produitProperty().get());
  }

  @Test
  public void setQteTest() {
    ligne.setQte(5);
    assertEquals(5, ligne.getQte());
    assertEquals(5, ligne.qteProperty().get());
  }
  @Test
  public void setProduitTest() {
    Produit produit0 = FabriqueProduits.getProduits().get(0);
    ligne.setProduit(produit0);
    assertSame(produit0, ligne.getProduit());
    assertSame(produit0, ligne.produitProperty().get());
  }
  @Test
  public void newLineAreIndependant() {
    Produit produit0 = FabriqueProduits.getProduits().get(0);
    Ligne ligne2 = new Ligne(5, produit0);
    assertEquals(3, ligne.getQte());
    assertEquals(3, ligne.qteProperty().get());
    assertSame(produit, ligne.getProduit());
    assertSame(produit, ligne.produitProperty().get());
  }
  @Test
  public void equalsTest() {
    Ligne ligne2 = new Ligne(3, produit);
    assertEquals(ligne2, ligne);
  }@Test
  public void notEqualsTestWhenDifferentQte() {
    Ligne ligne2 = new Ligne(5, produit);
    assertNotEquals(ligne2, ligne);
  }
  @Test
  public void notEqualsTestWhenDifferentProducts() {
    Produit produit0 = FabriqueProduits.getProduits().get(0);
    Ligne ligne2 = new Ligne(3, produit0);
    assertNotEquals(ligne2, ligne);
  }

  @Test
  public void mathTest1() {
    assertEquals(36.f, ligne.getTotalHT());
    assertEquals(36.f, ligne.totalHTProperty().getValue().floatValue());
    assertEquals(36.f*1.1f, ligne.getTotalTTC());
    assertEquals(36.f*1.1f, ligne.totalTTCProperty().getValue().floatValue());
  }

  @Test
  public void mathTestSetPrix() {
    produit.setPrix(10.f);
    assertEquals(30.f, ligne.getTotalHT());
    assertEquals(30.f, ligne.totalHTProperty().getValue().floatValue());
    assertEquals(30.f*1.1f, ligne.getTotalTTC());
    assertEquals(30.f*1.1f, ligne.totalTTCProperty().getValue().floatValue());
  }

  @Test
  public void mathTestSetQte() {
    ligne.setQte(10);
    assertEquals(120.f, ligne.getTotalHT());
    assertEquals(120.f, ligne.totalHTProperty().getValue().floatValue());
    assertEquals(120.f*1.1f, ligne.getTotalTTC());
    assertEquals(120.f*1.1f, ligne.totalTTCProperty().getValue().floatValue());
  }

  @Test
  public void mathTestSetProduit() {
    ligne.setProduit(FabriqueProduits.getProduits().get(0));
    assertEquals(3*0.17f, ligne.getTotalHT());
    assertEquals(3*0.17f, ligne.totalHTProperty().getValue().floatValue());
    assertEquals(3*0.17f*1.05f, ligne.getTotalTTC());
    assertEquals(3*0.17f*1.05f, ligne.totalTTCProperty().getValue().floatValue());
  }
}
