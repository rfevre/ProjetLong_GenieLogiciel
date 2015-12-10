package boggle.gui.components.ecrans;


public abstract class EcranFactory {
	
	
	
	public static Ecran getInstance(TypeEcrans typeEcran ){
		
		if(TypeEcrans.MENU == typeEcran) return EcranMenu.getInstance();
		if(TypeEcrans.SELECTION_JOUEURS == typeEcran) return EcranSelectionJoueurs.getInstance();
		if(TypeEcrans.JEU  == typeEcran) return EcranJeu.getInstance();
		if(TypeEcrans.OPTIONS  == typeEcran) return EcranOptions.getInstance();
		if(TypeEcrans.SCORES == typeEcran) return EcranScores.getInstance();
		
		return null;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
