/**
 * Created by glenn on 20/08/2017.
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

public final class FichePortefeuille extends MyPanel {



  FichePortefeuille() throws IOException, InvalidFormatException {
    super();


    JComboBox comboBoxListePortefeuille = new JComboBox();
    JComboBox<String> comboBoxTypePortefeuille = new JComboBox<>();
    JComboBox comboBoxDevisePortefeuille = new JComboBox();
    JComboBox comboBoxModePortefeuille = new JComboBox();


    // recupère le fichier excel
    Workbook wb = WorkbookFactory.create(new File("BASE DE DONNEES/portefeuilles.xlsx"));
    Sheet sheet = wb.getSheet("Feuil1");
    int Nrow = sheet.getLastRowNum();
    int colonneNomPortefeuille = sheet.getColumnOutlineLevel(1);
    int colonneTypePortefeuille = colonneNomPortefeuille+1;
    int colonneDevisePortefeuille = colonneNomPortefeuille+2;
    int colonneModePortefeuille = colonneNomPortefeuille+3;

    Cell cellNomPortefeuille = null;

    Map<String, Portefeuille> portefeuilleMap = new HashMap<>();
    Set<String> possibleTypes = new HashSet<>();

    for (int i = 1; i <= Nrow; i++) {

      Row row = sheet.getRow(i);
      cellNomPortefeuille = row.getCell(colonneNomPortefeuille);
      Cell cellTypePortefeuille = row.getCell(colonneTypePortefeuille);
      Cell cellDevisePortefeuille = row.getCell(colonneDevisePortefeuille);
      Cell cellModePortefeuille = row.getCell(colonneModePortefeuille);

      Portefeuille portefeuilleToAdd = new Portefeuille(
              cellNomPortefeuille.getStringCellValue(),
              cellTypePortefeuille.getStringCellValue(),
              cellDevisePortefeuille.getStringCellValue(),
              cellModePortefeuille.getStringCellValue());


      portefeuilleMap.put(cellNomPortefeuille.getStringCellValue(), portefeuilleToAdd);
      possibleTypes.add(cellTypePortefeuille.getStringCellValue());

      comboBoxListePortefeuille.addItem(cellNomPortefeuille);

    }

    possibleTypes.forEach(type -> comboBoxTypePortefeuille.addItem(type));


    final Cell finalCellNomPortefeuille = cellNomPortefeuille;
    ItemListener listener = new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {


          String portefeuilleName = comboBoxListePortefeuille.getSelectedItem().toString();
          Portefeuille portefeuille = portefeuilleMap.get(portefeuilleName);

          int indexPortefeuille = finalCellNomPortefeuille.getRowIndex();
          comboBoxTypePortefeuille.setSelectedItem(portefeuille.type);
          comboBoxTypePortefeuille.updateUI();

        }
      }
    };

    comboBoxListePortefeuille.addItemListener(listener);

    this.add(comboBoxListePortefeuille);
    this.add(comboBoxTypePortefeuille);
    this.add(comboBoxDevisePortefeuille);
    this.add(comboBoxModePortefeuille);

  }



  public String getTitre() {return "Fiche de portefeuille";}
}