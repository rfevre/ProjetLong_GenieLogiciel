package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joueur {
	
	// Attributs 
	
	private String nom;
	private int score;
	private List<String> listeMots;
	
	// CONSTRUCTEURS //////////////////////////////////////////////////////////
	
	public Joueur(String nom){
		this.nom = nom;
		this.score = 0;
		this.listeMots = new ArrayList<String>();
	}
	
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public String getNom() { return nom; }  
	public List<String> getListeMots() { Collections.sort(this.listeMots); return listeMots; }  
	public int getScore() { return score; }  
	
	public void setNom(String nom) { this.nom = nom; }  
	public void setScore(int score) { this.score = score; }  
	public void setListeMots(List<String> listeMots) { this.listeMots = listeMots; }
	
	public String toString(){ return nom+ " : " +score + " pts."; }
	
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/** Ajout du score un nombre au score. */
	public void ajoutScore(int score){ this.score+=score; }
	
	/** Permet d'ajouter un mot a liste */
	public void ajouterUnMot(String mot){ 
		if(!listeMots.contains(mot)){ listeMots.add(mot);}
	}
	
	/** Permet de vider la liste des mots du joueur. */
	public void resetListeMots(){ this.setListeMots(new ArrayList<String>()); }
	
}
