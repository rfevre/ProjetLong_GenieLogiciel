/**
 * 
 */
package boggle.autre;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author fevrer
 *
 */
public class JTableModel<T> extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	List<T> data = new ArrayList<T>();
    String colNames[] = new String[1];
    
    public JTableModel(String nomColonne){
    	colNames[0]=nomColonne;
    }
    
    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return colNames.length;
    }

    public T getValueAt(int rowIndex, int columnIndex) {
    	return data.get(rowIndex);
    }

    public String getColumnName(int columnIndex) {
        return colNames[columnIndex];
    }
    
    public void setData(List<T> liste){
    	data=liste;
    }
    
    public List<T> getData(){
    	return data;
    }
    
}

