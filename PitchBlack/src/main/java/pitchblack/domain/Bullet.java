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
public class Bullet extends Sprite {
    private Player player;

    public Bullet(Player player) {
        super(new Polygon(2, -1, 2, 1, -1, 1, -1, -1), player.getShape().getTranslateX(), player.getShape().getTranslateY());
        this.player = player;

    }
    
    public void shoot() {
        
        this.getShape().setRotate(this.player.getShape().getRotate());
        
        double deltaX = Math.cos(Math.toRadians(this.getShape().getRotate()));
        double deltaY = Math.sin(Math.toRadians(this.getShape().getRotate()));

        deltaX *= 20.0;
        deltaY *= 20.0;

        this.setVelocity(new Point2D(deltaX, deltaY));
    }

    @Override
    public void update() {
        super.update();

        if (this.getShape().getTranslateX() < 0) {
            this.setAlife(false);
        }
        if (this.getShape().getTranslateX() > Ui.WIDTH) {
            this.setAlife(false);

        }
        if (this.getShape().getTranslateY() < 0) {
            this.setAlife(false);
        }
        if (this.getShape().getTranslateY() > Ui.HEIGHT) {
            this.setAlife(false);
        }

    }

}
