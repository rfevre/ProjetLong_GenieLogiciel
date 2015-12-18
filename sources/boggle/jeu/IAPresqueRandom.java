/**
 * 
 */
package boggle.jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import boggle.gui.components.panels.TextInputPanel;
import boggle.gui.core.Game;
import boggle.mots.ArbreLexical;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

/**
 * Classe IAPresqueRandom qui correspond à l'intelligence artificielle
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class IAPresqueRandom extends Joueur {

	private ArbreLexical arbre;

	/**
	 * Constructeur
	 * @param nom 
	 */
	public IAPresqueRandom(String nom){
		super(nom);
		this.type = TypeJoueur.IA_RANDOM_NIV2;
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
		Random rand = new Random();
		int x = rand.nextInt(4);
		int y = rand.nextInt(4);
		De de = grille.getDe(x, y);
		de.setDejaVisite(true);
		listeRetourner.add(de);
		De lastDe = listeRetourner.get(listeRetourner.size()-1);
		List<De> listeAdjacents = grille.getListeDesAdjacents(lastDe);
		for(De unDeAdj : listeAdjacents){
			List<De> ls = grille.getListeDeAdjacentsNonVisites(unDeAdj);
			if(ls.isEmpty()) continue;
			listeRetourner.add( grille.getRandomDeFromList(ls));
			String mot = "";
			for (De current : listeRetourner){
				mot += current.getChaineFaceVisible();
			}
			List<String> listeMots = new ArrayList<>();
			arbre.motsCommencantPar(mot, listeMots);
			if(listeMots.contains(mot)){
				return listeRetourner;
			}else
				if(listeMots.isEmpty()){
					listeRetourner.remove(listeRetourner.size()-1);
					continue;
				}		}
		return listeRetourner;
	}

	/**
	 * Cette méthode retourne un Dè valide présent dans la liste
	 * Si aucun dè n'est valide ( attribut dejaVisité à true ), on retourne null
	 * @param liste
	 * @return de ou null
	 */
	public De unDeAdjacentValide(List<De> liste){
		Collections.shuffle(liste);

		for (De de : liste) {
			if(!de.isDejaVisite()){
				return de;
			}				
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see boggle.jeu.Joueur#jouer()
	 */
	public void jouer() {

		try {
			System.out.println(nom + " EST ENTRAIN DE JOUEUR !!!");
			List<De> liste = choisirUnMot(Game.modele.getGrille());
			Game.modele.getGrille().resetDejaVisite();
			StringBuilder str = new StringBuilder();
			for (De de : liste) {
				str.append(de.getChaineFaceVisible());
				TextInputPanel.sourceMessage = "click";
				Game.modele.getGrille().addDeToListeDesSelectionnes(de);
				Thread.sleep(300);
			}
			Game.modele.getJoueurEnCours().ajouterUnMot(str.toString());
			Game.modele.getGrille().resetDejaVisite();
			Game.modele.getGrille().setListeDeSelectionnes(new LinkedList<De>());
			Thread.sleep(1000);
		} catch (InterruptedException e) { e.printStackTrace(); }
	}



}