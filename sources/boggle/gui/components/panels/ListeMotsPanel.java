package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import boggle.autre.JTableModel;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

public class ListeMotsPanel extends JPanel implements Observer {

	private JTable table ;
	private JTableModel<UnMot> tableModel;
	private static Dimension dimension = new Dimension(300,560);

	private static final long serialVersionUID = 1L;
	private Joueur joueur;
	public ListeMotsPanel(){
		
		Game.modele.addObserver(this);
			
		this.joueur = Game.modele.getJoueurEnCours();
		
		//this.setPreferredSize(dimension);
		this.setBackground(Color.RED);

		this.setLayout(new FlowLayout()) ;

		// on cree la table a partir d'un modele de table
		tableModel = new JTableModel<UnMot>("Mots Ajoutés");
		
		if(Game.modele.getJoueurEnCours()!=null){
			tableModel.setData(getListeMots());
		}
		

		tableModel.fireTableStructureChanged();
		table = new JTable(tableModel) ;
		
		// et on la place dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(dimension);

		this.add(scrollPane);
		
	}

	@Override
	public void update(Observable o , Object arg) {
		//Game.modele.calculerScore(joueur);
		this.joueur = Game.modele.getJoueurEnCours();
		//System.out.println("ListeMotPanel : Joueur en cours :"+joueur+ " Liste : "+joueur.getListeMots());
		tableModel.setData(getListeMots());
		tableModel.fireTableDataChanged();
		
		
		RenduCellule renduCell = new RenduCellule();
		renduCell.setHorizontalAlignment( JLabel.CENTER );
		table.getColumnModel().getColumn(0).setCellRenderer( renduCell ); 
		table.getColumnModel().getColumn(0).setWidth(10);

		
	}


	
	private List<UnMot> getListeMots(){
		ArrayList<UnMot> tmp = new ArrayList<UnMot>();
		for(String s : Game.modele.getJoueurEnCours().getListeMots()){
			tmp.add(new UnMot(s));
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
			return  mot + " " +points + " \tpts.";
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

			if( row==0 ){ 
				c.setFont(new Font("default", Font.BOLD, 16));
				c.setBackground(Color.GRAY);
				c.setForeground(Color.white);		
			}
			
			else{            
				c.setFont(new Font("default", Font.BOLD, 12));
				c.setBackground(Color.white);
				c.setForeground(Color.BLACK);		
			}

			return c;
		}
	}
	
	
}
