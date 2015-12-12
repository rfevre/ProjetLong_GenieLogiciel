package boggle.gui.core;

import boggle.gui.components.ecrans.*;
import boggle.jeu.Partie;



public class Game implements Runnable  {

	public static final int WIDTH  = 1050;
	public static final int HEIGHT = WIDTH * 3 / 4 ;
	public static final Partie modele = new Partie();
	
	public static final Fenetre  fenetre = new Fenetre("BOGGLE", WIDTH, HEIGHT);
	
	private boolean  jeuEnCours;
	private Thread   thread;

	public static TypeEcrans ECRAN_EN_COURS = TypeEcrans.MENU; 
	private static Game instance; 
	
	// CONSTRUCTORS ///////////////////////////////////////////////////////////
	
	public static Game getInstance(){
		if(instance == null){
			instance = new Game();
		}
		return instance;
	}
	
	private Game(){
		this.jeuEnCours = false;
		goToEcran(ECRAN_EN_COURS);
	}
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public boolean isJeuEnCours() { return jeuEnCours; }
	
	public void setJeuEnCours(boolean jeuEnCours) { this.jeuEnCours = jeuEnCours; }
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	public synchronized void lancerJeu(){
		this.thread = new Thread(this);
		this.jeuEnCours = true;
		this.thread.start();
	}
	
	public synchronized void arreterLeJeu(){
		try {
			this.thread.join();
			this.jeuEnCours = false;
		
		} catch (Exception e) { System.out.println(e.getMessage());	}
	}
	
	public static void goToEcran(TypeEcrans ecran){
		ECRAN_EN_COURS = ecran;
		fenetre.updateEcranEnCours();
	}
	
	// PRIVATE METHODS /////////////////////////////////////////////////////////////

	@Override
	public void run() {
		//fenetre.updateEcranEnCours();
		System.out.println("RUN");
	}

	// MAIN ///////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		Game.getInstance().lancerJeu();
	}


}
