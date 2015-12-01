package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GrilleLettres {
    
    private int dimension;				// taille de la grille
    private De[][] grille;			

    
    // CONSTRUCTEUR ///////////////////////////////////////////////////////////
  
    public GrilleLettres(int dimension){
    	this.dimension = dimension;
    	this.grille = new De[dimension][dimension];
    }
    
    public GrilleLettres(int dimension, String chemin){
    	this(dimension);
    	this.initGrilleDepuisFichier(chemin);
    }

    // GET-SET ////////////////////////////////////////////////////////////////
    
    public int getDimension() { return dimension; }
    public De[][] getGrille() { return grille; }
    
    public void setDimension(int dimension) { this.dimension = dimension; }
    public void setGrille(De[][] grille) { this.grille = grille; }
        

	public String toString() {
    	final StringBuilder res = new StringBuilder();
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				res.append(grille[i][j]+ "  ");
			}
			res.append("\n");
		}
		return res.toString();
	}
    
    // PUBLIC METHODS /////////////////////////////////////////////////////////
    /** Permet d'initialiser la grille depuis un fichier */
    public void initGrilleDepuisFichier(String chemin){
    	int rx, ry;
		try {
			final File f = new File(chemin);
			final FileInputStream fis = new FileInputStream(f);
	    	final BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	    	
	    	String line;
			line = br.readLine();
			int nbDes = this.dimension * this.dimension;
	    	while (line != null && nbDes > 0) {
	    		if(line.equals("")){
	    			line = br.readLine();
	    			continue;
	    		}
	        	do{
	        		rx = (int) (Math.random()*this.dimension);
	        		ry = (int) (Math.random()*this.dimension);
	            }while(this.grille[rx][ry] != null);

	        	this.grille[rx][ry] = new De(rx, ry, line);
	    		line = br.readLine();
	    		nbDes--;
	    	}
	     
	    	br.close();

		} catch (FileNotFoundException e) {
			System.err.println("Erreur : " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Erreur : " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erreur : " + e.getMessage());
		}        
    }


    /**
     * Permet de verifier si une lettre est presente dans la grille.
     * @param lettre : lettre recherchee
     * @return vrai si lettre est dans la grille, sinon false.
     */
    public boolean estUneLettreValide(String lettre){
    	boolean isValide = false;
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			if(lettre.equals(grille[i][j].getChaineFaceVisible())){
    				isValide = true;
    				break;
    			}
    		}
    	}
    	
    	return isValide;
    	
    }
    
    
    
    /**
     * Permet de verifier si un mot est valide :
     *  1 - toutes les lettres utilisés sont dans la grille,
     *  2 - taille minimum 'dimension-1',
     *  3 - le mot est construit avec des dés adjacents
     * @param unMot : mot recherchee
     * @return vrai si le mot est valide, sinon false.
     */
    public boolean estUnMotValide(String unMot){
    	
    	// Verifier taille minimal
    	// Pour chaque lettre, appeler methode estUneLettreValid() et 
    	
    	// Stocker liste des dés adjacents de la première lettre
    	// A partir de la lettre numéro 2 :
    	// vérifier si elle est présente dans la liste des dés adjacents mais de la lettre précédente.
    	return true;
    }
    
    /** 
     * Permet de connaitre les dès situé autour d'un dè sélectionné
     * TODO : Interdire les valeurs qui dépasse les dimensions
     * */
    public List<De> getListeDesAdjacents(int x, int y){
    	List<De> desAdjacents = new ArrayList<De>();

    	De centre = new De(x, y);
    	for(int i=x-1;i<x+2;i++){
    		for (int j=y-1;j<y+2;j++){
    			if(i>=0 && i<dimension && j>=0 && j<dimension){
    				De unDe = grille[i][j];
    				if (!centre.equals(unDe)){
    					desAdjacents.add(unDe);
    				}	
    			}
    		}
    	}
    	return desAdjacents;
    }
    
    
    
    
	// PRIVATE METHODS ////////////////////////////////////////////////////////
    
    ///////////////////////////////////////////////////////////////////////////
    
   
    public static void main(String[] args) {
		GrilleLettres grille = new GrilleLettres(4, "config/des-4x4.csv");
		System.out.println(grille);
		System.out.println(grille.estUneLettreValide("M"));
		System.out.println(grille.estUneLettreValide("U"));
		System.out.println(grille.estUneLettreValide("S"));
		System.out.println(grille.estUneLettreValide("T"));
		System.out.println(grille.estUneLettreValide("A"));
	}

    
}
