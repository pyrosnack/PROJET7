package com.glennou.PROJET7; /**
 * Created by glenn on 13/07/2017.
 */
import com.glennou.PROJET7.MyPanel;

import javax.swing.*;
import java.awt.*;

public final class Pays extends MyPanel {


    Pays() {
        super();

        TableauExcel tableau = new TableauExcel("EXCEL/pays.xls", "Tableau");
        Object[][] donnees = tableau.getBody();
        String titre[] = {"Pays", "Drapeau", "code 1", "ISO 3166-1 alpha-2", "ISO 3166-1 alpha-2", "TKN (Six-Telekurs)"};



        JTable table = new JTable(donnees, titre);
        JScrollPane menuDeroulant = new JScrollPane(table);

        setLayout(new BorderLayout());
        this.add(menuDeroulant, BorderLayout.CENTER);

    }


    public String getTitre() {
        return "Pays";
    }
}