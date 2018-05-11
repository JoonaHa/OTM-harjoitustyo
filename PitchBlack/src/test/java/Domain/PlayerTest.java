/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import javafx.geometry.Point2D;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import pitchblack.domain.Player;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class PlayerTest {

    Player player;
    double x;
    double y;

    public PlayerTest() {
    }

    @Before
    public void setUp() {
        x = 50;
        y = 80;
        player = new Player(x, y);

    }

    @Test
    public void updatePlayerCantGoOffscreenLeft() {

        player.setVelocity(new Point2D(-Ui.WIDTH - 20, 0));

        player.update();

        assertEquals(0, player.getShape().getTranslateX(), 0);

    }

    @Test
    public void updatePlayerCantGoOffscreenRight() {

        player.setVelocity(new Point2D(Ui.WIDTH + 20, 0));

        player.update();

        assertEquals(Ui.WIDTH, player.getShape().getTranslateX(), 0);

    }

    @Test
    public void updatePlayerCantGoOffscreenBottom() {

        player.setVelocity(new Point2D(0, -Ui.HEIGHT - 20));

        player.update();

        assertEquals(0, player.getShape().getTranslateY(), 0);

    }

    @Test
    public void updatePlayerCantGoOffscreenTop() {

        player.setVelocity(new Point2D(0, Ui.HEIGHT + 20));

        player.update();

        assertEquals(Ui.HEIGHT, player.getShape().getTranslateY(), 0);

    }

    @Test
    public void LampFollowsPlayer() {

        player.setVelocity(new Point2D(x, y));

        player.update();

        assertEquals(player.getShape().getTranslateX(), player.getLamp().getTranslateX(), 0);

        assertEquals(player.lampoffset + player.getShape().getTranslateY(),
                player.getLamp().getTranslateY(), 0);

    }
}
