package boggle.gui.components.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.Deque;
import java.util.List;
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
	
	private JTextField champSaisie;
	private Button ajouter;
	private Button terminer;
	private Button retour;
	private GrilleLettres grille;
	
	public TextInputPanel(){
		this.grille = Game.getInstance().getModele().getGrille();
		this.grille.addObserver(this);
		//this.setPreferredSize(new Dimension(1200, 100));
		this.setBackground(Color.GRAY);
		this.champSaisie = new JTextField(10);
		this.retour = new Button(1, "Retour", SwingConstants.CENTER, 150, 40);
		this.ajouter = new Button(2, "Ajouter", SwingConstants.CENTER, 150, 30);
		this.terminer = new Button(3, "Terminer", SwingConstants.CENTER, 150, 40);
		
		JPanel panelHaut = new JPanel();
		JPanel panelBas = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		panelHaut.setLayout(new GridBagLayout());
		panelBas.setLayout(new BorderLayout());
		champSaisie.setPreferredSize(new Dimension(200, 30));
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.weightx = 0.75;	
		gbc.gridx = 0;
		panelHaut.add(champSaisie, gbc);
		gbc.weightx = 0.25;
		gbc.gridx = 1;
		panelHaut.add(ajouter,gbc);
		
		panelBas.add(retour, BorderLayout.WEST);
		panelBas.add(terminer, BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		this.add(panelHaut,  BorderLayout.NORTH);
		this.add(panelBas,  BorderLayout.SOUTH);

		panelHaut.setBackground(Color.GRAY);
		panelBas.setBackground(Color.green);
	}
	
	private class Button extends CustomButton{

		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			setBackground(Color.RED);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			Button button = (Button)e.getSource();
			if(button.getId() == 1) // Bouton retour
			{
				Game.getInstance().goToEcran(TypeEcrans.MENU);
			}
			else if(button.getId() == 2) // Bouton Ajouter
			{
				Deque<De> liste = Game.getInstance().getModele().getGrille().getListeDeSelectionnes();
				
				
				
			}
			
			else if(button.getId() == 3) // BOuton Terminer
			{
				
			}
				
			
			
			
		}
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		De de = (De) arg;
		this.champSaisie.setText(this.champSaisie.getText()+de.getChaineFaceVisible());
		
	}
}
