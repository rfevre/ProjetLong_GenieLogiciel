package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import boggle.mots.De;
import boggle.mots.GrilleLettres;

public class IARandom extends Joueur {

	public IARandom(String nom)
	{
		super(nom);
	}
	
	/**
	 * Méthode qui choisit aléatoirement un mot 
	 * @return Retourne un liste de De (qui forme un mot )
	 */
	public List<De> choisirUnMot(GrilleLettres grille){
		
		List<De> listeRetourner = new ArrayList<De>();
		
		
		// On sélectionne un nombre aléatoire de 3(inclus) à 17(exclus)
		Random rand = new Random();
		int nbAlea = rand.nextInt(14) + 3;
		
		// On sélectionne un De de depart de façon aléatoire de la grille
		// Mettre setDejaVisite() à true && ajouter ce De à la liste
		int x, y;
		x = rand.nextInt(4);
		y = rand.nextInt(4);
		De de = grille.getDe(x, y);
		de.setDejaVisite(true);
		listeRetourner.add(de);
		
		
		int i=1;
		boolean deAdj = true;
		
		while (listeRetourner.size() < nbAlea && deAdj == true ){
			
			// On récupère le dé mis dans la liste précedemment
			De leDe = listeRetourner.get(i-1);
			
			// On récupère ces dés adjacents
			List<De> listeAdjacents = grille.getListeDesAdjacents(leDe);
			
			// Parmis ces dés adjacents on récupère un dé valide
			De unDeAdj = unDeAdjacentValide(listeAdjacents);
			
			// Si le unDeAdj retourne null, donc il n'y a aucun dé adjacent valide
			if(unDeAdj == null)
			{
				deAdj = false;
			}
			else 
			{
				unDeAdj.setDejaVisite(true);
				listeRetourner.add(unDeAdj);
				i++;
			}
		}
		
		return listeRetourner;
	}
	
	/**
	 * Cette méthode retourne un Dé valide présent dans la liste
	 * Si aucun dé n'est valide ( attribut dejaVisité à true ), on retourne null
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
		/*IARandom r = new IARandom("test");
		
		for (int i = 0; i < 10; i++) {
			System.out.println(r.choisirUnMot());
		}*/
		
	}
	
}
