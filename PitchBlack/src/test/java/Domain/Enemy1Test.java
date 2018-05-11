/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import javafx.geometry.Point2D;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import pitchblack.domain.Enemy1;
import pitchblack.domain.Player;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class Enemy1Test {

    Enemy1 enemy;
    Player player;
    double x;
    double y;
    double offset;
    double sightRadius = Enemy1.sightRaudius;

    public Enemy1Test() {
    }

    @Before
    public void setUp() {
        x = 50;
        y = 80;
        enemy = new Enemy1(x + 100, y - 100);
        offset = Enemy1.borderOffset;
    }

    @Test
    public void patrolRotatesEnemyTowarsPlayerIfCloseEnough() {

        player = new Player(x, y);

        enemy.setVelocity(new Point2D(x + 10, y + 10));
        enemy.update();
        enemy.rotateTowards(x, y);

        double enemyRotation1 = enemy.getShape().getRotate();

        enemy.rotateTowards(0, 0);
        enemy.patrol(player);

        double enemyRotation2 = enemy.getShape().getRotate();

        assertEquals(enemyRotation1, enemyRotation2, 0);
    }

    @Test
    public void patrolSetsNewRandomTargetsWhenOnLocation() {

        enemy.setVelocity(new Point2D(x + 200, y + 200));
        player = new Player(x - 500, y - 500);

        enemy.patrol(player);
        double xTest1 = enemy.getVelocity().getX();
        double yTest1 = enemy.getVelocity().getY();
        double rt1 = enemy.getShape().getRotate();
        enemy.getShape().setTranslateX(xTest1);
        enemy.getShape().setTranslateY(yTest1);
        enemy.patrol(player);
        enemy.update();

        double xTest2 = enemy.getVelocity().getX();
        double yTest2 = enemy.getVelocity().getY();
        double rt2 = enemy.getShape().getRotate();

        assertNotEquals(rt1, rt2);
        assertNotEquals(xTest1, xTest2);
        assertNotEquals(yTest1, yTest2);

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
