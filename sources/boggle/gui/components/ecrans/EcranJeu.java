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

public class EcranJeu extends Ecran implements Observer  {
	
	private static final long serialVersionUID = 1L;
	private static EcranJeu instance;
	private Thread thread;
	private GrillePanel grilleGraphique;
	public static Ecran getInstance() {
		//TODO : supprimer singleton
		return new EcranJeu();
	}
	
	private EcranJeu() {
		Game.modele.setJoueurEnCours(Game.modele.getListeJoueurs().get(0));
		Game.modele.addObserver(this);
		this.setBackground(Color.BLUE);
		System.out.println("ECRAN JEU");
		init();
		
		//new Thread(Game.modele).start();;
		Game.modele.lancerLaPartie();
		
	}

	@Override
	public void init() {
		
		
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
