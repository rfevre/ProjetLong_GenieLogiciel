package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;

import boggle.gui.core.Fenetre;
import boggle.jeu.TypeJoueur;
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
		//this.setBackground(Color.PINK);
		System.out.println("ECRAN SELECTION");
		init();
	}
	
	
	
	
	@Override
	public void init() {
		GridBagConstraints gbc = new GridBagConstraints();
		GridBagConstraints gbc2 = new GridBagConstraints();
		this.setLayout(new GridBagLayout());

		final JPanel listeAvatars = new JPanel();
		final JPanel listeBtns = new JPanel();
		final JLabel btnContinuer = new JLabel("Continuer");
		final JLabel btnRetour = new JLabel("Retour");
		
		
		btnContinuer.setBackground(Color.red);
		btnRetour.setBackground(Color.red);
		btnContinuer.setPreferredSize(new Dimension(150, 40));
		btnRetour.setPreferredSize(new Dimension(150, 40));
		btnContinuer.setOpaque(true);
		btnRetour.setOpaque(true);
		
		
		// TODO : Mettre 5 avatars
		
		
		gbc.gridx = 0; gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1; gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.CENTER;

		for(int i=0; i<5; i++){
			listeAvatars.add(new Avatar());
			
		}

		gbc2.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc2.insets = new Insets(0, 150, 0, 150);
		//gbc2.fill = GridBagConstraints.REMAINDER;
		gbc2.gridwidth = 1;
		gbc2.weightx = 1;
		listeBtns.setLayout(new GridBagLayout());
		listeBtns.add(btnRetour, gbc2);
		gbc2.anchor = GridBagConstraints.FIRST_LINE_END;
		listeBtns.add(btnContinuer, gbc2);
		this.add(listeAvatars, gbc);
		
		gbc.gridy = 1;
		gbc.gridx = 0; 
		this.add(listeBtns, gbc);
		
	}
	
	private class Avatar extends JPanel implements MouseListener{
		
		private static final long serialVersionUID = 1L;
		
		private JLabel		photo;
		private TextField	nom;
		private JSpinner	typeIA;
		private boolean		actif;
		private boolean		estHumain;
		
		public Avatar(){
			this.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.insets = new Insets(5, 5, 5, 5);
			gbc.fill = GridBagConstraints.HORIZONTAL;
						
			photo = new JLabel("Ma Photo");
			photo.setPreferredSize(new Dimension(150, 150));
			nom = new TextField();
			typeIA = new JSpinner(new SpinnerListModel(TypeJoueur.getListeIA()));
			((DefaultEditor) typeIA.getEditor()).getTextField().setEditable(false);
			actif = false;
			estHumain=true;
			
			gbc.gridy = 0; 
			this.add(typeIA, gbc);
			gbc.gridy = 1; 
			this.add(photo, gbc);
			gbc.gridy = 2; 
			this.add(nom, gbc);
			
			this.addMouseListener(this);
			photo.setBackground(Color.BLUE);
			photo.setOpaque(true);
			updateAvatar();
		}
		
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
