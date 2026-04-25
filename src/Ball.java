import biuoop.DrawSurface;

public class Ball {

     // fields
     private Point center;
     private int radius;
     private java.awt.Color color;

     // constructor
     public Ball(Point center, int r, java.awt.Color color) {
          this.center = center;
          this.radius = r;
          this.color = color;
     }

     // accessors
     public int getX() {
          return (int) this.center.getX();
     }

     public int getY() {
          return (int) this.center.getY();
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
          surface.fillCircle(getX(), getY(), this.radius);
     }
}