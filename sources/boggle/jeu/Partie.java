package boggle.jeu;

import java.util.ArrayList;
import java.util.List;

import boggle.mots.ArbreLexical;
import boggle.mots.GrilleLettres;

public class Partie {
	private List<Joueur> listeJoueurs;
	private GrilleLettres grille; 
	private int nbTours;
	private ArbreLexical arbre;
	
	// CONSTRUCTEURS //////////////////////////////////////////////////////////
	// TODO : Mettre chemin fichier dans properties
	public Partie(int nbTours){
		listeJoueurs = new ArrayList<Joueur>();
		this.nbTours = nbTours;		
	}
	
	public Partie(int nbTours, String chemin){
		this(nbTours);
		this.arbre = ArbreLexical.creerArbreDepuisFichier(chemin);
	}
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public ArbreLexical getArbre() {
		return arbre;
	}

	public void setArbre(ArbreLexical arbre) {
		this.arbre = arbre;
	}

	public List<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public void setListeJoueurs(List<Joueur> listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	public GrilleLettres getGrille() {
		return grille;
	}

	public void setGrille(GrilleLettres grille) {
		this.grille = grille;
	}

	public int getNbTours() {
		return nbTours;
	}

	public void setNbTours(int nbTours) {
		this.nbTours = nbTours;
	}
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	public void ajouterJoueur(Joueur joueur){
		this.listeJoueurs.add(joueur);
	}

	public void lancerPartie(){
		
	}
	
	public void calculerScore(Joueur joueur){
		// Verifier liste mots joueur dans arbre
		// mettre a jour le score du joueur
		// vider liste mots joueurs
	}
	
	
	// PRIVATE METHODS ////////////////////////////////////////////////////////
	
	
}
