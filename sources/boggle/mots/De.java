class De {

    // Attribut : Position du dé
    private int x,y;

    // Attribut : Tableau de String correspondant aux faces du dé
    private String[] faces = new String[6] ; 

    // Attribut : Index de la face visible
    private int faceVisible;

    // Constructeur sans paramètres
    public De(){
	x = y = -1; 
	faceVisible = 0;
    }

    // Construteur avec String faces
    public De(String f){
	this();
	this.faces = f.split(";");
    }
    
    // Retourne string face visible
    public String stringFaceVisible()
    {
	return faces[faceVisible];
    }
    
}
