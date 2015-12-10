package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import boggle.gui.core.Game;

public class EcranMenu extends Ecran {

	private static final long serialVersionUID = 1L;
	private static EcranMenu instance;
	
	public static Ecran getInstance() {
		if(instance == null){
			instance = new EcranMenu();
		}
		return instance;
	}
		
	private EcranMenu() {
		this.setBackground(Color.black);
		System.out.println("ECRAN MENU");
		init();
	}
	
	
	
	
	public void init(){
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		
		this.setLayout(new GridBagLayout());
		
		JLabel btnJouer 	= new MenuBtn("JOUER", 1);
		JLabel btnOptions 	= new MenuBtn("OPTIONS", 2);
		JLabel btnScores 	= new MenuBtn("SCORES", 3);
		JLabel btnAide 		= new MenuBtn("AIDE", 4);
		JLabel btnQuitter 	= new MenuBtn("QUITTER", 5);
		
		this.add(Box.createGlue(), gbc);
		
		gbc.insets = new Insets(0, 10, 10, 10);
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.gridy = 1;
		this.add(btnJouer, gbc);
		gbc.gridy = 2;
		this.add(btnOptions, gbc);
		gbc.gridy = 3;
		this.add(btnScores, gbc);
		gbc.gridy = 4;
		this.add(btnAide, gbc);
		
		gbc.gridy = 5;
		this.add(btnQuitter, gbc);
		
		
	}
	
	
	private class MenuBtn extends JLabel implements MouseListener {
		
		private static final long serialVersionUID = 6833943045577353021L;
		private int id;
		public MenuBtn(String libelle, int id){
			super(libelle, SwingConstants.CENTER);
			this.id = id;
			this.setText("<html><h1>" +libelle+ "</h1></html>");
			this.setOpaque(true);
			this.setForeground(Color.WHITE);
			this.setBackground(Color.GRAY);
			this.setPreferredSize(new Dimension(200, 50));
			this.addMouseListener(this);
		}


		public int getId() { return id; }    



		@Override
		public void mouseClicked(MouseEvent e) {
			Game.ECRAN_EN_COURS = TypeEcrans.JEU;
			if(1 == ((MenuBtn) e.getSource()).getId() ){
				Game.goToEcran(TypeEcrans.SELECTION_JOUEURS);
				
			}
			System.out.println("click");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			this.setForeground(Color.GRAY);
			this.setBackground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			this.setForeground(Color.WHITE);
			this.setBackground(Color.GRAY);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.setBackground(Color.DARK_GRAY);
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			this.setBackground(Color.WHITE);
		}
		
	}
	
	
	
	
	
	
	


}
