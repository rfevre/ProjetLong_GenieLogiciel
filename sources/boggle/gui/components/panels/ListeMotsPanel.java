package boggle.gui.components.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import boggle.autre.JTableModel;
import boggle.gui.core.Game;
import boggle.jeu.Joueur;

public class ListeMotsPanel extends JPanel implements Observer{

	private JTable table ;
	private JTableModel<String> tableModel;
	private static Dimension dimension = new Dimension(300,800);

	private static final long serialVersionUID = 1L;

	public ListeMotsPanel(){
		if(Game.modele.getJoueurEnCours()!=null){
			Game.modele.getJoueurEnCours().addObserver(this);
			
		}
		
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
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		
	}
}
