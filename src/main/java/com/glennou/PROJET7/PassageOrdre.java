package com.glennou.PROJET7; /**
 * Created by glenn on 23/08/2017.
 */

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public final class PassageOrdre extends MyPanel  {



    PassageOrdre() throws IOException, InvalidFormatException {
        super();

        JLabel ListeActifs = new JLabel("Choisissez un actif");
        JComboBox comboBoxListeActifs = new JComboBox();
        this.add(ListeActifs);
        this.add(comboBoxListeActifs);

        JLabel DeviseActifs = new JLabel("Devise");
        JComboBox comboBoxDeviseActifs = new JComboBox();
        this.add(DeviseActifs);
        this.add(comboBoxDeviseActifs);

        JLabel ListePortefeuille = new JLabel("Choisissez le portefeuille");
        JComboBox comboBoxListePortefeuille = new JComboBox();
        this.add(ListePortefeuille);
        this.add(comboBoxListePortefeuille);

        JLabel Sens = new JLabel("Sens de l'ordre");
        JComboBox comboBoxAchatVente = new JComboBox();
        String[] TypeOrdre = {"Achat", "Vente", "Achat de couverture", "Vente à découvert"};
        comboBoxAchatVente = new JComboBox(TypeOrdre);
        this.add(Sens);
        this.add(comboBoxAchatVente);

        JLabel quantiteTransaction = new JLabel("Quantité");
        JTextField jtfQuantite = new JTextField("");
        jtfQuantite.setPreferredSize(new Dimension(150, 30));
        this.add(quantiteTransaction);
        this.add(jtfQuantite);

        JLabel prixTransaction = new JLabel("Prix");
        JTextField jtfPrix = new JTextField("");
        jtfPrix.setPreferredSize(new Dimension(150, 30));
        this.add(prixTransaction);
        this.add(jtfPrix);

        JLabel montantTransaction = new JLabel("Montant");
        JTextField jtfMontant = new JTextField("");
        jtfMontant.setPreferredSize(new Dimension(150, 30));
        this.add(montantTransaction);
        this.add(jtfMontant);

        JLabel dateOperation = new JLabel("Date d'opération");
        JTextField jtfDateOperation = new JTextField("");
        jtfDateOperation.setPreferredSize(new Dimension(150,30));
        this.add(dateOperation);
        this.add(jtfDateOperation);

        JLabel dateRL = new JLabel("Date de R/L");
        JTextField jtfDateRL = new JTextField("");
        jtfDateRL.setPreferredSize(new Dimension(150,30));
        this.add(dateRL);
        this.add(jtfDateRL);

        // recupère le fichier excel contenant les actifs
        Workbook wbActif = WorkbookFactory.create(new File("BASE DE DONNEES/actifs.xlsx"));
        Sheet sheetActif = wbActif.getSheet("Feuil1");
        int Nrow = sheetActif.getLastRowNum();
        int colonneNomActif = sheetActif.getColumnOutlineLevel(1);
        int colonneTypeActif = colonneNomActif+1;
        int colonneDeviseActif = colonneNomActif+2;
        int colonnePlaceDeMarcheActif = colonneNomActif+3;
        int colonnePaysActif = colonneNomActif+4;
        Cell cellNomActif = null;

        Map<String, Actif> actifMap = new HashMap<>();
        Set<String> possibleTypes = new HashSet<>();

        for (int i = 1; i <= Nrow; i++) {

            Row row = sheetActif.getRow(i);
            cellNomActif = row.getCell(colonneNomActif);
            Cell cellTypeActif = row.getCell(colonneTypeActif);
            Cell cellDeviseActif = row.getCell(colonneDeviseActif);
            Cell cellPlaceDeMarcheActif = row.getCell(colonnePlaceDeMarcheActif);
            Cell cellPaysActif = row.getCell(colonnePaysActif);

            Actif actifToAdd = new Actif(
                    cellNomActif.getStringCellValue(),
                    cellTypeActif.getStringCellValue(),
                    cellDeviseActif.getStringCellValue(),
                    Optional.ofNullable(cellPlaceDeMarcheActif)
                            .map(Cell::getStringCellValue)
                            .orElse("Pas de place de marché")
            );

            actifMap.put(cellNomActif.getStringCellValue(), actifToAdd);
            possibleTypes.add(cellDeviseActif.getStringCellValue());

            comboBoxListeActifs.addItem(cellNomActif);

        }

        possibleTypes.forEach(devise -> comboBoxDeviseActifs.addItem(devise));


        final Cell finalCellNomActif = cellNomActif;
        ItemListener listener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {


                    String actifName = comboBoxListeActifs.getSelectedItem().toString();
                    Actif actif = actifMap.get(actifName);

                    int indexActif = finalCellNomActif.getRowIndex();
                    comboBoxDeviseActifs.setSelectedItem(actif.devise);
                    comboBoxDeviseActifs.updateUI();

                }
            }
        };

        // recupère le fichier excel contenant les portefeuilles
        Workbook wbPortefeuille = WorkbookFactory.create(new File("BASE DE DONNEES/portefeuilles.xlsx"));
        Sheet sheetPortefeuille = wbPortefeuille.getSheet("Feuil1");
        int Nrowbis = sheetPortefeuille.getLastRowNum();
        int colonneNomPortefeuille = sheetPortefeuille.getColumnOutlineLevel(1);
        int colonneTypePortefeuille = colonneNomPortefeuille+1;
        int colonneDevisePortefeuille = colonneNomPortefeuille+2;
        int colonneModePortefeuille = colonneNomPortefeuille+3;
        Cell cellNomPortefeuille = null;

        Map<String, Portefeuille> portefeuilleMap = new HashMap<>();
        Set<String> possibleTypesBis = new HashSet<>();

        for (int i = 1; i <= Nrowbis; i++) {

            Row row = sheetPortefeuille.getRow(i);
            cellNomPortefeuille = row.getCell(colonneNomPortefeuille);
            Cell cellTypePortefeuille = row.getCell(colonneTypePortefeuille);
            Cell cellDevisePortefeuille = row.getCell(colonneDevisePortefeuille);
            Cell cellModePortefeuille = row.getCell(colonneModePortefeuille);


            Portefeuille portefeuilleToAdd = new Portefeuille(
                    cellNomPortefeuille.getStringCellValue(),
                    cellTypePortefeuille.getStringCellValue(),
                    cellDevisePortefeuille.getStringCellValue(),
                    cellModePortefeuille.getStringCellValue()
            );

            portefeuilleMap.put(cellNomPortefeuille.getStringCellValue(), portefeuilleToAdd);
            possibleTypesBis.add(cellTypePortefeuille.getStringCellValue());

            comboBoxListePortefeuille.addItem(cellNomPortefeuille);

        }


        JButton boutonValider = new JButton("Valider");
        this.add(boutonValider);


        final JComboBox finalComboBoxAchatVente = comboBoxAchatVente;

        boutonValider.addActionListener((ActionEvent e) -> {


            final File file = new File("BASE DE DONNEES/ordres.xlsx");

            try {
                final Workbook workbook = WorkbookFactory.create(file);
                final Sheet sheet = workbook.getSheet("Feuil1");
                final Row titreRow = sheet.getRow(0);
                titreRow.createCell(0).setCellValue("Portefeuille");
                titreRow.createCell(1).setCellValue("Sens");
                titreRow.createCell(2).setCellValue("Actif");
                titreRow.createCell(3).setCellValue("Date d'opération");
                titreRow.createCell(4).setCellValue("Date de R/L");
                titreRow.createCell(5).setCellValue("Quantité");
                titreRow.createCell(6).setCellValue("Prix");
                titreRow.createCell(7).setCellValue("Montant");
                titreRow.createCell(8).setCellValue("Devise");

                int nbRows = sheet.getLastRowNum();
                final Row nouvelleLigne = sheet.createRow(nbRows+1);

                nouvelleLigne.createCell(0).setCellValue(comboBoxListePortefeuille.getSelectedItem().toString());
                nouvelleLigne.createCell(1).setCellValue(finalComboBoxAchatVente.getSelectedItem().toString());
                nouvelleLigne.createCell(2).setCellValue(comboBoxListeActifs.getSelectedItem().toString());
                nouvelleLigne.createCell(3).setCellValue(jtfDateOperation.getText().toString());
                nouvelleLigne.createCell(4).setCellValue(jtfDateRL.getText().toString());
                nouvelleLigne.createCell(5).setCellValue(jtfQuantite.getText().toString());
                nouvelleLigne.createCell(6).setCellValue(jtfPrix.getText().toString());

                double prix = Double.parseDouble(jtfPrix.getText());
                double quantite = Double.parseDouble(jtfQuantite.getText());
                double montantNet = prix * quantite;
                String montant = Double.toString(montantNet);

                jtfMontant.setText(montant);
                jtfMontant.updateUI();


                nouvelleLigne.createCell(7).setCellValue(jtfMontant.getText().toString());
                nouvelleLigne.createCell(8).setCellValue(comboBoxDeviseActifs.getSelectedItem().toString());


                final File file2 = new File("BASE DE DONNEES/ordres2.xlsx");

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



        comboBoxListeActifs.addItemListener(listener);


    }
    public String getTitre() {return "Passage d'ordre";}
}

