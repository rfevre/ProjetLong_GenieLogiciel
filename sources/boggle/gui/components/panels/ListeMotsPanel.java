package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import boggle.autre.JTableModel;
import boggle.gui.core.Game;

public class ListeMotsPanel extends JPanel {

	private JTable table ;
	private JTableModel<String> tableModel;
	private static Dimension dimension = new Dimension(300,800);

	private static final long serialVersionUID = 1L;

	public ListeMotsPanel(){

		this.setPreferredSize(dimension);
		this.setBackground(Color.RED);

		this.setLayout(new FlowLayout()) ;

		// on cree la table a partir d'un modele de table
		tableModel = new JTableModel<String>("Mots Ajoutés");
	
		
		if(Game.modele.getJoueurEnCours()!=null){
			tableModel.setData(Game.modele.getJoueurEnCours().getListeMots());
		}
		

		tableModel.fireTableStructureChanged();
		table = new JTable(tableModel) ;
		
		// et on la place dans un JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(dimension);

		this.add(scrollPane);
		
		
		/*try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.add("toto");*/
	}
}
