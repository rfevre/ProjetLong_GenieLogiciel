package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * La classe ArbreLexical permet de stocker de façon compacte et d'accéder
 * rapidement à un ensemble de mots.
 */
public class ArbreLexical {
	public static final int TAILLE_ALPHABET = 26;
	private boolean estMot; // vrai si le noeud courant est la fin d'un mot
							// valide
	private int nbFils;
	private ArbreLexical[] fils = new ArbreLexical[TAILLE_ALPHABET]; // les
																		// sous-arbres

	/** Crée un arbre vide (sans aucun mot) */
	public ArbreLexical() {
		this.estMot = false;
		this.nbFils = 0;

	}

	/**
	 * Indique si le noeud courant est situé à l'extrémité d'un mot valide
	 */
	public boolean estMot() {
		return estMot;
	}

	/**
	 * Place le mot spécifié dans l'arbre
	 * 
	 * @return <code>true</code> si le mot a été ajouté, <code>false</code>
	 *         sinon
	 */
	public boolean ajouter(String word) {
		char lettre = word.charAt(0);
		this.fils[lettre - 65] = new ArbreLexical();
		this.nbFils++;
		if (word.length() == 1) { // si la longueur de la chaine = 1 => fin du
									// mot
			this.fils[lettre - 65].estMot = true;
			return true;
		}
		return this.fils[lettre - 65].ajouter(word.substring(1, word.length()));
	}

	/**
	 * Teste si l'arbre lexical contient le mot spécifié.
	 * 
	 * @return <code>true</code> si <code>o</code> est un mot (String) contenu
	 *         dans l'arbre, <code>false</code> si <code>o</code> n'est pas une
	 *         instance de String ou si le mot n'est pas dans l'arbre lexical.
	 */
	public boolean contient(String word) {
		// à compléter
		return false;
	}

	/**
	 * Ajoute à la liste <code>resultat<code> tous les mots de
	 * l'arbre commençant par le préfixe spécifié.
	 * 
	 * @return <code>true</code> si <code>resultat</code> a été modifié,
	 *         <code>false</code> sinon.
	 */
	public boolean motsCommencantPar(String prefixe, List<String> resultat) {
		// à compléter
		return false;
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
	
	public String toString(){
		return "+";	
	}
	public String afficher(){
		if(this.nbFils == 0) return "";
		for(int i=0; i< TAILLE_ALPHABET ; i++) {
			 ArbreLexical arbre = fils[i];
			 if (arbre!=null) {
				 if(this.nbFils == 0) System.out.println(""+(char)(i+65)); 
				 arbre.afficher();
				 System.out.println((char)(i+65));
			 }	 
				
		 }
		return "";
	}
	

	public static void main(String[] args) {
		ArbreLexical a = ArbreLexical.lireMots("config/dico.txt");
		System.out.println(a.afficher());

		
		
		
		
	}

}
