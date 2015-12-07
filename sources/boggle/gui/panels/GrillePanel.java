package boggle.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import boggle.gui.core.Fenetre;
import boggle.mots.De;
import boggle.mots.GrilleLettres;

public class GrillePanel extends JPanel {

	private GrilleLettres  grille = Fenetre.modele.getGrille(); 

	
	
	public GrillePanel(){
		this.setPreferredSize(new Dimension(800, 400));
		//this.setBackground(Color.YELLOW);
		init();
		
	}
	
	public void init(){
		
		this.setLayout(new GridLayout(grille.getDimension(), grille.getDimension(), 5, 5));
		
		for(int i=0; i<grille.getDimension(); i++){
			for(int j=0; j<grille.getDimension(); j++){
				this.add(new DeGraphique(grille.getDe(i, j)));
				
			}
		}
		
		
		
		
		
	}
	
	
	
	
	

	
	private class DeGraphique extends JLabel implements MouseListener {
		
		private De de;
		public DeGraphique(De de){
			super("<html><h1>" +de.getChaineFaceVisible()+ "</h1></html>", SwingConstants.CENTER);
			this.de = de;
			this.setBackground(Color.gray);
			this.setForeground(Color.black);
			this.setOpaque(true);
			this.addMouseListener(this);
			
		}
		
		public De getDe() { return de; }

		@Override
		public void mouseClicked(MouseEvent e) {
			DeGraphique current = (DeGraphique) e.getSource();
			De de = current.getDe();
			grille.updateListeDesSelectionnes(de);
			
			System.out.println(grille.getListeDeSelectionnes());
			
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
			this.setBackground(Color.WHITE);
			this.setForeground(Color.black);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
//			De de = current.getDe();
//			if(de)
			this.setBackground(Color.gray);
			this.setForeground(Color.black);
			
			
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	
	
	
}
