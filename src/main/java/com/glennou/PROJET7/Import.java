package com.glennou.PROJET7;

import javax.swing.*;
import java.io.File;


/**
 * Created by glenn on 16/07/2017.
 */
public final class Import extends MyPanel {

    static String completeFileName = "";
    private JButton importer = new JButton("Importer");
    private JButton selectionner = new JButton("Sélectionner un fichier");

    Import() {
        super();

        this.add(importer);
        this.add(selectionner);
        selectionner.addActionListener(event -> selection());
        importer.addActionListener(event -> afficher());


    }

    public void selection() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            completeFileName =  "Excel/"+selectedFile.getName();

        }
    }

    public static TableauExcel afficher() {


        TableauExcel tableau = new TableauExcel(completeFileName, "Tableau");

        return tableau;
    }

    public String getTitre() {
        return "Import de données";
    }
}
