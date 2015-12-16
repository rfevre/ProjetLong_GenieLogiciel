package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import boggle.autre.Couleurs;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;


/**
*  Classe qui correspond à l'EcranOptions
* @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
* @version 1.0
*
*/
public class EcranOptions extends Ecran {

	
	private static final long serialVersionUID = 1L;
	private static EcranOptions instance;
	
	private JTextField dictionnaire, score, nbTours, tempsJeu;
	
	private String[] listeTailleGrille;
	private JComboBox<String> tailleGrille;
	
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
		this.score = new JTextField("20");
		this.nbTours = new JTextField("3");
		this.tempsJeu = new JTextField("200");
		
		this.listeTailleGrille = new String[]{"4x4","5x5"};
		this.tailleGrille = new JComboBox<>(listeTailleGrille);
		
		this.dicoLabel = new JLabel(" Choix du dictionnaire ");
		this.scoreLabel = new JLabel(" Score à  atteindre ");
		this.nbToursLabel = new JLabel(" Nombre de tours maximum ");
		this.tempsJeuLabel = new JLabel(" Durée d'une manche (en sec)");
		this.tailleGrilleLabel = new JLabel(" Taille de la grille ");
		
		this.btnBrowse = new Button(1, "PARCOURIR", SwingConstants.CENTER, 150,50);
		this.btnRetour = new Button(2, "RETOUR", SwingConstants.CENTER, 150, 50);
		this.btnValider = new Button(3, "VALIDER", SwingConstants.CENTER, 150, 50);
		
		initLayout();
	}
	
	/**
	 * Méthode init() qui initialise l'EcranOptions en plaçant  les élé©ments
	 */
	@Override
	public void initLayout() {
		
		dicoLabel.setForeground(Couleurs.SMOKE_WHITE);
		scoreLabel.setForeground(Couleurs.SMOKE_WHITE);
		nbToursLabel.setForeground(Couleurs.SMOKE_WHITE);
		tempsJeuLabel.setForeground(Couleurs.SMOKE_WHITE);
		tailleGrilleLabel.setForeground(Couleurs.SMOKE_WHITE);
		
		this.setBackground(Couleurs.DARK_BLUE);
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		// Marge interne
		gbc.insets = new Insets(10,30,10,0);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		// PremiÃ¨re ligne
		gbc.gridx=1; gbc.gridy = 0; // gridx et gridy sert Ã  la position
		this.add(dicoLabel, gbc);
		
		gbc.gridx=2; //gbc.gridy = 0;
		dictionnaire.setPreferredSize(new Dimension(150, 30));
		this.add(dictionnaire, gbc);
		
		gbc.gridx=3; //gbc.gridy=0;
		this.add(btnBrowse, gbc);
		
		// DeuxiÃ¨me ligne
		gbc.gridx=1; gbc.gridy = 1;
		this.add(scoreLabel, gbc);
		
		gbc.gridx=2; //gbc.gridy = 1;
		score.setPreferredSize(new Dimension(150, 30));
		this.add(score, gbc);
		
		
		// TroisiÃ¨me ligne
		gbc.gridx=1; gbc.gridy = 2;
		this.add(nbToursLabel, gbc);
		
		gbc.gridx=2; //gbc.gridy = 2;
		nbTours.setPreferredSize(new Dimension(150, 30));
		this.add(nbTours, gbc);
				
		// TroisiÃ¨me ligne
		gbc.gridx=1; gbc.gridy = 3;
		this.add(tempsJeuLabel, gbc);
		
		gbc.gridx=2; //gbc.gridy = 3;
		tempsJeu.setPreferredSize(new Dimension(150, 30));
		this.add(tempsJeu, gbc);
		
		// QuatriÃ¨me ligne
		gbc.gridx=1; gbc.gridy = 4;
		this.add(tailleGrilleLabel, gbc);
		
		gbc.gridx=2; //gbc.gridy = 4;
		tailleGrille.setPreferredSize(new Dimension(150, 30));
		this.add(tailleGrille, gbc);
		
		
		// CinquiÃ¨me ligne
		gbc.gridx =0; gbc.gridy=5;
		gbc.insets = new Insets(100,100,0,0);
		this.btnRetour.setBackground(Color.RED);
		this.add(btnRetour, gbc);
		
		gbc.gridx = 5; //gbc.gridy=5;
		gbc.insets = new Insets(100,0,0,100);
		this.btnValider.setBackground(Color.RED);
		this.add(btnValider, gbc);
		
	}
	
	/**
	 * Classe interne Button
	 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
	 * @version 1.0
	 *
	 */
	private class Button extends CustomButton{

		private static final long serialVersionUID = 1L;
		
		/**
		 * Constructeur de la classe intern bouton
		 * @param id : correspond Ã  l'id du boutton
		 * @param libelle :  correspond Ã  libelle du bouton
		 * @param alignement : correspond Ã  l'alignement du bouton
		 * @param w : correspond Ã  la largeur du bouton
		 * @param h : correspond Ã  la hauteur du bouton
		 */
		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setForeground(Couleurs.SMOKE_WHITE);
			this.setOpaque(false);
		}
		
		@Override
		/**
		 * Classe qui correspond à  l'évenement : 'pression sur la souris'
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
				verifOptions();
				// Modif Ã  faire en fonction des choix de l'utilisateur
			}
				
			
		}
		
		
	}
	
	/**
	*
	* Méthode qui vérifie si les options ont été changées
	*
	*/
	private void verifOptions(){
		boolean changement = false;
		
		//Vérification du champ nbTours
		if (Pattern.matches("[0-9]*",nbTours.getText())){
			if (Integer.parseInt(nbTours.getText())>0 && Integer.parseInt(nbTours.getText())<20){
				Game.modele.setNbTours(Integer.parseInt(nbTours.getText()));
				changement = true;
			}
			else{
				changement = false;
			}
		}
		else{
			changement = false;
		}
		
		//Vérification du champ tempsJeu
		if (Pattern.matches("[0-9]*",tempsJeu.getText())){
			if (Integer.parseInt(tempsJeu.getText())>0 && Integer.parseInt(tempsJeu.getText())<600){
				Game.modele.setDurreeManche(Integer.parseInt(tempsJeu.getText()));
				changement = true;
			}
			else{
				changement = false;
			}
		}
		else{
			changement = false;
		}
		
		//Vérification du champ score 
		//TODO : faire un setScore
		if (Pattern.matches("[0-9]*",score.getText())){ // true)
			if (Integer.parseInt(score.getText())>0 && Integer.parseInt(score.getText())<1000){
				//Game.modele.setScoreMax(Integer.parseInt(score.getText()));
				changement = true;
			}
			else{
				changement = false;
			}
		}
		else{
			changement = false;
		}
		
		//On vérifie si tout à bien étais pris en compte
		if (changement){
			JOptionPane.showMessageDialog(null, "Changements pris en compte." );
			Game.goToEcran(TypeEcrans.MENU);
		}
		else {
			JOptionPane.showMessageDialog(null, "Changements non pris en compte." );
		}
		
	}
	

}
