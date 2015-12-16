package boggle.autre;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 	Classe utilitaire
 * 	@author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 *  @version 1.0
 */
public class Utils {
	
	public static final String DOSSIER_CONFIG = "./config/";
	public static final String CONFIG_REGLES_4X4_CONFIG = DOSSIER_CONFIG + "regles-4x4.config";
	

	/**
	 * Permet de recuperer une propriete depuis un fichier properties.
	 * @param nomProp		Nom de la propriété
	 * @return String 		valeur de la propriete.
	 */
	public static String getConfigProperty(String nomProp){ 
		final Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(CONFIG_REGLES_4X4_CONFIG));
		} catch (IOException e) { 
			System.out.println(e.getMessage()); 
		} catch (Exception e) { 
			System.out.println(e.fillInStackTrace()); 
		} 
		 
		return prop.getProperty(nomProp);
	}
		

	/** 
	 * Permet de répéter une chaine 
	 * 
	 * @param str 		Chaine à répeter
	 * @param nb 		Nombre de répétition
	 * @return String 	Chaine répété nb fois
	 * */
	public static String repeter(String str, int nb){ return new String(new char[nb]).replace("\0", str); }
		
}
