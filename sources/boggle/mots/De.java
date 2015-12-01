package boggle.mots;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
/**
 * Cette Classe represente un De de 6 faces.
 * Une lettre est inscrite sur chaque face.
 *
 * TODO : Longueur chaine differente de 6 
 */
public class De {
	
    private int x,y;								// Position du de
    private String[] faces = new String[6] ;		// Liste des faces du tableau
    private int indexFaceVisible;					// Index de la face visible
    
    // CONSTRUCTORS ///////////////////////////////////////////////////////////
    
    public De(int x, int y){
    	this.x = x;
    	this.y = y;
    	this.indexFaceVisible = 0;
    }
    
    public De(int x, int y, String lettres){
    	this(x, y);
    	this.faces = lettres.split(";");
    	setRandomFace();
    }
    
    
    // GET-SET ////////////////////////////////////////////////////////////////
        
    public int getX() { return x; }
    public int getY() { return y; }
    public String[] getFaces() { return faces; }
    public int getIndexFaceVisible() { return indexFaceVisible; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setFaces(String[] faces) { this.faces = faces; }
    public void setIndexFaceVisible(int indexFaceVisible) { this.indexFaceVisible = indexFaceVisible; }
    

	public String toString() { return getChaineFaceVisible(); }

	public boolean equals(Object o) {
		final De autre = (De) o;
		return (x == autre.getX() && y == autre.getY());  
	}
	
	
	// STATIC METHODS /////////////////////////////////////////////////////////
    /**
    * Permet de savoir si un de est present dans une liste ou pas.
    * @param de : Correspond au dé que l'on doit vérifier.
    * @param liste : Liste dans laquelle il y a tous les dés déjà selectionnés.
    * @return <code>vrai</code> si le de existe dans la liste sinon <code>false</code> 
    */
    public static boolean existeDeja(final De de, final List<De> liste){
    	if(de == null || liste == null || liste.size() == 0) return false;
    	for (De d : liste){
    		if(d.equals(de)){ return true; }
    	}
    	return false;
    }
    
    
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	

	/** Permet de recuperer la chaine presente sur la face visible du dé */
	public String getChaineFaceVisible(){
		return indexFaceVisible < faces.length ? faces[indexFaceVisible] : "-";
    }
	
	/** Permet de recuperer une face aléatoire */
	public void setRandomFace(){
		int rnd = (int) (Math.random() * 6);
		this.setIndexFaceVisible(rnd);
	}

	
	// PRIVATE METHODS ////////////////////////////////////////////////////////
    
	///////////////////////////////////////////////////////////////////////////
	
	
	
	public static void main(String[] args)
	{
		
		De de1 = new De(1,2);
		De de2 = new De(2,3);
		De de3 = new De(1,2);
		
		List<De> liste = new ArrayList<De>();
		
		liste.add(de1);
		liste.add(de2);
		
		System.out.println(existeDeja(de3, liste));
		
		
		
		
	}
	
}
