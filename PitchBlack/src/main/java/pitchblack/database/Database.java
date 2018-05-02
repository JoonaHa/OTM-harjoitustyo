package pitchblack.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import pitchblack.dao.ScoresDao;
import pitchblack.domain.Score;

/**
 * Luokka hallitsee tietokantayhteyttä ja luo tietokannan jos sitä ei ole.
 *
 * @author JoonaHa
 *
 *
 *
 *
 */
public class Database {

    private String databaseAddress;

    /**
     *
     * @param databaseAddress Käytettävän tietokannan osoite.
     *
     */
    public Database(String databaseAddress) throws SQLException {
        this.databaseAddress = databaseAddress;

        File f = new File("highScores.db");

        if (!f.exists()) {
            initDatabase();
        }
    }

    /**
     *
     * @return Muodostaa yhteyden tietokantaan ja palauttaa Connection-olion.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    private void initDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(databaseAddress);
                PreparedStatement stmt = conn.prepareStatement("CREATE TABLE Scores (id serial PRIMARY KEY, score integer, name varchar(100))")) {

            stmt.execute();

            stmt.close();

            conn.close();
            
            // Add test data
            ScoresDao sc = new ScoresDao(this);
            sc.add(new Score("Impossible", -1));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
