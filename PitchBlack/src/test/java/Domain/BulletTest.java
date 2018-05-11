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
import pitchblack.domain.Bullet;
import pitchblack.domain.Player;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class BulletTest {

    Bullet bullet;
    Player player;
    double x;
    double y;

    public BulletTest() {
    }

    @Before
    public void setUp() {

        x = 50;
        y = 80;
        player = new Player(x, y);
        bullet = new Bullet(player);
    }

    @Test
    public void bulletIsCreatedToPlayersPosition() {

        double playerX = player.getShape().getTranslateX();
        double playerY = player.getShape().getTranslateY();
        double bulletX = bullet.getShape().getTranslateX();
        double bulletY = bullet.getShape().getTranslateY();

        assertEquals(bulletX, playerX, 0);
        assertEquals(bulletY, playerY, 0);

    }

    @Test
    public void shootBulletsRotationIsSameAsPlayers() {

        player.rotateTowards(x, y);

        bullet.shoot();

        double playerRotation = player.getShape().getRotate();
        double bulletRotation = bullet.getShape().getRotate();

        assertEquals(bulletRotation, playerRotation, 0);

    }

    public void shootBulletsLocationChanges() {

        bullet.shoot();
        bullet.update();

        double playerX = player.getShape().getTranslateX();
        double playerY = player.getShape().getTranslateY();
        double bulletX = bullet.getShape().getTranslateX();
        double bulletY = bullet.getShape().getTranslateY();

        assertNotEquals(bulletX, playerX);
        assertNotEquals(bulletY, playerY);

    }

    @Test
    public void updatePlayerCantGoOffscreenLeft() {

        bullet.setVelocity(new Point2D(-Ui.WIDTH - 20, 0));

        bullet.update();

        assertEquals(bullet.isAlife(), false);

    }

    @Test
    public void updateBulletDiesWhenOffscreenRight() {

        bullet.setVelocity(new Point2D(Ui.WIDTH + 20, 0));

        bullet.update();

        assertEquals(bullet.isAlife(), false);

    }

    @Test
    public void updateBulletDiesWhenOffscreenBottom() {

        bullet.setVelocity(new Point2D(0, -Ui.HEIGHT - 20));

        bullet.update();

        assertEquals(bullet.isAlife(), false);

    }

    @Test
    public void updateBulletDiesWhenOffscreenTop() {

        bullet.setVelocity(new Point2D(0, Ui.HEIGHT + 20));

        bullet.update();

        assertEquals(bullet.isAlife(), false);

    }
}
