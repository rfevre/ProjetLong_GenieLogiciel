package boggle.gui.components.ecrans;

import java.awt.BorderLayout;
import java.awt.Color;

import boggle.gui.components.panels.GrillePanel;
import boggle.gui.components.panels.InfosPanel;
import boggle.gui.components.panels.ListeJoueursPanel;
import boggle.gui.components.panels.ListeMotsPanel;
import boggle.gui.components.panels.TextInputPanel;
import boggle.gui.core.Game;

public class EcranJeu extends Ecran implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private static EcranJeu instance;
	private Thread thread;
	
	public static Ecran getInstance() {
		//TODO : supprimer singleton
		return new EcranJeu();
	}
	
	private EcranJeu() {
		Game.modele.setJoueurEnCours(Game.modele.getListeJoueurs().get(0));
		this.setBackground(Color.BLUE);
		System.out.println("ECRAN JEU");
		init();
	}

	@Override
	public void init() {
		
		
		this.setLayout(new BorderLayout());
		// Liste des joueurs
		ListeJoueursPanel listeJoueursPenel = new ListeJoueursPanel();
		InfosPanel 		infosPanel 		= new InfosPanel();
		ListeMotsPanel 	listeMotsPanel 	= new ListeMotsPanel();
		TextInputPanel 	textInputPanel 	= new TextInputPanel();
		GrillePanel 	grilleGraphique = new GrillePanel();
		
		// Liste des mots
		// grille
		
		
		this.add(infosPanel, BorderLayout.NORTH);

		this.add(listeJoueursPenel, BorderLayout.WEST);
		
		this.add(grilleGraphique, BorderLayout.CENTER);
		this.add(listeMotsPanel, BorderLayout.EAST);
		
		
		this.add(textInputPanel, BorderLayout.SOUTH);
		this.lancerPartie();
	}

	///////////////////////////////////////////////////////////////////////////
	

	public void lancerPartie(){
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	@Override
	public void run() {
		Game.modele.lancerPartie();
	}
	
	public void arreterPartie(){
		try {
			this.thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
