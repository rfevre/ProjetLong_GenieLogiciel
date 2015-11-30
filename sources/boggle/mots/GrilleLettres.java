package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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
    *
    * Cette méthode permet de savoir si l'on a dejà selectionné un dé.
    * Elle regarde si dans la liste( mise en paramètre), le dé( mis en paramètre)
    * s'y trouve déjà.
    * Si le dé s'y trouve, on retourne <code>true</code>,
    * sinon on retourne <code>false</code>.
    *
    *
    * @param De de : Correspond au dé que l'on doit vérifier
    * @param List<De> liste : Liste dans laquelle il y a tous les dés déjà selectionnés.
    * @return boolean
    */
    public boolean existeDeja(De de, List<De> liste){

    	for ( De unDe : liste)
    	{
    		if( unDe.getX() == de.getX() && unDe.getY() == de.getY())
    		{
    			return true;
    		}
    	}
    	return false;
    }



    
    


	// PRIVATE METHODS ////////////////////////////////////////////////////////
    
    public static void main(String[] args) throws IOException {

    	//GrilleLettres g = new GrilleLettres(4);    	
    	//*System.out.println(g);
    	//g.initGrilleDepuisFichier("config/des-4x4.csv");
    	//System.out.println(g);

    	/**
    	*
    	* Test de la méthode existeDeja
    	*
    	*/
    	De de1 = new De(0,1);
    	De de2 = new De(1,1);
    	De de3 = new De(2,3);
    	De de4 = new De(2,2);

    	List<De> liste = new List<De>();

    	liste.add(de1);
    	liste.add(de2);
    	liste.add(de3);

    	System.out.println(existeDeja(de1,liste));
    	System.out.println(existeDeja(de4,liste));


	}
    
}
