package boggle.gui.components.panels;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import boggle.autre.Couleurs;
import boggle.gui.core.Game;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

/**
 * Cette Classe permet de generer l'interface de la grille 
 * @author elmassam, 
 *
 */
public class GrillePanel extends JPanel implements Observer {

	private GrilleLettres  grille;
	private DeGraphique[][] listeDesGraphiques;

	private final ImageIcon RED0 = new ImageIcon(getClass().getResource("/img/red0.png"));
	private final ImageIcon RED1 = new ImageIcon(getClass().getResource("/img/red1.png"));
	private final ImageIcon RED2 = new ImageIcon(getClass().getResource("/img/red2.png"));
	private final ImageIcon YEL0 = new ImageIcon(getClass().getResource("/img/yel0.png"));
	private final ImageIcon YEL1 = new ImageIcon(getClass().getResource("/img/yel1.png"));
	private final ImageIcon DEBUT = new ImageIcon(getClass().getResource("/img/debut.png"));
	private final ImageIcon DIR0 = new ImageIcon(getClass().getResource("/img/dir0.png"));
	private final ImageIcon DIR1 = new ImageIcon(getClass().getResource("/img/dir1.png"));
	private final ImageIcon DIR2 = new ImageIcon(getClass().getResource("/img/dir2.png"));
	private final ImageIcon DIR3 = new ImageIcon(getClass().getResource("/img/dir3.png"));
	private final ImageIcon DIR4 = new ImageIcon(getClass().getResource("/img/dir4.png"));
	private final ImageIcon DIR5 = new ImageIcon(getClass().getResource("/img/dir5.png"));
	private final ImageIcon DIR6 = new ImageIcon(getClass().getResource("/img/dir6.png"));
	private final ImageIcon DIR7 = new ImageIcon(getClass().getResource("/img/dir7.png"));



	public GrillePanel(){
		this.grille = Game.modele.getGrille();
		this.grille.addObserver(this);
		this.listeDesGraphiques = new DeGraphique[grille.getDimension()][grille.getDimension()];
		initLayout();
		this.grille.resetDejaVisite();

	}

	public DeGraphique[][] getListeDesGraphiques() {
		return listeDesGraphiques;
	}

	public void setListeDesGraphiques(DeGraphique[][] listeDesGraphiques) {
		this.listeDesGraphiques = listeDesGraphiques;
	}


	public GrilleLettres getGrille() {
		return grille;
	}


	public void setGrille(GrilleLettres grille) {
		this.grille = grille;
	}

	/** * Permet d'initialiser le layout du panel */
	public void initLayout(){
		this.setBackground(Couleurs.DARK_BLUE);
		this.setLayout(new GridBagLayout());
		JPanel jp = new JPanel(new GridLayout(grille.getDimension(), grille.getDimension(), 10, 10));
		jp.setBackground(Couleurs.DARK_BLUE);
		jp.setPreferredSize(new Dimension(500, 500));
		for(int i=0; i<grille.getDimension(); i++){
			for(int j=0; j<grille.getDimension(); j++){
				DeGraphique current = new DeGraphique(grille.getDe(i, j));
				this.listeDesGraphiques[i][j] = current;
				jp.add(current);
			}
		}
		this.add(jp);
	}

	@Override
	public void update(Observable o, Object arg) {
		final int nbSelected = Game.modele.getGrille().getListeDeSelectionnes().size();
		De lastDe = nbSelected > 0 ? Game.modele.getGrille().getListeDeSelectionnes().getLast() : null;
		for(int i=0; i<grille.getDimension(); i++){
			for(int j=0; j<grille.getDimension(); j++){
				De de = grille.getDe(i, j);
				DeGraphique deGr = this.listeDesGraphiques[i][j];

				if(deGr != null){

					// On supprime la direction si le de n'a pas ete visite ou est le dernier 
					if(de!= null &&  (!de.isDejaVisite() || de.equals(lastDe))){
						deGr.getDirection().setIcon(null);
					} 

					if(0 == nbSelected) deGr.getPremier().setIcon(null);
					if(deGr.getDe().isDejaVisite()){
						deGr.setForeground(Couleurs.DARK_BLUE);
						deGr.setIcon(YEL0);	
					}else{
						deGr.setForeground(Couleurs.SMOKE_WHITE);
						deGr.setIcon(RED0);	
					}
				}
			}

			setDirectionImage();

		}
	}	

