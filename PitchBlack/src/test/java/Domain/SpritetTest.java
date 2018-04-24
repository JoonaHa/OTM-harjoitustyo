/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import pitchblack.domain.Sprite;

/**
 *
 * @author JoonaHa
 */
public class SpritetTest {

    Sprite sprite;
    Polygon shape;

    public SpritetTest() {
    }

    @Before
    public void setUp() {
        // make new shape for testing
        shape = new Polygon(10, -10, 10, 10, -10, 10, -10, -10);
        sprite = new Sprite(shape, 0, 0) {
        };
    }

    @Test
    public void getShapeReturnShape() {
        assertEquals(sprite.getShape(), shape);
    }

    @Test
    public void isAlifeReturnsTrue() {
        assertEquals(sprite.isAlife(), true);
    }

    @Test
    public void setAlifeUpdatesIsAlife() {
        sprite.setAlife(false);
        assertEquals(sprite.isAlife(), false);
    }

    @Test
    public void setVelocityUpdatesVelocity() {
        Point2D vel = new Point2D(5, 5);
        sprite.setVelocity(vel);

        assertEquals(sprite.getVelocity(), vel);
    }

    @Test
    public void rotateTowardSetsShapesRotation() {
        double x = 5;
        double y = 10;
        sprite.rotateTowards(x, y);

        double angle = Math.toDegrees(Math.atan2(sprite.getShape().getTranslateY() - y,
                x - sprite.getShape().getTranslateX())) * -1;

        assertEquals(angle, sprite.getShape().getRotate(), 0);
    }



}
