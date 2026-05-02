import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * Runs an animation with multiple bouncing balls.
 */
public class MultipleBouncingBallsAnimation {
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
        Ball[] balls = GameEngine.createValidBallsArr(intArray);
        for (Ball ball : balls) {
            ball.addObstacles(bounds.getLines());
            ball.setVelocity(GameEngine.getVelocityFromRadius(ball.getSize()));
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
}
