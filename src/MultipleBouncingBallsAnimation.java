import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Runs an animation with multiple bouncing balls.
 */
public class MultipleBouncingBallsAnimation {

    // constants
    private static final int MAX_RADIUS_SIZE = 100;
    private static final int MIN_RADIUS_SIZE = 1;
    private static final int MIN_COLOR_VALUE = 40;
    private static final int MAX_COLOR_VALUE = 255;
    private static final double SAFE_MULTIPLIER_FOR_RANDOM_POINT = 1.5;
    /**
     * Parses command-line arguments and starts the animation.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("you need to write integers");
            return;
        }
        int[] intArray = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            intArray[i] = (int) Double.parseDouble(args[i]);
            if (intArray[i] < 0) {
                System.out.println("all integers must be bigger or eqaul to 0");
                intArray[i] = 0;
                return;
            }
        }
        drawbals(intArray);
    }

    /**
     * Draws the animation for a list of ball radii.
     *
     * @param intArray array of ball radii
     */
    private static void drawbals(int[] intArray) {
        GUI gui = new GUI("Bouncing Ball", GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        Sleeper sleeper = new Sleeper();
        DrawSurface surface = gui.getDrawSurface();

        // Ball ball = new Ball(start, GameConstants.DEFAULT_BALL_RADIUS,
        // java.awt.Color.BLACK);
        // ball.setVelocity(dx, dy);
        // my obstacles
        Rectangle bounds = new Rectangle(0, 0, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        Ball[] balls = createValidBallsArr(intArray);
        for (Ball ball : balls) {
            ball.addObstacles(bounds.getLines());
            ball.setVelocity(GameConstants.getVelocityFromRadius(ball.getSize()));
        }

        while (true) {

            for (Ball ball : balls) {
                ball.moveOneStep();
            }
            surface = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.drawOn(surface);
            }
            gui.show(surface);
            sleeper.sleepFor(GameConstants.FRAME_DELAY_MS);
        }
    }


    /**
     * Returns a random point in a certain area.
     *
     * @param p1     the first corner of the bounding rectangle
     * @param p2     the second corner of the bounding rectangle
     * @param radius the radius of the ball
     * @return a random Point within the valid area
     */
    private static Point getRandomCenterPointForBall(Point p1, Point p2, int radius) {

        // increase the radius to make sure the ball is fully visible within the bounds
        radius = (int) (radius * SAFE_MULTIPLIER_FOR_RANDOM_POINT);
        double x1 = p1.getX();
        double x2 = p2.getX();

        double y1 = p1.getY();
        double y2 = p2.getY();

        double x = getRandomIntWithThershold(x1, x2, radius);
        double y = getRandomIntWithThershold(y1, y2, radius);

        return new Point(x, y);
    }

    /**
     * Returns the number of valid balls.
     *
     * @param intArray array of ball radii
     * @return number of valid balls
     */
    private static int getNumberOfValidBalls(int[] intArray) {
        int n = 0;
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] > 0) {
                n++;
            }
        }
        return n;
    }

    /**
     * Normalizes a value inside the accepted range.
     *
     * @param value the value to normalize
     * @param min   the minimum accepted value
     * @param max   the maximum accepted value
     * @return the normalized value
     */
    public static int normalizeValueInsideRange(int value, int min, int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        }
        return value;

    }

    /**
     * Creates a valid ball radius array.
     * Normalizes the radius values to be inside the accepted range.
     *
     * @param intArray array of raw radius values
     * @return valid ball radius array
     */
    public static int[] createValidBallsRadiusArr(int[] intArray) {
        int validBalls = getNumberOfValidBalls(intArray);
        int[] validBallsRadiusArr = new int[validBalls];
        int j = 0;
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i] > 0) {
                validBallsRadiusArr[j] = normalizeValueInsideRange(intArray[i], MIN_RADIUS_SIZE, MAX_RADIUS_SIZE);
                j++;
            }
        }
        return validBallsRadiusArr;
    }

    /**
     * Creates an array of balls with valid radius values.
     *
     * @param intArray array of raw radius values
     * @return array of Balls with valid radius
     */
    private static Ball[] createValidBallsArr(int[] intArray) {
        int[] validBallsRadiusArray = createValidBallsRadiusArr(intArray);
        int validBallsLength = validBallsRadiusArray.length;
        Ball[] balls = new Ball[validBallsLength];

        for (int i = 0; i < validBallsLength; i++) {
            Point ballCenterPoint = getRandomCenterPointForBall(new Point(0, 0),
                    new Point(GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT), validBallsRadiusArray[i]);

            balls[i] = new Ball(ballCenterPoint, validBallsRadiusArray[i], getrandomColor());

        }
        return balls;

    }

    /**
     * Returns a random color.
     *
     * @return random color
     */
    private static java.awt.Color getrandomColor() {
        int redCol = (int) getRandomInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE);
        int greenCol = (int) getRandomInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE);
        int blueCol = (int) getRandomInt(MIN_COLOR_VALUE, MAX_COLOR_VALUE);
        return new java.awt.Color(redCol, greenCol, blueCol);
    }

    /**
     * Returns a random number in range.
     *
     * @param min min value
     * @param max max value
     * @return random number within the range
     */
    private static double getRandomInt(double min, double max) {
        if (max < min) {
            double temp = max;
            max = min;
            min = temp;
        }
        return (Math.random() * (max - min + 1)) + min;
    }

    /**
     * Returns a random number in range with a threshold.
     *
     * @param min       min value
     * @param max       max value
     * @param threshold threshold value
     * @return random number within the range using the threshold
     */
    private static double getRandomIntWithThershold(double min, double max, double threshold) {
        if (max < min) {
            double temp = max;
            max = min;
            min = temp;
        }
        return getRandomInt(min + threshold, max - threshold);

    }
}
