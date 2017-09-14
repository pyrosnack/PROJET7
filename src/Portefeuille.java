/**
 * Created by glenn on 25/08/2017.
 */
public class Portefeuille {

  String nom;
  String type;
  String devise;
  String mode;



  public Portefeuille(final String parNom,
               final String parType,
               final String parDevise,
               final String parMode) {
    nom = parNom;
    type = parType;
    devise = parDevise;
    mode = parMode;
  }
}