
/* Ball class  
represemnt a ball 
*/

import biuoop.DrawSurface;

public class Ball {

    private Point point;
    private int radius;
    private java.awt.Color color;

    // constants

    /*
    max size for Radius
     */
    private static final int MAX_RADIUS_SIZE = 300;

    // constructor
    public Ball(Point center, int r, java.awt.Color color) {

        if (r < MAX_RADIUS_SIZE) {
            this.radius = r;
        } else {
            this.radius = MAX_RADIUS_SIZE;
        }

        if (center == null) {
            this.point = new Point(this.radius, this.radius);
        } else {
            this.point = center;
        }
        this.color = color;
    }

    // accessors
    public int getX() {
        return (int) this.point.getX();
    }

    public int getY() {
        return (int) this.point.getY();
    }

    public int getSize() {
        return this.radius;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }
}