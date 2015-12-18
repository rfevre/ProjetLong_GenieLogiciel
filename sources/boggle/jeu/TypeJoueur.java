package boggle.jeu;

import java.util.ArrayList;
import java.util.List;

public enum TypeJoueur {

	HUMAIN("Humain"), IA_RANDOM_NIV1("IA Facile"), IA_RANDOM_NIV2("IA Moyenne");
	
	
	private String nom;
	
	TypeJoueur(String nom){
		this.nom = nom;
	}
	
	
	
	
	
	public static List<TypeJoueur> getListeIA(){
		List<TypeJoueur> tmp = new ArrayList<>();
		for(TypeJoueur el : TypeJoueur.values()){
			if(el != HUMAIN){
				tmp.add(el);
			}
		}
		
		return tmp;
		
	}
	
	
	
	
}
