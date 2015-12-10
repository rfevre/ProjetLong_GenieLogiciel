package boggle.gui.components.ecrans;

import java.awt.Color;


public class EcranOptions extends Ecran {

	
	private static final long serialVersionUID = 1L;
	private static EcranOptions instance;
	
	
	public static Ecran getInstance() {
		if(instance == null){
			instance = new EcranOptions();
		}
		return instance;
	}
	
	private EcranOptions() {
		this.setBackground(Color.BLUE);
		System.out.println("ECRAN OPTIONS");
		init();
	}
	
	@Override
	public void init() {
		
		
		
		
	}

}
