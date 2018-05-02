/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.dao;

import pitchblack.database.Database;
import pitchblack.domain.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *Luokka tarjoaa metodeita Score-olioden tietokantakyselyitä varten.
 * @author JoonaHa
 * 
 */
public class ScoresDao {

    private Database database;
    
    
    /**
     * 
     * @param database Database-olio tietokantathteyden muodostamiseksi.
     * @see Database
     */

    public ScoresDao(Database database) {
        this.database = database;
    }
    
    /**
     * Lisää tietokantaan uuden score-olion.
     * @param score Tietokantaan lisättävä score-olio.
     * @return score-olion lisäyksen onnistuminen
     * @throws SQLException
     * @throws Exception 
     */

    public boolean add(Score score) throws SQLException, Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Scores (score, name) VALUES(?,?)");
            stmt.setInt(1, score.getScore());
            stmt.setString(2, score.getNickname());

            stmt.executeUpdate();
            stmt.close();
            connection.close();

            return true;
        }

    }
    
    /**
     * Palauttaa kaikki tietokanna score-oliot listana.
     * @return Lista tietokannan score-oliosta.
     * @throws SQLException
     * @throws Exception 
     */

    public List<Score> getAll() throws SQLException, Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Scores");

        ResultSet rs = stmt.executeQuery();
        List<Score> listOfScores = new ArrayList<>();

        while (rs.next()) {
            String nickname = rs.getString("name");
            int score = rs.getInt("score");

            listOfScores.add(new Score(nickname, score));

        }

        rs.close();
        stmt.close();
        connection.close();

        return listOfScores;

    }
    
    /**
     * Poistaa tietokannasta annetun pelaajan score-tulokset.
     * @param nickname Poistettavan pelaajan nimimerkki.
     * @return Poistamisen onnistuminen.
     * @throws SQLException
     * @throws Exception 
     */

    public boolean delete(String nickname) throws SQLException, Exception {
        try (Connection connection = database.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM Scores WHERE name = ?");
            stmt.setString(1, nickname);

            stmt.executeUpdate();
            stmt.close();
            connection.close();
            
            return true;
        }
    }

}
