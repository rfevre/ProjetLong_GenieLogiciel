package boggle.gui.components.ecrans;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import boggle.autre.Couleurs;
import boggle.gui.components.elements.CustomButton;
import boggle.gui.core.Game;

/**
*
* Classe qui correspond à l'EcranMenu
* @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
* @version 1.0
*/
public class EcranMenu extends Ecran {

	private static final long serialVersionUID = 1L;
	private static EcranMenu instance;
	
	/**
	*
	* Méthode singleton qui permet d'avoir un seul EcranMenu
	*/
	public static Ecran getInstance() {
		if(instance == null){
			instance = new EcranMenu();
		}
		return instance;
	}
	
	/**
	* Constructeur
	*/	
	private EcranMenu() {
		this.setBackground(Couleurs.DARK_BLUE);
		System.out.println("ECRAN MENU");
		initLayout();
	}
	
	
	private final ImageIcon MENU0 = new ImageIcon(getClass().getResource("/img/menu0.png"));
	private final ImageIcon MENU1 = new ImageIcon(getClass().getResource("/img/menu1.png"));
	private final ImageIcon MENU2 = new ImageIcon(getClass().getResource("/img/menu2.png"));
	private final ImageIcon LOGO = new ImageIcon(getClass().getResource("/img/logo.png"));
	
	/**
	*
	* Méthode qui initiliase l'EcranMenu en ajoutant les éléments
	*
	*/
	public void initLayout(){
		GridBagConstraints gbc = new GridBagConstraints();
		
		this.setLayout(new GridBagLayout());
		
		JLabel logo 		= new JLabel("", SwingConstants.CENTER);
		JLabel btnJouer 	= new MenuBtn("<html><h2>JOUER</h2></html>", 1);
		JLabel btnOptions 	= new MenuBtn("<html><h2>OPTIONS</h2></html>", 2);
		JLabel btnScores 	= new MenuBtn("<html><h2>SCORES</h2></html>", 3);
		JLabel btnQuitter 	= new MenuBtn("<html><h2>QUITTER</h2></html>", 4);
		
		//this.add(Box.createGlue(), gbc);
		logo.setPreferredSize(new Dimension(500, 300));
		logo.setIcon(LOGO);

		gbc.gridy = 0;
		this.add(logo, gbc);
		gbc.insets = new Insets(0, 10, 10, 10);
		
		gbc.gridy = 1;
		this.add(btnJouer, gbc);
		gbc.gridy = 2;
		this.add(btnOptions, gbc);
		gbc.gridy = 3;
		this.add(btnScores, gbc);
		
		gbc.gridy = 4;
		this.add(btnQuitter, gbc);
		
		
	}
	
	
	/**
	* Classe abstraite MenuBtn
	* @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
	* @version 1.0
	*/
	private class MenuBtn extends CustomButton {
		
		private static final long serialVersionUID = 1L;

		/**
		* Constructeur
		* @param libelle	Libelle du boutton
		* @param id 		Id du boutton
		*/
		public MenuBtn(String libelle, int id){
			super(id, libelle, SwingConstants.CENTER, 250, 50);
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			
			//this.setFont(new Font("Monospaced", Font.BOLD, 18));
			this.setVerticalTextPosition(SwingConstants.CENTER);
			this.setHorizontalTextPosition(SwingConstants.CENTER);
			this.setForeground(Couleurs.SMOKE_WHITE);
			this.setOpaque(false);
			setIcon(MENU0);
		}


		@Override
		public void mousePressed(MouseEvent e) {
			this.setForeground(Couleurs.SMOKE_WHITE);
			setIcon(MENU2);
			switch (((MenuBtn) e.getSource()).getId()) {
			
			case 1:		Game.goToEcran(TypeEcrans.SELECTION_JOUEURS); setIcon(MENU0); break;
			case 2:		Game.goToEcran(TypeEcrans.OPTIONS); setIcon(MENU0); break;
			case 3:		Game.goToEcran(TypeEcrans.SCORES); setIcon(MENU0); break;
			case 4:		Game.quitter();	setIcon(MENU0); break;	
			default:	break;
			}
		}


		@Override
		public void mouseEntered(MouseEvent e) {
			setForeground(Couleurs.DARK_BLUE);
			setIcon(MENU1);
		}


		@Override
		public void mouseExited(MouseEvent e) {
			this.setForeground(Couleurs.SMOKE_WHITE);
			setIcon(MENU0);
		}


		@Override
		public void mouseReleased(MouseEvent e) {
			this.setForeground(Couleurs.SMOKE_WHITE);
			setIcon(MENU0);
		}

		
		
	}



	
	


}
