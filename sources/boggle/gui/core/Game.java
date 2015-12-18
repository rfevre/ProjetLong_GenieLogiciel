package boggle.gui.core;

import javax.swing.SwingUtilities;

import boggle.gui.components.ecrans.*;
import boggle.jeu.Partie;



public class Game {

	public static final int WIDTH  = 1200;
	public static final int HEIGHT = 825 ;
	public static Partie modele = new Partie();
	
	private static final Fenetre  fenetre = new Fenetre("BOGGLE", WIDTH, HEIGHT);

	public static TypeEcrans ECRAN_EN_COURS = TypeEcrans.MENU; 
	private static Game instance; 
	
	// CONSTRUCTORS ///////////////////////////////////////////////////////////
	
	/**
	 * Charge l'ecran en cours
	 */
	private Game(){
		goToEcran(ECRAN_EN_COURS);
	}
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return new Game();
	}
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/**
	 * Remet à zero la partie
	 */
	public static void resetPartie(){
		System.out.println("Avant " + modele);
		//modele.getThread().interrupt();
		modele = new Partie();
		System.out.println("Apres " + modele);
	}
	
	/**
	 * Permet de changer d'ecran
	 * @param ecran : ecran à charger
	 */
	public static void goToEcran(TypeEcrans ecran){
		System.out.println("Changement ecran");
		ECRAN_EN_COURS = ecran;
		fenetre.updateEcranEnCours();
	}
	
	/**
	 * Quitte le programme
	 */
	public static void quitter(){
		System.exit(0);
	}
	

	// MAIN ///////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	Game.getInstance();
		    }
		});
	}


}
