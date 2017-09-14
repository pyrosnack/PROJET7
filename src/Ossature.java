/**
 * Created by glenn on 04/07/2017.
 */

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Ossature extends JFrame {
    /**
     *
     */


    private static final long serialVersionUID = 1L;

    private JMenuBar menuBar = new JMenuBar();
    public JTabbedPane tabPanel = new JTabbedPane();


    public class MyMenu {
        boolean estFeuille;
        //sousMenus devient une liste parce que c'est plus facile a manipuler (via la stream API)
        List<MyMenu> sousMenus;
        String label;
        JMenuItem gui;

        Class<? extends MyPanel> panelClass;

        MyMenu(String label) {
            estFeuille = true;
            this.label = label;
        }

        MyMenu(String label, MyMenu... sousMenus) {
            estFeuille = false;
            this.label = label;
            this.sousMenus = Arrays.asList(sousMenus);
        }

        // Remarque: Je n'utilise plus de rawType, un autre wildcard suffit :)
        MyMenu(Class<? extends MyPanel> panelClass) {
            estFeuille = true;
            this.panelClass = panelClass;
        }

        // Un constructeur qui prend en entrée un vararg de Classes pour écrire moins de lignes:
        @SafeVarargs
        // Je Rajoute cette annotation qui dit que je sais ce que je fais sur les wildcards (pour éviter les warning de varargs de wildcard)
        MyMenu(String label, Class<? extends MyPanel>... subPanels) {
            this.label = label;
            estFeuille = false;
            // si tu as un list (1,2,3) et tu fait list.map(x -> x * 2) ca donne la liste (2,4,6)
            sousMenus = Arrays.asList(subPanels).stream() //Stream sert juste a manipuler mieux une liste
                    .map(MyMenu::new) // Ca ca s'applique sur chaque élément du stream( = de la liste)
                    .collect(Collectors.toList()); // Collecte sert juste a revenir sur une liste (et pas rester sur un stream)
        }

        JComponent buildGui() {
            if (gui != null) {
                return gui;
            }

            if (estFeuille) {
                if (label != null) {
                    gui = new JMenuItem(label);
                } else {
                    try {
                        MyPanel newPanel = panelClass.newInstance();
                        String titre = newPanel.getTitre();
                        int selectedIndex = tabPanel.getSelectedIndex();

                        gui = new JMenuItem(titre);
                        // Au passage on modifie le action listener pour qu'il créer un nouveau panel à chaque clic
                        // comme ca on peut avoir plusieurs fois le même écran.
                        gui.addActionListener(
                                event -> {
                                    try {
                                        tabPanel.add(titre, panelClass.newInstance());
                                        tabPanel.setSelectedIndex(tabPanel.getTabCount() - 1);
                                    } catch (InstantiationException | IllegalAccessException e) {
                                        System.out.println("quelqu'un s'est planté avec la réfléction dans les panels");
                                    }
                                }
                        );
                    } catch (InstantiationException ie) {
                        System.out.println("quelqu'un s'est planté avec la réflection sur les menus :p");
                    } catch (IllegalAccessException iae) {
                        System.out.println("quelqu'un s'est planté avec la réflection sur les panels :p");
                    }
                }
            } else {
                gui = new JMenu(label);
                for (MyMenu sousMenu : sousMenus) {
                    gui.add(sousMenu.buildGui());
                }
            }
            return gui;
        }
    }

//***************************** MENU REFERENTIEL ***********************

    // On utilise le varargs sur les classes:


    private MyMenu portefeuille = new MyMenu("Portefeuilles", FichePortefeuille.class, VisualiseurPortefeuille.class, TabPortefeuilles.class, ArbreDePortefeuilles.class, APIYahoo.class);
    private MyMenu actif = new MyMenu("Actifs", FicheActif.class, TabActifs.class, ArbreDActifs.class);
    private MyMenu ordre = new MyMenu(VisualiseurOrdre.class);
    private MyMenu autre = new MyMenu("Autres", PlaceDeCotation.class, PlaceDeMarche.class, Ville.class, Pays.class);
    private MyMenu ost = new MyMenu("Visualiseur d'OST");

    private JMenu theme = new JMenu("Thèmes couleur");
    private JMenu classic = new JMenu("Classic");
    private JMenu jtattoo = new JMenu("Jtattoo");
    private JMenu synthetic = new JMenu("Synthetic");
    private JMenu pgs = new JMenu("PGS");


    private MyMenu visualisation = new MyMenu("Visualisation", actif, portefeuille, ordre, ost, autre);
    private MyMenu edition = new MyMenu("Edition", ModifierActif.class, ModifierPortefeuille.class);
    private MyMenu creation = new MyMenu("Création", CreationActifs.class, CreationPortefeuilles.class);
    private MyMenu importexport = new MyMenu("Import/Export", Import.class, Export.class);

    private MyMenu referentiel = new MyMenu("REFERENTIEL", visualisation, edition, creation, importexport);
    private MyMenu frontoffice = new MyMenu("FRONT OFFICE", PassageOrdre.class);
    private JMenu parametre = new JMenu("PARAMETRES");


// INITIALISATION DE LA FENETRE PRINCIPALE


    public Ossature() {


        this.setExtendedState(Ossature.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //REFERENTIEL
        this.menuBar.add(referentiel.buildGui());

        //FRONT OFFICE
        this.menuBar.add(frontoffice.buildGui());

        //PARAMETRE
        this.menuBar.add(parametre);
        parametre.add(theme);
        theme.add(classic);
        theme.add(jtattoo);
        theme.add(synthetic);
        theme.add(pgs);

        //region region theme

        //LISTE DES THEMES COULEUR
        LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        for (LookAndFeelInfo lookAndFeelInfo : lookAndFeels) {
            JMenuItem item = new JMenuItem(lookAndFeelInfo.getName());
            item.addActionListener(event -> {
                try {
                    // Set the look and feel for the frame and update the UI
                    // to use a new selected look and feel.
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            classic.add(item);
        }


        String[][] listeThemeJtattoo = {
                {"Acryl", "com.jtattoo.plaf.acryl.AcrylLookAndFeel"},
                {"Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel"},
                {"Aluminium", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"},
                {"Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel"},
                {"Fast", "com.jtattoo.plaf.fast.FastLookAndFeel"},
                {"Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel"},
                {"Hifi", "com.jtattoo.plaf.hifi.HiFiLookAndFeel"},
                {"Luna", "com.jtattoo.plaf.luna.LunaLookAndFeel"},
                {"McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel"},
                {"Mint", "com.jtattoo.plaf.mint.MintLookAndFeel"},
                {"Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel"},
                {"Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel"},
                {"Texture", "com.jtattoo.plaf.texture.TextureLookAndFeel"}};

        String[][] listeThemeSynthetica = {
                {"Synthetica", "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel"},
                {"Alu Oxide", "de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel"},
                {"Black Eye", "de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel"},
                {"Black Moon", "de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel"},
                {"Black Star", "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"},
                {"Blue Ice", "de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel"},
                {"Black Light", "de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel"},
                {"Blue Moon", "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel"},
                {"Black Steel", "de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel"},
                {"Classy", "de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel"},
                {"Green Dream", "de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel"},
                {"Mauve Metallic", "de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel"},
                {"Orange Metallic", "de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel"},
                {"Plain", "de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel"},
                {"Silver Moon", "de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel"},
                {"Simple 2D", "de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel"},
                {"Sky Metallic", "de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel"},
                {"White Vision", "de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel"}};

        String[][] listeThemePGS = {
                {"PGS", "com.pagosoft.plaf.PgsLookAndFeel"}};


        for (String[] themeJtattoo : listeThemeJtattoo) {
            String nomJtattoo = themeJtattoo[0];
            String classeJtattoo = themeJtattoo[1];
            JMenuItem nomThemeJtattoo = new JMenuItem(nomJtattoo);
            nomThemeJtattoo.addActionListener(event -> {
                try {

                    UIManager.setLookAndFeel(classeJtattoo);
                    AcrylLookAndFeel.setTheme(nomJtattoo, "", "");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            jtattoo.add(nomThemeJtattoo);
        }

        for (String[] themeSynthetic : listeThemeSynthetica) {
            String nomSynthetic = themeSynthetic[0];
            String classeSynthetic = themeSynthetic[1];
            JMenuItem nomThemeSynthetic = new JMenuItem(nomSynthetic);
            nomThemeSynthetic.addActionListener(event -> {
                try {

                    UIManager.setLookAndFeel(classeSynthetic);
                    AcrylLookAndFeel.setTheme(nomSynthetic, "", "");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            synthetic.add(nomThemeSynthetic);
        }

        for (String[] themePGS : listeThemePGS) {
            String nomPGS = themePGS[0];
            String classePGS = themePGS[1];
            JMenuItem nomThemePGS = new JMenuItem(nomPGS);
            nomThemePGS.addActionListener(event -> {
                try {

                    UIManager.setLookAndFeel(classePGS);
                    AcrylLookAndFeel.setTheme(nomPGS, "", "");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pgs.add(nomThemePGS);
        }

        //endregion

//AFFICHAGE DE LA FENETRE PRINCIPALE ET DE LA BARRE DES MENUS

        this.setJMenuBar(menuBar);
        this.setVisible(true);
        this.setContentPane(tabPanel);


    }
}



