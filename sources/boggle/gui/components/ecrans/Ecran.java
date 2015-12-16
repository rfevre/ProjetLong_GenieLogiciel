package boggle.gui.components.ecrans;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
* Classe abstraite Ecran qui hérite de JPanel
* @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
* @version 1.0
*
*
*/
public abstract class Ecran extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	protected int mx, my;
	
	public Ecran(){}
	
	public abstract void initLayout();
	
	
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
