package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import boggle.autre.Couleurs;
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
		this.setBackground(Couleurs.SMOKE_WHITE);
		
		init();
		
		Game.modele.addObserver(this);
		
	}
	
	public void init() {
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		numTour = new JLabel("<html><h2> MANCHE N° <span style='color:c0392b;'>" +Game.modele.getNumTour() + "/" + Game.modele.getNbTours()+ "</span></h2></html>");
		nomJoueurEnCours = new JLabel("<html><h2>JOUEUR EN COURS : <span style='color:c0392b;'>" +Game.modele.getJoueurEnCours().getNom()+ "</span></h2></html>");
		scoreJoueurEnCours = new JLabel("<html><h2>SCORE : <span style='color:c0392b;'>" +Game.modele.getJoueurEnCours().getScore()+ " points.</span></h2></html>");
		
		chrono  = new JProgressBar(0, 100);
		chrono.setBorder(BorderFactory.createLineBorder(Couleurs.CONCRETE));
		
		//gbc.insets = new Insets(20, 10, 5, 10);
		gbc.insets = new Insets(0, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		

		gbc.gridy = 0; gbc.gridx = 0; gbc.weightx = 0.4;
		this.add(numTour, gbc);

		//gbc.anchor = GridBagConstraints.EAST;
		//gbc.fill = GridBagConstraints.NONE;

		gbc.gridx = 1;	gbc.weightx = 0.3;
		this.add(nomJoueurEnCours, gbc);
		
		gbc.gridx = 2; 
		this.add(scoreJoueurEnCours, gbc );
		

		gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3; 		gbc.weightx = 1;
		//gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = new Insets(0, 5, 5, 5);
		this.add(chrono, gbc);
		
		
		chrono.setBackground(Couleurs.CLOUD);
		chrono.setForeground(Couleurs.GREEN1);
		chrono.setPreferredSize(new Dimension(this.getWidth(), 16));
		chrono.setBorderPainted(false);
		chrono.setStringPainted(true);
		
//		UIManager.put("ProgressBar.selectionForeground", Color.red);
//		UIManager.put("ProgressBar.selectionBackground", Color.darkGray);
		chrono.updateUI();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String ia = Game.modele.getJoueurEnCours().getClass().getName().equals("boggle.jeu.Joueur") ? "" : "(IA)";
		numTour.setText("<html><h2> MANCHE N° <span style='color:c0392b;'>" +Game.modele.getNumTour() + "/" + Game.modele.getNbTours()+ "</span></h2></html>");
		nomJoueurEnCours.setText("<html><h2>JOUEUR EN COURS : <span style='color:c0392b;'>" +Game.modele.getJoueurEnCours().getNom()+ "</span> " +ia+"</h2></html>");
		scoreJoueurEnCours.setText("<html><h2>SCORE : <span style='color:c0392b;'>" +Game.modele.getJoueurEnCours().getScore()+ " points.</span></h2></html>");
		int val = (int) ((Game.modele.getDurreeManche()/dureeMax)*100);
		chrono.setValue(val);
		
		if(val>50){ chrono.setForeground(Couleurs.GREEN1); }
		else if(val>25){chrono.setForeground(Couleurs.CARROT);}
		else if(val>=0){chrono.setForeground(Couleurs.RED1);}
		
		int time = Game.modele.getDurreeManche();
		String txt = time>60 ? (time / 60) + " minutes et " +time%60+ " secondes." : time+ " secondes.";  
		
		chrono.setString(txt);
	}
}
