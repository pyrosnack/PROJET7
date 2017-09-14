/**
 * Created by glenn on 13/07/2017.
 */
import javax.swing.*;
import java.awt.*;

public final class PlaceDeMarche extends MyPanel {


    PlaceDeMarche() {
        super();

        TableauExcel tableau = new TableauExcel("EXCEL/place de marche.xls", "Tableau");
        Object[][] donnees = tableau.getBody();
        String titre[] = {"Place", "Type", "MIC"};


        JTable table = new JTable(donnees, titre);
        JScrollPane menuDeroulant = new JScrollPane(table);

        setLayout(new BorderLayout());
        this.add(menuDeroulant, BorderLayout.CENTER);

    }


    public String getTitre() {
        return "Places de marché";
    }
}