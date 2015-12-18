package boggle.autre;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import boggle.jeu.Joueur;
/**
 * Classe BDD qui contient les outils pour manipuler la base de données
 * @author Rémy FEVRE, Zakaria ZEMMIRI, Mustapha EL MASSAOUDI
 * @version 1.0
 *
 */
public class BDD {
	private Connection connexion = null;
	private PreparedStatement ps ;
	
	/**
	 * Constructeur qui se connecte à la base de données
	 */
	public BDD(){
		try
		    {   
				final URL bdd = getClass().getResource("/bdd/top10.db");
				Class.forName("org.sqlite.JDBC");
				connexion =  DriverManager.getConnection("jdbc:sqlite:"+bdd);
		    }
		catch (ClassNotFoundException | SQLException e) 
		    {
				e.printStackTrace();
		    }
	}
	
	/**
	 * Méthode qui ferme la connexion
	 * @throws SQLException Si la connexion n'arrive pas à se fermer
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
	 * @param joueur Joueur qu'on doit insérer dans la base de données
	 * @throws SQLException Si l'insertion dans la base de données à échouer
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
	 * Méthode qui retourne la liste de 10 premiers joueurs ( en fonction du score )
	 * Cette  méthode sert faire le Top 10
	 * 
	 * @throws SQLException Si la requete ne s'effectue pas
	 * @return Une liste contenant au maximum les 10 premiers joueurs ayant les plus gros scores.
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
}