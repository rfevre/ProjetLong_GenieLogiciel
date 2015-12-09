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
	private PreparedStatement ps ;
	
	/**
	 * Constructeur
	 */
	public BDD(){
		try
		    {   
				Class.forName("org.sqlite.JDBC");
				connexion =  DriverManager.getConnection("jdbc:sqlite:top10.db");
		    }
		catch (ClassNotFoundException | SQLException e) 
		    {
				e.printStackTrace();
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
	 * Méthode qui ajoute dans la base de donnés un joueur ( et donc son score )
	 * 
	 * @param joueur
	 */
	public void ajouterUnScore(Joueur joueur) throws SQLException{
		
		String nomJoueur = joueur.getNom();
		int scoreJoueur = joueur.getScore();
		String insertion = "INSERT INTO top10 VALUES ( '"+nomJoueur+"' , "+scoreJoueur+" )";
				
		try 
	    {	
			ps = connexion.prepareStatement(insertion);
			ps.executeUpdate();
	    } 
		catch (SQLException e){e.printStackTrace();}
	}
	
	/**
	 * 
	 * Méthode qui retourne une liste de String
	 * Cette  méthode sert faire le Top 10
	 * 
	 * @return Une liste contenant au maximum 10 String sous la forme 'nom score'
	 */
	public List<Joueur> getListScores() throws SQLException{
		List<Joueur> liste = new ArrayList<Joueur>();
		String requeteTop10 = " SELECT nom, score FROM top10 ORDER BY score DESC LIMIT 10";
		String nomJoueur;
		int scoreJoueur;
		try {
			ps = connexion.prepareStatement(requeteTop10);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				nomJoueur = rs.getString("nom");
				scoreJoueur = rs.getInt("score");
				Joueur joueurTop10 =  new Joueur(nomJoueur);
				joueurTop10.setScore(scoreJoueur);
				liste.add(joueurTop10);
			}
		} catch (SQLException e) {e.getMessage();}
		return liste;
	}
	
	public static void main(String[] args) throws SQLException {
		
		BDD con = new BDD();
		System.out.println(" ************* TOP 10 ************");
		List<Joueur> joueurs = con.getListScores();
		for (Joueur j : joueurs) {
			System.out.println(j.getNom()+ " "+String.valueOf(j.getScore()) );
		}
		con.fermer();
	}
}
