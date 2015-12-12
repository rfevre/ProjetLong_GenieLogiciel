package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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

	public InfosPanel(){
		
		this.setBackground(Color.PINK);
		init();
	}
	
	public void init() {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		

		JLabel numTour = new JLabel("<html><h3>Manche N° </h3></html>");
		JLabel nomJoueurEnCours = new JLabel("<html>Mustapa</h3></html>");
		JLabel scoreJoueurEnCours = new JLabel("<html>105000000</h3></html>");
		JProgressBar chrono  = new JProgressBar(0,100);
		
		//numTour.setPreferredSize(new Dimension(1200, 50));
		chrono.setValue(60);
		
		gbc.insets = new Insets(20, 10, 5, 10);
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		

		gbc.gridy = 0; gbc.gridx = 0; gbc.gridheight = 2; gbc.weightx = 0.6;
		this.add(numTour, gbc);

		//gbc.anchor = GridBagConstraints.EAST;
		//gbc.fill = GridBagConstraints.NONE;

		gbc.gridx = 1;	gbc.weightx = 0.2;
		this.add(nomJoueurEnCours, gbc);
		
		gbc.gridx = 2; 
		this.add(scoreJoueurEnCours, gbc );
		

		gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3; 		gbc.weightx = 1;
		//gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 0, 5, 0);
		this.add(chrono, gbc);
		
		
	}
}
