package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;
import boggle.jeu.JoueurFactory;
import boggle.jeu.TypeJoueur;
public class EcranSelectionJoueurs extends Ecran {

	private static final long serialVersionUID = 1L;
	private static EcranSelectionJoueurs instance;
	private Avatar[] listeAvatars = new Avatar[5];
	
	public static EcranSelectionJoueurs getInstance() {
		if(instance == null){
			instance = new EcranSelectionJoueurs();
		}
		return instance;
	}
		
	private EcranSelectionJoueurs() {
		//this.setBackground(Color.PINK);
		System.out.println("ECRAN SELECTION");
		init();
	}
	
	/**
	 * Fonction qui vérifie si la liste des avatars est vide ou non
	 * @return
	 */
	public boolean listeAvatarsVide(){
		for (Avatar av : listeAvatars) {
			if(av.isActif()){
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void init() {
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagConstraints gbc2 = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		final JPanel avatarsPanel = new JPanel();
		final JPanel btnsAvatar = new JPanel();
		final Button btnContinuer = new Button(1, "Continuer");
		final Button btnRetour = new Button(2, "Retour");
		
		
		
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1; gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;

		for(int i=0; i<5; i++){
			final Avatar current = new Avatar();
			avatarsPanel.add(current);
			this.listeAvatars[i] = current;
		}

		gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc2.insets = new Insets(0, 150, 0, 150);
		//gbc2.fill = GridBagConstraints.REMAINDER;
		gbc2.gridwidth = 1;
		gbc2.weightx = 1;
		btnsAvatar.setLayout(new GridBagLayout());
		btnsAvatar.add(btnRetour, gbc2);
		gbc2.anchor = GridBagConstraints.FIRST_LINE_END;
		btnsAvatar.add(btnContinuer, gbc2);
		this.add(avatarsPanel, gbc);
		
		gbc.gridy = 1;
		gbc.gridx = 0; 
		this.add(btnsAvatar, gbc);
		
	}
	
	private class Avatar extends JPanel implements MouseListener{
		
		private static final long serialVersionUID = 1L;
		
		private JLabel		photo;
		private JTextField	nom;
		private JSpinner	typeIA;
		private boolean		actif;
		private boolean		estHumain;
		
		public Avatar(){
			this.actif = false;
			this.estHumain = true;
			init();
			updateAvatar();
		}
		
		
		
		
		public String getNom() { return nom.getText(); }

		public TypeJoueur getTypeIA() { return (TypeJoueur) typeIA.getValue(); }

		public boolean isActif() { return actif; }

		public boolean estHumain() { return estHumain; }


		public Joueur getJoueurInstance(){
			if(actif){
				if(estHumain){
					return JoueurFactory.getInstance( TypeJoueur.HUMAIN, getNom());
				}else{					
					return JoueurFactory.getInstance( getTypeIA(), getNom());
				}
			}
			return null;
		}

		
		
		
		private void init(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
						
			photo = new JLabel("Ma Photo");
			photo.setPreferredSize(new Dimension(150, 150));
			nom = new JTextField();
			typeIA = new JSpinner(new SpinnerListModel(TypeJoueur.getListeIA()));
			((DefaultEditor) typeIA.getEditor()).getTextField().setEditable(false);
			
			gbc.gridy = 0; 
			this.add(typeIA, gbc);
			gbc.gridy = 1; 
			this.add(photo, gbc);
			gbc.gridy = 2; 
			this.add(nom, gbc);
			
			this.addMouseListener(this);
			photo.setBackground(Color.BLUE);
			photo.setOpaque(true);
			
		}
		
		
		
		/** Permet de mettre a jour l'affichage de l'avatar selon ses attributs */
		private void updateAvatar(){
			if(actif){
				this.nom.setEnabled(true);
				if(estHumain){
					typeIA.setEnabled(false);
					photo.setBackground(Color.ORANGE);
					
				} else {
					typeIA.setEnabled(true);
					photo.setBackground(Color.CYAN);
				}
			} else {
				photo.setBackground(Color.GRAY);
				nom.setEnabled(false);
				typeIA.setEnabled(false);
			}
			photo.setText("Actif : " + actif + (estHumain ? " Humain" : " Robot"));
			//System.out.println(JoueurFactory.getInstance( (TypeJoueur)typeIA.getValue(), nom.getText()));
		}

		public void mouseClicked(MouseEvent e){}

		public void mouseEntered(MouseEvent e){}

		public void mouseExited(MouseEvent e){}

		public void mousePressed(MouseEvent e){
			if(SwingUtilities.isLeftMouseButton(e)){
				if(actif){
					estHumain=!estHumain;
				}
			}else if(SwingUtilities.isRightMouseButton(e)){
				actif=!actif;
			}
			System.out.println(estHumain + " " + actif);
			updateAvatar();
		}

		public void mouseReleased(MouseEvent e) {}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	private class Button extends CustomButton {
		
		private static final long serialVersionUID = 1L;

		public Button(int id, String libelle) {
			super(id, libelle, SwingConstants.CENTER, 150, 40);
			this.setBackground(Color.red);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			Button button = (Button) e.getSource();
			if(button.getId() == 1){
				// Click sur continuer
				//Game.getInstance().goToEcran(TypeEcrans.JEU);
				verifierListeJoueurs();
				
			}else if(button.getId() == 2){
				// Click sur retour
				Game.goToEcran(TypeEcrans.MENU);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
		}




	}
	
	
	
	
	
	
	
	
	private void verifierListeJoueurs(){
		
		for(Avatar avatar : listeAvatars){
			if(avatar.isActif()){
				if(avatar.getNom().isEmpty()){
					avatar.nom.requestFocus();		
					
				}else{
					Joueur joueur = avatar.getJoueurInstance();
					System.out.println(joueur);
					Game.modele.ajouterJoueur(joueur);
					Game.goToEcran(TypeEcrans.JEU);
				}				
			}
		}
		
		
	}
	
	
	
	
	
	
	

}
