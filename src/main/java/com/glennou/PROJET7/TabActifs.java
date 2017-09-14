package com.glennou.PROJET7; /**
 * Created by glenn on 04/07/2017.
 */
import com.glennou.PROJET7.MyPanel;

import javax.swing.*;
import java.awt.*;


public final class TabActifs extends MyPanel {


    TabActifs() {
        super();


        //TableauExcel tableau = Import.afficher();

        TableauExcel tableau = new TableauExcel("BASE DE DONNEES/actifs.xlsx", "Feuil1");
        Object[][] donnees = tableau.getBody();
        String titre[] = {"Nom", "Type", "Devise", "Place de marché", "Pays"};

        JTable table = new JTable(donnees, titre);
        JScrollPane menuDeroulant = new JScrollPane(table);

        setLayout(new BorderLayout());
        this.add(menuDeroulant, BorderLayout.CENTER);


    }


    public String getTitre() {
        return "Tableau d'actifs";
    }

}