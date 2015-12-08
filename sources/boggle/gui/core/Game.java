package boggle.gui.core;

import boggle.gui.components.ecrans.*;
import boggle.jeu.Partie;



public class Game implements Runnable {

	public static final int WIDTH  = 1100;
	public static final int HEIGHT = WIDTH * 3 / 4 ;
	
	private Partie	 modele;
	private Fenetre  fenetre;
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
		this.fenetre = new Fenetre("BOGGLE", WIDTH, HEIGHT);
		this.modele  = new Partie();
		this.jeuEnCours = false;
	}
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public boolean isJeuEnCours() { return jeuEnCours; }
	public Partie getModele() { return modele; }  
	
	public void setModele(Partie modele) { this.modele = modele; }
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
	
	
	// PRIVATE METHODS /////////////////////////////////////////////////////////////

	@Override
	public void run() {
		this.fenetre.updateEcranEnCours();
	}

	// MAIN ///////////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		Game.getInstance().lancerJeu();
	}


}
