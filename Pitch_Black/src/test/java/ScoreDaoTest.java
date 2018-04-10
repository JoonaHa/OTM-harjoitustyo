/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.ScoresDao;
import database.Database;
import domain.Score;
import java.sql.SQLException;
import java.util.List;
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

    public ScoreDaoTest() {

    }

    @Before
    public void setUp() {
        try {
            database = new Database("jdbc:sqlite:db/highScores.db");
            database.getConnection();
        } catch (Exception e) {
            System.out.println("Can not create database");
        }

        daoScores = new ScoresDao(database);

    }

    @Test
    public void addMethodReturnsTrue() throws SQLException, Exception {
        assertEquals(daoScores.add(new Score("test", 0)), true);

    }

    @Test
    public void getAllReturnsScores() throws SQLException, Exception {

        String nickname = "testGetAll";
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

}

