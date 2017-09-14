package com.glennou.PROJET7; /**
 * Created by glenn on 26/08/2017.
 */

import javax.swing.*;
import java.awt.*;

public final class VisualiseurOrdre extends MyPanel {


  VisualiseurOrdre() {
    super();

    TableauExcel tableau = new TableauExcel("BASE DE DONNEES/ordres.xlsx", "Feuil1");
    Object[][] donnees = tableau.getBody();
    String titre[] = {"Portefeuille", "Sens", "Actif", "Date d'opération", "Date de R/L", "Quantité", "Prix", "Montant", "Devise"};

    JTable table = new JTable(donnees, titre);
    JScrollPane menuDeroulant = new JScrollPane(table);

    setLayout(new BorderLayout());
    this.add(menuDeroulant, BorderLayout.CENTER);
  }

  public String getTitre() {return "Visualiseur d'ordres";
  }





}