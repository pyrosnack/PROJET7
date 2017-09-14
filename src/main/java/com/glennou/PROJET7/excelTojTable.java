package com.glennou.PROJET7;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by glenn on 17/07/2017.
 */
public class excelTojTable {


    static Vector headers = new Vector();
    static Vector data = new Vector();


    static void fillData(File file) {

        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    excelTojTable.class.
                            getName()).log(Level.SEVERE,
                    null, ex);
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheet(0);

        headers.clear();
        for (int i = 0; i < sheet.getColumns(); i++) {
            Cell cell1 = sheet.getCell(i, 0);
            headers.add(cell1.getContents());
        }

        data.clear();
        for (int j = 1; j < sheet.getRows(); j++) {
            Vector d = new Vector();
            for (int i = 0; i < sheet.getColumns(); i++) {

                Cell cell = sheet.getCell(i, j);

                d.add(cell.getContents());

            }
            d.add("\n");
            data.add(d);
        }
    }
}
