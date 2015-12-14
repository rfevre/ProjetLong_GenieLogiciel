package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Joueur extends Observable implements Comparable<Joueur>, CommentJouer {
	
	// Attributs 
	
	protected String nom;
	protected int score;
	protected List<String> listeMots;
	protected boolean entrainDeJouer; 
	// CONSTRUCTEURS //////////////////////////////////////////////////////////
	
	public Joueur(String nom){
		this.nom = nom;
		this.score = 0;
		this.listeMots = new ArrayList<String>();
	}
	
	
	// GET-SET ////////////////////////////////////////////////////////////////


	public boolean isEntrainDeJouer() { return entrainDeJouer; }
	public String getNom() { return nom; }  
	public List<String> getListeMots() { 
		//Collections.sort(this.listeMots); 
		return listeMots;
	}  
	public int getScore() { return score; }
	
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
	
	public String toString(){ return nom+ " : " +score + " pts."; }
	
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/** Ajout du score un nombre au score. */
	public void ajoutScore(int score){ 
		this.score+=score;

	}
	
	/** Permet d'ajouter un mot a liste */
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
	
	@Override
	public boolean equals(Object obj) {
		Joueur autre = (Joueur)obj;
		return this.nom.equals(autre.getNom());
	}
	
	@Override
	public int compareTo(Joueur autre) {
		return this.score-autre.getScore();
	}



	public synchronized void jouer() {
		//System.out.println(nom + " EST ENTRAIN DE JOUEUR !!!");
	}
	


	public synchronized void arreterDeJoueur(){
			System.out.println(nom + " a dit STOP");
			this.entrainDeJouer = false;
	}
	
}
