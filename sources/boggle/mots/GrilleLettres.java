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

public class GrilleLettres {
    
    private int dimension;				// taille de la grille
    private De[][] grille;			

    
    // CONSTRUCTORS ///////////////////////////////////////////////////////////
    public GrilleLettres(){
    	this(4,	Utils.DOSSIER_CONFIG + Utils.getConfigProperty("des"));
    }
    
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
	
	public De getDe(int x, int y){
		if(x<0 || x>dimension-1 || y<0 || y>dimension-1) return null;
		return this.grille[x][y];
	}
	/** Permet d'initialiser une grille a partir d'une chaine qui represente le contenu de chaquue de
	 * @param chaine
	 */
	public void initGrilleDepuisChaine(String chaine){
		chaine = chaine.replaceAll(" ", "");
		int k = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				grille[i][j] = new De(i, j, Utils.repeter(chaine.charAt(k)+";", 6));
				k++;
			}
		}
	}
	
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

		} catch (FileNotFoundException e) {	System.err.println("Erreur : " + e.getMessage());
		} catch (IOException e) { System.err.println("Erreur : " + e.getMessage());
		} catch (Exception e) { System.err.println("Erreur : " + e.getMessage()); }        
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
     * Permet de verifier la validite d'un mot.<br/>
     * <strong>IMPORTANT</strong> il faut appeler la methode resetDejaVisite() 
     * pour reinitialiser les des visites.
     * @param mot mot recherche.
     * @return vrai si trouve, sinon false.
     */
    public boolean estUnMotValide(String mot){
    	if(mot==null || mot.isEmpty()) return false;
    	if(mot.length() == 1){ return estUneLettreValide(mot);}
    	
    	final String premiereLettre = ""+mot.charAt(0);	
    	final List<De> listeDesLettre = getListeDesFromLettre(premiereLettre);
    	
    	for(De d : listeDesLettre){
    		if(estUnMotValide(d, mot.substring(1))){
    			return true;
    		}
    		d.setDejaVisite(false);
    	}
    	return false;  	
    }
    
    /**
     * Permet de verifier la validitie d'un mot en partant d'un De
     * @param de De de depart
     * @param mot mot recherche
     * @return vrai si trouve sinon false.
     */
    public boolean estUnMotValide(De de, String mot){
    	de.setDejaVisite(true);
    	//System.out.print (de + "("+de.getX() + ","+de.getY()+") »» ");
    	if(mot.length() == 0) return true;
    	final List<De> desAdjacents = getListeDesAdjacents(de);
    	final String lettre = ""+mot.charAt(0);
    	final List<De> nextDeList = getListeDesFromLettre(lettre, desAdjacents);
    	for(De d : nextDeList){
    		if(!d.isDejaVisite()){
    			//System.out.println(de + "("+de.getX() + ","+de.getY()+")");
    			d.setDejaVisite(true);
    			if(estUnMotValide(d, mot.substring(1))){
    				return true;
    			}else{
    				d.setDejaVisite(false);
    			}
    			
    		}
    	}
    	return false;
    }
    
    /**
     * Permet de parcourir <strong>une grille</strong> de De et de recuperer 
     * tous ceux  qui affichent la lettre <em>lettre</em> passée en parametre.
     * @param lettre : lettre recherchee
     * @return liste de De.
     */
    public List<De> getListeDesFromLettre(String lettre){
    	List<De> tmp = new ArrayList<De>();
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			final De current = this.grille[i][j];
    			if(lettre.equals(current.getChaineFaceVisible())){
    				tmp.add(current);
    			}
    		}
    	}
    	return tmp;
    }
    
    /**
     * Permet de parcourir une liste de De et de recuperer tous ceux  qui 
     * affichent la lettre <em>lettre</em> passée en parametre.
     * @param lettre lettre recherchée.
     * @param liste liste de De
     * @return liste
     */
    public List<De> getListeDesFromLettre(String lettre, List<De> liste){
    	List<De> tmp = new ArrayList<De>();
    	for(De de : liste){
    		if(lettre.equals(de.getChaineFaceVisible())){
    			tmp.add(de);
    		}    		
    	}   	
    	return tmp;
    }
    
    /** Permet de reinistialiser les des visites. */
    public void resetDejaVisite(){
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			final De current = this.grille[i][j];
    			current.setDejaVisite(false);
    		}
    	}
    }
    
    /** Permet de recuperer la liste des des adjacents */
    public List<De> getListeDesAdjacents(De centre){
    	if(centre==null) return null;
    	List<De> desAdjacents = new ArrayList<De>();
    	int x = centre.getX();
    	int y = centre.getY();
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
    	GrilleLettres g = new GrilleLettres();
    	//g.initGrilleDepuisChaine("ABCD EFGH IJKL MNOP");
    	System.out.println(g);
    	
    	
    	
    	
    	
//    	GrilleLettres grilleTest = new GrilleLettres(4, "config/des-4x4.csv");
//
//		Joueur j = new Joueur("Toto");
//    	System.out.println(grilleTest);
//		
//		Scanner sc = new Scanner(System.in);
//		String str = "";
//		do{
//			str = sc.nextLine().toUpperCase();
//			if(grilleTest.estUnMotValide(str)){
//				j.ajouterUnMot(str);
//			}
//			grilleTest.resetDejaVisite();
//			
//			
//		}while(!"".equals(str));
//		System.out.println(j.getNom() + " : " + j.getListeMots());
//		sc.close();
	}

    
}
