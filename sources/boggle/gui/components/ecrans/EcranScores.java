package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		JPanel scorePanel = new JPanel();
		scorePanel.setLayout(new GridLayout(0,3));
		// On récupère le top10
		List<Joueur> joueurs = null;
		try{
			BDD bdd = new BDD();
			joueurs = bdd.getListScores();	
		}
		catch(SQLException e){e.getMessage();}

		// Layout
		gbc.gridy = 0;
		//Titre
		JLabel titre = new ListScores("Meilleure scores",SwingConstants.CENTER,300);
		this.add(titre,gbc);
		
		int i=1;
		for (Joueur j : joueurs) {
			JLabel classement = new ListScores(String.valueOf(i), SwingConstants.CENTER, 20);
			JLabel nomJoueur = new ListScores(j.getNom(), SwingConstants.LEFT,200);
			JLabel scoreJoueur = new ListScores( String.valueOf(j.getScore()), SwingConstants.RIGHT,100 );
			scorePanel.add(classement);
			scorePanel.add(nomJoueur);
			scorePanel.add(scoreJoueur);
			i++;
		}
		
		gbc.gridy = 1;
		this.add(scorePanel, gbc);
	}
	
	
	private class ListScores extends JLabel{
		
		private static final long serialVersionUID = 1L;

		public ListScores(String libelle,int alignement, int w){
			super(libelle,alignement);
			this.setText("<html><h3>" +libelle+ "</h3></html>");
			this.setOpaque(true);
			this.setForeground(Color.WHITE);
			this.setBackground(Color.GRAY);
			this.setPreferredSize(new Dimension(w, 40));
		}
	}

}
