package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import boggle.gui.core.Fenetre;
import boggle.jeu.Joueur;

public class EcranSelectionJoueurs extends Ecran {

	private Fenetre fenetre;
	private static final long serialVersionUID = 1L;
	private static EcranSelectionJoueurs instance;
	
	public static EcranSelectionJoueurs getInstance(Fenetre fenetre) {
		if(instance == null){
			instance = new EcranSelectionJoueurs(fenetre);
		}
		return instance;
	}
		
	private EcranSelectionJoueurs(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.setBackground(Color.PINK);
		System.out.println("ECRAN SELECTION");
		init();
	}
	
	
	
	
	@Override
	public void init() {
		JPanel listeAvatars = new JPanel();
		JLabel continuer = new JLabel("Continuer");
		JLabel retour = new JLabel("Retour");
		// TODO : Mettre 5 avatars
		
		Avatar avatar = new Avatar();
		listeAvatars.add(new Avatar());
		listeAvatars.add(new Avatar());
		this.add(listeAvatars);
		this.add(retour);
		this.add(continuer);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	private class Avatar extends JPanel implements MouseListener{
		
		private JLabel photo;
		private TextField nom;
		private JList<String> difficulte;
		private boolean actif;
		private boolean estHumain;
		
		public Avatar(){
			this.setLayout( new GridLayout(3, 1));
			photo = new JLabel("Ma Photo");
			photo.setPreferredSize(new Dimension(200, 200));
			nom = new TextField();
			difficulte = new JList<String>();
			actif = false;
			estHumain=true;
			this.add(difficulte);
			this.add(photo);
			this.add(nom);
			this.addMouseListener(this);
			photo.setBackground(Color.BLUE);
			photo.setOpaque(true);
			changerTypeJoueur();
		}
		
		private void changerTypeJoueur()
		{
			if(actif)
			{
				if(estHumain)
				{
					photo.setBackground(Color.BLACK);
				}
				else
				{
					photo.setBackground(Color.YELLOW);
				}
			}
			else
			{
				photo.setBackground(Color.red);
			}
			photo.setText("Actif : " + actif + (estHumain ? " Humain" : " Robot"));
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
			if(SwingUtilities.isLeftMouseButton(e)){
				if(actif){
					estHumain=!estHumain;
					
				}
			}
			else if(SwingUtilities.isRightMouseButton(e))
			{
				actif=!actif;
			}
			System.out.println(estHumain + " " + actif);
			changerTypeJoueur();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

			
		}
		
		
		
	}
	
	

}
