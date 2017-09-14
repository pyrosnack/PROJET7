package com.glennou.PROJET7; /**
 * Created by glenn on 04/07/2017.
 */
/**
 * Created by glenn on 04/07/2017.
 */

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public final class CreationPortefeuilles extends MyPanel {

    //private JButton buttonToto = new JButton("Fermer l'application dans tab actif!!!");


    CreationPortefeuilles() throws IOException {
        super();

        this.setLayout(new GridLayout(20, 1));

        JLabel labelNomPortefeuille = new JLabel("Nom");
        JTextField jtf = new JTextField("");
        jtf.setPreferredSize(new Dimension(150, 30));

        this.add(labelNomPortefeuille);
        this.add(jtf);


        JLabel labelTypePortefeuille = new JLabel("Type de portefeuille");
        String typePortefeuille[] = {"Gestion collective", "Gestion privée"};
        JComboBox comboBoxTypePortefeuille = new JComboBox(typePortefeuille);
        comboBoxTypePortefeuille.setSelectedIndex(0);

        this.add(labelTypePortefeuille);
        this.add(comboBoxTypePortefeuille);


        JLabel labelDevisePortefeuille = new JLabel("Devise du portefeuille");
        String devisePortefeuille[] = {"EUR", "USD", "GBP", "GBp", "JPY", "CHF", "SEK", "NOK", "CAD", "AUD"};
        JComboBox comboBoxDevisePortefeuille = new JComboBox(devisePortefeuille);
        comboBoxDevisePortefeuille.setSelectedIndex(0);

        this.add(labelDevisePortefeuille);
        this.add(comboBoxDevisePortefeuille);


        JLabel labelModePortefeuille = new JLabel("Mode du portefeuille");
        String modePortefeuille[] = {"Front", "Back"};
        JComboBox comboBoxModePortefeuille = new JComboBox(modePortefeuille);
        comboBoxModePortefeuille.setSelectedIndex(0);

        this.add(labelModePortefeuille);
        this.add(comboBoxModePortefeuille);


        JButton boutonValider = new JButton("Valider");
        this.add(boutonValider);


        boutonValider.addActionListener((ActionEvent e) -> {


            final File file = new File("BASE DE DONNEES/portefeuilles.xlsx");

            try {
                final Workbook workbook = WorkbookFactory.create(file);
                final Sheet sheet = workbook.getSheet("Feuil1");
                final Row titreRow = sheet.getRow(0);
                titreRow.createCell(0).setCellValue("Nom");
                titreRow.createCell(1).setCellValue("Type");
                titreRow.createCell(2).setCellValue("Devise");
                titreRow.createCell(3).setCellValue("Mode");


                int nbRows = sheet.getLastRowNum();
                final Row nouvelleLigne = sheet.createRow(nbRows + 1);

                nouvelleLigne.createCell(0).setCellValue(jtf.getText().toString());
                nouvelleLigne.createCell(1).setCellValue(comboBoxTypePortefeuille.getSelectedItem().toString());
                nouvelleLigne.createCell(2).setCellValue(comboBoxDevisePortefeuille.getSelectedItem().toString());
                nouvelleLigne.createCell(3).setCellValue(comboBoxModePortefeuille.getSelectedItem().toString());


                final File file2 = new File("BASE DE DONNEES/portefeuilles2.xlsx");

                final FileOutputStream fos = new FileOutputStream(file2);

                workbook.write(fos);
                fos.close();
                workbook.close();
                file.delete();
                file2.renameTo(file);

            } catch (InvalidFormatException | IOException i) {
                i.printStackTrace();
            }


        });
    }



    public String getTitre() {
        return "Creation de portefeuilles";
    }
}