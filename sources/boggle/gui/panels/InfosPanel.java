package boggle.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import boggle.gui.core.Fenetre;
import boggle.jeu.Joueur;
import javafx.scene.control.ProgressBar;

public class InfosPanel extends JPanel {

	/** Joueur présent dans la partie 
	 * 
	 *  Comment on récupère le joueur qui est en train de joué ?
	 * **/
	private List<Joueur>  joueurs = Fenetre.modele.getListeJoueurs();
	
	
	/**
	 * Constructeur
	 * 
	 */
	public InfosPanel(){
		this.setPreferredSize(new Dimension(1200, 100));
		this.setBackground(Color.PINK);
		init();
		
	}
	
	public void init() {
		
		// Joueur
		Joueur j = new Joueur("Mustapha");
		j.setScore(200);
		joueurs.add(0, j);
		
		
		// Elements présents dans le PanelInfos
		JLabel nomEtScore, btnRetour;
		JProgressBar chrono  = new JProgressBar();
		
		// Nom et score
		nomEtScore = new JLabel(j.toString());
		
		
		// Chronomètre
		final int MIN = 0;
		final int MAX = 100;
		chrono.setMinimum(MIN);
		chrono.setMinimum(MAX);
		
		
		// Bouton retour
		btnRetour = new JLabel("Retour");
		
		
		this.add(nomEtScore);
		this.add(chrono);
		this.add(btnRetour);
	}
	
	
}
