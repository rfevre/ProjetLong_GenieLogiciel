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

	/**
	 * 
	 * Méthode qui permet de se connecter à la base de données SQLite
	 * 
	 * TODO : refaire la méthode de connexion
	 * 
	 */
	public Connection connexion(){
		
		Connection con = null;
		
		try
		    {   
				Class.forName("org.sqlite.JDBC");
				con =  DriverManager.getConnection("jdbc:sqlite:top10.db");
		    }
		catch (ClassNotFoundException | SQLException e) 
		    {
				e.printStackTrace();
		    }
		
		return con;
	}
	
	/**
	 * Méthode qui ajoute dans la base de données un joueur ( et donc son score )
	 * 
	 * @param joueur
	 */
	public void ajouterUnScore(Joueur joueur){
		
		// On récupère les informations du joueur
		String nomJoueur = joueur.getNom();
		int scoreJoueur = joueur.getScore();
		
		// On se connecte à la base de données
		Connection con =  this.connexion();
		
		// On insère dans la base de données les données
		PreparedStatement ps;
		int rs;
		
		try 
	    {			
			
			ps = con.prepareStatement("INSERT INTO top10 VALUES ( '"+nomJoueur+"' , "+scoreJoueur+" )");
			rs = ps.executeUpdate();
				
			// L'insertion a bien été effectué
			if(rs!=0)
			    {
				 	
			    }
			else // L'insertion n'as pas été effectué
			    {
					
			    }
			
	    } 
		catch (SQLException e) 
	    {
			e.printStackTrace();
	    }
		finally
	    {
			try 
			    {
				con.close();
			    } 
			catch (SQLException e) 
			    {
				e.printStackTrace();
			    }
	    }
		
	}
	
	/**
	 * 
	 * Méthode qui retourne une liste de String sous la forme 'nom_score'
	 * Cette méthode sert à faire le Top 10
	 * 
	 * @return
	 */
	public List<String> getListScores()
	{
		List<String> liste = new ArrayList<String>();
		
		// On récupère les 10 meilleurs scores
		String requete = " SELECT nom, score FROM top10 ORDER BY score DESC LIMIT 10";
		
		
		return liste;
	}
}
