package com.glennou.PROJET7;/*
 *      _   _   _       ___  ___   _____            ___       ___  ___   _____
 *     | | | | | |     /   |/   | |  _  \          /   |     /   |/   | /  ___/
 *     | | | | | |    / /|   /| | | |_| |         / /| |    / /|   /| | | |___
 *  _  | | | | | |   / / |__/ | | |  ___/        / / | |   / / |__/ | | \___  \
 * | |_| | | |_| |  / /       | | | |           / /  | |  / /       | |  ___| |
 * \_____/ \_____/ /_/        |_| |_|          /_/   |_| /_/        |_| /_____/
 *
 *
 * Jump Asset Management Solution Jump Informatique. Tous droits réservés.
 * Ce programme est protégé par la loi relative au droit d'auteur et par les conventions internationales.
 * Toute reproduction ou distribution partielle ou totale du logiciel, par quelque moyen que ce soit, est
 * strictement interdite. Toute personne ne respectant pas ces dispositions se rendra coupable du délit de
 * contrefaçon et sera passible des sanctions pénales prévues par la loi.
 * daté du 26/08/2017.
 */

import javax.swing.JTextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * User: <a href="mailto:g.fortune@jump-informatique.com">g.fortune</a>
 * Date: 26/08/2017
 */
public  class ClavierListener implements KeyListener {


    public void keyPressed(KeyEvent event) {

      if(event.getKeyCode() == 13){

        System.out.println("toto");

      }

    }

    public void keyReleased(KeyEvent event) {
      System.out.println("Code touche relâchée : " + event.getKeyCode() + " - caractère touche relâchée : " + event.getKeyChar());

    }

    public void keyTyped(KeyEvent event) {

    }
}
