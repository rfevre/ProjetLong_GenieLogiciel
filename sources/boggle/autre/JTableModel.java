package boggle.autre;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Classe JTableModel<T> qui hérite de la classe abstraite AbstractTableModel
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 */
public class JTableModel<T> extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	List<T> data = new ArrayList<T>();
    String colNames[] = new String[1];
    
    /**
     * Constructeur
     * @param nomColonne 	le nom de la colonne
     */
    public JTableModel(String nomColonne){
    	colNames[0]=nomColonne;
    }
    
    /**
     * Retourne le nombre de lignes 
     * @return int		le nombre de lignes
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Retourne le nombre de colonnes
     * @return int 		le nombre de colonnes
     */
    public int getColumnCount() {
        return colNames.length;
    }

    /*
     * Retourne la valeur ligne, colonne correspondante
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public T getValueAt(int rowIndex, int columnIndex) {
    	return data.get(rowIndex);
    }

    /**
     * Retourne le nom de la colonne
     * @param int			le numéro de la colonne
     * @return String 		le nom de la colonne
     */
    public String getColumnName(int columnIndex) {
        return colNames[columnIndex];
    }
    
    /**
     * Remplace la liste par celle mise en paramètres
     * @param liste		Liste à remplacer
     */
    public void setData(List<T> liste){
    	data=liste;
    }
    
    /**
     * Retourne la liste 
     * @return List<T> 		Liste de la classe
     */
    public List<T> getData(){
    	return data;
    }
    
}

