package boggle.gui.components.ecrans;

/**
* Classe abstraite EcranFactory
* @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
* @version 1.0
*
*/
public abstract class EcranFactory {

	/**
	* Cette méthode retourne l'instance adéquate en fonction du typeEcran mis en paramètre
	* @param typeEcran 		Renseigne un TypeEcran
	* @return Ecran ou null
	*/
	public static Ecran getInstance(TypeEcrans typeEcran ){
		
		if(TypeEcrans.MENU == typeEcran) return EcranMenu.getInstance();
		if(TypeEcrans.SELECTION_JOUEURS == typeEcran) return EcranSelectionJoueurs.getInstance();
		if(TypeEcrans.JEU  == typeEcran) return EcranJeu.getInstance();
		if(TypeEcrans.OPTIONS  == typeEcran) return EcranOptions.getInstance();
		if(TypeEcrans.SCORES == typeEcran) return EcranScores.getInstance();
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
