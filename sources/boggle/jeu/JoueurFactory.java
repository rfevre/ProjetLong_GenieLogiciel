package boggle.jeu;


/**
 * Classe JoueurFactory
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class JoueurFactory {
	
	/**
	 * Retourne l'instance courante
	 * @param typeJoueur		Renseigne sur le type de joueur ( IA ou Humain)
	 * @param nom				REnseigne le nom
	 * @return					Joueur
	 */
	public static Joueur getInstance(TypeJoueur typeJoueur, String nom){
		
		if(TypeJoueur.IA_RANDOM_NIV1 == typeJoueur) return new IARandom(nom);
		if(TypeJoueur.IA_RANDOM_NIV2 == typeJoueur) return new IAPresqueRandom(nom);
		if(TypeJoueur.HUMAIN  == typeJoueur) return new Joueur(nom);
		
		return null;
	}
	
	

}
