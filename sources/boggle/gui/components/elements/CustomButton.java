package boggle.gui.components.elements;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public abstract class CustomButton extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = 6833943045577353021L;
	
	private int id;
	
	public CustomButton(int id, String libelle, int alignement, int w, int h){
		super(libelle, alignement);
		this.id = id;
		this.setText(libelle);
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(w, h));
		this.addMouseListener(this);
	}

	public int getId() { return id; }    

	
	public void mouseClicked(MouseEvent e) 	{}

	public void mouseEntered(MouseEvent e)	{}

	public void mouseExited(MouseEvent e) 	{}

	public void mousePressed(MouseEvent e) 	{}

	public void mouseReleased(MouseEvent e) {}
	
	
	
	
}