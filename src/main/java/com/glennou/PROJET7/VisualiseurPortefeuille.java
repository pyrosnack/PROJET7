package com.glennou.PROJET7; /**
 * Created by glenn on 26/08/2017.
 */

import com.sun.istack.internal.NotNull;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.html.ObjectView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class VisualiseurPortefeuille extends MyPanel {

  JTable table = null;


  VisualiseurPortefeuille() throws IOException, InvalidFormatException {
    super();


    JComboBox<String> comboBoxChoixPortefeuille = new JComboBox<>();
    setLayout(new BorderLayout());
    this.add(comboBoxChoixPortefeuille, BorderLayout.SOUTH);

    // recupère le fichier excel contenant les portefeuilles
    Workbook wbPortefeuille = WorkbookFactory.create(new File("BASE DE DONNEES/portefeuilles.xlsx"));
    Sheet sheetPortefeuille = wbPortefeuille.getSheet("Feuil1");
    int Nrow = sheetPortefeuille.getLastRowNum();
    int colonneNomPortefeuille = sheetPortefeuille.getColumnOutlineLevel(1);
    int colonneTypePortefeuille = colonneNomPortefeuille + 1;
    int colonneDevisePortefeuille = colonneNomPortefeuille + 2;
    int colonneModePortefeuille = colonneNomPortefeuille + 3;
    Cell cellNomPortefeuille = null;

    Map<String, Portefeuille> portefeuilleMap = new HashMap<>();
    Set<String> possibleTypes = new HashSet<>();

    String titre[] = {"Actif", "Quantité", "PRU", "Montant", "Devise"};
    final DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.setColumnIdentifiers(titre);
    table = new JTable(tableModel);


    JScrollPane menuDeroulant = new JScrollPane(table);
    this.add(menuDeroulant, BorderLayout.CENTER);

    for (int i = 1; i <= Nrow; i++) {

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
      possibleTypes.add(cellTypePortefeuille.getStringCellValue());

      comboBoxChoixPortefeuille.addItem(cellNomPortefeuille.getStringCellValue());

    }
    comboBoxChoixPortefeuille.addActionListener(event -> metAJourLeTableauDesOrdres(
            comboBoxChoixPortefeuille.getItemAt(comboBoxChoixPortefeuille.getSelectedIndex())));
    metAJourLeTableauDesOrdres(
            comboBoxChoixPortefeuille.getItemAt(comboBoxChoixPortefeuille.getSelectedIndex()));
  }

  private void metAJourLeTableauDesOrdres(@NotNull final String portefeuilleSelectionne) {
    // recupère le fichier excel contenant les ordres
    try {
      Workbook wbOrdre = WorkbookFactory.create(new File("BASE DE DONNEES/ordres.xlsx"));
      Sheet sheetOrdre = wbOrdre.getSheet("Feuil1");
      int Nrowbis = sheetOrdre.getLastRowNum();
      int colonneNomPortefeuilleBis = sheetOrdre.getColumnOutlineLevel(1);
      int colonneSens = colonneNomPortefeuilleBis + 1;
      int colonneNomActif = colonneNomPortefeuilleBis + 2;
      int colonneDateOperation = colonneNomPortefeuilleBis + 3;
      int colonneDateRL = colonneNomPortefeuilleBis + 4;
      int colonneQuantite = colonneNomPortefeuilleBis + 5;
      int colonnePrix = colonneNomPortefeuilleBis + 6;
      int colonneMontant = colonneNomPortefeuilleBis + 7;
      int colonneDevise = colonneNomPortefeuilleBis + 8;


      Map<String, Portefeuille> portefeuilleMapBis = new HashMap<>();
      Set<String> possibleTypesBis = new HashSet<>();
      DefaultTableModel locModel = (DefaultTableModel) table.getModel();
      while (locModel.getRowCount() > 0) {
        locModel.removeRow(0);
      }
      for (int i = 1; i <= Nrowbis; i++) {

        Row row = sheetOrdre.getRow(i);
        Cell cellNomPortefeuilleBis = row.getCell(colonneNomPortefeuilleBis);
        Cell cellSens = row.getCell(colonneSens);
        Cell cellNomActif = row.getCell(colonneNomActif);
        Cell cellDateOperation = row.getCell(colonneDateOperation);
        Cell cellDateRL = row.getCell(colonneDateRL);
        Cell cellQuantite = row.getCell(colonneQuantite);
        Cell cellPrix = row.getCell(colonnePrix);
        Cell cellMontant = row.getCell(colonneMontant);
        Cell cellDevise = row.getCell(colonneDevise);


        CompositionPortefeuille compoPortefeuilleToAdd = new CompositionPortefeuille(
                cellNomActif.getStringCellValue(),
                cellQuantite.getStringCellValue(),
                cellPrix.getStringCellValue(),
                cellMontant.getStringCellValue(),
                cellDevise.getStringCellValue()
        );

        if (cellNomPortefeuilleBis.getStringCellValue().equals(portefeuilleSelectionne)) {
          locModel.addRow(new Object[]{cellNomActif, cellQuantite, cellPrix, cellMontant, cellDevise});
//        table.add(comboNom, 1);
//        table.add(((Component) cellQuantite), 2);
//        table.add(((Component) cellPrix), 3);
//        table.add(((Component) cellMontant), 4);
//        table.add(((Component) cellDevise), 5);

        }

      }
    } catch (Throwable exception) {
      System.out.println("On a eu une exception quand on a ouvert un fichier d'ordres");
    }

  }


  public String getTitre() {
    return "Visualiseur de portefeuille";
  }

}