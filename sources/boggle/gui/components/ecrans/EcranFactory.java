package boggle.gui.components.ecrans;

import boggle.gui.core.Fenetre;

public abstract class EcranFactory {
	
	
	
	public static Ecran getInstance(TypeEcrans typeEcran, Fenetre fenetre){
		
		if(TypeEcrans.MENU == typeEcran) return EcranMenu.getInstance(fenetre);
		if(TypeEcrans.SELECTION_JOUEURS == typeEcran) return EcranSelectionJoueurs.getInstance(fenetre);
		if(TypeEcrans.JEU  == typeEcran) return EcranJeu.getInstance(fenetre);
		if(TypeEcrans.OPTIONS  == typeEcran) return EcranOptions.getInstance(fenetre);
		
		return null;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
