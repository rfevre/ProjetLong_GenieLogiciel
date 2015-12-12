package boggle.gui.components.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import boggle.gui.core.Game;
import boggle.jeu.Joueur;

/** 
 * TODO Nom du joueur en cours, son score
 *  
 * @author elmassam
 *
 */
public class InfosPanel extends JPanel {
	
	private JLabel joueurLabel;
	private JProgressBar chrono;
	private Joueur joueurEnCours;
	private final int MIN = 0;
	private final int MAX = 100;
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 * 
	 */
	public InfosPanel(){
		this.joueurEnCours = Game.modele.getJoueurEnCours();
		this.joueurLabel = new JLabel("Joueur en cours : "+this.joueurEnCours.getNom());
		this.chrono = new JProgressBar(this.MIN, this.MAX);
		this.setPreferredSize(new Dimension(1200,70));
		init();
	}
	
	public void init() {
		
		
		// Label nom joueur en cours
		joueurLabel.setPreferredSize(new Dimension(200,60));
		joueurLabel.setHorizontalAlignment(JLabel.CENTER);
		joueurLabel.setVerticalAlignment(JLabel.CENTER);
		this.add(joueurLabel,BorderLayout.CENTER);
		
		// Label chronometre
		chrono.setPreferredSize(new Dimension(200,20));
		joueurLabel.setHorizontalAlignment(JLabel.CENTER);
		joueurLabel.setVerticalAlignment(JLabel.CENTER);
		this.add(chrono, BorderLayout.SOUTH);
		
	}
}
