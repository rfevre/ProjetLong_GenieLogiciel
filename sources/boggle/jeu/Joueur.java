package boggle.jeu;

import java.util.ArrayList;
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
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<String> getListeMots() {
		return listeMots;
	}

	public void setListeMots(List<String> listeMots) {
		this.listeMots = listeMots;
	}
	
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/**
	 * Ajout du score
	 * @param score
	 */
	public void ajoutScore(int score){
		this.score+=score;
	}
	
	/**
	 * 
	 * @param mot
	 */
	public void ajouterMot(String mot){
		listeMots.add(mot);
	}
	
	/**
	 * TODO Implémenter compareTo pour trier la liste des mots ( Collections.sort())
	 */
	
}
