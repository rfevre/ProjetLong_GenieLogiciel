package boggle.jeu;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum qui permet de connaitre le type du joueur
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public enum TypeJoueur {

	HUMAIN("Humain"), IA_RANDOM_NIV1("IA Facile"), IA_RANDOM_NIV2("IA Moyenne");
	
	private String nom;
	
	/**
	 * Constructeur de l'enum
	 * @param nom : nom du joueur
	 */
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
