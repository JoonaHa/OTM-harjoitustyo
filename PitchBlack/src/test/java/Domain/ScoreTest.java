/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

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

}
