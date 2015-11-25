package boggle.mots;

/**
 * TODO : 
 * 		- Longueur chaine differente de 6 
 */
public class De {

    
	
    private int x,y;								// Position du de
    private String[] faces = new String[6] ;		// Liste des faces du tableau
    private int indexFaceVisible;					// Index de la face visible
    
    // CONSTRUCTEURS //////////////////////////////////////////////////////////
    
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
    

	public String toString() {
		//return "-> Pos(" + x + "," + y + "), faces=" + Arrays.toString(faces) + ", Face visible : " + indexFaceVisible+ " -> " + getChaineFaceVisible();
		//return "(" + x + "," + y + "," +getChaineFaceVisible()+")";
		return getChaineFaceVisible();
	}
    
	
	// PUBLIC METHODS /////////////////////////////////////////////////////////
	
	/** Permet de recuperer la chaine presente sur la face visible du dé */
	public String getChaineFaceVisible(){
		return indexFaceVisible < faces.length ? faces[indexFaceVisible] : "-";
    }
	
	/** Permet de recupere une face aleatoir */
	public void setRandomFace(){
		int rnd = (int) (Math.random() * 6);
		this.setIndexFaceVisible(rnd);
	}




	
	
	
	// PRIVATE METHODS ////////////////////////////////////////////////////////
    
    
}
