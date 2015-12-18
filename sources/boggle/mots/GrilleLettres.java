package boggle.mots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Stack;

import boggle.autre.Utils;
import boggle.gui.components.panels.TextInputPanel;

public class GrilleLettres extends Observable {
    
    private int dimension;				// taille de la grille
    private De[][] grille;			
    private Deque<De> listeDeSelectionnes;
    
    // CONSTRUCTORS ///////////////////////////////////////////////////////////
    
    /**
     * Initialise une grille de lettres composé de dès
     */
    public GrilleLettres(){
    	this(4,	Utils.DOSSIER_CONFIG + Utils.getConfigProperty("des"));
    	this.setListeDeSelectionnes(new LinkedList<De>());
    	this.setChanged();
    	this.notifyObservers();
    }
    
    /**
     * Initialise une grille de lettres composé de dès, avec une certaine dimension
     * @param dimension : dimension de la grille
     */
    public GrilleLettres(int dimension){
    	this.dimension = dimension;
    	this.grille = new De[dimension][dimension];
    }
    
    /**
     * Initialise une grille de lettres composé de dès, avec une certaine dimension, depuis un fichier
     * @param dimension : dimension de la grille
     * @param chemin : chemin du fichier
     */
    public GrilleLettres(int dimension, String chemin){
    	this(dimension);
    	this.initGrilleDepuisFichier(chemin);
    }

    // GET-SET ////////////////////////////////////////////////////////////////
    
    public int getDimension() { return dimension; }
    public De[][] getGrille() { return grille; }
    
    public void setDimension(int dimension) { this.dimension = dimension; }
    public void setGrille(De[][] grille) { 
    	this.grille = grille;
    	this.setChanged();
		this.notifyObservers();
    }
       
    /**
     * Récupére les dès sélectionné par l'utilisateur dans la grille 
     * @return dès sélectionné
     */
    public Deque<De> getListeDeSelectionnes() { return listeDeSelectionnes; } 
    
    /**
     * Permet de notifier à la grille les dès selectionnés
     * @param listeDeSelectionnes : liste de dès que le joueur à selectionnés
     */
    public void setListeDeSelectionnes(Deque<De> listeDeSelectionnes) { 
    	this.listeDeSelectionnes = listeDeSelectionnes;
		this.setChanged();
		this.notifyObservers();
	}
    
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
	public synchronized void addDeToListeDesSelectionnes(De de){
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
		//TextInputPanel.sourceMessage = "click";
		this.setChanged();
		this.notifyObservers(de);
	}
	
	/**
	 * Retire le dernier dè selectionné
	 */
	public void removeLastDesSelectionnes(){
		if(!this.listeDeSelectionnes.isEmpty() && "clavier".equals(TextInputPanel.sourceMessage)){
			De de = this.listeDeSelectionnes.removeLast();
			de.setDejaVisite(false);
			this.setChanged();
			this.notifyObservers(de);
		}
	}
	
	/**
	 * Permet de recuperer un de grâce à ses coordonné x, y
	 * @param x
	 * @param y
	 * @return le dè à la position x,y
	 */
	public De getDe(int x, int y){
		if(x<0 || x>dimension-1 || y<0 || y>dimension-1) return null;
		return this.grille[x][y];
	}
	
	/** Permet d'initialiser une grille a partir d'une chaine qui represente le contenu de chaque dè
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
		this.setChanged();
		this.notifyObservers();
	}
	
    /** 
     * Permet d'initialiser la grille depuis un fichier 
     * 
     */
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
			this.setChanged();
			this.notifyObservers();
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
    	for (int i = 0; i < dimension; i++) {
    		for (int j = 0; j < dimension; j++) {
    			final De de = getDe(i, j);
    			final boolean memeLettre = lettre.equals(de.getChaineFaceVisible());
    			
    			if(memeLettre){
//    				if(this.listeDeSelectionnes.size()>0){
//    					final De dernierDe = listeDeSelectionnes.getLast();
//    					return lettreExisteDansLesDesAdjacents(dernierDe, lettre);
//    				}
    				return true;
    			}
    		}
    	}
    	return false;
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
    	if(mot.isEmpty()) return true;
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
    
