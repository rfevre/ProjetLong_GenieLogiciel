package boggle.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import boggle.autre.Utils;
import boggle.gui.core.Game;
import boggle.mots.ArbreLexical;
import boggle.mots.GrilleLettres;

public class Partie {
	private List<Joueur> listeJoueurs;
	private GrilleLettres grille; 
	private int nbTours;
	private ArbreLexical arbre;
	private Joueur joueurEnCours;
	
	// CONSTRUCTEURS //////////////////////////////////////////////////////////
	public Partie(){
		this.nbTours = 1;
		this.grille  = new GrilleLettres();
		this.arbre   = ArbreLexical.creerArbreDepuisFichier(Utils.DOSSIER_CONFIG + Utils.getConfigProperty("dictionnaire"));
		this.listeJoueurs = new ArrayList<Joueur>();
	}
	
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public ArbreLexical getArbre() { return arbre; }  
	public GrilleLettres getGrille() { return grille; }  
	public List<Joueur> getListeJoueurs() { return listeJoueurs; }  
	public int getNbTours() { return nbTours; }  
	public Joueur getJoueurEnCours() { return joueurEnCours; }   
	
	public void setListeJoueurs(List<Joueur> listeJoueurs) { this.listeJoueurs = listeJoueurs; }  
	public void setArbre(ArbreLexical arbre) { this.arbre = arbre; }  
	public void setGrille(GrilleLettres grille) { this.grille = grille; }  
	public void setNbTours(int nbTours) { this.nbTours = nbTours; }
	public void setJoueurEnCours(Joueur joueurEnCours) { this.joueurEnCours = joueurEnCours; }
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/**
	 * Ajoute un joueur à la liste de joueurs
	 * @param joueur
	 */
	public void ajouterJoueur(Joueur joueur){
		if(!this.listeJoueurs.contains(joueur)){
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
			this.listeJoueurs.remove(joueur);
		}
	}

	
	
	public void lancerPartie(){
		System.out.println(this);
		for(Joueur joueur : Game.modele.getListeJoueurs()){
			joueur.setEntrainDeJouer(true);
			this.joueurEnCours=joueur;
			while(this.joueurEnCours.isEntrainDeJouer()){
				//System.out.println(joueurEnCours+" est entrain de jouer --> " + joueurEnCours.getListeMots());
			}
			Game.modele.calculerScore(joueur);
			Game.modele.joueurEnCours.resetListeMots();
		}
			
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
		this.calculerScore(joueur);
		joueur.resetListeMots();
		System.out.println(joueur);
		sc.close();
	}
	
	/**Permet de calculer le score du d'un joueur a partir des mots presents dans sa liste	 */
	public void calculerScore(Joueur joueur){
		String points = Utils.getConfigProperty("points");
		String[] pts = points.split(",");
		int[] intPts = new int[pts.length];
		for(int i=0; i<intPts.length; i++){
			intPts[i] = Integer.parseInt(pts[i]);
		}
		
		for(String mot : joueur.getListeMots()){
			if (arbre.contient(mot)){
				switch(mot.length()){
				case 3:
				case 4: joueur.ajoutScore(intPts[0]); break;
				case 5: joueur.ajoutScore(intPts[1]); break;
				case 6: joueur.ajoutScore(intPts[2]); break;
				case 7: joueur.ajoutScore(intPts[4]); break;
				default:joueur.ajoutScore(intPts[5]); break;
				}
			}
		}
	}
	
	
	

	
	// PRIVATE METHODS ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	
	


	public static void main(String[] args) {
		Partie p = new Partie();
		GrilleLettres g = new GrilleLettres(4);
		g.initGrilleDepuisChaine("PAGE LESO TELE EILP");
		//System.out.println(g);
		p.setGrille(g);
		p.lancerPartieConsole();

	}
	
}
