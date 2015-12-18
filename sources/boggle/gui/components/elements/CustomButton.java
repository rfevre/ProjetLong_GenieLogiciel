package boggle.gui.components.elements;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class CustomButton extends JLabel implements MouseListener {
	
	private static final long serialVersionUID = 6833943045577353021L;
	
	private int id;
	private final ImageIcon BTN_0 = new ImageIcon(getClass().getResource("/img/btn0.png"));
	private final ImageIcon BTN_1 = new ImageIcon(getClass().getResource("/img/btn1.png"));
	private final ImageIcon BTN_2 = new ImageIcon(getClass().getResource("/img/btn2.png"));
	public CustomButton(int id, String libelle, int alignement, int w, int h){
		super(libelle, alignement);
		this.setIcon(BTN_0);
		this.id = id;
		this.setText(libelle);
		this.setOpaque(true);
		this.setPreferredSize(new Dimension(w, h));
		this.addMouseListener(this);
	}

	public int getId() { return id; }    

	
	public void mouseClicked(MouseEvent e) 	{}

	public void mouseEntered(MouseEvent e)	{
		setIcon(BTN_1);
	}

	public void mouseExited(MouseEvent e) 	{
		setIcon(BTN_0);
	}

	public void mousePressed(MouseEvent e) 	{
		setIcon(BTN_2);
	}

	public void mouseReleased(MouseEvent e) {
		setIcon(BTN_0);
	}
	
	
	
	
}