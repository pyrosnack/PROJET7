package com.glennou.PROJET7;

import javax.swing.*;

/**
 * Created by glenn on 16/07/2017.
 */
public final class Export extends MyPanel {

    private JButton exporter = new JButton("Exporter");

    Export() {
        super();

        this.add(exporter);
        exporter.addActionListener(event -> System.exit(0));
    }


    public String getTitre() {
        return "Export de données";
    }
}
