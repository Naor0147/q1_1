/*
class that respresnt the speed of an object
 */
public class Velocity {

    private double dx;
    private double dy;

    /**
     * Builds a velocity from dx and dy componants.
     *
     * @param dx change along x-axis.
     * @param dy change along y-axis.
     */
    public Velocity(double dx, double dy) {

        this.dx = dx;
        this.dx = dy;

    }

    /**
     * @return the change value on x-axis.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * @return the change value on y-axis.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * change value on x -axis speed.
     * 
     * @param dx
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    /**
     * change value on y -axis speed.
     * 
     * @param dy
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    /**
     * change value on x/y -axis speed.
     * 
     * @param dx
     * @param dy
     */
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates velocity from an angle and speed.
     * Angle 0 means up, and positive angles go clock-wise.
     * this beacuse the (0,0) corandion is in the top left
     *
     * @param angle the direction angle in degrees.
     * @param speed the movement speed.
     * @return a new velocity object based on the angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double rad = Math.toRadians(angle);
        double dx = speed * Math.sin(rad);
        double dy = -speed * Math.cos(rad);
        return new Velocity(dx, dy);
    }

}
