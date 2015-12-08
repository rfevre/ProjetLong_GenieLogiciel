package boggle.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
		table = new JTable(new MyTableModel()) ;

		// et on la place dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(dimension);

		this.add(scrollPane);
	}
}
