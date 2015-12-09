package boggle.gui.components.ecrans;

import java.awt.Color;

import boggle.gui.core.Fenetre;

public class EcranOptions extends Ecran {

	
	private Fenetre fenetre;
	private static final long serialVersionUID = 1L;
	private static EcranOptions instance;
	
	
	public static Ecran getInstance(Fenetre fenetre) {
		if(instance == null){
			instance = new EcranOptions(fenetre);
		}
		return instance;
	}
	
	private EcranOptions(Fenetre fenetre) {
		this.fenetre = fenetre;
		this.setBackground(Color.BLUE);
		System.out.println("ECRAN OPTIONS");
		init();
	}
	
	@Override
	public void init() {
		
		
		
		
	}

}
