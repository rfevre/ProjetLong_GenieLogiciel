/**
 * 
 */
package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import boggle.autre.Utils;
import boggle.mots.ArbreLexical;
import boggle.mots.De;
import boggle.mots.GrilleLettres;


public class IAPresqueRandom extends Joueur {

	private ArbreLexical arbre;
	
	public IAPresqueRandom(String nom){
		super(nom);
		
	}
	
    public ArbreLexical getArbre() { return arbre; }            
    
    public void setArbre(ArbreLexical arbre) { this.arbre = arbre; } 


	/**
	 * Méthode qui choisit aléatoirement un mot mais qui se trouve dans le dico 
	 * @return Retourne une liste de De (qui forme un mot )
	 */
	public List<De> choisirUnMot(GrilleLettres grille){
		
		List<De> listeRetourner = new ArrayList<De>();
		
		// On sélectionne un De de depart de façon aléatoire dans la grille
		// Mettre setDejaVisite() à true && ajouter ce De à la liste
		Random rand = new Random();
		int x, y;
		x = rand.nextInt(4);
		y = rand.nextInt(4);
		De de = grille.getDe(x, y);
		de.setDejaVisite(true);
		listeRetourner.add(de);
		
		
		int i=1;
		boolean deAdj = true;
		
		while (deAdj == true ){
			
			// On récupére le dè mis dans la liste prècedemment
			De leDe = listeRetourner.get(i-1);
			
			// On récupére ces dès adjacents
			List<De> listeAdjacents = grille.getListeDesAdjacents(leDe);
			
			// Parmis ces dès adjacents on récupère un dè valide
			De unDeAdj = unDeAdjacentValide(listeAdjacents);
			
			// Si le unDeAdj retourne null, donc il n'y a aucun dè adjacent valide
			if(unDeAdj == null)
			{
				deAdj = false;
			}
			else 
			{
				unDeAdj.setDejaVisite(true);
				listeRetourner.add(unDeAdj);
				i++;
				
				String chaine = "";
				for(De lettre : listeRetourner){
					chaine+= lettre.getChaineFaceVisible();
				}
				List<String> liste = new ArrayList<>();
				arbre.motsCommencantPar(chaine, liste);
				
				System.out.println(chaine);
				System.out.println(arbre.contient(chaine));
				System.out.println(liste);
				
				if(listeRetourner.size()>=3 && liste.contains(chaine)){
					return listeRetourner;
				}
				else if(listeRetourner.size()>=3 ){
					listeRetourner.remove(unDeAdj);
					i--;
				}
			}
		}
		
		return listeRetourner;
	}
	
	/**
	 * Cette méthode retourne un Dè valide présent dans la liste
	 * Si aucun dè n'est valide ( attribut dejaVisité à true ), on retourne null
	 * @param liste
	 * @return de ou null
	 */
	public De unDeAdjacentValide(List<De> liste){
		
		// On mélange aléatoirement la liste
		Collections.shuffle(liste);
		
		for (De de : liste) {
			if(!de.isDejaVisite()){
				return de;
			}				
		}
		return null;
	}
	
	public static void main(String[] args) {
		GrilleLettres grilleTest = new GrilleLettres(4, "config/des-4x4.csv");
		System.out.println(grilleTest.toString());
		IAPresqueRandom j = new IAPresqueRandom("J");
		List<De> liste = j.choisirUnMot(grilleTest);
		for (De de : liste) {
			//System.out.println(de.getX()+" - "+de.getY());
			System.out.println(de.toString());
		}
		
		
	}
	
}