/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class Player extends Sprite {

    private Polygon shape;
    private Point2D velocity;
    private boolean alife;
    private Ellipse lamp;

    public Player(double x, double y) {
        super((new Polygon(10, -10, 10, 10, -10, 10, -10, -10)), x, y);
        this.lamp = new Ellipse(50, 120);
        this.lamp.setTranslateX(x);
        this.lamp.setTranslateY(y - 120);
        this.lamp.setFill(Color.WHITE);

    }

    public Ellipse getLamp() {
        return lamp;
    }

    @Override
    public void update() {
        super.update();
        this.getLamp().setTranslateX(this.getLamp().getTranslateX() + this.getVelocity().getX());
        this.getLamp().setTranslateY(this.getLamp().getTranslateY() + this.getVelocity().getY());

    }

}
