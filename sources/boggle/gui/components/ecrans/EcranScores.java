package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import boggle.autre.BDD;
import boggle.autre.Couleurs;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

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
		this.setBackground(Couleurs.DARK_BLUE);
		this.btnRetour = new Button(1, "RETOUR");
		System.out.println("ECRAN SCORE");
		initLayout();
	}
	
	
	@Override
	public void initLayout() {
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		this.setForeground(Couleurs.SMOKE_WHITE);
		
		JPanel panel = new JPanel();

		JLabel titre = new JLabel("<html><h1>MEILLEURS SCORES</h1></html>", SwingConstants.CENTER);
		titre.setForeground(Couleurs.SMOKE_WHITE);
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
		this.add(titre, gbc);
		
		
		List<Joueur> joueurs = null;
		try{
			BDD bdd = new BDD();
			joueurs = bdd.getListScores();	
		} catch(SQLException e){e.getMessage();}

//		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 10, 2, 10);
//		gbc.gridy = 0;		
//		gbc.gridwidth = 1; 
//		
//		gbc.weightx = 1;
//		gbc.gridx=0;
		
		int i = 1;
		for (Joueur j : joueurs) {
			
			final String str = String.format("<html><table><tr><td align=\"left\" width=\"50\"><h3>%s</h3></td><td width=\"600\"><h3>%s</h3></td><td align=\"right\" width=\"150\"><h3>%s pts.</h3></td></tr></table></html>", i, j.getNom(), j.getScore());
			
			JLabel uneLigne = new JLabel(str);
			
			uneLigne.setForeground(Couleurs.SMOKE_WHITE);
			gbc.gridy = i;
			this.add(uneLigne, gbc);
			
			i++;
		}
		
		gbc.gridwidth = 3;
		gbc.weightx = 1; gbc.gridy = i;
		gbc.gridx=0;
		this.add(btnRetour, gbc);
	}
	
	

	/**
	 * Classe interne Button
	 *
	 */
	private class Button extends CustomButton {

		private static final long serialVersionUID = 1L;


		public Button(int id, String libelle) {
			super(id, libelle, SwingConstants.CENTER, 150, 50);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setForeground(Couleurs.SMOKE_WHITE);
			this.setOpaque(false);
		}

		
		public void mousePressed(MouseEvent e) {
			Button button = (Button)e.getSource();
			if(button.getId() == 1){ 
				Game.goToEcran(TypeEcrans.MENU);
			}
		}
	}
}
