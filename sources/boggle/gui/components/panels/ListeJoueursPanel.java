package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import boggle.autre.JTableModel;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

public class ListeJoueursPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table ;
	private JTableModel<Joueur> tableModel ;
	private static Dimension dimension = new Dimension(200,800);
	
	public ListeJoueursPanel(){
		this.setPreferredSize(dimension);
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
	
	
	

}
