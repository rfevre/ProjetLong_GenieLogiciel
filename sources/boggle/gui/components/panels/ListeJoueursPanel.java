package boggle.gui.components.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import boggle.autre.Couleurs;
import boggle.autre.JTableModel;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

/**
 * Cette classe permet d'afficher la liste des joueurs qui participent a la partie en cours.
 * @author elmassam
 *
 */
public class ListeJoueursPanel extends JPanel implements Observer {

	private JTable table;
	private JTableModel<Joueur> tableModel ;
	private static Dimension dimension = new Dimension(250,560);
	private final RenduCellule renduCell = new RenduCellule();

	public ListeJoueursPanel(){

		Game.modele.addObserver(this);

		this.setBackground(Couleurs.DARK_BLUE);

		tableModel = new JTableModel<Joueur>("<html><h3>JOUEURS</h3></html>");
		tableModel.setData(Game.modele.getListeJoueurs());
		tableModel.fireTableStructureChanged();


		table = new JTable(tableModel) ;
		table.setFillsViewportHeight(true);
		table.setBackground(Couleurs.DARK_BLUE);
		table.setPreferredScrollableViewportSize(dimension);
		table.setGridColor(Couleurs.DARK_BLUE);
		table.getColumnModel().getColumn(0).setCellRenderer( renduCell ); 
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());

		JTableHeader header = table.getTableHeader();
		header.setBackground(Couleurs.CLOUD);
		header.setForeground(Couleurs.DARK_BLUE);

		this.add(scrollPane);
		table.setRowHeight(50);
	}


	@Override
	public void update(Observable o , Object arg) {
		List<Joueur> liste = new ArrayList<>(Game.modele.getListeJoueurs());
		Collections.sort(liste);
		Collections.reverse(liste);
		tableModel.setData(liste);
		tableModel.fireTableDataChanged();

	}



	/**
	 * Classe interne permettant de modifier le rendu d'un cellule
	 * @author elmassam
	 */
	private class RenduCellule extends DefaultTableCellRenderer {

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			Joueur j = (Joueur) value;
			if(j!= null && j.isEntrainDeJouer()){
				c.setFont(new Font("default", Font.BOLD, 14));
				c.setBackground(Couleurs.YELLOW);
				c.setForeground(Couleurs.DARK_BLUE);
			}
			else{            
				c.setBackground(Couleurs.DARK_BLUE);
				c.setForeground(Couleurs.SMOKE_WHITE);	
			}
			return c;
		}
	}

}
