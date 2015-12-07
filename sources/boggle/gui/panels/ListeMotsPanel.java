package boggle.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

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

	public class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		// nom des champs
		private String[]
				columnNames = {"Liste de Mots"};
		// données brutes
		private Object[][] data = {
				{"Bonjour"},
				{"Bonjour"},
				{"Je"},
				{"Test"},
				{"Un"},
				{"WAZZZZZZZZZZZZZA"},
				{"Truc"}
		};	

		// Redefinition de quelques methodes de AbstractTableModel 

		/** Nombre de colonnes dans la table */
		public int getColumnCount() { return columnNames.length ; }
		/** Nombre de lignes dans la table */
		public int getRowCount() { return data.length ; }
		/** Nom du champ correspondant a la colonne specifiee */
		public String getColumnName(int col) { return columnNames[col] ; }
		/** Valeur du champ de la colonne col pour la ligne specifiee */
		public Object getValueAt(int row, int col) { return data[row][col] ; }

		public Class getColumnClass(int columnIndex) {
			return getValueAt(0, columnIndex).getClass() ;
		}

		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue ;
			fireTableCellUpdated(rowIndex, columnIndex);
		}
	}


}
