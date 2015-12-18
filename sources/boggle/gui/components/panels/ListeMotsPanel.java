package boggle.gui.components.panels;

import java.awt.Component;
import java.awt.Dimension;
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

/**
 * Classe permettant d'afficher l'ensemble des mots trouves par les joueurs.
 * @author elmassam
 *
 */
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
		
		this.setBackground(Couleurs.DARK_BLUE);

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
		this.joueur = Game.modele.getJoueurEnCours();
		tableModel.setData(getListeMots());
		tableModel.fireTableDataChanged();
		renduCell.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( renduCell ); 
	}
	
	/**
	 * Permet de recuperer la liste des mots sous forme d'un objet formate de trype UnMot 
	 * @return liste des mots
	 */
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
	
	
	
	
	/**
	 * classe interne permettant le formattage d'un mot et le nombre des points associes.
	 * @author elmassam
	 */
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
	
	
	/**
	 * classe interne permettant de modifier le rendu d'une cellule
	 * @author elmassam
	 *
	 */
	private class RenduCellule extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			UnMot mot = (UnMot) value;
			table.setRowHeight(25);
			
			if(mot!= null && mot.points>0){
				c.setBackground(Couleurs.CARROT);
				c.setForeground(Couleurs.SMOKE_WHITE);		
			}else{
				c.setBackground(Couleurs.CONCRETE);
				c.setForeground(Couleurs.DARK_BLUE);	
			}

			return c;
		}
	}
	
	
}
