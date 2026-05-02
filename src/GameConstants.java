/**
 * save all the game constants for easier acces.
 */
public final class GameConstants {

    /**
     * just consstant class - should not be instantiated.
     */
    private GameConstants() {
    }

    /**
     * width in pixels.
     */
    public static final int WINDOW_WIDTH = 800;

    /**
     * window height in pixels.
     */
    public static final int WINDOW_HEIGHT = 600;

    /**
     * value for deveiton
     */
    public static final double EPSILON = 1e-6;

    /**
     * value for distance that is not valid
     */
    public static final double THE_NULL_DISTANCE = -1;

    /**
     * default delay
     */
    public static final int FRAME_DELAY_MS = 20;

    /**
     * default ball radius
     */
    public static final int DEFAULT_BALL_RADIUS = 10;


    /**
     * maximum ball speed
     */
    private static final int MAX_BALL_SPEED = 10;

    /**
     * minimum size of a ball that has the minimum speed
     */
    private static final int MIN_SIZE_BALL_OF_MIN_SPEED = 50;

    /**
     * Calcute a velocity based on the radius.
     *
     * @param radius the effective radius used for speed
     * @return velocity based on radius
     */
    public static Velocity getVelocityFromRadius(int radius) {
        radius = (radius < MIN_SIZE_BALL_OF_MIN_SPEED) ? radius
                : MIN_SIZE_BALL_OF_MIN_SPEED;
        double divisor = (double) MIN_SIZE_BALL_OF_MIN_SPEED / MAX_BALL_SPEED;
        double speed = Math.max(1.0, MAX_BALL_SPEED - (radius / divisor));
        return new Velocity(speed, speed);
    }

}
