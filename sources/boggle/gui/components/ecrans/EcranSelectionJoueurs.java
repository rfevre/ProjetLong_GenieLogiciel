package boggle.gui.components.ecrans;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import boggle.autre.Couleurs;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;
import boggle.jeu.JoueurFactory;
import boggle.jeu.TypeJoueur;

/**
 * Classe EcranSelectionJoueurs qui correspond à l'écran de selection des joueurs qui est affiché à l'utilisateur
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class EcranSelectionJoueurs extends Ecran {

	private static EcranSelectionJoueurs instance;
	private Avatar[] listeAvatars = new Avatar[5];
	
	private final ImageIcon USROFF = new ImageIcon(getClass().getResource("/img/usroff.png"));
	private final ImageIcon USRH = new ImageIcon(getClass().getResource("/img/usrh.png"));
	private final ImageIcon USRIA = new ImageIcon(getClass().getResource("/img/usria.png"));
	
	
	/**
	 * Retourne l'attribut instance de la classe, qui correspond à l'instance courante
	 * @return EcranSelectionJoueurs
	 */
	public static EcranSelectionJoueurs getInstance() {
		if(instance == null){
			instance = new EcranSelectionJoueurs();
		}
		return new EcranSelectionJoueurs();
	}
	
	/**
	 * Constructeur de la classe
	 */
	private EcranSelectionJoueurs() {
		System.out.println("ECRAN SELECTION");
		initLayout();
	}
	
	/**
	 * Fonction qui vérifie si la liste des avatars est vide ou non
	 * @return boolean
	 */
	public boolean listeAvatarsVide(){
		for (Avatar av : listeAvatars) {
			if(av.isActif()){
				return true;
			}
		}
		return false;
	}
	
	
	/* (non-Javadoc)
	 * @see boggle.gui.components.ecrans.Ecran#initLayout()
	 */
	public void initLayout() {
		final JPanel avatarsPanel = new JPanel();
		final JPanel btnsPanel = new JPanel();
		final JPanel helpPanel = new JPanel();
		
		final Button btnContinuer = new Button(1, "CONTINUER");
		final Button btnRetour = new Button(2, "RETOUR");
		final JLabel aide1 = new JLabel("<html><small>CLIC <strong>DROIT</strong> POUR ACTIVER / DESACTIVER UN JOUEUR.</small></html>", SwingConstants.RIGHT);
		final JLabel aide2 = new JLabel("<html><small>CLIC <strong>GAUCHE</strong> POUR CHANGER LE TYPE DE DU JOUEUR HUMAIN / IA</small></html>", SwingConstants.RIGHT);
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Couleurs.DARK_BLUE);
		helpPanel.setBackground(Couleurs.DARK_BLUE);
		
		helpPanel.setLayout(new GridLayout(2, 1,20,5));
		helpPanel.add(aide1);
		helpPanel.add(aide2);
		
		aide1.setForeground(Couleurs.SILVER);
		aide2.setForeground(Couleurs.SILVER);
		avatarsPanel.setBackground(Couleurs.DARK_BLUE);
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagConstraints gbc2 = new GridBagConstraints();

		
		for(int i=0; i<5; i++){
			final Avatar current = new Avatar();
			avatarsPanel.add(current);
			this.listeAvatars[i] = current;
		}
		
		
		gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc2.insets = new Insets(0, 176, 0, 176);
		gbc2.gridwidth = 1;
		gbc2.weightx = 1;
		
		btnsPanel.setBackground(Couleurs.DARK_BLUE);
		btnsPanel.setLayout(new GridBagLayout());
		btnsPanel.add(btnRetour, gbc2);
		gbc2.anchor = GridBagConstraints.FIRST_LINE_END;
		btnsPanel.add(btnContinuer, gbc2);
		
		
		gbc.insets = new Insets(20,10,0,40);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 0;
		this.add(helpPanel, gbc);
		
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 1; gbc.weightx = 1; gbc.weighty = 1;
		this.add(avatarsPanel, gbc);
		
		gbc.gridy = 2;
		this.add(btnsPanel, gbc);
	}
	
	/**
	 * Méthode qui verifie la liste des joueurs
	 */
	private void verifierListeJoueurs(){
		for(Avatar avatar : listeAvatars){
			if(avatar.isActif()){
				if(avatar.getNom().isEmpty()){
					avatar.nom.requestFocus();	
				}else{
					Joueur joueur = avatar.getJoueurInstance();
					Game.modele.ajouterJoueur(joueur);
				}				
			}
		}
		if(Game.modele.getListeJoueurs().isEmpty()) return;
		Game.goToEcran(TypeEcrans.JEU);
	}
	
	
	

	
	/**
	 * Classe interne privée Button
	 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
	 * @version 1.0
	 *
	 */
	private class Button extends CustomButton {
		
		/**
		 * Constructeur de la classe
		 * @param id		Identifiant de la classe
		 * @param libelle	Libelle de la classe
		 */
		public Button(int id, String libelle) {
			super(id, libelle, SwingConstants.CENTER, 150, 50);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			this.setFont(new Font("default", Font.BOLD, 12));
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setForeground(Couleurs.SMOKE_WHITE);
			this.setOpaque(false);
		}

		
		public void mousePressed(MouseEvent e) {
			Button button = (Button) e.getSource();
			if(button.getId() == 1){
				Game.modele.resetListeJoueurs();
				verifierListeJoueurs();
			}else if(button.getId() == 2){
				Game.goToEcran(TypeEcrans.MENU);
			}
		}
		
		
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
		}
	}
	
	/**
	 * Classe interne privée Avatar
	 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
	 * @version 1.0
	 *
	 */
	private class Avatar extends JPanel implements MouseListener{
		
		private JLabel		photo;
		private JTextField	nom;
		private JSpinner	typeIA;
		private boolean		actif;
		private boolean		estHumain;
		
		/**
		 * Constructeur de la classe Avatar
		 */
		public Avatar(){
			this.actif = false;
			this.estHumain = true;
			init();
			updateAvatar();
		
		}
		
		public String getNom() { return nom.getText(); }

		public TypeJoueur getTypeIA() { return (TypeJoueur) typeIA.getValue(); }

		public boolean isActif() { return actif; }
		
		/**
		 * Retourne l'instance de Joueur
		 * @return Joueur
		 */
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

		
		
		
		/**
		 * Permet d'initialiser les éléments de l'ecran de selections des joueurs
		 */
		private void init(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			
			this.setBackground(Couleurs.SMOKE_WHITE);
			photo = new JLabel();
			photo.setPreferredSize(new Dimension(150, 150));
			photo.setIcon(USROFF);
			int rnd = (int) (Math.random()*100);
			nom = new JTextField("Joueur"+rnd);
			typeIA = new JSpinner(new SpinnerListModel(TypeJoueur.getListeIA()));
			((DefaultEditor) typeIA.getEditor()).getTextField().setEditable(false);
			
			gbc.gridy = 0; 
			this.add(photo, gbc);
			gbc.gridy = 1; 
			this.add(nom, gbc);
			gbc.gridy = 2; 
			this.add(typeIA, gbc);
			
			this.addMouseListener(this);
			photo.setOpaque(true);
			
		}
		
		
		
		/** 
		 * Permet de mettre a jour l'affichage de l'avatar selon ses attributs 
		 * 
		 */
		private void updateAvatar(){
			if(actif){
				this.nom.setEnabled(true);
				if(estHumain){
					typeIA.setEnabled(false);
					photo.setIcon(USRH);
					
				} else {
					typeIA.setEnabled(true);
					photo.setIcon(USRIA);
				}
			} else {
				nom.setEnabled(false);
				typeIA.setEnabled(false);
				photo.setIcon(USROFF);
			}
			photo.setText("Actif : " + actif + (estHumain ? " Humain" : " Robot"));
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
			updateAvatar();
		}

		public void mouseReleased(MouseEvent e) {}
		
		
		
	}
	
	
}
