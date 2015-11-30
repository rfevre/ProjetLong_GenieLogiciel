package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe ArbreLexical permet de stocker de façon compacte et d'accéder
 * rapidement à un ensemble de mots.
 */
public class ArbreLexical {
	public static final int TAILLE_ALPHABET = 26;
	private boolean estMot; // vrai si le noeud courant est la fin d'un mot valide
	private int nbFils;
	private String lettre;
	private ArbreLexical[] fils = new ArbreLexical[TAILLE_ALPHABET]; // les sous-arbres
	private int[] indexesFils = new int[TAILLE_ALPHABET];

	/** Crée un arbre vide (sans aucun mot) */
	public ArbreLexical() {
		this.estMot = false;
		this.nbFils = 0;
		this.lettre = "";
	}
	
	public void setLettre(String lettre){ this.lettre = lettre; }
	public int getNbFils() { return nbFils; }  
	public void setNbFils(int nbFils) { this.nbFils = nbFils; }
	
	/**
	 * Indique si le noeud courant est situé à l'extrémité d'un mot valide
	 */
	public boolean estMot() { return estMot; }

	/**
	 * Place le mot spécifié dans l'arbre
	 * 
	 * @return <code>true</code> si le mot a été ajouté, <code>false</code>
	 *         sinon
	 */
	public boolean ajouter(String word) {
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
	 * 
	 * @return <code>true</code> si <code>o</code> est un mot (String) contenu
	 *         dans l'arbre, <code>false</code> si <code>o</code> n'est pas une
	 *         instance de String ou si le mot n'est pas dans l'arbre lexical.
	 */
	public boolean contient(String word) {
		
		if(word.isEmpty()) return false;
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
	 */
	public static ArbreLexical lireMots(String fichier) {
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
	
	/**
	 * Méthode toString qui renvoi l'attribut lettre
	 */
	public String toString(){
		return this.lettre ;	
	}
	
	/**
	 * 
	 * Affiche l'arbre
	 * 
	 * @param k : valeur du décalage (pour l'affichage)
	 */
	public void afficherArbre(int k){
		System.out.println( repeter(" ║ ", k) + " ╠═ " +this );
		for(int i=0; i< TAILLE_ALPHABET ; i++) {
			 ArbreLexical arbre = fils[i];
			 if (arbre!=null) {
				arbre.afficherArbre(k+1);
			 } 				
		 }
	}
	

	/**
	 * 
	 * Permet de répéter une chaine
	 * 
	 * @param str : Chaine à répéter
	 * @param nb : Nombre de répétition
	 * @return
	 */
	private static String repeter(String str, int nb){
		   return new String(new char[nb]).replace("\0", str);
	}
	
	

	public static void main(String[] args) {
		ArbreLexical a = ArbreLexical.lireMots("config/dico.txt");
		a.afficherArbre(0);
		System.out.println(a.contient("JAVA"));
		
		ArrayList<String> res = new ArrayList<String>();

 		//a.motsCommencantPar("JAVAN", res);
 		//System.out.println(res);
		ArbreLexical n = a.getArbreFromString("AB");
		n.getListeMots("->", res);
 		System.out.println(res);
	}

	

}