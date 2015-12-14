package boggle.jeu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import boggle.autre.Utils;
import boggle.gui.components.ecrans.TypeEcrans;
import boggle.gui.core.Game;
import boggle.mots.ArbreLexical;
import boggle.mots.GrilleLettres;

public class Partie extends Observable implements Observer, Runnable {
	private List<Joueur> listeJoueurs;
	private GrilleLettres grille; 
	private int nbTours, numTour;
	private ArbreLexical arbre;
	private Joueur joueurEnCours;
	private Thread thread;
	private int durreeManche = 500;

	// CONSTRUCTEURS //////////////////////////////////////////////////////////
	public Partie(){
		this.nbTours = 3;
		this.grille  = new GrilleLettres();
		//this.grille.initGrilleDepuisChaine("JOUR PAPA MAMA ZOZN");
		this.arbre   = ArbreLexical.creerArbreDepuisFichier(Utils.DOSSIER_CONFIG + Utils.getConfigProperty("dictionnaire"));
		this.listeJoueurs = new ArrayList<Joueur>();
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

	public int getDurreeManche() {
		return durreeManche;
	}


	public Thread getThread() {
		return thread;
	}


	public void setThread(Thread thread) {
		this.thread = thread;
	}


	public void setDurreeManche(int durreeManche) {
		this.durreeManche = durreeManche;
		this.setChanged();
		this.notifyObservers();
	}
	public void setNbTours(int nbTours) { this.nbTours = nbTours; }
	public void setJoueurEnCours(Joueur joueurEnCours) { 
		//calculerScore(this.joueurEnCours);
		this.joueurEnCours = joueurEnCours;
		this.joueurEnCours.setEntrainDeJouer(true);
		this.setChanged();
		this.notifyObservers();
	}

	// PUBLIC METHODS /////////////////////////////////////////////////////////

	/**
	 * Ajoute un joueur à la liste de joueurs
	 * @param joueur
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
	 * @param joueur
	 */
	public void supprimerJoueur(Joueur joueur){
		if(this.listeJoueurs.contains(joueur)){
			joueur.deleteObserver(this);
			this.listeJoueurs.remove(joueur);
		}
	}

	public Joueur getGagnant(){
		if(getListeJoueurs().size() == 1) return getListeJoueurs().get(0);
		List<Joueur> tmp = new ArrayList<>(getListeJoueurs());
		Collections.sort(tmp);
		Collections.reverse(tmp);
		return tmp.get(0);
	}


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

	/**Permet de calculer le score du d'un joueur a partir des mots presents dans sa liste	 */
	public synchronized void calculerScore(Joueur joueur){
		//System.out.println("Calcul de score de : " + joueur);
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
			//System.out.println(mot +" rapporte " + gain);
			joueur.ajoutScore(gain);
		}
//		this.setChanged();
//		this.notifyObservers();

	}


	@Override
	public void update(Observable o, Object arg) {
		System.out.println("********* UPDATE PARTIE ************* " + o.getClass());
		Joueur j = (Joueur) o;
		calculerScore(j);
		this.setChanged();
		this.notifyObservers();

	}


	Timer t;
	@Override
	public void run() {
		int dureeMax = durreeManche;
		t = new Timer (1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 
				durreeManche--;
				if(durreeManche<0){
					joueurEnCours.setEntrainDeJouer(false);
					t.stop();
				}
				System.out.println("Il reste " + durreeManche + " sec.");
				setChanged();
				notifyObservers();
				
			}
		});
		System.out.println("________________________________________________________________ DEBUT DE LA PARTIE ____");	
		for(int tour=1; tour<=nbTours; tour++){
			this.setNumTour(tour);
			for(int i=0; i<getListeJoueurs().size(); i++){
				durreeManche = dureeMax;
				Joueur joueur = getListeJoueurs().get(i);
				this.setJoueurEnCours(joueur);
				t.restart();
				while(joueur.isEntrainDeJouer() && durreeManche > 0){	
					joueur.jouer();
				}
				
				joueur.arreterDeJoueur();
			}
		}
		finDeLaPartie();
	}


	public void lancerLaPartie(){
		this.thread = new Thread(this);
		this.thread.start();
		
	}

	public void finDeLaPartie(){
		try {
			System.out.println("__________________________________________________________________ FIN DE LA PARTIE ____");	
			t.stop();
			JOptionPane.showMessageDialog(null, "Victoire de " + getGagnant().getNom() + " avec " + getGagnant().getScore() + " points." );
			Game.goToEcran(TypeEcrans.MENU);
			this.thread.join();
		} catch (Exception e) { System.out.println(e.getMessage());	}
	}

	// PRIVATE METHODS ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////




}
