/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pitchblack.gui;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author JoonaHa
 */
public class Darkness {
    private Shape shape;
    private Shape canvas;
    private Shape old;

    public Darkness() {
        this.canvas = new Rectangle(Ui.WIDTH, Ui.HEIGHT);
        
    }

    public Shape getOld() {
        return old;
    }
    
    private void updateOld() {
        this.old = this.shape;
    }

   
    
    
    public Shape update(Shape lamp) {
        updateOld();
        this.shape = Shape.subtract(this.canvas, lamp);
        return shape;
    }
    
    
    
    
}
