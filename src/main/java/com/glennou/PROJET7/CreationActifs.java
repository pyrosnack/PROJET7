package com.glennou.PROJET7; /**
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
import java.io.*;

public final class CreationActifs extends MyPanel {



    CreationActifs() throws IOException {
        super();


        this.setLayout(new GridLayout(20, 1));

        JLabel labelTypeActif = new JLabel("Type d'actif");
        String typeActif[] = {"Action", "Obligation", "Fonds", "TCN", "Forward", "Future", "Swap", "Option"};
        JComboBox comboBoxTypeActif = new JComboBox(typeActif);
        comboBoxTypeActif.setSelectedIndex(0);

        this.add(labelTypeActif);
        this.add(comboBoxTypeActif);


        JLabel labelDeviseActif = new JLabel("Devise de l'actif");
        String deviseActif[] = {"EUR", "USD", "GBP", "GBp", "JPY", "CHF", "SEK", "NOK", "CAD", "AUD"};
        JComboBox comboBoxDeviseActif = new JComboBox(deviseActif);
        comboBoxDeviseActif.setSelectedIndex(0);

        this.add(labelDeviseActif);
        this.add(comboBoxDeviseActif);

        BufferedReader buff = new BufferedReader(new FileReader("TEXTE/place de marche.txt"));
        String line;
        JComboBox comboBoxPlaceDeMarcheActif = new JComboBox();
        comboBoxPlaceDeMarcheActif.setEnabled(true);

        while ((line = buff.readLine()) != null)
        {
            comboBoxPlaceDeMarcheActif.addItem(line);
        }

        buff.close(); //Lecture fini donc on ferme le flux

        JLabel labelPlaceDeMarcheActif = new JLabel("Place de marché");

        this.add(labelPlaceDeMarcheActif);
        this.add(comboBoxPlaceDeMarcheActif);
        comboBoxPlaceDeMarcheActif.setSelectedIndex(790);




        BufferedReader buff2 = new BufferedReader(new FileReader("TEXTE/pays.txt"));
        String line2;
        JComboBox comboBoxPaysActif = new JComboBox();

        while ((line2 = buff2.readLine()) != null)
        {
            comboBoxPaysActif.addItem(line2);
        }

        buff2.close(); //Lecture fini donc on ferme le flux

        JLabel labelPaysActif = new JLabel("Pays");

        this.add(labelPaysActif);
        this.add(comboBoxPaysActif);
        comboBoxPaysActif.setSelectedIndex(67);




        JLabel labelNomActif = new JLabel("Nom");
        JTextField jtf = new JTextField("");
        jtf.setPreferredSize(new Dimension(150, 30));

        this.add(labelNomActif);
        this.add(jtf);




        JLabel labelIsinActif = new JLabel("ISIN");
        JTextField jtf2 = new JTextField("");
        jtf2.setPreferredSize(new Dimension(150, 30));

        this.add(labelIsinActif);
        this.add(jtf2);


        JLabel labelBBGIDActif = new JLabel("Bloomberg ID");
        JTextField jtf3 = new JTextField("");
        jtf2.setPreferredSize(new Dimension(150, 30));

        this.add(labelBBGIDActif);
        this.add(jtf3);



        JLabel labelTickerActif = new JLabel("Ticker Bloomberg");
        JTextField jtf4 = new JTextField("");
        jtf2.setPreferredSize(new Dimension(150, 30));

        this.add(labelTickerActif);
        this.add(jtf4);


        JButton boutonValider = new JButton("Valider");
        this.add(boutonValider);


        boutonValider.addActionListener((ActionEvent e) -> {


                final File file = new File("BASE DE DONNEES/actifs.xlsx");

                try {
                    final Workbook workbook = WorkbookFactory.create(file);
                    final Sheet sheet = workbook.getSheet("Feuil1");
                    final Row titreRow = sheet.getRow(0);
                    titreRow.createCell(0).setCellValue("Nom");
                    titreRow.createCell(1).setCellValue("Type");
                    titreRow.createCell(2).setCellValue("Devise");
                    titreRow.createCell(3).setCellValue("Place de marché");
                    titreRow.createCell(4).setCellValue("Pays");

                    int nbRows = sheet.getLastRowNum();
                    final Row nouvelleLigne = sheet.createRow(nbRows+1);

                    nouvelleLigne.createCell(0).setCellValue(jtf.getText().toString());
                    nouvelleLigne.createCell(1).setCellValue(comboBoxTypeActif.getSelectedItem().toString());
                    nouvelleLigne.createCell(2).setCellValue(comboBoxDeviseActif.getSelectedItem().toString());
                    nouvelleLigne.createCell(3).setCellValue(comboBoxPlaceDeMarcheActif.getSelectedItem().toString());
                    nouvelleLigne.createCell(4).setCellValue(comboBoxPaysActif.getSelectedItem().toString());



                final File file2 = new File("BASE DE DONNEES/actifs2.xlsx");

                final FileOutputStream fos = new FileOutputStream(file2);

                workbook.write(fos);
                fos.close();
                workbook.close();
                file.delete();
                file2.renameTo(file);


                } catch (InvalidFormatException | IOException i) {
                    i.printStackTrace();
                }



            //XSSFWorkbook wb = new XSSFWorkbook();
            //2. Créer une Feuille de calcul vide
            //Sheet feuille = wb.createSheet("new sheet");
            //3. Créer une ligne et mettre qlq chose dedans
            //Row row = feuille.createRow((short)0);
            //4. Créer une Nouvelle cellule
            //Cell cell = row.createCell(0);
            //5. Donner la valeur
            //cell.setCellValue(comboBoxTypeActif.getSelectedItem().toString());

            //Ajouter d'autre cellule avec différents type
   ///*int*///row.createCell(1).setCellValue(comboBoxDeviseActif.getSelectedItem().toString());
   ///*char*/row.createCell(2).setCellValue(comboBoxPlaceDeMarcheActif.getSelectedItem().toString());
                // ///*String*/row.createCell(3).setCellValue("chaine");
 //*boolean*/row.createCell(4).setCellValue(false);

        });

    }


    public String getTitre() {return "Creation d'actifs";}
}