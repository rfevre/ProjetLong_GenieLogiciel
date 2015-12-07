package boggle.gui.components.ecrans;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public abstract class Ecran extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	protected int mx, my;
	
	public Ecran(){}
	
	public abstract void init();
	
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
