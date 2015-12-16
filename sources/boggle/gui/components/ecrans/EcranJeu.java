package boggle.gui.components.ecrans;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import boggle.gui.components.panels.GrillePanel;
import boggle.gui.components.panels.InfosPanel;
import boggle.gui.components.panels.ListeJoueursPanel;
import boggle.gui.components.panels.ListeMotsPanel;
import boggle.gui.components.panels.TextInputPanel;
import boggle.gui.core.Game;

/**
*
* Classe qui correspond à l'EcranJeu
* @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
* @version 1.0
*/
public class EcranJeu extends Ecran implements Observer  {
	
	private static final long serialVersionUID = 1L;
	private static EcranJeu instance;
	private Thread thread;
	private GrillePanel grilleGraphique;
	public static Ecran getInstance() {
		//TODO : supprimer singleton
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
		
		//new Thread(Game.modele).start();;
		Game.modele.lancerLaPartie();
		
	}

//	@Override
//	public void initLayout() {
//		this.setLayout(new GridBagLayout());
//
//		GridBagConstraints gbc = new GridBagConstraints();
//		
//		// Liste des joueurs
//		ListeJoueursPanel listeJoueursPenel = new ListeJoueursPanel();
//		InfosPanel 		infosPanel 		= new InfosPanel();
//		ListeMotsPanel 	listeMotsPanel 	= new ListeMotsPanel();
//		TextInputPanel 	textInputPanel 	= new TextInputPanel();
//		grilleGraphique = new GrillePanel();
//		
//		// Liste des mots
//		// grille
//		
//		gbc.weightx = 1; gbc.weighty = 0.5;
//		gbc.fill = GridBagConstraints.BOTH;
//		gbc.gridx=0; gbc.gridy =0; gbc.gridwidth = 4; gbc.gridheight = 1;
//		this.add(infosPanel, gbc);
//		
//		gbc.weightx = 0.2;
//		gbc.gridx=0; gbc.gridy =1; gbc.gridwidth = 1; gbc.gridheight = 3; gbc.weighty = 2;
//		this.add(listeJoueursPenel, gbc);
//		
//		gbc.weightx = 0.6;
//		gbc.gridx=1; gbc.gridwidth = 2; gbc.gridwidth = 1; gbc.gridheight = 3; gbc.weighty = 2;
//		this.add(grilleGraphique, gbc);
//		
//		gbc.weightx = 0.2;
//		gbc.gridx=3; gbc.gridwidth = 1; gbc.gridwidth = 1; gbc.gridheight = 3; gbc.weighty = 2;
//		this.add(listeMotsPanel, gbc);
//		
//		gbc.weightx = 1;
//		gbc.gridx=0; gbc.gridy =4; gbc.gridwidth = 4; gbc.gridheight = 1; gbc.weighty = 0.5;
//		this.add(textInputPanel, gbc);
//		//this.lancerPartie();
//	}
	
	
	/**
	*
	* Méthode qui initiliase l'EcranJeu en ajoutant les éléments
	*
	*/
	public void initLayout() {
		
		
		this.setLayout(new BorderLayout());
		// Liste des joueurs
		ListeJoueursPanel listeJoueursPenel = new ListeJoueursPanel();
		InfosPanel 		infosPanel 		= new InfosPanel();
		ListeMotsPanel 	listeMotsPanel 	= new ListeMotsPanel();
		TextInputPanel 	textInputPanel 	= new TextInputPanel();
		grilleGraphique = new GrillePanel();
		
		// Liste des mots
		// grille
		
		
		this.add(infosPanel, BorderLayout.NORTH);

		this.add(listeJoueursPenel, BorderLayout.WEST);
		
		this.add(grilleGraphique, BorderLayout.CENTER);
		this.add(listeMotsPanel, BorderLayout.EAST);
		
		
		this.add(textInputPanel, BorderLayout.SOUTH);
		//this.lancerPartie();
	}

	
	@Override
	public void update(Observable o, Object arg) {
		if("grille".equals(arg)){
			this.remove(grilleGraphique);
			grilleGraphique = new GrillePanel();
			grilleGraphique.setGrille(Game.modele.getGrille());
			this.add(grilleGraphique, BorderLayout.CENTER);			
			this.revalidate();
			this.repaint();

			
			
		}
	}

	///////////////////////////////////////////////////////////////////////////
	
//	public void lancerLaPartie() {
//		System.out.println("__________________________________________________________________ DEBUT DE LA PARTIE ____");
//		Iterator<Joueur> it = Game.modele.getListeJoueurs().iterator();
//		while(it.hasNext()){
//			Joueur joueur = it.next();
//			Game.modele.setJoueurEnCours(joueur);	
//			joueur.jouer();
//
//			//it.remove();
//			joueur.arreterDeJoueur();
//		}
//		System.out.println("__________________________________________________________________ FIN DE LA PARTIE ____");
//		Game.goToEcran(TypeEcrans.MENU);
//	}
//	public void lancerPartie(){
//		for(Joueur joueur : Game.modele.getListeJoueurs()){
//			Game.modele.setJoueurEnCours(joueur);
//			while(joueur.isEntrainDeJouer()){
//				this.thread = new Thread(joueur);
//				
//				
//			}
//			joueur.arreterDeJoueur();
//			
//		}
//	}
//	
//
//	public void arreterPartie(){
//		try {
//			this.thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
	

}
