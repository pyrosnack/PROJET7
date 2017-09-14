package com.glennou.PROJET7;

/**
 * Created by glenn on 23/08/2017.
 */
public class Actif {

    String nom;
    String type;
    String devise;
    String placeDeMarche;
    String placeDeCotation;
    String ISIN;
    String BBGID;
    String ticker;
    String RIC;
    String pays;
    String emetteur;


    public Actif(final String parNom,
                 final String parType,
                 final String parDevise,
                 final String parPlaceDeMarche) {
        nom = parNom;
        type = parType;
        devise = parDevise;
        placeDeMarche = parPlaceDeMarche;
    }
}