package com.glennou.PROJET7; /**
 * Created by glenn on 27/08/2017.
 */

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class ModifierActif extends MyPanel {



  ModifierActif() throws IOException, InvalidFormatException {
    super();


    JComboBox comboBoxListeActifs = new JComboBox();
    JComboBox<String> comboBoxTypeActifs = new JComboBox<>();
    JComboBox comboBoxDeviseActifs = new JComboBox();
    JComboBox comboBoxPlaceDeMarcheActifs = new JComboBox();
    JComboBox comboBoxPaysActifs = new JComboBox();

    // recupère le fichier excel
    Workbook wb = WorkbookFactory.create(new File("BASE DE DONNEES/actifs.xlsx"));
    Sheet sheet = wb.getSheet("Feuil1");
    int Nrow = sheet.getLastRowNum();
    int colonneNomActif = sheet.getColumnOutlineLevel(1);
    int colonneTypeActif = colonneNomActif+1;
    int colonneDeviseActif = colonneNomActif+2;
    int colonnePlaceDeMarcheActif = colonneNomActif+3;
    int colonnePaysActif = colonneNomActif+4;
    Cell cellNomActif = null;

    Map<String, Actif> actifMap = new HashMap<>();
    Set<String> possibleTypes = new HashSet<>();

    for (int i = 1; i <= Nrow; i++) {

      Row row = sheet.getRow(i);
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
      possibleTypes.add(cellTypeActif.getStringCellValue());

      comboBoxListeActifs.addItem(cellNomActif);

    }

    possibleTypes.forEach(type -> comboBoxTypeActifs.addItem(type));


    final Cell finalCellNomActif = cellNomActif;
    ItemListener listener = new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {


          String actifName = comboBoxListeActifs.getSelectedItem().toString();
          Actif actif = actifMap.get(actifName);

          int indexActif = finalCellNomActif.getRowIndex();
          comboBoxTypeActifs.setSelectedItem(actif.type);
          comboBoxTypeActifs.updateUI();

        }
      }
    };

    comboBoxListeActifs.addItemListener(listener);

    this.add(comboBoxListeActifs);
    this.add(comboBoxTypeActifs);
    this.add(comboBoxDeviseActifs);
    this.add(comboBoxPlaceDeMarcheActifs);
    this.add(comboBoxPaysActifs);

  }



  public String getTitre() {return "Modification d'actif";}
}