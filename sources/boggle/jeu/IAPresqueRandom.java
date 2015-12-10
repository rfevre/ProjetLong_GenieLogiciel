/**
 * 
 */
package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boggle.autre.Utils;
import boggle.mots.ArbreLexical;
import boggle.mots.De;
import boggle.mots.GrilleLettres;


public class IAPresqueRandom extends Joueur {

	private ArbreLexical arbre;

    private static Pattern pattern;
    private static Matcher matcher;
	
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
			{	//on ajoute le dé	
				unDeAdj.setDejaVisite(true);
				listeRetourner.add(unDeAdj);
				i++;
				//on construit la chaine grâce à la face visible de tous les dés de la liste
				String chaine = "";
				for(De lettre : listeRetourner){
					chaine+= lettre.getChaineFaceVisible();
				}
				//on créer la liste avec tous les mots commencent par la chaine (précédement créer), dans l'arbre (le dico)
				List<String> liste = new ArrayList<>();
				arbre.motsCommencantPar(chaine, liste);
				
				/*System.out.println(chaine);
				System.out.println(arbre.contient(chaine));
				System.out.println(liste);*/
				
				//on utilise un Regex pour matcher dans cette liste si la chaine est présente
				pattern = Pattern.compile(chaine+"*");
		        matcher = pattern.matcher(liste.toString());
		        
		        //on ajoute si le mot est > 3 et qu'il est contenu dans l'arbre
		        if(listeRetourner.size()>=3 && arbre.contient(chaine)){
					return listeRetourner;
				}
		        else if(matcher.find()) {
			        //si on match, on continue sur le même dé, donc on fait rien
		        }
				else {
					//sinon on retire un dè, pour passer sur un autre dé adjacent au dè précédent
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
	
	/*public static void main(String[] args) {
		GrilleLettres grilleTest = new GrilleLettres(4, "config/des-4x4.csv");
		System.out.println(grilleTest.toString());
		IAPresqueRandom j = new IAPresqueRandom("J");
		j.setArbre(ArbreLexical.creerArbreDepuisFichier(Utils.DOSSIER_CONFIG + Utils.getConfigProperty("dictionnaire")));
		List<De> liste = j.choisirUnMot(grilleTest);
		for (De de : liste) {
			System.out.println(de.toString());
		}
		System.out.println();
		liste = j.choisirUnMot(grilleTest);
		for (De de : liste) {
			System.out.println(de.toString());
		}
	}*/
	
}