    public boolean lettreExisteDansLesDesAdjacents(De de, String lettre){
    	if(de == null) return false;
    	List<De> listeDesAdjacents = getListeDesAdjacents(de);
    	List<De> listeDesLettre = getListeDesFromLettre(lettre, listeDesAdjacents);
    	return listeDesLettre.size() != 0;
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
    
    /**
     * Reset la liste de dès selectionné
     */
    public void resetListeDeSelectionnes(){
    	this.setListeDeSelectionnes(new LinkedList<De>());
    	setChanged();
    	notifyObservers();
    }

    /**
     * Permet de recuperer la liste des des adjacents
     * @param centre : recherche autour de ce dè
     * @return la liste des dès adjacents
     */
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
    
    /**
     * Permet de recuperer la liste des des adjacents, qui n'on pas été selectionné par l'utilisateur
     * @param centre : recherche autour de ce dè
     * @return la liste des dès adjacents, non selectionné
     */
    public List<De> getListeDeAdjacentsNonVisites(De centre){
    	List<De> tmp = new ArrayList<>();
    	
    	for(De de : getListeDesAdjacents(centre)){
    		if(!de.isDejaVisite()) tmp.add(de);
    	}
    	
    	return tmp;
    }
    
    /**
     * Permet d'obtenir un dè de façon aléatoire par rapport à une liste de dès envoyé en paramètre
     * @param liste de dè
     * @return un dè de la liste de dès
     */
    public De getRandomDeFromList(List<De> liste){
    	return liste.get(new Random().nextInt(liste.size()));
    }
    
    public List<De> getListeDeSelectionTemp(String str, Stack<De> resultat){
    	if(str.isEmpty()) return resultat;
    	String lettre = ""+str.charAt(0);
    	List<De> listeDeLettre = getListeDesFromLettre(lettre);
    	
   		for(De currentDe : listeDeLettre){
   			if(resultat.size()>1 && !getListeDesAdjacents(resultat.lastElement()).contains(currentDe)) continue;
   			resultat.push(currentDe);
   			if(estUnMotValide(currentDe, str.substring(1), resultat)){
   					//resultat.add(currentDe);
    				getListeDeSelectionTemp(str.substring(1), resultat);
   			}else{
   				//resultat.pop();
   			}
    			
   		}
   			
		return resultat;	
    }
    
    /**
     * Vérifie si un mot est valide en partant d'un dè
     * @param de : dè d'ou l'on commence
     * @param mot : mot à verifier
     * @param resultat : liste de dè par ou l'on est passé dans la grille
     * @return "true" si le mot est valide (composé que de dès adjacents)
     */
    public boolean estUnMotValide(De de, String mot, Stack<De> resultat){
    	de.setDejaVisite(true);
    	if(mot.length() == 0) return true;
    	final List<De> desAdjacents = getListeDesAdjacents(de);
    	final String lettre = ""+mot.charAt(0);
    	final List<De> nextDeList = getListeDesFromLettre(lettre, desAdjacents);
    	for(De d : nextDeList){
    		if(!d.isDejaVisite()){
    			d.setDejaVisite(true);
    			resultat.push(d);
    			if(estUnMotValide(d, mot.substring(1), resultat) ){
    				return true;
    			}else{
    				if(!resultat.empty())resultat.pop();
    				d.setDejaVisite(false);
    			}
    			
    		}
    	}
    	if(!resultat.empty())resultat.pop();
    	return false;
    }
    
    /**
     * Vérifie si un mot est valide en partant de n'importe ou dans la grille
     * @param mot : mot à verifier
     * @param resultat : liste de dè par ou l'on est passé dans la grille
     * @return "true" si le mot est valide (composé que de dès adjacents)
     */
    public boolean estUnMotValideBis(String mot, Stack<De> resultat){
    	if(mot==null || mot.isEmpty()) return false;
    	if(mot.length() == 1){
    		boolean estDansLaGrille = estUneLettreValide(mot);
    		if(estDansLaGrille) resultat.push(getListeDesFromLettre(mot).get(0));
    		return estDansLaGrille;
    	}
    	
    	final String premiereLettre = ""+mot.charAt(0);	
    	final List<De> listeDesLettre = getListeDesFromLettre(premiereLettre);
    	
    	for(De d : listeDesLettre){
    		//resultat.push(d);
    		if(findWordInBoard(mot, d, resultat )){
    			//d.setDejaVisite(true);
    			return true;
    		}
    		//resultat.pop();
    		d.setDejaVisite(false);
    	}
    	
    	return false;  	
    }
    
	// PRIVATE METHODS ////////////////////////////////////////////////////////
    
    private boolean findWordInBoard(String word, De de, Stack<De> resultat) {
    	String ltr = ""+word.charAt(0);
    	if(!ltr.equals(de.getChaineFaceVisible())) return false;
    	de.setDejaVisite(true);
    	resultat.push(de);
    	//System.out.println("-----------> "+ de);
		if(word.length()==1) return ltr.equals(de.getChaineFaceVisible());
		for(De adj : getListeDesAdjacents(de)){
			if(!adj.isDejaVisite()){
					if ((""+word.charAt(1)).equals(adj.getChaineFaceVisible())) {
						adj.setDejaVisite(true);
						if((findWordInBoard(word.substring(1), adj, resultat))){
							return true;
						}
					}else{
						//if(!resultat.empty()) resultat.pop();
						adj.setDejaVisite(false);
					} 
			}
		}
		
		if(!resultat.empty()) resultat.pop();
		de.setDejaVisite(false);
		return false;
	}
 
    ///////////////////////////////////////////////////////////////////////////
    
    
}