package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import boggle.autre.BDD;
import boggle.autre.Utils;
import boggle.gui.components.ecrans.TypeEcrans;
import boggle.gui.core.Game;
import boggle.mots.ArbreLexical;
import boggle.mots.GrilleLettres;


/**
 * Classe Partie qui correspond à une partie de jeu
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class Partie extends Observable implements Observer, Runnable {
	private List<Joueur> listeJoueurs;
	private GrilleLettres grille; 
	private int nbTours, numTour;
	private ArbreLexical arbre;
	private Joueur joueurEnCours;
	private Thread thread;
	private int dureeManche, tempsRestant;
	private int scoreMax = 100;

	/**
	 * Constructeur de la classe
	 */
	public Partie(){
		this.nbTours = 3;
		this.grille  = new GrilleLettres();
		this.arbre   = ArbreLexical.creerArbreDepuisFichier(Utils.DOSSIER_CONFIG + Utils.getConfigProperty("dictionnaire"));
		this.listeJoueurs = new ArrayList<Joueur>();
		this.dureeManche = 180;
		this.tempsRestant = dureeManche;
	}


	// GET-SET ////////////////////////////////////////////////////////////////

	public ArbreLexical getArbre() { return arbre; }  
	public GrilleLettres getGrille() { return grille; }  
	public synchronized List<Joueur> getListeJoueurs() { return listeJoueurs;}  
	public int getNbTours() { return nbTours; }  
	public Joueur getJoueurEnCours() { return joueurEnCours; }   
	public int getNumTour() { return numTour; }   

	public void setNumTour(int numTour) { this.numTour = numTour; }
	public void setListeJoueurs(List<Joueur> listeJoueurs) { this.listeJoueurs = listeJoueurs; }  
	public void setArbre(ArbreLexical arbre) { this.arbre = arbre; }  
	public void setGrille(GrilleLettres grille) { 
		this.grille = grille;
		this.setChanged();
		this.notifyObservers("grille");
	}  

	public int getScoreMax() {
		return scoreMax;
	}


	public void setDureeManche(int dureeManche) {
		this.dureeManche = dureeManche;
	}


	public int getDureeManche() {
		return dureeManche;
	}


	public int getTempsRestant() {
		return tempsRestant;
	}


	public void setTempsRestant(int tempsRestant) {
		this.tempsRestant = tempsRestant;
	}


	public Thread getThread() {
		return thread;
	}


	public void setThread(Thread thread) {
		this.thread = thread;
	}


	public void setDurreeManche(int dureeManche) {
		System.out.println("DUREE " + dureeManche);
		this.dureeManche = dureeManche;
		this.setChanged();
		this.notifyObservers();
	}
	public void setNbTours(int nbTours) { this.nbTours = nbTours; }
	public void setJoueurEnCours(Joueur joueurEnCours) { 
		this.joueurEnCours = joueurEnCours;
		this.joueurEnCours.setEntrainDeJouer(true);
		this.setChanged();
		this.notifyObservers();
	}
	
	public void setScoreMax(int scoreMax) {
		this.scoreMax = scoreMax;
	}

	// PUBLIC METHODS /////////////////////////////////////////////////////////
	

	
	/**
	 * Ajoute un joueur à la liste de joueurs
	 * @param joueur	Joueur à ajouter
	 */
	public synchronized void ajouterJoueur(Joueur joueur){
		if(!this.listeJoueurs.contains(joueur)){
			joueur.addObserver(this);
			this.listeJoueurs.add(joueur);
		}
	}

	/**
	 * Méthode qui 'vide' la liste de joueurs en créant une nouvelle instance
	 */
	public void resetListeJoueurs(){
		this.listeJoueurs = new ArrayList<Joueur>();
	}

	/**
	 * Supprime un joueur à la liste de joueurs
	 * @param joueur	Joueur à supprimer
	 */
	public void supprimerJoueur(Joueur joueur){
		if(this.listeJoueurs.contains(joueur)){
			joueur.deleteObserver(this);
			this.listeJoueurs.remove(joueur);
		}
	}

	/**
	 * Méthode qui renseigne le joueur gagnant
	 * @return	Joueur
	 */
	public Joueur getGagnant(){
		if(getListeJoueurs().size() == 1) return getListeJoueurs().get(0);
		List<Joueur> tmp = new ArrayList<>(getListeJoueurs());
		Collections.sort(tmp);
		Collections.reverse(tmp);
		return tmp.get(0);
	}

	/**
	 * Permet de lancer une partie en mode console
	 */
	public void lancerPartieConsole(){
		final Scanner sc = new Scanner(System.in);
		// nom du joueur
		System.out.println("Entrez votre nom : ");
		final String nom    = sc.nextLine();
		final Joueur joueur = new Joueur(nom);

		String rep = "";
		do{
			System.out.println(this.getGrille());
			System.out.print("Reponse : ");
			rep = sc.nextLine().toUpperCase();
			if(rep.isEmpty()){
				System.out.println("Fin de la partie");
			}else{
				if(this.grille.estUnMotValide(rep)){
					joueur.ajouterUnMot(rep);
					System.out.println("-> OK");
				}else{
					System.err.println("-> Refusé");
				}				
			}
			this.grille.resetDejaVisite();
		}while(!"".equals(rep));
		System.out.println("Mots entres  : " + joueur.getListeMots());
		System.out.println("Mots valides : " + this.getArbre().sontDansLeDictionnaire(joueur.getListeMots()));
		//this.calculerScore(joueur);
		joueur.resetListeMots();
		System.out.println(joueur);
		sc.close();
	}

	/**
	 * Permet de calculer le score d'un joueur a partir des mots presents dans sa liste	 
	 * @param joueur		Joueur a qui ont calcule le score
	 * 
	 */
	public synchronized void calculerScore(Joueur joueur){
		if(joueur == null) return;
		joueur.setScore(0);
		String points = Utils.getConfigProperty("points");
		String[] pts = points.split(",");
		int[] intPts = new int[pts.length];
		for(int i=0; i<intPts.length; i++){
			intPts[i] = Integer.parseInt(pts[i]);
		}
		for(int i=0; i<joueur.getListeMots().size(); i++){
			String mot = joueur.getListeMots().get(i);
			int gain = arbre.getPointMot(mot);
			joueur.ajoutScore(gain);
		}
	}


	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		Joueur j = (Joueur) o;
		calculerScore(j);
		this.setChanged();
		this.notifyObservers();

	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		for(int tour=1; tour<=nbTours; tour++){
			this.setNumTour(tour);
			tempsRestant = this.dureeManche;			
			for(int i=0; i<getListeJoueurs().size(); i++){
				Timer t = new Timer(false);
				t.scheduleAtFixedRate(new Verificateur(), 1000, 1000);
				Joueur joueur = getListeJoueurs().get(i);
				this.setJoueurEnCours(joueur);
				grille.resetDejaVisite();
				grille.resetListeDeSelectionnes();
				
				while(joueur.isEntrainDeJouer() && tempsRestant > 0  && estDansEcranJeu(t)){
					joueur.jouer();
					if(joueur.getScore() >= scoreMax){
						joueur.setEntrainDeJouer(false);
						tempsRestant = this.dureeManche;
						t.cancel();
						t.purge();
						break;
					}
				}
				if(joueur.getScore() >= scoreMax){
					tempsRestant = this.dureeManche;
					t.cancel();
					t.purge();
					break;
				}
				joueur.arreterDeJoueur(); 
				tempsRestant = this.dureeManche;
				t.cancel();
				t.purge();
				Game.modele.setGrille(new GrilleLettres());
			}
			if(!estDansEcranJeu(null)) break;
		}
		finDeLaPartie();
	}

	/**
	 * Méthode qui lance la partie
	 */
	public void lancerLaPartie(){
		this.thread = new Thread(this);
		this.thread.start();
		
	}

	/**
	 * Méthode qui détermine la fin de la partie
	 */
	public void finDeLaPartie(){
		try {
			Joueur gagnant = getGagnant();
			if(gagnant.getScore() == 0){
				JOptionPane.showMessageDialog(null, "<html><h2> Pas de gagnant! </h2></html>" );
			}else{
				JOptionPane.showMessageDialog(null, "<html><h2>Victoire de " + gagnant.getNom() + " avec " + gagnant.getScore() + " points.</h2></html>" );
				BDD bdd = new BDD();
				bdd.ajouterUnScore(gagnant);
				bdd.fermer();
			}
			Game.goToEcran(TypeEcrans.SCORES);
			try {
				this.thread.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) { System.out.println(e.getMessage());	}
	}
	
	/**
	 * Méthode qui permet de savoir si l'EcranJeu a été abandonnée
	 * Dans le cas ou l'on joue un partie et qu'on annule la partie en cours
	 * @param t			Timer
	 * @return	boolean
	 */
	private boolean estDansEcranJeu(Timer t){
		if(Game.ECRAN_EN_COURS != TypeEcrans.JEU){
			System.out.println("Arret de la partie");
			try {
				if(t!= null){
					t.cancel();
					t.purge();
				}
				this.thread.join();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	/**
	 * Classe privée interne Verificateur qui génère un timer pour connaitre le temps restant
	 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
	 * @version 1.0
	 *
	 */
	private class Verificateur extends TimerTask {

		
		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			tempsRestant--;
			if(tempsRestant<1){
				joueurEnCours.setEntrainDeJouer(false);
			}
			setChanged();
			notifyObservers();
			
		}
		
	}
}
