package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import boggle.gui.core.Game;

/** 
 * TODO Nom du joueur en cours, son score
 *  
 * @author elmassam
 *
 */
public class InfosPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;

	private JLabel numTour, nomJoueurEnCours, scoreJoueurEnCours;
	private JProgressBar chrono; 
	private double dureeMax;
	
	public InfosPanel(){
		this.dureeMax = Game.modele.getDurreeManche();
		this.setBackground(Color.PINK);
		init();
		
		Game.modele.addObserver(this);
		
	}
	
	public void init() {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		

		numTour = new JLabel("<html><h2>Manche N° " +Game.modele.getNumTour() + "/" + Game.modele.getNbTours()+ "</h2></html>");
		nomJoueurEnCours = new JLabel("<html><h2>" +Game.modele.getJoueurEnCours().getNom()+ "</h2></html>");
		scoreJoueurEnCours = new JLabel("<html><h2>" +Game.modele.getJoueurEnCours().getScore()+ " points.</h2></html>");
		chrono  = new JProgressBar(0, 100);
		
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
		
		
//		chrono.setString("(°L°)");
//		chrono.setBackground(Color.GREEN);
//		chrono.setForeground(Color.WHITE);
//		chrono.setPreferredSize(new Dimension(this.getWidth(), 11));
//		chrono.setBorderPainted(false);
//		chrono.setStringPainted(true);
//		
//		UIManager.put("ProgressBar.selectionForeground", Color.red);
//		UIManager.put("ProgressBar.selectionBackground", Color.darkGray);
//		chrono.updateUI();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.numTour.setText("<html><h2>Manche N° " +Game.modele.getNumTour() + " / " + Game.modele.getNbTours()+ "</h2></html>");
		this.nomJoueurEnCours.setText("<html><h2>" +Game.modele.getJoueurEnCours().getNom()+ "</h2></html>");
		this.scoreJoueurEnCours.setText("<html><h2>" +Game.modele.getJoueurEnCours().getScore()+ "</h2></html>");
		chrono.setValue((int) ((Game.modele.getDurreeManche()/dureeMax)*100));
		chrono.setString(""+Game.modele.getDurreeManche() + " minutes." );
	}
}
