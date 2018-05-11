/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class Enemy2 extends Sprite {

    public static double borderOffset;

    public Enemy2(double x, double y) {
        super(new Polygon(-10, -15, 10, 0, -10, 15), x, y);
        this.borderOffset = 40;

    }

    @Override
    public void patrol(Player player) {

        moveTowards(player.getShape().getTranslateX(), player.getShape().getTranslateY());

    }

    private void moveTowards(double x, double y) {

        this.rotateTowards(x, y);

        double deltaX = Math.cos(Math.toRadians(this.getShape().getRotate()));
        double deltaY = Math.sin(Math.toRadians(this.getShape().getRotate()));

        deltaX *= 2.2;
        deltaY *= 2.2;

        this.setVelocity(new Point2D(deltaX, deltaY));
    }

    @Override
    public void update() {
        super.update();

        if (this.getShape().getTranslateX() < -this.borderOffset) {
            this.getShape().setTranslateX(0);
        }
        if (this.getShape().getTranslateX() > Ui.WIDTH + this.borderOffset) {
            this.getShape().setTranslateX((Ui.WIDTH));

        }
        if (this.getShape().getTranslateY() < -this.borderOffset) {
            this.getShape().setTranslateY(0);
        }
        if (this.getShape().getTranslateY() > Ui.HEIGHT + this.borderOffset) {
            this.getShape().setTranslateY(Ui.HEIGHT);
        }

    }

}
