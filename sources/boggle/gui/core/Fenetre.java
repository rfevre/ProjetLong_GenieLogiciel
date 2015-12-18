package boggle.gui.core;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import boggle.gui.components.ecrans.EcranFactory;

/**
 * Classe qui permet de créer la fenetre de l'application
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class Fenetre extends JFrame{

	/**
	 * Initialise la fenetre de jeu
	 * @param libelle : nom de la fenetre
	 * @param width : largeur
	 * @param hight : hauteur
	 */
	public Fenetre(String libelle, int width, int hight){
		this.setTitle(libelle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, hight);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		//this.setContentPane(EcranFactory.getInstance(Game.ECRAN_EN_COURS, this));
		this.setVisible(true);
		this.requestFocus();
	}
	
	/**
	 * MaJ de l'écran courant
	 */
	public void updateEcranEnCours(){
		this.setContentPane(EcranFactory.getInstance(Game.ECRAN_EN_COURS));
		this.revalidate();
	}
	
}