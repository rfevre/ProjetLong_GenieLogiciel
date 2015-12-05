package boggle.autre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import boggle.jeu.Joueur;

public class BDD {
	private Connection connexion = null;
	PreparedStatement ps ;
	
	/**
	 * Constructeur
	 */
	public BDD(){
		try
		    {   
				Class.forName("org.sqlite.JDBC");
				connexion =  DriverManager.getConnection("jdbc:sqlite:top10.db");
				System.out.println("Connexion réussi");
		    }
		catch (ClassNotFoundException | SQLException e) 
		    {
				e.printStackTrace();
				System.out.println("Connexion pas réussi");
		    }
	}
	
	/**
	 * Méthode qui ferme la connexion
	 * @throws SQLException
	 */
	public void fermer() throws SQLException {
		try 
	    {
			this.connexion.close();
		} 
		catch (SQLException e) 
		    {
			e.printStackTrace();
		    }
	}
	
	/**
	 * Méthode qui ajoute dans la base de données un joueur ( et donc son score )
	 * 
	 * @param joueur
	 */
	public void ajouterUnScore(Joueur joueur) throws SQLException{
		
		// On récupère les informations du joueur
		String nomJoueur = joueur.getNom();
		int scoreJoueur = joueur.getScore();
		// On crée la requête
		String insertion = "INSERT INTO top10 VALUES ( '"+nomJoueur+"' , "+scoreJoueur+" )";
				
		try 
	    {	
			ps = connexion.prepareStatement(insertion);
			int nb = ps.executeUpdate();
			if(nb!=0)
			    {System.out.println("Insertion réussi");}
			else
			    {System.out.println("Insertion pas réussi");}
	    } 
		catch (SQLException e){e.printStackTrace();}
	}
	
	/**
	 * 
	 * Méthode qui retourne une liste de String
	 * Cette méthode sert à  faire le Top 10
	 * 
	 * @return Une liste contenant au maximum 10 String sous la forme 'nom_score'
	 */
	public List<String> getListScores() throws SQLException{
		List<String> liste = new ArrayList<String>();
		String requeteTop10 = " SELECT nom, score FROM top10 ORDER BY score DESC LIMIT 10";
		String nomJoueur, scoreJoueur;
		
		try {
			ps = connexion.prepareStatement(requeteTop10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				nomJoueur = rs.getString("nom");
				scoreJoueur = rs.getString("score");
				liste.add(nomJoueur+"_"+scoreJoueur);
			}
		} catch (SQLException e) {e.getMessage();}
		return liste;
	}
	
	public static void main(String[] args) throws SQLException {
		
		BDD con = new BDD();
		
		for (int i = 0; i < 15; i++) {
			Joueur j = new Joueur("Joueur"+i);
			j.setScore(i+10);
			con.ajouterUnScore(j);
		}
		
		System.out.println(" ************* TOP 10 ************");
		List<String> liste = con.getListScores();
		for (String string : liste) {
			System.out.println(string);
		}
		con.fermer();
	}
}
