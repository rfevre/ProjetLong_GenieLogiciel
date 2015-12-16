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

import boggle.autre.Couleurs;
import boggle.autre.JTableModel;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

public class ListeMotsPanel extends JPanel implements Observer {

	private JTable table ;
	private JTableModel<UnMot> tableModel;
	private static Dimension dimension = new Dimension(300,560);

	private static final long serialVersionUID = 1L;
	private Joueur joueur;
	RenduCellule renduCell = new RenduCellule();
	
	public ListeMotsPanel(){
		
		Game.modele.addObserver(this);
			
		this.joueur = Game.modele.getJoueurEnCours();
		
		//this.setPreferredSize(dimension);
		this.setBackground(Couleurs.DARK_BLUE);

		//this.setLayout(new FlowLayout()) ;

		// on cree la table a partir d'un modele de table
		tableModel = new JTableModel<UnMot>("<html><h3>MOTS AJOUTES</h3></html>");
		
		if(Game.modele.getJoueurEnCours()!=null){
			tableModel.setData(getListeMots());
		}
		tableModel.fireTableStructureChanged();
		

		table = new JTable(tableModel) ;
		table.setFillsViewportHeight(true);
		table.setBackground(Couleurs.DARK_BLUE);
		table.setPreferredScrollableViewportSize(dimension);
		table.setGridColor(Couleurs.DARK_BLUE);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(Couleurs.CLOUD);
		header.setForeground(Couleurs.DARK_BLUE);
		
		this.add(scrollPane);
		
	}

	@Override
	public void update(Observable o , Object arg) {
		//Game.modele.calculerScore(joueur);
		this.joueur = Game.modele.getJoueurEnCours();
		//System.out.println("ListeMotPanel : Joueur en cours :"+joueur+ " Liste : "+joueur.getListeMots());
		tableModel.setData(getListeMots());
		tableModel.fireTableDataChanged();
		
		
		renduCell.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( renduCell ); 
		//table.getColumnModel().getColumn(0).setWidth(10);
		

		
	}


	
	private List<UnMot> getListeMots(){
		ArrayList<UnMot> tmp = new ArrayList<UnMot>();
		List<String> liste = Game.modele.getJoueurEnCours().getListeMots();
		for (int i = 0; i < liste.size(); i++) {
			tmp.add(new UnMot(liste.get(i)));
		}
		Collections.sort(tmp);
		Collections.reverse(tmp);
		return tmp;
	}
	
	
	
	
	private class UnMot implements Comparable<UnMot>{
		
		private String mot;
		private int points;
		
		public UnMot(String mot){
			this.mot = mot;
			this.points = Game.modele.getArbre().getPointMot(mot);
		}

		@Override
		public String toString() {
			return "<html><table><tr><td width=\"200\">" +mot+ "</td><td align=\"right\">" +points+ " point.</td></tr></table></html>";
		}
		
		@Override
		public int compareTo(UnMot autre) {
			return points - autre.points;
		}
		
	}
	
	
	private class RenduCellule extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			//ModeleDeTable mod = (ModeleDeTable) table.getModel();

			c.setFont(new Font("default", Font.BOLD, 12));
			if( row==0 ){
				c.setBackground(Couleurs.CARROT);
				c.setForeground(Couleurs.SMOKE_WHITE);		
			}
			
			else{            
				c.setBackground(Couleurs.DARK_BLUE);
				c.setForeground(Couleurs.SMOKE_WHITE);	
			}

			return c;
		}
	}
	
	
}
