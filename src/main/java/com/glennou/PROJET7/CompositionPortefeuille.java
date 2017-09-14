package com.glennou.PROJET7;

/**
 * Created by glenn on 28/08/2017.
 */
public class CompositionPortefeuille {

  String actif;
  String quantite;
  String prix;
  String montant;
  String devise;


  public CompositionPortefeuille(
               final String parActif,
               final String parQuantite,
               final String parPrix,
               final String parMontant,
               final String parDevise) {
    actif = parActif;
    quantite = parQuantite;
    prix = parPrix;
    montant = parMontant;
    devise = parDevise;
  }
}