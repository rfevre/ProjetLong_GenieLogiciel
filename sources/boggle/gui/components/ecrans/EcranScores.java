package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import boggle.autre.BDD;
import boggle.jeu.Joueur;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;

public class EcranScores extends Ecran {

	private static final long serialVersionUID = 1L;
	private static EcranScores instance;
	private Button btnRetour;
	
	
	public static Ecran getInstance() {
		if(instance == null){
			instance = new EcranScores();
		}
		return instance;
	}
	
	private EcranScores() {
		this.setBackground(Color.BLACK);
		this.btnRetour=new Button(1, "RETOUR", SwingConstants.CENTER, 120, 50);
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
		
		gbc.gridy = 2;
		this.add(btnRetour, gbc);
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

	/**
	 * Classe interne Button
	 *
	 */
	private class Button extends CustomButton{

		private static final long serialVersionUID = 1L;

		/**
		 * Constructeur de la classe intern bouton
		 * @param id : correspond à l'id du boutton
		 * @param libelle :  correspond à libelle du bouton
		 * @param alignement : correspond à l'alignement du bouton
		 * @param w : correspond à la largeur du bouton
		 * @param h : correspond à la hauteur du bouton
		 */
		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			setBackground(Color.GRAY);
		}

		@Override
		/**
		 * Classe qui correspond à l'évenement : 'pression sur la souris'
		 */
		public void mousePressed(MouseEvent e) {

			Button button = (Button)e.getSource();
			if(button.getId() == 1) // Bouton retour
			{ 
				Game.goToEcran(TypeEcrans.MENU);
			}
		}
	}
}
