package boggle.gui.components.panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import boggle.autre.Couleurs;
import boggle.gui.components.ecrans.TypeEcrans;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

/**
 * Cette classe pemet de controler et d'afficher la zone de saisie des mots
 * entres par le joueur.
 * @author elmassam
 *
 */
public class TextInputPanel extends JPanel implements Observer, KeyListener {
	
	public static String sourceMessage = "clavier";

	private JTextField champSaisie;
	private Button ajouter;
	private Button terminer;
	private Button retour;
	private GrilleLettres grille;
	private Stack<De> temp = new Stack<>();
	
	
	private final ImageIcon BTN_AJOUTER_0 = new ImageIcon(getClass().getResource("/img/btnajouter0.png"));
	private final ImageIcon BTN_AJOUTER_1 = new ImageIcon(getClass().getResource("/img/btnajouter1.png"));
	private final ImageIcon BTN_AJOUTER_2 = new ImageIcon(getClass().getResource("/img/btnajouter2.png"));
	
	
	public GrilleLettres getGrille() {
		return grille;
	}


	public void setGrille(GrilleLettres grille) {
		this.grille = grille;
		this.grille.addObserver(this);
	}


	public TextInputPanel(){
		
		this.champSaisie = new JTextField();
		this.retour = new Button(1, "RETOUR", SwingConstants.CENTER, 150, 40);
		this.ajouter = new Button(2, "AJOUTER", SwingConstants.CENTER, 150, 30);
		this.terminer = new Button(3, "TERMINER", SwingConstants.CENTER, 150, 40);
		initLayout();		
		this.champSaisie.setText("");
		this.champSaisie.addKeyListener(this);
		this.grille = Game.modele.getGrille();
	
		this.grille.addObserver(this);
		
		
	}
	
	
	public JTextField getChampSaisie() {
		return champSaisie;
	}


	/**
	 * Permet d'initialiser le layout du panel 
	 */
	private void initLayout(){
		champSaisie.setBorder(BorderFactory.createLineBorder(Couleurs.CONCRETE));
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.ipadx = 0;
		champSaisie.setPreferredSize(new Dimension(350, 30));
		champSaisie.setDocument(new JTextFiledFormat(16));
		gbc.gridx = 1; gbc.gridy = 0;
		gbc.gridwidth = 5;
		this.add(champSaisie, gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 7; 
		this.add(ajouter, gbc);
		gbc.insets = new Insets(0, 0, 20, 50);
		gbc.gridx = 0; gbc.gridy = 1;
		this.add(retour, gbc);
		gbc.gridx = 8; gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(0, 100, 20, 0);
		this.add(terminer, gbc);
		this.setBackground(Couleurs.SMOKE_WHITE);
	}
	
	
	
	
	
	/**
	 * Permet de gener des button custom.
	 * @author elmassam
	 *
	 */
	private class Button extends CustomButton{

		private static final long serialVersionUID = 1L;
		private String motSaisie;

		public Button(int id, String libelle, int alignement, int w, int h) {
			super(id, libelle, alignement, w, h);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			this.setFont(new Font("default", Font.BOLD, 12));
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setForeground(Couleurs.SMOKE_WHITE);
			this.setOpaque(false);
			if(id==2){
				this.setIcon(BTN_AJOUTER_0);
				
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			super.mousePressed(e);
			Button button = (Button)e.getSource();
			
			if(button.getId() == 1){
				// Bouton retour
				Game.goToEcran(TypeEcrans.MENU);
			}else if(button.getId() == 2){
				// Bouton ajouter
				executerAjouter(motSaisie);
				this.setIcon(BTN_AJOUTER_2);
			}else if(button.getId() == 3){ 
				// BOuton Terminer
				executerTerminer();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			Button button = (Button)e.getSource();
			if(button.getId() == 2){		// Bouton ajouter
				this.setIcon(BTN_AJOUTER_1);
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			Button button = (Button)e.getSource();
			if(button.getId() == 2){		// Bouton ajouter
				this.setIcon(BTN_AJOUTER_0);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			Button button = (Button)e.getSource();
			if(button.getId() == 2){		// Bouton ajouter
				this.setIcon(BTN_AJOUTER_0);
			}
		}
		
		
		
		
	}
	
	/** * Methode a executer apres appuie  sur le bouton terminer */
	private void executerTerminer(){
		System.out.println("BTN TERMINER");
		Game.modele.getJoueurEnCours().setEntrainDeJouer(false);
		Game.modele.setGrille(new GrilleLettres());
		this.grille = Game.modele.getGrille();
		this.grille.setListeDeSelectionnes(new LinkedList<De>());
		this.grille.addObserver(this);
	}
	
	
	/** Instructions a executer apres appuie sur btn ajouter */
	private void executerAjouter(String motSaisie){
		motSaisie = champSaisie.getText();
		
		// On récupère le joueur en cours
		Joueur joueurEnCours = Game.modele.getJoueurEnCours();
		
		// On ajoute au joueur en cours, le mot saisie
		joueurEnCours.ajouterUnMot(motSaisie);
		
		// On remet les cases en gris
		grille.resetDejaVisite();
		grille.setListeDeSelectionnes(new LinkedList<De>());
		//grille.getListeDeSelectionnes().clear();
		champSaisie.setText("");
		
	}
	
	
	

	@Override
	public void update(Observable o, Object arg) {
		if("click".equals(sourceMessage)){
			GrilleLettres g = (GrilleLettres) o;
			StringBuilder unMot = new StringBuilder();
			for(De s : g.getListeDeSelectionnes()){
				unMot.append(s.getChaineFaceVisible());
			}
			this.champSaisie.setText(unMot.toString());
			
		}
	}
	
	/**
	 * Permet de controler le contenu d'un JTextfield
	 * @author elmassam
	 *
	 */
	private class JTextFiledFormat extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
		public JTextFiledFormat(int limit){
			this.limit = limit;
			
		}

		@Override
		protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
			super.insertUpdate(chng, attr);
			if("clavier".equals(sourceMessage)){
				sourceMessage = "clavier";
				Game.modele.getGrille().resetDejaVisite();
				temp = new Stack<>();
				grille.estUnMotValideBis(champSaisie.getText(), temp);
				Deque<De> ls = new LinkedList<>(temp);
				for(De de : ls){
					de.setDejaVisite(true);
				}
				grille.setListeDeSelectionnes(ls);
				Deque<De> lis = grille.getListeDeSelectionnes();
				grille.setListeDeSelectionnes(new LinkedList<De>());
				grille.setListeDeSelectionnes(lis);
				System.out.println(">>>>>>>>>>> " + ls);
											
			}
		}




		@Override
		protected void removeUpdate(DefaultDocumentEvent chng) {
			super.removeUpdate(chng);
			grille.removeLastDesSelectionnes();
		}




		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if(str == null) return;
			str = str.toUpperCase();
			if ((getLength() + str.length()) <= limit){ 
					if(str.length() == 1){
						if(grille.estUneLettreValide(str)) {
							super.insertString(offs, str, a);
						}
						
					}else{
						// Insertion avec la souris
						super.insertString(offs, str, a);
					}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		sourceMessage = "clavier";
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			final String mot = champSaisie.getText();
			if(!mot.isEmpty()){
				executerAjouter(mot);
			}
		}
	}


	public void keyReleased(KeyEvent e) {}

	public void keyTyped(KeyEvent e) {}
	
	
}
