package boggle.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import boggle.autre.MyTableModel;

public class ListeMotsPanel extends JPanel {

	private JTable table ;
	private static Dimension dimension = new Dimension(300,800);

	private static final long serialVersionUID = 1L;

	public ListeMotsPanel(){

		this.setPreferredSize(dimension);
		this.setBackground(Color.RED);

		this.setLayout(new FlowLayout()) ;

		// on cree la table a partir d'un modele de table
		MyTableModel tableModel = new MyTableModel();
		
		//exemple d'insertion dans le model
		List<String> test = new ArrayList<>();
		test.add("ouech");
		test.add("ouech");
		test.add("canne");
		test.add("a");
		test.add("peche");
		tableModel.setData(test);
		

		tableModel.fireTableStructureChanged();
		table = new JTable(tableModel) ;
		
		// et on la place dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(dimension);

		this.add(scrollPane);
	}
}
