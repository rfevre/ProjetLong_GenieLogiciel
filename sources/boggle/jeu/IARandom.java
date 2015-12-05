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
	 * M�thode qui choisit al�atoirement un mot 
	 * @return Retourne un liste de De (qui forme un mot )
	 */
	public List<De> choisirUnMot(GrilleLettres grille){
		
		List<De> listeRetourner = new ArrayList<De>();
		
		
		// On s�lectionne un nombre al�atoire de 3(inclus) � 17(exclus)
		Random rand = new Random();
		int nbAlea = rand.nextInt(14) + 3;
		
		// On s�lectionne un De de depart de fa�on al�atoire de la grille
		// Mettre setDejaVisite() � true && ajouter ce De � la liste
		int x, y;
		x = rand.nextInt(4);
		y = rand.nextInt(4);
		De de = grille.getDe(x, y);
		de.setDejaVisite(true);
		listeRetourner.add(de);
		
		
		int i=1;
		boolean deAdj = true;
		
		while (listeRetourner.size() < nbAlea && deAdj == true ){
			
			// On r�cup�re le d� mis dans la liste pr�cedemment
			De leDe = listeRetourner.get(i-1);
			
			// On r�cup�re ces d�s adjacents
			List<De> listeAdjacents = grille.getListeDesAdjacents(leDe);
			
			// Parmis ces d�s adjacents on r�cup�re un d� valide
			De unDeAdj = unDeAdjacentValide(listeAdjacents);
			
			// Si le unDeAdj retourne null, donc il n'y a aucun d� adjacent valide
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
	 * Cette m�thode retourne un D� valide pr�sent dans la liste
	 * Si aucun d� n'est valide ( attribut dejaVisit� � true ), on retourne null
	 * @param liste
	 * @return de ou null
	 */
	public De unDeAdjacentValide(List<De> liste){
		
		// On m�lange al�atoirement la liste
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
