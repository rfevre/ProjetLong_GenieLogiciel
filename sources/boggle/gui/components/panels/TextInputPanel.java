package boggle.gui.components.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.Deque;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import boggle.gui.components.ecrans.TypeEcrans;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

public class TextInputPanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField champSaisie;
	private Button ajouter;
	private Button terminer;
	private Button retour;
	private GrilleLettres grille;
	
	public TextInputPanel(){
		this.grille = Game.modele.getGrille();
		this.grille.addObserver(this);
		//this.setPreferredSize(new Dimension(1200, 100));
		//this.setBackground(Color.GRAY);
		this.champSaisie = new JTextField();
		this.retour = new Button(1, "Retour", SwingConstants.CENTER, 150, 40);
		this.ajouter = new Button(2, "Ajouter", SwingConstants.CENTER, 150, 30);
		this.terminer = new Button(3, "Terminer", SwingConstants.CENTER, 150, 40);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.ipadx = 0;
		champSaisie.setPreferredSize(new Dimension(350, 31));
		
		gbc.gridx = 1; gbc.gridy = 0;
		gbc.gridwidth = 5;
		this.add(champSaisie, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 7; 
		this.add(ajouter, gbc);
		gbc.insets = new Insets(0, -50, 20, 50);
		gbc.gridx = 0; gbc.gridy = 1;
		this.add(retour, gbc);
		gbc.gridx = 8; gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(0, 100, 0, 0);
		this.add(terminer, gbc);
		

	}
	
	private class Button extends CustomButton{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			setBackground(Color.RED);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			Button button = (Button)e.getSource();
			if(button.getId() == 1) // Bouton retour
			{
				Game.goToEcran(TypeEcrans.MENU);
			}
			else if(button.getId() == 2) // Bouton Ajouter
			{
				Deque<De> liste = Game.modele.getGrille().getListeDeSelectionnes();
				
				
				
			}
			
			else if(button.getId() == 3) // BOuton Terminer
			{
				
			}
				
			
			
			
		}
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		GrilleLettres g = (GrilleLettres) o;
		StringBuilder unMot = new StringBuilder();
		for(De s : g.getListeDeSelectionnes()){
			unMot.append(s.getChaineFaceVisible());
		}
		this.champSaisie.setText(unMot.toString());
		
	}
}
