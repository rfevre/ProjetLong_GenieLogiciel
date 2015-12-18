package boggle.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import boggle.gui.core.Game;

/**
 * Classe Joueur
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class Joueur extends Observable implements Comparable<Joueur>, CommentJouer {
		
	protected String nom;
	protected int score;
	protected List<String> listeMots;
	protected boolean entrainDeJouer;
	protected TypeJoueur type;
	
	/**
	 * Constructeur de la classe
	 * @param nom
	 */
	public Joueur(String nom){
		this.nom = nom;
		this.score = 0;
		this.listeMots = new ArrayList<String>();
		this.type = TypeJoueur.HUMAIN;
	}

	public boolean isEntrainDeJouer() { return entrainDeJouer; }
	public String getNom() { return nom; }  
	public List<String> getListeMots() { 
		return listeMots;
	}  
	public int getScore() { return score; }


	public TypeJoueur getType() {
		return this.type;
	}
	public void setEntrainDeJouer(boolean entrainDeJouer) { 
		this.entrainDeJouer = entrainDeJouer;
		this.setChanged();
		this.notifyObservers();
	}
	public void setNom(String nom) { this.nom = nom; }  
	public void setScore(int score) { 
		this.score = score;

	}  
	public void setListeMots(List<String> listeMots) { this.listeMots = listeMots; }
	
	public String toString(){ 
		return "<html><table><tr><td width=\"200\">" +nom+ "</td><td width=\"50\" align=\"right\">" +score+ " pts</td></tr></table></html>";
	}
	
	
	/** 
	 * Ajout du score un nombre au score. 
	 * @param score		Score à ajouter
	 */
	public void ajoutScore(int score){ 
		this.score+=score;

	}
	
	/** 
	 * 
	 * Permet d'ajouter un mot a liste 
	 * @param mot 		Mot à ajouter dans la liste 
	 */
	public void ajouterUnMot(String mot){ 
		if(!mot.isEmpty() && !listeMots.contains(mot)){ 
			listeMots.add(mot);
			this.setChanged();
			this.notifyObservers();
		}
	}
	
	/** Permet de vider la liste des mots du joueur. */
	public void resetListeMots(){ 
		this.setListeMots(new ArrayList<String>());
		this.setChanged();
		this.notifyObservers();	
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		Joueur autre = (Joueur)obj;
		return this.nom.equals(autre.getNom());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Joueur autre) {
		return this.score-autre.getScore();
	}

	/* (non-Javadoc)
	 * @see boggle.jeu.CommentJouer#jouer()
	 */
	public synchronized void jouer() {
	}
	


	/* (non-Javadoc)
	 * @see boggle.jeu.CommentJouer#arreterDeJoueur()
	 */
	public void arreterDeJoueur(){
			this.setEntrainDeJouer(false);
	}
	
	/**
	 * Méthode qui permet de savoir si un joueur peut jouer
	 * @return boolean 
	 */
	protected boolean peutJoueur(){ return Game.modele.getJoueurEnCours().isEntrainDeJouer() && Game.modele.getDureeManche() > 0; }

}
