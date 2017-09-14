package com.glennou.PROJET7; /**
 * Created by glenn on 04/07/2017.
 */
import javax.swing.*;
import java.awt.*;

public final class PlaceDeCotation extends MyPanel {


    PlaceDeCotation() {
        super();

        TableauExcel tableau = new TableauExcel("EXCEL/place de cotation 2.xls", "Tableau");
        Object[][] donnees = tableau.getBody();
        String titre[] = {"Place de cotation", "Description", "Pays", "MIC"};

        JTable table = new JTable(donnees, titre);
        JScrollPane menuDeroulant = new JScrollPane(table);

        setLayout(new BorderLayout());
        this.add(menuDeroulant, BorderLayout.CENTER);

    }


    public String getTitre() {
        return "Places de cotation";
    }
}

