package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import boggle.autre.JTableModel;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

public class ListeJoueursPanel extends JPanel implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTableModel<Joueur> tableModel ;
	private static Dimension dimension = new Dimension(200,560);
	
	public ListeJoueursPanel(){
		
		Game.modele.addObserver(this);
		
		//this.setPreferredSize(dimension);
		this.setBackground(Color.GREEN);

		this.setLayout(new FlowLayout()) ;

		// on cree la table a partir d'un modele de table
		tableModel = new JTableModel<Joueur>("Joueurs");
		
		//exemple d'insertion dans le model
		tableModel.setData(Game.modele.getListeJoueurs());
		tableModel.fireTableStructureChanged();
		table = new JTable(tableModel) ;
		
		// et on la place dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(dimension);

		this.add(scrollPane);
	}
	
	
	@Override
	public void update(Observable o , Object arg) {
		//Game.modele.calculerScore(joueur);
		List<Joueur> liste = new ArrayList<>(Game.modele.getListeJoueurs());
		Collections.sort(liste);
		Collections.reverse(liste);
		//System.out.println("ListeMotPanel : Joueur en cours :"+joueur+ " Liste : "+joueur.getListeMots());
		tableModel.setData(liste);
		tableModel.fireTableDataChanged();
		
		System.out.println("LISTE JOUEURS : " + Game.modele.getListeJoueurs());
	}
	
	
	


}
