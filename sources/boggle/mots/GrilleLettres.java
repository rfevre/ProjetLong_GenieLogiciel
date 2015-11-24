class GrilleLettres
{
    // Attribut : taille de la grille
    private int taille;

    // Attribut : grille qui correspont à un tableau à deux dimensions de De
    private De[][] grille;

    // Constructeur 
    public GrilleLettres()
    {
	this.taille = 4;
	this.grille = new De[this.taille][this.taille];
    }
    

    public void affecterDes()
    {
	for(int i=0;i<this.taille;i++){
	    
	    for(int j=0;j<this.taille;j++){
		int rndY = (int) (Math.random()*this.taille);
		int rndX = (int) (Math.random()*this.taille);
		if(estVide(rndX,rndY)){
		    this.grille[rndX][rndY] = new De(rndX, rndY); // A compléter
		}
	    }
	}
    }


    //ESTVIDE
    
}
