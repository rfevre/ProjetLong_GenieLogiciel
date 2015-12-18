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
import boggle.autre.Utils;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;

/**
 * Classe EcranOptions qui correspond à l'écran d'options affiché à l'utilisateur
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class EcranOptions extends Ecran {

	private static EcranOptions instance;
	
	private JTextField dictionnaire, score, nbTours, tempsJeu;
	
	private String[] listeTailleGrille;
	private JComboBox<String> tailleGrille;
	
	private JLabel dicoLabel, scoreLabel, nbToursLabel, tempsJeuLabel, tailleGrilleLabel;
	private Button btnBrowse, btnRetour, btnValider;
	
	
	/**
	 * Retourne l'attribut instance de la classe, qui correspond Ã  l'instance courante
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
		
		this.dictionnaire.setText(Utils.getConfigProperty("des"));
		initLayout();
	}
	
	
	/* (non-Javadoc)
	 * @see boggle.gui.components.ecrans.Ecran#initLayout()
	 */
	public void initLayout() {
		
		dicoLabel.setForeground(Couleurs.SMOKE_WHITE);
		scoreLabel.setForeground(Couleurs.SMOKE_WHITE);
		nbToursLabel.setForeground(Couleurs.SMOKE_WHITE);
		tempsJeuLabel.setForeground(Couleurs.SMOKE_WHITE);
		tailleGrilleLabel.setForeground(Couleurs.SMOKE_WHITE);
		
		this.setBackground(Couleurs.DARK_BLUE);
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.insets = new Insets(10,30,10,0);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.gridx=1; gbc.gridy = 0; 
		this.add(dicoLabel, gbc);
		
		gbc.gridx=2; 
		dictionnaire.setPreferredSize(new Dimension(150, 30));
		this.add(dictionnaire, gbc);
		
		gbc.gridx=1; gbc.gridy = 1;
		this.add(scoreLabel, gbc);
		
		gbc.gridx=2;
		score.setPreferredSize(new Dimension(150, 30));
		this.add(score, gbc);
		
		gbc.gridx=1; gbc.gridy = 2;
		this.add(nbToursLabel, gbc);
		
		gbc.gridx=2;
		nbTours.setPreferredSize(new Dimension(150, 30));
		this.add(nbTours, gbc);
				
		gbc.gridx=1; gbc.gridy = 3;
		this.add(tempsJeuLabel, gbc);
		
		gbc.gridx=2; 
		tempsJeu.setPreferredSize(new Dimension(150, 30));
		this.add(tempsJeu, gbc);
		
		gbc.gridx=1; gbc.gridy = 4;
		this.add(tailleGrilleLabel, gbc);
		
		gbc.gridx=2;
		tailleGrille.setPreferredSize(new Dimension(150, 30));
		this.add(tailleGrille, gbc);
		
		gbc.gridx =0; gbc.gridy=5;
		gbc.insets = new Insets(100,100,0,0);
		this.btnRetour.setBackground(Color.RED);
		this.add(btnRetour, gbc);
		
		gbc.gridx = 5;
		gbc.insets = new Insets(100,0,0,100);
		this.btnValider.setBackground(Color.RED);
		this.add(btnValider, gbc);
		
	}
	
	/**
	 * Classe interne privée Button
	 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
	 * @version 1.0
	 *
	 */
	private class Button extends CustomButton{
		
		/**
		 * Constructeur de la classe intern bouton
		 * @param id : 			correspond à  l'id du boutton
		 * @param libelle :  	correspond à  libelle du bouton
		 * @param alignement : 	correspond à  l'alignement du bouton
		 * @param w : 			correspond à  la largeur du bouton
		 * @param h : 			correspond à  la hauteur du bouton
		 */
		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setForeground(Couleurs.SMOKE_WHITE);
			this.setOpaque(false);
		}
		
		/**
		 * Classe qui correspond à l'événement : 'pression sur la souris'
		 */
		public void mousePressed(MouseEvent e) {
			
			Button button = (Button)e.getSource();
			if(button.getId() == 1)
			{
				
			}
			else if(button.getId() == 2)
			{ 
				Game.goToEcran(TypeEcrans.MENU);
			}
			else if(button.getId() == 3)
			{  
				verifOptions();
			}
				
			
		}
		
		
	}
	
	/**
	 * Méthode qui vérifie les options inscrites par l'utilisateur
	 */
	private void verifOptions(){
		boolean changement = false;
		
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
		
		if (Pattern.matches("[0-9]*", score.getText())){ // true)
			if (Integer.parseInt(score.getText())>0 && Integer.parseInt(score.getText())<1000){
				Game.modele.setScoreMax(Integer.parseInt(score.getText()));
				changement = true;
			}
			else{
				changement = false;
			}
		}
		else{
			changement = false;
		}
		if (changement){
			JOptionPane.showMessageDialog(null, "Changements pris en compte." );
			Game.goToEcran(TypeEcrans.MENU);
		}
		else {
			JOptionPane.showMessageDialog(null, "Changements non pris en compte." );
		}
		
	}
	

}
