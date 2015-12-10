package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


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
	
	public void init() {// Elements présents dans le PanelInfos
		JLabel nomEtScore;
		JProgressBar chrono  = new JProgressBar();
		
		// Nom et score
		nomEtScore = new JLabel("toto");
		
		// Chronomètre
		final int MIN = 0;
		final int MAX = 100;
		chrono.setMinimum(MIN);
		chrono.setMinimum(MAX);
		
		
		this.add(nomEtScore);
		this.add(chrono);
	}
}
