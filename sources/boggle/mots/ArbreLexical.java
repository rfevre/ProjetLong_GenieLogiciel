package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import boggle.autre.Utils;

/**
 * La classe ArbreLexical permet de stocker de façon compacte et d'accéder
 * rapidement à un ensemble de mots.
 */
public class ArbreLexical {

	public static final int TAILLE_ALPHABET = 26;
	private boolean estMot; 
	private int nbFils;
	private String lettre;
	private ArbreLexical[] fils = new ArbreLexical[TAILLE_ALPHABET]; 
	private int[] indexesFils = new int[TAILLE_ALPHABET];

	
	// CONSTRUCTEURS //////////////////////////////////////////////////////////
	
	public ArbreLexical() {
		this.estMot = false;
		this.lettre = "";
		this.nbFils = 0;
	}
	
	
	// GET-SET ////////////////////////////////////////////////////////////////
	
	public int getNbFils() { return nbFils; }  
	public void setLettre(String lettre){ this.lettre = lettre; }
	public void setNbFils(int nbFils) { this.nbFils = nbFils; }
	
	public String toString(){ return this.lettre ; }
	
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/** Indique si le noeud courant est situé à l'extrémité d'un mot valide */
	public boolean estMot() { return estMot; }

	/**
	 * Place le mot spécifié dans l'arbre
	 * @param word : mot à ajouter
	 * @return <b>true</b> si le mot a été ajouté, <b>false</b> sinon
	 */
	public boolean ajouter(String word) {
		if(word == null || word.length() == 0) return false;
		char lettre = word.charAt(0);
		int index = lettre - 65;
		if(this.fils[index] == null){
			this.fils[index] = new ArbreLexical();
			this.indexesFils[this.nbFils] = index;
			this.nbFils++;
			this.fils[index].setLettre(""+lettre);			
		}
		if (1 == word.length()) { 
			this.fils[index].estMot = true;
			return true;
		}
		return this.fils[index].ajouter(word.substring(1, word.length()));
	}

	/**
	 * Teste si l'arbre lexical contient le mot spécifié.
	 * @param word : mot à trouver
	 * @return <code>true</code> si <code>o</code> est un mot (String) contenu
	 * dans l'arbre, <code>false</code> si <code>o</code> n'est pas une
	 * instance de String ou si le mot n'est pas dans l'arbre lexical.
	 */
	public boolean contient(String word) {
		if(word == null || word.isEmpty()) return false;
		word = word.toUpperCase();
		final char lettre = word.charAt(0);
		final int index = lettre - 65;
		final ArbreLexical arbre = this.fils[index];
		if(word.length()==1) return arbre != null && arbre.estMot();
		
		if(arbre == null){ return false; }
		return arbre.contient(word.substring(1));
	}

	/**
	 * Ajoute à la liste <code>resultat<code> tous les mots de
	 * l'arbre commençant par le préfixe spécifié.
	 * 
	 * @return <code>true</code> si <code>resultat</code> a été modifié,
	 *         <code>false</code> sinon.
	 */
	public boolean motsCommencantPar(String prefixe, List<String> resultat) {
		if(resultat == null || prefixe == null || prefixe.isEmpty()) return false;
		final int taille = resultat.size();
		final ArbreLexical parent = getArbreFromString(prefixe);
		if(parent == null || parent.nbFils == 0){ return false; }
		parent.getListeMots(prefixe, resultat);
		return taille != resultat.size() ;
	}

	/**
	 * Permet de recupere la liste des mots contenus dans un arbre
	 * et de les prefixes par une chaine.
	 * @param prefixe	: le prefixe qui est ajouté au debut de chaque mot trouvé.
	 * @param resultat  : tableau qui contient l'ensemble des mots trouvés.
	 */
	public void getListeMots(String prefixe, List<String> resultat){
		if(this.nbFils == 0) return;
		for(int i=0; i<nbFils; i++){
			final int index = indexesFils[i];
			final ArbreLexical currentArbre = fils[index];
			final String newPrefixe = prefixe + currentArbre.lettre;
			if(currentArbre.estMot()){
				resultat.add(newPrefixe);
			}
			currentArbre.getListeMots(newPrefixe, resultat);
		}
	}
	
	/**
	 * Permet de recuperer l'arbre lexical commancant par la derniere lettre d'un mot
	 * @param prefixe
	 * @return
	 */
	public ArbreLexical getArbreFromString(String mot){
		final char lettre = mot.charAt(0);
		final int index = lettre - 65;		
		final ArbreLexical arbre = this.fils[index];
		if(arbre == null || mot.length()==1) return arbre;
		return arbre.getArbreFromString(mot.substring(1));
	}
	
	/**
	 * Crée un arbre lexical qui contient tous les mots du fichier spécifié.
	 * @param fichier : chemin vers le fichier source
	 * @return ArbreLexical representant le contenu du ficher.
	 */
	public static ArbreLexical creerArbreDepuisFichier(String fichier) {
		ArbreLexical arbre = new ArbreLexical();
		try {
			final File f = new File(fichier);
			FileInputStream fis;
			fis = new FileInputStream(f);
			final BufferedReader br = new BufferedReader(new InputStreamReader(fis));

			String line;
			line = br.readLine();

			while (line != null) {
	    		if(line.equals("")){
	    			line = br.readLine();
	    			continue;
	    		}
				arbre.ajouter(line);
				line = br.readLine();
			}
			br.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return arbre;
	}
	
	
	/** Affiche le contenu de l'arbre */
	public void afficherArbre(int k){
		System.out.println( Utils.repeter(" ║ ", k) + " ╠═ " +this );
		for(int i=0; i< TAILLE_ALPHABET ; i++) {
			 ArbreLexical arbre = fils[i];
			 if (arbre!=null) {
				arbre.afficherArbre(k+1);
			 } 				
		 }
	}
	
	/** Permet de verifier que les mots qui sont dans la liste sont dans le dictionnaire */
	public List<String> sontDansLeDictionnaire(List<String> listeMots){
		if(listeMots == null) return null;
		final ArrayList<String> tmp = new ArrayList<String>();
		
		for(String mot : listeMots){
			if(this.contient(mot)){
				tmp.add(mot);
			}
		}
		return tmp;
	}
	
	// PRIVATE METHODS ////////////////////////////////////////////////////////

	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////


	

}