import biuoop.DrawSurface;

/**
 * ball class with a center point, radius, and color repsent the ball in game.
 */
public class Ball {

    // fields
    private Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity velocity;

    // const
    private final int MAX_RADIUS = 300;

    /**
     * buid a new Ball given its center point, radius, and color.
     * set vecloty to zero
     * 
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.radius = normalizeRadius(r);
        // make sure the center is valid
        this.center = (center != null) ? center : new Point(this.radius, this.radius);
        this.color = (color != null) ? color : java.awt.Color.BLACK;
        this.velocity = new Velocity(0, 0);

    }

    /**
     * Gets the x-coordinate of the ball's center.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y-coordinate of the ball's center.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * Gets the exact point of the ball.
     *
     * @return the center Point
     */
    public Point getPoint() {
        return center;
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * get the vecloctiy fo the ball
     * 
     * @return the veclocty
     */
    public Velocity getVelocity() {
        return velocity;
    }

    /**
     * set the veclocty of the ball
     * 
     * @param vel the new velocity
     */
    public void setVelocity(Velocity vel) {
        this.velocity = (vel != null) ? vel : new Velocity(0, 0);
    }
    
    /**
     * set the veclocty of the ball using dx and dy
     * 
     * @param dx the change in x-coordinate
     * @param dy the change in y-coordinate
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }
    }


    /**
     * Draw the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(getX(), getY(), this.radius);
    }

    /**
     * set the radius into the accepted range.
     *
     * @param r raw radius.
     * @return normalized radius.
     */
    private int normalizeRadius(int r) {
        if (r < 0) {
            return 0;
        }
        if (r > MAX_RADIUS) {
            return MAX_RADIUS;
        }
        return r;
    }

    /**
     * set the center of the ball to a new point\
     * @param newCenter the new center point of the ball
     */
    public void setCenter(Point newCenter) {
        this.center = (newCenter != null) ? newCenter : new Point(this.radius, this.radius);
    }

    /**
     * set the center of the ball to a new point
     * @param x the x-coordinate of the new center
     * @param y the y-coordinate of the new center  
     */
    public void setCenter(double x, double y) {
        this.center = new Point(x, y);
    }
}