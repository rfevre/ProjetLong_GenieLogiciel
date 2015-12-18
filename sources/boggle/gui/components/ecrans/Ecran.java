package boggle.gui.components.ecrans;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

/**
 * Classe abstraite Ecran
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public abstract class Ecran extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	protected int mx, my;
	
	/**
	 * Constructeur
	 */
	public Ecran(){}
	
	/**
	 * Méthode qui initialise les composants de l'écran
	 */
	public abstract void initLayout();
	
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

}
