package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import boggle.autre.BDD;
import boggle.gui.core.Fenetre;
import boggle.jeu.Joueur;

public class EcranScores extends Ecran {

	private Fenetre fenetre;
	private static final long serialVersionUID = 1L;
	private static EcranScores instance;
	
	
	public static Ecran getInstance(Fenetre fenetre) {
		if(instance == null){
			instance = new EcranScores(fenetre);
		}
		return instance;
	}
	
	private EcranScores(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.setBackground(Color.BLACK);
		System.out.println("ECRAN SCORE");
		init();
	}
	
	
	@Override
	public void init() {
		
		// On récupère le top10
		List<Joueur> joueurs = null;
		try{
			BDD bdd = new BDD();
			joueurs = bdd.getListScores();	
		}
		catch(SQLException e){e.getMessage();}

		// Layout
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		//Titre
		JLabel titre = new ListScores("Meilleure scores");
		this.add(titre);
		
		int i;
		for (Joueur j : joueurs) {
			i = 1;
			JLabel classement = new ListScores(String.valueOf(i));
			JLabel nomJoueur = new ListScores(j.getNom());
			JLabel scoreJoueur = new ListScores( String.valueOf(j.getScore()) );
			this.add(classement);
			this.add(nomJoueur);
			this.add(scoreJoueur);
			i++;
		}
		
	}
	
	
	private class ListScores extends JLabel{
		
		private static final long serialVersionUID = 1L;

		public ListScores(String libelle){
			super(libelle,SwingConstants.CENTER);
			this.setText("<html><h1>" +libelle+ "</h1></html>");
			this.setOpaque(true);
			this.setForeground(Color.WHITE);
			this.setBackground(Color.GRAY);
			this.setPreferredSize(new Dimension(200, 50));
		}
	}

}
