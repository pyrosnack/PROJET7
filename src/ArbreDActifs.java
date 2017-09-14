/**
 * Created by glenn on 04/07/2017.
 */
import javax.swing.JButton;

public class ArbreDActifs extends MyPanel {

    private JButton buttonToto = new JButton("Fermer l'application !!!");

    // Constructeur. Ce constructeur est hyper important car il n'a aucun paramètre:
    // C'est celui utilisé par la réflection pour la création d'une instance.
    ArbreDActifs() {
        super();

        this.add(buttonToto);
        buttonToto.addActionListener(event -> System.exit(0));
    }


    public String getTitre() {
        return "Arbre d'actifs";
    }
}
