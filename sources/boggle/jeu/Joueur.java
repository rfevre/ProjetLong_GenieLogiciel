package boggle.jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
	 *
	 * @author Mustapha EL MASSAOUDI
	 * @return int
	 * @param String mot1
	 * @param String mot2	
	 *
	 * Si le mot mis en paramètres est égale au mot testé, on retourne 0
	 * Si le mot mis en paramètres est plus grand que le mot testé, on retourne 1 
	 * Si le mot mis en paramètres est plsu petit que le mot testé, on retourne -1
	 *
	 */
	public int compareTo(String mot1, String mot2)
	{
		if(mot1.length() == mot2.length() )
		{
			return 0;
		}
		else if(mot1.length() > mot2.length())
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}


}
