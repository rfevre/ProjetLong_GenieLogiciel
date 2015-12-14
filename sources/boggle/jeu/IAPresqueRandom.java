/**
 * 
 */
package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import boggle.autre.Utils;
import boggle.gui.components.panels.TextInputPanel;
import boggle.gui.core.Game;
import boggle.mots.ArbreLexical;
import boggle.mots.De;
import boggle.mots.GrilleLettres;


public class IAPresqueRandom extends Joueur {

	private ArbreLexical arbre;

	private static Pattern pattern;
	private static Matcher matcher;

	public IAPresqueRandom(String nom){
		super(nom);
		this.arbre = Game.modele.getArbre();
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
		int x = rand.nextInt(4);
		int y = rand.nextInt(4);


		//int taille = rand.nextInt(16);


		De de = grille.getDe(x, y);
		de.setDejaVisite(true);

		listeRetourner.add(de);




		De lastDe = listeRetourner.get(listeRetourner.size()-1);

		List<De> listeAdjacents = grille.getListeDesAdjacents(lastDe);

		for(De unDeAdj : listeAdjacents){

			List<De> ls = grille.getListeDeAdjacentsNonVisites(unDeAdj);
			listeRetourner.add( grille.getRandomDeFromList(ls));



				String mot = "";
				for (De current : listeRetourner){
					mot += current.getChaineFaceVisible();
				}

				List<String> listeMots = new ArrayList<>();
				arbre.motsCommencantPar(mot, listeMots);

				if(listeMots.contains(mot)){
					System.out.println("----> " + mot);
					return listeRetourner;
				}else
				if(listeMots.isEmpty()){
					listeRetourner.remove(listeRetourner.size()-1);
					continue;
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







	@Override
	public synchronized void jouer() {

		try {
			System.out.println(nom + " EST ENTRAIN DE JOUEUR !!!");
			List<De> liste = choisirUnMot(Game.modele.getGrille());
			Game.modele.getGrille().resetDejaVisite();
			StringBuilder str = new StringBuilder();
			for (De de : liste) {
				str.append(de.getChaineFaceVisible());
				System.out.println(de.toString());
				TextInputPanel.sourceMessage = "click";
				Game.modele.getGrille().addDeToListeDesSelectionnes(de);
				Thread.sleep(30);
			}
			Game.modele.getJoueurEnCours().ajouterUnMot(str.toString());
			Game.modele.getGrille().resetDejaVisite();
			Game.modele.getGrille().setListeDeSelectionnes(new LinkedList<>());
			Thread.sleep(100);
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

















	public static void main(String[] args) {

		GrilleLettres grilleTest = new GrilleLettres();
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
	}

}