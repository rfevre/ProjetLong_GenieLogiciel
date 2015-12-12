package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import boggle.gui.components.ecrans.TypeEcrans;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

public class TextInputPanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String sourceMessage = "clavier";

	private JTextField champSaisie;
	private Button ajouter;
	private Button terminer;
	private Button retour;
	private GrilleLettres grille;
	private Stack<De> temp = new Stack<>();
	public TextInputPanel(){
		this.champSaisie = new JTextField();
		this.retour = new Button(1, "Retour", SwingConstants.CENTER, 150, 40);
		this.ajouter = new Button(2, "Ajouter", SwingConstants.CENTER, 150, 30);
		this.terminer = new Button(3, "Terminer", SwingConstants.CENTER, 150, 40);
		init();		
		this.champSaisie.setText("");
		
		this.grille = Game.modele.getGrille();
		this.grille.addObserver(this);
		
		
	}
	
	
	private void init(){
		GridBagConstraints gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.ipadx = 0;
		champSaisie.setPreferredSize(new Dimension(350, 31));
		champSaisie.setDocument(new JTextFiledFormat(16));
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

		private static final long serialVersionUID = 1L;
		private String motSaisie;

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
			else if(button.getId() == 2){
				executerAjouter(motSaisie);
			}else if(button.getId() == 3){ 
				// BOuton Terminer
				Game.modele.getJoueurEnCours().setEntrainDeJouer(false);
			}
				
			
			
			
		}
		
		
		
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

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> MESSAGE " + sourceMessage + " >>>>> TEXT INPUT" );
		
		if("click".equals(sourceMessage)){
			GrilleLettres g = (GrilleLettres) o;
			StringBuilder unMot = new StringBuilder();
			
			for(De s : g.getListeDeSelectionnes()){
				unMot.append(s.getChaineFaceVisible());
			}
			System.out.println("ajout de |" +unMot+ "| dans l'input.");
			this.champSaisie.setText(unMot.toString());
			
		}else{
			System.out.println("Rien a faire");
		}
		System.out.println("FIN TEXT INPUT UPDATE <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		
		
		
	}
	
	private class JTextFiledFormat extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
		public JTextFiledFormat(int limit){
			this.limit = limit;
			
		}

		
		

		@Override
		protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
			super.insertUpdate(chng, attr);
			System.out.println("++++ UN INSERT UPDATE ++++ SOURCE EN COURS : " + sourceMessage);
			if("clavier".equals(sourceMessage)){
				sourceMessage = "clavier";
				Game.modele.getGrille().resetDejaVisite();
				temp = new Stack<>();
				System.out.println(champSaisie.getText() + "-------> " +  grille.estUnMotValideBis(champSaisie.getText(), temp) + " ::: " + temp );
				Deque<De> ls = new LinkedList<>(temp);
				for(De de : ls){
					de.setDejaVisite(true);
				}
				grille.setListeDeSelectionnes(ls);
											
			}else{
				sourceMessage = "clavier";
			}
		}




		@Override
		protected void removeUpdate(DefaultDocumentEvent chng) {
			System.out.println("suppresseion : " + chng.getLength());
			super.removeUpdate(chng);
			grille.removeLastDesSelectionnes();
		}




		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if(str == null) return;
			str = str.toUpperCase();
			System.out.println("\tInsertion de |"+str+"|");
			if ((getLength() + str.length()) <= limit){ 
					if(str.length() == 1){
						System.out.println("******************************************************************");
						if(grille.estUneLettreValide(str)) {
							super.insertString(offs, str, a);
							
							sourceMessage = "clavier";
							System.out.println("CECI EST UN CLAVIER");

							
						}
						
					}else{
						// Insertion avec la souris
						super.insertString(offs, str, a);
					}
			}
		}
	}
	
	
}
