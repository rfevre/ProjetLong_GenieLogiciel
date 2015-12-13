package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import boggle.gui.components.elements.CustomButton;

public class EcranOptions extends Ecran {

	
	private static final long serialVersionUID = 1L;
	private static EcranOptions instance;
	
	private JTextField dictionnaire, score, nbTours, tempsJeu;
	
	private String[] listeTailleGrille;
	private JComboBox tailleGrille;
	
	private JLabel dicoLabel, scoreLabel, nbToursLabel, tempsJeuLabel, tailleGrilleLabel;
	private Button browse;
	
	
	
	/**
	 * Retourne l'attribut instance de la classe, qui correspond à l'instance courante
	 * @return EcranOptions
	 */
	public static Ecran getInstance() {
		if(instance == null){
			instance = new EcranOptions();
		}
		return instance;
	}
	
	/**
	 * Constructeur de la classe
	 */
	private EcranOptions() {
		this.dictionnaire = new JTextField();
		this.score = new JTextField();
		this.nbTours = new JTextField();
		this.tempsJeu = new JTextField();
		
		this.listeTailleGrille = new String[]{"4x4","5x5"};
		this.tailleGrille = new JComboBox(listeTailleGrille);
		
		this.dicoLabel = new JLabel("<html><h2> Choix du dictionnaire </html></h2>");
		this.scoreLabel = new JLabel("<html><h2> Score à atteindre </html></h2>");
		this.nbToursLabel = new JLabel("<html><h2> Nombre de tours maximum </html></h2>");
		this.tempsJeuLabel = new JLabel("<html><h2> Temps de jeu par joueur </html></h2>");
		this.tailleGrilleLabel = new JLabel("<html><h2> Taille de la grille </html></h2>");
		
		this.browse = new Button(1, "BROWSE", SwingConstants.CENTER, 100,30);
		
		init();
	}
	
	/**
	 * Méthode init() qui initialise l'EcranOptions en plaçant  les éléments
	 */
	@Override
	public void init() {
		
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		// Marge interne
		gbc.insets = new Insets(10,30,10,0);
		gbc.anchor = GridBagConstraints.EAST;
		
		// Première ligne
		gbc.gridx=0; gbc.gridy = 0; // gridx et gridy sert à la position
		this.add(dicoLabel, gbc);
		
		gbc.gridx=1; //gbc.gridy = 0;
		dictionnaire.setPreferredSize(new Dimension(150, 30));
		this.add(dictionnaire, gbc);
		
		gbc.gridx=2; //gbc.gridy=0;
		this.add(browse, gbc);
		
		// Deuxième ligne
		gbc.gridx=0; gbc.gridy = 1;
		this.add(scoreLabel, gbc);
		
		gbc.gridx=1; //gbc.gridy = 1;
		score.setPreferredSize(new Dimension(150, 30));
		this.add(score, gbc);
		
		
		// Troisième ligne
		gbc.gridx=0; gbc.gridy = 2;
		this.add(nbToursLabel, gbc);
		
		gbc.gridx=1; //gbc.gridy = 2;
		nbTours.setPreferredSize(new Dimension(150, 30));
		this.add(nbTours, gbc);
				
		// Troisième ligne
		gbc.gridx=0; gbc.gridy = 3;
		this.add(tempsJeuLabel, gbc);
		
		gbc.gridx=1; //gbc.gridy = 3;
		tempsJeu.setPreferredSize(new Dimension(150, 30));
		this.add(tempsJeu, gbc);
		
		// Quatrième ligne
		gbc.gridx=0; gbc.gridy = 4;
		this.add(tailleGrilleLabel, gbc);
		
		gbc.gridx=1; //gbc.gridy = 4;
		tailleGrille.setPreferredSize(new Dimension(150, 30));
		this.add(tailleGrille, gbc);
		
	}
	
	/**
	 * Classe interne Button
	 * @author Mustapha
	 *
	 */
	private class Button extends CustomButton{

		private static final long serialVersionUID = 1L;

		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			setBackground(Color.GRAY);
		}
	}
	

}
