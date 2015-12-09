package boggle.jeu;



public class JoueurFactory {
	
	
	public static Joueur getInstance(TypeJoueur typeJoueur, String nom){
		
		if(TypeJoueur.IA_RANDOM_NIV1 == typeJoueur) return new IARandom(nom);
		if(TypeJoueur.IA_RANDOM_NIV2 == typeJoueur) return new IAPresqueRandom(nom);
		if(TypeJoueur.HUMAIN  == typeJoueur) return new Joueur(nom);
		
		return null;
		
		
		
	}
	
	

}
