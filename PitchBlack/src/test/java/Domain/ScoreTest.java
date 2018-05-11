/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pitchblack.domain.Score;

/**
 *
 * @author JoonaHa
 */
public class ScoreTest {

    String nickname;
    int points;

    public ScoreTest() {
    }

    @Before
    public void setUp() {
        nickname = "Test";
        points = 50;

    }

    @Test
    public void getScoreReturnsScore() {
        Score test = new Score(nickname, points);
        assertEquals(test.getScore(), points);

    }

    @Test
    public void getNicknameReturnsNickname() {
        Score test = new Score(nickname, points);
        assertEquals(test.getNickname(), nickname);

    }

    @Test
    public void compareToPutsHigherScoreFirst() {

        List<Score> testList = new ArrayList<>();

        for (int i = 0; i <= points; i++) {
            Score sc = new Score(nickname, i);
            testList.add(sc);
        }
        Collections.sort(testList);

        assertEquals(points, testList.get(0).getScore());

    }

    @Test
    public void compareToPutsLowestScoreLast() {

        List<Score> testList = new ArrayList<>();

        for (int i = points; i >= 0; i--) {
            Score sc = new Score(nickname, i);
            testList.add(sc);
        }
        Collections.sort(testList);
        System.out.println(testList.size());

        assertEquals(0, testList.get(testList.size() - 1).getScore());

    }

    public void compareToPutsIdenticalScoresAdjacent() {

        List<Score> testList = new ArrayList<>();
        testList.add(new Score(nickname, 50));
        testList.add(new Score(nickname, 50));

        for (int i = 0; i <= points; i++) {
            Score sc = new Score(nickname, i);
            testList.add(sc);
        }
        Collections.sort(testList);

        assertEquals(points, testList.get(0).getScore());
        assertEquals(points, testList.get(1).getScore());
        assertEquals(points, testList.get(2).getScore());

    }

}
