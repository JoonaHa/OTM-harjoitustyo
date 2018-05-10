/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class Enemy1 extends Sprite {

    private double sightRaudius;
    private long time;
    private double x = 0;
    private double y = 0;
    public static double borderOffset;

    public Enemy1(double x, double y) {
        super(new Polygon(15, -10, 15, 5, -10, 5, -10, -10), x, y);
        this.sightRaudius = 190;
        this.borderOffset = 40;
        time = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

    }

    @Override
    public void patrol(Player player) {

        double distance = Math.sqrt(Math.pow(player.getShape().getTranslateX() - this.getShape().getTranslateX(), 2)
                + Math.pow(player.getShape().getTranslateY() - this.getShape().getTranslateY(), 2));

        long compTime = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);

        if (distance <= sightRaudius) {
            moveTowards(player.getShape().getTranslateX(), player.getShape().getTranslateY());

        } else if ((int) this.getShape().getTranslateX() == (int) x && (int) this.getShape().getTranslateY() == (int) y) {
            this.randomMove();

        } else if (compTime - time >= 4) {
            time = TimeUnit.SECONDS.convert(System.nanoTime(), TimeUnit.NANOSECONDS);
            this.randomMove();
        } else {
            moveTowards(x, y);
        }

    }

    private void moveTowards(double x, double y) {

        this.rotateTowards(x, y);

        double deltaX = Math.cos(Math.toRadians(this.getShape().getRotate()));
        double deltaY = Math.sin(Math.toRadians(this.getShape().getRotate()));

        deltaX *= 0.9;
        deltaY *= 0.9;

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

    private void randomMove() {

        double step = 80;

        double xBorder = Ui.WIDTH + this.borderOffset;
        double yBorder = Ui.HEIGHT + this.borderOffset;

        x = xBorder * new Random().nextDouble() + step;
        y = yBorder * new Random().nextDouble() + step;

        moveTowards(x, y);

    }

}
