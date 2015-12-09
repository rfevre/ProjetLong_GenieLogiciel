package boggle.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import boggle.autre.JTableModel;

public class ListeJoueursPanel extends JPanel {
	
	private JTable table ;
	private JTableModel tableModel ;
	private static Dimension dimension = new Dimension(200,800);
	
	public ListeJoueursPanel(){
		this.setPreferredSize(dimension);
		this.setBackground(Color.GREEN);

		this.setLayout(new FlowLayout()) ;

		// on cree la table a partir d'un modele de table
		tableModel = new JTableModel("Joueurs");
		
		//exemple d'insertion dans le model
		List<String> test = tableModel.getData();	
		test.add("Zac");
		test.add("Mousssssss");
		test.add("Rémy");
		tableModel.setData(test);
		tableModel.fireTableStructureChanged();
		table = new JTable(tableModel) ;
		
		// et on la place dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(dimension);

		this.add(scrollPane);
	}
	
	
	

}
