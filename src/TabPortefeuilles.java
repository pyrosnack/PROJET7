/**
 * Created by glenn on 04/07/2017.
 */
/**
 * Created by glenn on 04/07/2017.
 */

import javax.swing.*;
import java.awt.*;

public final class TabPortefeuilles extends MyPanel {

    //private JButton buttonToto = new JButton("Fermer l'application dans tab actif!!!");

    TabPortefeuilles() {
        super();

        //this.add(buttonToto);
        //buttonToto.addActionListener(event -> System.exit(0));


        //TableauExcel tableau = Import.afficher();

        TableauExcel tableau = new TableauExcel("BASE DE DONNEES/portefeuilles.xlsx", "Feuil1");
        Object[][] donnees = tableau.getBody();
        String titre[] = {"Nom", "Type", "Devise", "Mode"};

        JTable table = new JTable(donnees, titre);
        JScrollPane menuDeroulant = new JScrollPane(table);

        setLayout(new BorderLayout());
        this.add(menuDeroulant, BorderLayout.CENTER);
    }

    public String getTitre() {return "Tableau de portefeuilles";
    }





}