package Dao;

import pitchblack.dao.ScoresDao;
import pitchblack.database.Database;
import pitchblack.domain.Score;
import java.sql.SQLException;
import java.util.List;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author JoonaHa
 */
public class ScoreDaoTest {

    ScoresDao daoScores;
    Database database;
    String nickname;

    public ScoreDaoTest() {

    }

    @Before
    public void setUp() {
        try {
            database = new Database("jdbc:sqlite:highScores.db");
            database.getConnection();
        } catch (Exception e) {
            System.out.println("Can not create database");
        }

        daoScores = new ScoresDao(database);
        nickname = "testGetAll";

    }

    @Test
    public void addMethodReturnsTrue() throws SQLException, Exception {
        assertEquals(daoScores.add(new Score(nickname, 0)), true);

    }

    @Test
    public void getAllReturnsScores() throws SQLException, Exception {

        
        daoScores.add(new Score(nickname, 0));

        List<Score> list = daoScores.getAll();

        Boolean test = new Boolean(false);

        for (Score score : list) {
            if (score.getNickname().equals(nickname) && score.getScore() == 0) {
                test = true;
            }
        }
        
        assertEquals(test, true);

    }
    
    @After
    public void tearDown() throws SQLException, Exception {
        daoScores.delete(nickname);
    }

}

