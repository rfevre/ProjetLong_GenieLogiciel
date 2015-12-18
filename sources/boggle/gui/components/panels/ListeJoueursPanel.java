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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import boggle.autre.Couleurs;
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
	private static Dimension dimension = new Dimension(250,560);
	private final RenduCellule renduCell = new RenduCellule();

	public ListeJoueursPanel(){

		Game.modele.addObserver(this);

		//this.setPreferredSize(dimension);
		this.setBackground(Couleurs.DARK_BLUE);

		//this.setLayout(new FlowLayout()) ;

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
		//Game.modele.calculerScore(joueur);
		List<Joueur> liste = new ArrayList<>(Game.modele.getListeJoueurs());
		Collections.sort(liste);
		Collections.reverse(liste);
		//System.out.println("ListeMotPanel : Joueur en cours :"+joueur+ " Liste : "+joueur.getListeMots());
		tableModel.setData(liste);
		tableModel.fireTableDataChanged();

		//renduCell.setHorizontalAlignment( JLabel.CENTER );
		//table.getColumnModel().getColumn(0).setCellRenderer( renduCell ); 

	}



	private class RenduCellule extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;
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
			//table.setRowHeight(row, 50);

			//			if( row==0 ){
			//				
			//				c.setBackground(Couleurs.CARROT);
			//				c.setForeground(Couleurs.SMOKE_WHITE);		
			//			}
			//			
			//			else{            
			//				c.setBackground(Couleurs.DARK_BLUE);
			//				c.setForeground(Couleurs.SMOKE_WHITE);	
			//			}

			return c;
		}
	}

}
