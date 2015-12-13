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
import java.util.Stack;

import boggle.autre.Utils;
import boggle.gui.components.panels.TextInputPanel;

public class GrilleLettres extends Observable {
    
    private int dimension;				// taille de la grille
    private De[][] grille;			
    private Deque<De> listeDeSelectionnes;
    
    // CONSTRUCTORS ///////////////////////////////////////////////////////////
    public GrilleLettres(){
    	this(4,	Utils.DOSSIER_CONFIG + Utils.getConfigProperty("des"));
    	this.setListeDeSelectionnes(new LinkedList<De>());
    	this.setChanged();
    	this.notifyObservers();
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
    public void setGrille(De[][] grille) { 
    	this.grille = grille;
    	this.setChanged();
		this.notifyObservers();
    }
        
    public Deque<De> getListeDeSelectionnes() { return listeDeSelectionnes; }  
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
	public void addDeToListeDesSelectionnes(De de){
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

	public void removeLastDesSelectionnes(){
		if(!this.listeDeSelectionnes.isEmpty() && "clavier".equals(TextInputPanel.sourceMessage)){
			De de = this.listeDeSelectionnes.removeLast();
			de.setDejaVisite(false);
			this.setChanged();
			this.notifyObservers(de);
		}
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
		this.setChanged();
		this.notifyObservers();
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
    
    
    
    
    
    
    
    public boolean estUnMotValide(De de, String mot, Stack<De> resultat){
    	de.setDejaVisite(true);
    	//System.out.println(mot + " : " + de + " Deja Visite : " + de.isDejaVisite());
    	//resultat.push(de);
    	//System.out.print (de + "("+de.getX() + ","+de.getY()+") »» ");
    	if(mot.length() == 0) return true;
    	final List<De> desAdjacents = getListeDesAdjacents(de);
    	final String lettre = ""+mot.charAt(0);
    	final List<De> nextDeList = getListeDesFromLettre(lettre, desAdjacents);
    	for(De d : nextDeList){
    		if(!d.isDejaVisite()){
    			//System.out.println(de + "("+de.getX() + ","+de.getY()+")");
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
    
    
    
    
    
    
    
    
    private boolean findWordInBoard(String word, De de, Stack<De> resultat) {
    	String ltr = ""+word.charAt(0);
    	if(!ltr.equals(de.getChaineFaceVisible())) return false;
    	de.setDejaVisite(true);
    	resultat.push(de);
    	System.out.println("-----------> "+ de);
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
		//g.initGrilleDepuisChaine("ABCD EAGH IJKL MNOP");
		g.initGrilleDepuisChaine("TSET LSNA ANVA FUIE");
		//g.initGrilleDepuisChaine("ABCD ABCD ABCD ABCD");
		System.out.println(g);
		De de = g.getDe(0, 0);
		System.out.println(g.lettreExisteDansLesDesAdjacents(de, "K"));
		//System.out.println(g.estUnMotValide(g.getDe(0, 0), "A"));
		

		Stack<De> res = new Stack<>();
		//System.out.println(">>> " + g.findWordInBoard("IVAE", g.getDe(3,2)));
		//System.out.println(">>> " + g.estUnMotValideBis("SENVA", res));
		//System.out.println(res);
//		List<De> ls = g.getListeDeSelectionTemp("FUN", res);
//		System.out.println(ls.size());
//		System.out.println(ls);

    	
    	
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
