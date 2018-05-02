
package pitchblack.domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

/**
 *Abstrakti luokka jonka kaikki pelin hahmot perivät.
 * @author JoonaHa
 * 
 * 
 * 
 */
public abstract class Sprite {

    private Polygon shape;
    private Point2D velocity;
    private boolean alife;
    
    /**
     * Luo uuden Sprite-olion 
     * @param shape hahmon muoto
     * @param x x-akselin koordinaatti hahmon aloitusaloitussijainniksi.
     * @param y y-akselin koordinaatti hahmon aloitusaloitussijainniksi.
     */

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

    /**
     * Kääntää hahmon kohti annettuja koordinaatteja
     * @param mouseX x-akselin koordinaatti hahmon katsomissuunnaksi.
     * @param mouseY y-akselin koordinaatti hahmon katsomissuunnaksi.
     */
    public void rotateTowards(double mouseX, double mouseY) {

        double angle = Math.toDegrees(Math.atan2(this.getShape().getTranslateY() - mouseY,
                mouseX - this.getShape().getTranslateX())) * -1;

        this.getShape().setRotate(angle);

    }
    
    /**
     * Päivittää hahmon sijainnin nopeuden perusteella.
     */

    public void update() {
        this.getShape().setTranslateX(this.getShape().getTranslateX() + this.getVelocity().getX());
        this.getShape().setTranslateY(this.getShape().getTranslateY() + this.getVelocity().getY());
    }

    /**
     * Tarkastaa onko hahmo törmännyt Shape-olioon.
     * @param s Muoto johon Sprite-hahmo on voinut törmätä.
     * @return Palautta true jos kohteet ovat törmänneet, muuten false.
     */
    public boolean intersects(Shape s) {
        Shape area = Shape.intersect(this.getShape(), s);
        return area.getBoundsInLocal().getWidth() != -1;
    }

}
