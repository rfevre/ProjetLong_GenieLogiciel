package boggle.gui.core;

import javax.swing.JFrame;

import boggle.gui.components.ecrans.EcranFactory;
import boggle.gui.components.ecrans.TypeEcrans;
import boggle.jeu.Partie;

public class Fenetre extends JFrame{


	private static final long serialVersionUID = 4391704298940272483L;

	public static TypeEcrans ECRAN_EN_COURS = TypeEcrans.JEU;
	public final static int WIDTH = 1100, HEIGHT = WIDTH * 3 / 4; 
	public static final Partie modele = new Partie();
	
	public Fenetre(){
		this.setTitle("Boggle");
		this.setSize(WIDTH, HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.updateEcranEnCours();
		this.setVisible(true);
		this.requestFocus();
	}
	
	public void updateEcranEnCours() {
		this.setContentPane(EcranFactory.getInstance(ECRAN_EN_COURS, this));
		this.revalidate();
	}

	
	public static void main(String[] args) throws InterruptedException {
		Fenetre f = new Fenetre();
	}
}