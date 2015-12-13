package boggle.gui.core;

import javax.swing.SwingUtilities;

import boggle.gui.components.ecrans.*;
import boggle.jeu.Partie;



public class Game {

	public static final int WIDTH  = 1100;
	public static final int HEIGHT = WIDTH * 3 / 4 ;
	public static final Partie modele = new Partie();
	
	private static final Fenetre  fenetre = new Fenetre("BOGGLE", WIDTH, HEIGHT);
	

	public static TypeEcrans ECRAN_EN_COURS = TypeEcrans.SELECTION_JOUEURS; 
	private static Game instance; 
	
	// CONSTRUCTORS ///////////////////////////////////////////////////////////
	
	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}
	
	private Game(){
		goToEcran(ECRAN_EN_COURS);
	}
	
	// GET-SET ////////////////////////////////////////////////////////////////
	

	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	

	
	public static void goToEcran(TypeEcrans ecran){
		System.out.println("Changement ecran");
		ECRAN_EN_COURS = ecran;
		fenetre.updateEcranEnCours();
	}
	
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
