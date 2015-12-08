/**
 * 
 */
package boggle.autre;

import javax.swing.table.AbstractTableModel;

/**
 * @author fevrer
 *
 */
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
