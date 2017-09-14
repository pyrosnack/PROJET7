/**
 * Created by glenn on 13/07/2017.
 */
import javax.swing.*;
import java.awt.*;

public final class Ville extends MyPanel {


    Ville() {
        super();

        TableauExcel tableau = new TableauExcel("EXCEL/ville.xls", "Tableau");
        Object[][] donnees = tableau.getBody();
        String titre[] = {"Villes", "Pays"};

        JTable table = new JTable(donnees, titre);
        JScrollPane menuDeroulant = new JScrollPane(table);

        setLayout(new BorderLayout());
        this.add(menuDeroulant, BorderLayout.CENTER);

    }


    public String getTitre() {
        return "Villes";
    }
}