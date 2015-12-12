package boggle.gui.components.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/** 
 * TODO Nom du joueur en cours, son score
 *  
 * @author elmassam
 *
 */
public class InfosPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;

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
		
		this.setLayout(new BorderLayout());
		
		JLabel joueurEnCours = new JLabel("Mustapa");
		//joueurEnCours.setPreferredSize(new Dimension(600,20));
		this.add(joueurEnCours, BorderLayout.AFTER_LAST_LINE );
		
//		JLabel scoreJoueur = new JLabel("200");
//		scoreJoueur.setPreferredSize(new Dimension(200,100));
//		this.add(scoreJoueur, BorderLayout.LINE_START );
//		
//		JProgressBar chrono  = new JProgressBar();
//		this.add(chrono);
//		
//		// Chronomètre
//		final int MIN = 0;
//		final int MAX = 100;
//		chrono.setMinimum(MIN);
//		chrono.setMinimum(MAX);
		
	}
}
