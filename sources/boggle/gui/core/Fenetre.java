package boggle.gui.core;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import boggle.gui.components.ecrans.EcranFactory;


public class Fenetre extends JFrame{

	private static final long serialVersionUID = -4810618286807932601L;

	
	public Fenetre(String libelle, int width, int hight){
		this.setTitle(libelle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, hight);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		this.setContentPane(EcranFactory.getInstance(Game.ECRAN_EN_COURS, this));
		this.setVisible(true);
		this.requestFocus();
	}
	
	public void updateEcranEnCours(){
		this.setContentPane(EcranFactory.getInstance(Game.ECRAN_EN_COURS, this));
		this.revalidate();
	}
		

	
}