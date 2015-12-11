package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
import boggle.mots.ArbreLexical;
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
	private ArbreLexical arbreDebutsMots;
	
	public TextInputPanel(){
		this.grille = Game.modele.getGrille();
		//this.arbreDebutsMots = ArbreLexical.creerArbreDepuisUneListe(this.grille.get);
		this.grille.addObserver(this);
		//this.setPreferredSize(new Dimension(1200, 100));
		//this.setBackground(Color.GRAY);
		this.champSaisie = new JTextField();
		this.retour = new Button(1, "Retour", SwingConstants.CENTER, 150, 40);
		this.ajouter = new Button(2, "Ajouter", SwingConstants.CENTER, 150, 30);
		this.terminer = new Button(3, "Terminer", SwingConstants.CENTER, 150, 40);
		init();		
		this.champSaisie.setText("");
		
		
		
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
			else if(button.getId() == 2) // Bouton Ajouter
			{
				// On récupère le contenu du champ saisie
				motSaisie = champSaisie.getText();
				
				// On récupère le joueur en cours
				Joueur joueurEnCours = Game.modele.getJoueurEnCours();
				
				// On ajoute au joueur en cours, le mot saisie
				joueurEnCours.ajouterUnMot(motSaisie);
				
				// On remet les cases en gris
				grille.resetDejaVisite();
				grille.updateListeDesSelectionnes(null);
				grille.getListeDeSelectionnes().clear();
				champSaisie.setText("");
			}else if(button.getId() == 3){ 
				// BOuton Terminer
				Game.modele.getJoueurEnCours().setEntrainDeJouer(false);
			}
				
			
			
			
		}
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {

		//De de = (De) arg;
		//this.champSaisie.setText(this.champSaisie.getText()+de.getChaineFaceVisible());
		GrilleLettres g = (GrilleLettres) o;
		//System.out.println("GRILLE : " + g);
		StringBuilder unMot = new StringBuilder();
		
		for(De s : g.getListeDeSelectionnes()){
			unMot.append(s.getChaineFaceVisible());
		}
		this.champSaisie.setText(unMot.toString());
		
	}
	
	private class JTextFiledFormat extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
		public JTextFiledFormat(int limit){
			this.limit = limit;
			
		}


		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			if(str == null) return;
			System.out.println("|" +str + "|");
			str = str.toUpperCase();
			
			if ((getLength() + str.length()) <= limit){ 
					if(str.length() == 1){
						if(grille.estUneLettreValide(str)) {
							super.insertString(offs, str, a);
							List<De> lsDe = Game.modele.getGrille().getListeDesFromLettre(str); 
							for(De d : lsDe){
								Game.modele.getGrille().updateListeDesSelectionnes(d);
							}
							//ArbreLexical t = arbreDebutsMots.getArbreFromString(champSaisie.getText());
							//t.afficherArbre(0);
							
						}
						
					}else{
						super.insertString(offs, str, a);
					}
			}
		}
	}
	
	
}
