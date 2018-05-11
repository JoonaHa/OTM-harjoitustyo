package Dao;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
            database = new Database("highScores.db");
            database.getConnection();
        } catch (Exception e) {
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
    
    
    @Test
    public void createsDBIfItDoesntExist() throws SQLException, Exception {
        
         Database testdatabase = null ;
        try {
           
            testdatabase = new Database("Test.db");
        } catch (Exception e) {
        }
        
        
       ScoresDao testDaoScores = new ScoresDao(testdatabase);
        
        assertEquals(testDaoScores.add(new Score(nickname, 0)), true);

    }
    
    @After
    public void tearDown() throws SQLException, Exception {
        daoScores.delete(nickname);
        File test1File = new File("TestDB.db");
        File test2File = new File("Test.db");
       test1File.delete();
       test2File.delete();
    }

}

