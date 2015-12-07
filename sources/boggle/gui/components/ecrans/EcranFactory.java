package boggle.gui.components.ecrans;

import gui.core.Fenetre;

public abstract class EcranFactory {
	
	
	
	public static Ecran getInstance(TypeEcrans typeEcran, Fenetre fenetre){
		
		if(TypeEcrans.MENU == typeEcran) return EcranMenu.getInstance(fenetre);
		if(TypeEcrans.JEU  == typeEcran) return EcranJeu.getInstance(fenetre);
		
		return null;
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
