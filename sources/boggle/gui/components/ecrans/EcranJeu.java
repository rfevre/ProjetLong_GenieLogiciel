package boggle.gui.components.ecrans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import boggle.gui.components.panels.GrillePanel;
import boggle.gui.components.panels.InfosPanel;
import boggle.gui.components.panels.ListeJoueursPanel;
import boggle.gui.components.panels.ListeMotsPanel;
import boggle.gui.components.panels.TextInputPanel;
import boggle.gui.core.Game;

/**
 * Classe EcranJeu qui correspond à l'écran de jeu affiché à l'utilisateur
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class EcranJeu extends Ecran implements Observer  {
	
	private static EcranJeu instance;
	private Thread thread;
	private GrillePanel grilleGraphique;
	private TextInputPanel 	textInputPanel;
	
	/**
	 * Renvoie l'instance courante
	 * @return
	 */
	public static Ecran getInstance() {
		return new EcranJeu();
	}
	
	/**
	 * Constructeur
	 */
	private EcranJeu() {
		Game.modele.setJoueurEnCours(Game.modele.getListeJoueurs().get(0));
		Game.modele.addObserver(this);
		this.setBackground(Color.BLUE);
		System.out.println("ECRAN JEU");
		initLayout();
		Game.modele.lancerLaPartie();
		
	}
	
	
	/* (non-Javadoc)
	 * @see boggle.gui.components.ecrans.Ecran#initLayout()
	 */
	public void initLayout() {
		
		
		this.setLayout(new BorderLayout());
		ListeJoueursPanel listeJoueursPenel = new ListeJoueursPanel();
		InfosPanel 		infosPanel 		= new InfosPanel();
		ListeMotsPanel 	listeMotsPanel 	= new ListeMotsPanel();
		textInputPanel 	= new TextInputPanel();
		grilleGraphique = new GrillePanel();
		this.add(infosPanel, BorderLayout.NORTH);

		this.add(listeJoueursPenel, BorderLayout.WEST);
		
		this.add(grilleGraphique, BorderLayout.CENTER);
		this.add(listeMotsPanel, BorderLayout.EAST);
		
		
		this.add(textInputPanel, BorderLayout.SOUTH);
	}

	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		if("grille".equals(arg)){
			this.remove(grilleGraphique);
			grilleGraphique = new GrillePanel();
			grilleGraphique.setGrille(Game.modele.getGrille());
			textInputPanel.setGrille(Game.modele.getGrille());
			textInputPanel.getChampSaisie().setText("");
			this.add(grilleGraphique, BorderLayout.CENTER);			
			this.revalidate();
			this.repaint();
			
			
		}
	}

}