	/**
	 * Permet de connaitre sens de deplacement lors de la selection des DEs.
	 */
	private void setDirectionImage(){
		Object[] it =  Game.modele.getGrille().getListeDeSelectionnes().toArray();
		final int nb = Game.modele.getGrille().getListeDeSelectionnes().size();

		for (int i=0; i <nb; i++) {

			De de = (De) it[i];
			DeGraphique deGr = listeDesGraphiques[de.getX()][de.getY()];
			if(deGr!= null){
				if(i==0){
					deGr.getPremier().setIcon(DEBUT);
				}else{
					deGr.getPremier().setIcon(null);
				}
			}

			if(nb>1 && i < nb-1){
				De next = (De) it[i+1]; 
				int y = next.getX() - de.getX();
				int x = next.getY() - de.getY();

				if(x == -1){
					if( y == -1 )	 { deGr.getDirection().setIcon(DIR0); }
					else if( y == 0 ){ deGr.getDirection().setIcon(DIR3); }
					else if( y == 1 ){ deGr.getDirection().setIcon(DIR5); }

				}else if( x== 0 ){

					if( y == -1 )	 { deGr.getDirection().setIcon(DIR1); }
					else if( y == 1 ){ deGr.getDirection().setIcon(DIR6); }

				}else if( x== 1 ){
					if( y == -1 )	 { deGr.getDirection().setIcon(DIR2); }
					else if( y == 0 ){ deGr.getDirection().setIcon(DIR4); }
					else if( y == 1 ){ deGr.getDirection().setIcon(DIR7); }
				}
			}
		}
	}


	/** Permet de creer une representation graphique d'un De */
	private class DeGraphique extends JLabel implements MouseListener {
		private De de;
		private JLabel direction, premier;

		public DeGraphique(De de){
			super("<html><h1>" +de.getChaineFaceVisible()+ "</h1></html>", SwingConstants.CENTER);
			this.direction = new JLabel();
			this.premier = new JLabel();
			
			premier.setBounds(3, 3,110, 110);
			direction.setBounds(3, 3,110, 110);
			//direction.setBackground(Color.blue);

			premier.setVerticalTextPosition(SwingConstants.CENTER);
			premier.setHorizontalTextPosition(SwingConstants.CENTER);
			this.add(premier);
			this.add(direction);
			this.setIcon(RED0);
			this.de = de;
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			//this.setPreferredSize(new Dimension(100	, 100));
			//this.setIconTextGap(20);
			this.setFont(new Font("default", Font.PLAIN, 12));
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);

			//this.setBackground(Color.gray);
			this.setForeground(Couleurs.SMOKE_WHITE);
			//this.setOpaque(true);
			this.setBorder(null);
			this.addMouseListener(this);
			this.repaint();

		}

		public De getDe() { return de; }
		public JLabel getPremier() { return premier; }
		public JLabel getDirection() { return direction; }

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if(de.isDejaVisite()){
				//this.setForeground(Couleurs.DARK_BLUE);
				this.setIcon(YEL1);				
			}else{
				//this.setForeground(Couleurs.SMOKE_WHITE);
				this.setIcon(RED1);				
			}
			this.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(de.isDejaVisite()){
				//this.setForeground(Couleurs.DARK_BLUE);
				this.setIcon(YEL0);				
			}else{
				this.setForeground(Couleurs.SMOKE_WHITE);
				this.setIcon(RED0);				
			}
			this.repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.setIcon(RED2);	
			this.repaint();
			// Click sur un De Graphique
			DeGraphique current = (DeGraphique) e.getSource();
			De de = current.getDe();
			//System.out.println("---> Click sur De Graphique " + de + "("+de.getX()+","+de.getY()+")" );
			TextInputPanel.sourceMessage = "click";
			grille.addDeToListeDesSelectionnes(de);

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if(de.isDejaVisite()){
				//this.setForeground(Couleurs.DARK_BLUE);
				this.setIcon(YEL0);				
			}else{
				this.setForeground(Couleurs.SMOKE_WHITE);
				this.setIcon(RED0);				
			}

		}
	}
}