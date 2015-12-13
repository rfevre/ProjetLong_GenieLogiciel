package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;

public class EcranOptions extends Ecran {

	
	private static final long serialVersionUID = 1L;
	private static EcranOptions instance;
	
	private JTextField dictionnaire, score, nbTours, tempsJeu;
	
	private String[] listeTailleGrille;
	private JComboBox tailleGrille;
	
	private JLabel dicoLabel, scoreLabel, nbToursLabel, tempsJeuLabel, tailleGrilleLabel;
	private Button btnBrowse, btnRetour, btnValider;
	
	
	
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
		
		this.btnBrowse = new Button(1, "BROWSE", SwingConstants.CENTER, 100,30);
		this.btnRetour = new Button(2, "RETOUR", SwingConstants.CENTER, 120, 50);
		this.btnValider = new Button(3, "VALIDER", SwingConstants.CENTER, 120, 50);
		
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
		this.add(btnBrowse, gbc);
		
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
		
		// Cinquième ligne
		gbc.gridx =0; gbc.gridy=5;
		gbc.insets = new Insets(100,0,10,100);
		this.btnRetour.setBackground(Color.RED);
		this.add(btnRetour, gbc);
		
		gbc.gridx = 5²; //gbc.gridy=5;
		gbc.insets = new Insets(100,100,10,100);
		this.btnValider.setBackground(Color.RED);
		this.add(btnValider, gbc);
		
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
			if(button.getId() == 1) // Bouton browse
			{
				
			}
			else if(button.getId() == 2) // Bouton retour
			{ 
				Game.goToEcran(TypeEcrans.MENU);
			}
			else if(button.getId() == 3) // Bouton valider
			{  
				
				// Modif à faire en fonction des choix de l'utilisateur
			}
				
			
		}
		
		
	}
	

}
