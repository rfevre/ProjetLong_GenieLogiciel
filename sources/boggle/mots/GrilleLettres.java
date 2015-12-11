package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

import boggle.autre.Utils;

public class GrilleLettres extends Observable {
    
    private int dimension;				// taille de la grille
    private De[][] grille;			
    private Deque<De> listeDeSelectionnes;
    
    // CONSTRUCTORS ///////////////////////////////////////////////////////////
    public GrilleLettres(){
    	this(4,	Utils.DOSSIER_CONFIG + Utils.getConfigProperty("des"));
    	this.setListeDeSelectionnes(new LinkedList<De>());
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
        
    public Deque<De> getListeDeSelectionnes() { return listeDeSelectionnes; }  
    public void setListeDeSelectionnes(Deque<De> listeDeSelectionnes) { this.listeDeSelectionnes = listeDeSelectionnes; }

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
	
	/**
	 * Permet de mettre à jour la liste des dés sélectionnés
	 * @param de a ajouter
	 */
	public void updateListeDesSelectionnes(De de){
		if(de!=null){
			if(listeDeSelectionnes.isEmpty()){
				de.setDejaVisite(true);
				listeDeSelectionnes.add(de);

			}else{
				if(de.isDejaVisite() && de.equals(listeDeSelectionnes.getLast())){
					listeDeSelectionnes.removeLast();
					de.setDejaVisite(false);
				}
				else if(!de.isDejaVisite() && getListeDesAdjacents(listeDeSelectionnes.getLast()).contains(de)){
					de.setDejaVisite(true);
					listeDeSelectionnes.add(de);
				}
			}
		}
		this.setChanged();
		this.notifyObservers(de);
	}
	
	public void updateStatutListeDes(List<De> liste){
		for(De d : liste){
			d.setDejaVisite(true);
		}
		this.setChanged();
		this.notifyObservers();
		
	}
	
	
	
	/** Permet de recuperer un de avec ses coord x, y*/
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
    // TODO trouver tous les mots.
//    public String getTousLesMots(De de, String str){
//    	//str += de.getChaineFaceVisible();
//    	//System.out.println(de);
//    	for(De d : getListeDesAdjacents(de)){
//    		if(!d.isDejaVisite()){
//    			
//    			d.setDejaVisite(true);
//    			str += getTousLesMots(d, d.getChaineFaceVisible());
//    			d.setDejaVisite(false);
//    			
//    		}else
//    			continue;
//    		
//    	}
//    	return str+"\n";
//    }

    
    
//    public ArbreLexical genererArbreDebutsMots(){
//    	ArbreLexical arbre = new ArbreLexical();
//    	
//    	for (int i = 0; i < dimension; i++) {
//    		for (int j = 0; j < dimension; j++) {
//    			De premierDe = this.getDe(i, j);
//    			trouverTout(premierDe.getChaineFaceVisible(), premierDe, arbre);
//    			resetDejaVisite();
//    		}
//    	}
//    	return arbre;
//    }
//    
//
//    private void trouverTout(String motEnCours, De deEnCours, ArbreLexical arbre) {
//    	if(motEnCours.length()>8) return;
//    	deEnCours.setDejaVisite(true);
//    	//if(motEnCours.length()==16)
//    	//System.out.println(motEnCours);
//    	if(estUnMotValide(deEnCours, motEnCours)){
//    		//System.out.println(motEnCours);
//    		arbre.ajouter(motEnCours);
//    	}
//    	List<De> desAdjacents = getListeDesAdjacents(deEnCours);
//    	for(De de : desAdjacents){
//    		if(de.isDejaVisite()) continue;
//    		de.setDejaVisite(true);
//    		trouverTout(motEnCours+de.getChaineFaceVisible(), de, arbre);
//    		de.setDejaVisite(false);
//    	}
//    }
    
    
    
    
    
    
    
    
    
    
    void recupererTousLesMots(De de, String str, List<String> motsTrouves, List<De> dejaVisite){
    	//if(str.length()<3 && str.length()>10) return;
    	// Mark current cell as visited and append current character
    	de.setDejaVisite(true);
    	str += de.getChaineFaceVisible();
    	//System.out.println(str);
    	if(!dejaVisite.contains(de)){
    		motsTrouves.add(str);
    		
    	}
    		

		// Traverse 8 adjacent cells of boggle[i][j]
    		for(De autre : getListeDesAdjacents(de)){
    			if (!autre.isDejaVisite()){
    				recupererTousLesMots(autre, str, motsTrouves, dejaVisite);
    			}
    			
    		}

    	// Erase current character from string and mark visited
    	// of current cell as false
    	str = str.substring(1);
    	de.setDejaVisite(false);
    }

    //Prints all words present in dictionary.
    public List<String> getTousLesMotsQuiCommencentPar(String s){
    	// Mark all characters as not visited
    	List<String> motsTrouves = new ArrayList<String>();
    	// Initialize current string
    	// Consider every character and look for all words
// starting with this character
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			final De premierDe = getDe(i, j);
    			final List<De> dejaVisite = new ArrayList<>();
    			dejaVisite.add(premierDe);
    			recupererTousLesMots(premierDe, "", motsTrouves, dejaVisite);
    			resetDejaVisite();
    		}
    	}
    	
    	//arbre.afficherArbre(0);
    	return motsTrouves;
    	
    }
    
    
    
    
    public List<String> getTousLesMotsQuiCommencentPar(De premierDe){
    	// Mark all characters as not visited
    	List<String> motsTrouves = new ArrayList<String>();
    	final List<De> dejaVisite = new ArrayList<>();
    	dejaVisite.add(premierDe);
    	recupererTousLesMots(premierDe, "", motsTrouves, dejaVisite);
    	resetDejaVisite();
    	
    	//arbre.afficherArbre(0);
    	return motsTrouves;
    	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    
   

	public static void main(String[] args) {
//    	GrilleLettres g = new GrilleLettres();
//    	//g.initGrilleDepuisChaine("ABCD EFGH IJKL MNOP");
//    	System.out.println(g);
//    	 Set<String> ls = g.getTousLesMots();;
//    	System.out.println(ls.size());
//    	ArbreLexical arbre = ArbreLexical.creerArbreDepuisUneListe(ls);
//    	arbre.afficherArbre(0);
		
		GrilleLettres g = new GrilleLettres();
		g.initGrilleDepuisChaine("ABCD EFGH IJKL MNOP");
		//g.initGrilleDepuisChaine("AAAA BBBB CCCC DDDD");
		//g.initGrilleDepuisChaine("ABCD ABCD ABCD ABCD");
		System.out.println(g);
		//System.out.println(g.estUnMotValide(g.getDe(0, 0), "A"));
		
//		
		List<String> ls = g.getTousLesMotsQuiCommencentPar(g.getDe(0, 0));
		System.out.println(ls.size());
		//System.out.println(ls);
		ArbreLexical arbre = ArbreLexical.creerArbreDepuisUneListe(ls);
		//arbre.afficherArbre(0);
//		

    	
    	
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
