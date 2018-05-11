/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import javafx.geometry.Point2D;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pitchblack.domain.Enemy2;
import pitchblack.domain.Player;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class Enemy2Test {

    Enemy2 enemy;
    Player player;
    double x;
    double y;
    double offset;

    public Enemy2Test() {

    }

    @Before
    public void setUp() {
        x = 50;
        y = 80;
        enemy = new Enemy2(x + 100, y - 100);
        player = new Player(x, y);
        offset = Enemy2.borderOffset;
    }

    @Test
    public void patrolRotatesEnemyTowarsPlayer() {

        enemy.rotateTowards(x, y);

        double enemyRotation1 = enemy.getShape().getRotate();

        enemy.rotateTowards(0, 0);
        enemy.patrol(player);

        double enemyRotation2 = enemy.getShape().getRotate();

        assertEquals(enemyRotation1, enemyRotation2, 0);
    }

    @Test
    public void updateEnemyCanGoOffscreenOnlyByOffsetLeft() {

        enemy.setVelocity(new Point2D(-(Ui.WIDTH + offset) - 20, 0));

        enemy.update();

        assertEquals(0, enemy.getShape().getTranslateX(), 0);

    }

    @Test
    public void updateEnemyCanGoOffscreenOnlyByOffsetRight() {

        enemy.setVelocity(new Point2D(Ui.WIDTH + offset + 20, 0));

        enemy.update();

        assertEquals(Ui.WIDTH, enemy.getShape().getTranslateX(), 0);

    }

    @Test
    public void updateEnemyCanGoOffscreenOnlyByOffsetBottom() {

        enemy.setVelocity(new Point2D(0, -(Ui.HEIGHT + offset) - 20));

        enemy.update();

        assertEquals(0, enemy.getShape().getTranslateY(), 0);

    }

    @Test
    public void updateEnemyCanGoOffscreenOnlyByOffsetTop() {

        enemy.setVelocity(new Point2D(0, (Ui.HEIGHT + offset + 100)));

        enemy.update();

        assertEquals(Ui.HEIGHT, enemy.getShape().getTranslateY(), 0);

    }
}
