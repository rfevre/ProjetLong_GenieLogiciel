package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import boggle.gui.core.Game;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

public class GrillePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private GrilleLettres  grille;
	private DeGraphique[][] listeDesGraphiques;
	
	
	public GrillePanel(){
		this.grille = Game.modele.getGrille();
		this.grille.addObserver(this);
		this.listeDesGraphiques = new DeGraphique[grille.getDimension()][grille.getDimension()];
		init();
		
	}
	
	
	public DeGraphique[][] getListeDesGraphiques() {
		return listeDesGraphiques;
	}


	public void setListeDesGraphiques(DeGraphique[][] listeDesGraphiques) {
		this.listeDesGraphiques = listeDesGraphiques;
	}


	public void init(){
		this.setLayout(new GridBagLayout());
		JPanel jp = new JPanel(new GridLayout(grille.getDimension(), grille.getDimension(), 10, 10));
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
		System.out.println(o.getClass());
		GrilleLettres g = (GrilleLettres) o ;
		for(int i=0; i<grille.getDimension(); i++){
			for(int j=0; j<grille.getDimension(); j++){
				DeGraphique current = this.listeDesGraphiques[i][j];
				if(current.getDe().isDejaVisite()){
					current.setBackground(Color.red);
				}else{
					current.setBackground(Color.gray);
				}
			}
		}
		
			
	}	
	
	
	

	
	private class DeGraphique extends JLabel implements MouseListener {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private De de;
		public DeGraphique(De de){
			super("<html><h1>" +de.getChaineFaceVisible()+ "</h1></html>", SwingConstants.CENTER);
			
			this.de = de;
			this.setBackground(Color.gray);
			this.setForeground(Color.black);
			this.setOpaque(true);
			this.setBorder(null);
			this.addMouseListener(this);
			
		}
		
		public De getDe() { return de; }

		@Override
		public void mouseClicked(MouseEvent e) {

			
//			if(etat){
//				this.setBackground(Color.RED);
//			}else{
//				this.setBackground(Color.GRAY);
//			}
//			
//			if(de.isDejaVisite()){
//				this.setBackground(Color.RED);
//			}
//			
			//System.out.println("Vous avez cliqué sur " + .getChaineFaceVisible());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			DeGraphique current = (DeGraphique) e.getSource();
			De de = current.getDe();
			grille.updateListeDesSelectionnes(de);
			
			System.out.println(grille.getListeDeSelectionnes());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}







	
	
	
	
	
	
}
