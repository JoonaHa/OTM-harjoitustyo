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
import javafx.scene.transform.Rotate;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public class Player extends Sprite {

    private Polygon shape;
    private Point2D velocity;
    private boolean alife;
    private final Ellipse lamp;
    private Rotate lampRt;
    private double lampoffset;

    public Player(double x, double y) {
        super((new Polygon(10, -10, 10, 10, -10, 10, -10, -10)), x, y);
        this.lamp = new Ellipse(80, 120);
        this.lamp.setTranslateX(x);
        this.lampoffset = -115;
        this.lamp.setTranslateY(y + lampoffset);
        this.lamp.setFill(Color.WHITE);
        this.lampRt = new Rotate();
        this.lampRt.setPivotY(Math.abs(lampoffset));
        this.getLamp().getTransforms().add(lampRt);

    }

    public Ellipse getLamp() {
        return lamp;
    }

    @Override
    public void update() {
        super.update();

        if (this.getShape().getTranslateX() < 0) {
            this.getShape().setTranslateX(0);
        }
        if (this.getShape().getTranslateX() > Ui.WIDTH) {
            this.getShape().setTranslateX((Ui.WIDTH));

        }
        if (this.getShape().getTranslateY() < 0) {
            this.getShape().setTranslateY(0);
        }
        if (this.getShape().getTranslateY() > Ui.HEIGHT) {
            this.getShape().setTranslateY(Ui.HEIGHT);
        }

        this.getLamp().setTranslateX(this.getShape().getTranslateX());
        this.getLamp().setTranslateY(this.getShape().getTranslateY() + lampoffset);
        this.lampRt.setAngle(this.getShape().getRotate() + 90);

    }

}
