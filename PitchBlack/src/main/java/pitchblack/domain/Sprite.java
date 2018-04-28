/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import pitchblack.gui.Ui;

/**
 *
 * @author JoonaHa
 */
public abstract class Sprite {

    private Polygon shape;
    private Point2D velocity;
    private boolean alife;

    public Sprite(Polygon shape, double x, double y) {
        this.shape = shape;
        this.shape.setTranslateX(x);
        this.shape.setTranslateY(y);
        this.velocity = new Point2D(0, 0);
        this.alife = true;

    }

    public Polygon getShape() {
        return shape;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public boolean isAlife() {
        return alife;
    }

    public void setAlife(boolean alife) {
        this.alife = alife;
    }

    public void rotateTowards(double mouseX, double mouseY) {

        double angle = Math.toDegrees(Math.atan2(this.getShape().getTranslateY() - mouseY,
                mouseX - this.getShape().getTranslateX())) * -1;

        this.getShape().setRotate(angle);

    }

    public void update() {
        this.getShape().setTranslateX(this.getShape().getTranslateX() + this.getVelocity().getX());
        this.getShape().setTranslateY(this.getShape().getTranslateY() + this.getVelocity().getY());
    }

    public boolean intersects(Shape s) {
        Shape area = Shape.intersect(this.getShape(), s);
        return area.getBoundsInLocal().getWidth() != -1;
    }

}
