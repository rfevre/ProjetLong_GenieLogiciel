package boggle.gui.components.ecrans;

import java.awt.BorderLayout;
import java.awt.Color;

import boggle.gui.components.panels.GrillePanel;
import boggle.gui.components.panels.InfosPanel;
import boggle.gui.components.panels.ListeJoueursPanel;
import boggle.gui.components.panels.ListeMotsPanel;
import boggle.gui.components.panels.TextInputPanel;

public class EcranJeu extends Ecran{
	
	private static final long serialVersionUID = 1L;
	private static EcranJeu instance;
	
	
	public static Ecran getInstance() {
//		if(instance == null){
//			instance = new EcranJeu();
//		}
//		return instance;
		return new EcranJeu();
	}
	
	private EcranJeu() {
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
		GrillePanel grilleGraphique = new GrillePanel();
		
		// Liste des mots
		// grille
		
		
		this.add(infosPanel, BorderLayout.NORTH);

		this.add(listeJoueursPenel, BorderLayout.WEST);
		
		this.add(grilleGraphique, BorderLayout.CENTER);
		this.add(listeMotsPanel, BorderLayout.EAST);
		
		
		this.add(textInputPanel, BorderLayout.SOUTH);
	}

	///////////////////////////////////////////////////////////////////////////
	




	

}
