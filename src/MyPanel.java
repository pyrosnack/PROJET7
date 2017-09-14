/**
 * Created by glenn on 04/07/2017.
 */
import javax.swing.*;

abstract public class MyPanel extends JPanel {
    // Ya déja tout dans le JPanel, il me manque juste le titre
    // (en vrai on pourrai juste réutiliser des champs du JPanel,
    // mais pour l'example on va faire comme ci y avait pas le titre)

    abstract String getTitre();

}
