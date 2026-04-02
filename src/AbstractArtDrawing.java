
import biuoop.GUI;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.Random;

/**
 * Draws random lines and visualizes their geometric properties.
 */
public class AbstractArtDrawing {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int NUMBER_OF_LINES = 10;
    private static final int POINT_RADIUS = 3;
    private static final String WINDOW_TITLE = "Random lines";
    private static final double EPSILON = 1e-6;

    private final Random random = new Random();

    /**
     * Opens a GUI window and draws random lines, their midpoints, intersections, and triangle segments.
     */
    public void drawRandomLines() {
        GUI gui = new GUI(WINDOW_TITLE, WIDTH, HEIGHT);
        DrawSurface surface = gui.getDrawSurface();

        Line[] lines = createRandomLines();

        for (Line line : lines) {
            drawLine(line, surface);
        }

        drawTriangleSegments(surface, lines);

        for (Line line : lines) {
            drawMiddlePoint(line, surface);
        }

        drawIntersectionPoints(surface, lines);
        gui.show(surface);
    }

    /**
     * Creates an array of randomly generated line segments.
     *
     * @return array containing {@link #NUMBER_OF_LINES} random lines.
     */
    private Line[] createRandomLines() {
        Line[] lines = new Line[NUMBER_OF_LINES];
        for (int i = 0; i < NUMBER_OF_LINES; i++) {
            lines[i] = generateRandomLine();
        }
        return lines;
    }

    /**
     * Draws the midpoint of a line in blue.
     *
     * @param line line whose midpoint is drawn.
     * @param surface drawing surface.
     */
    private void drawMiddlePoint(Line line, DrawSurface surface) {
        Point middle = line.middle();
        drawPoint(surface, middle, Color.BLUE);
    }

    /**
     * Draws all pairwise intersection points in red.
     *
     * @param surface drawing surface.
     * @param lines lines to inspect.
     */
    private void drawIntersectionPoints(DrawSurface surface, Line[] lines) {
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                if (lines[i].isIntersecting(lines[j])) {
                    Point intersection = lines[i].intersectionWith(lines[j]);
                    if (intersection != null) {
                        drawPoint(surface, intersection, Color.RED);
                    }
                }
            }
        }
    }

    /**
     * Draws green triangle edges when three lines produce three distinct intersection points.
     *
     * @param surface drawing surface.
     * @param lines candidate lines.
     */
    private void drawTriangleSegments(DrawSurface surface, Line[] lines) {
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {
                Point firstIntersection = lines[i].intersectionWith(lines[j]);
                if (firstIntersection == null) {
                    continue;
                }

                for (int k = j + 1; k < lines.length; k++) {
                    Point secondIntersection = lines[i].intersectionWith(lines[k]);
                    Point thirdIntersection = lines[j].intersectionWith(lines[k]);

                    if (formsTriangle(firstIntersection, secondIntersection, thirdIntersection)) {
                        drawSegment(surface, firstIntersection, secondIntersection, Color.GREEN);
                        drawSegment(surface, firstIntersection, thirdIntersection, Color.GREEN);
                        drawSegment(surface, secondIntersection, thirdIntersection, Color.GREEN);
                    }
                }
            }
        }
    }

    /**
     * Checks whether three points form a non-degenerate triangle.
     *
     * @param first first point.
     * @param second second point.
     * @param third third point.
     * @return {@code true} when the points are distinct and non-collinear, {@code false} otherwise.
     */
    private boolean formsTriangle(Point first, Point second, Point third) {
        if (first == null || second == null || third == null) {
            return false;
        }

        if (first.distance(second) < EPSILON
                || first.distance(third) < EPSILON
                || second.distance(third) < EPSILON) {
            return false;
        }

        double areaTwice = Math.abs(
                first.getX() * (second.getY() - third.getY())
                        + second.getX() * (third.getY() - first.getY())
                        + third.getX() * (first.getY() - second.getY()));

        return areaTwice > EPSILON;
    }

    /**
     * Draws a segment between two points in the given color.
     *
     * @param surface drawing surface.
     * @param start segment start.
     * @param end segment end.
     * @param color segment color.
     */
    private void drawSegment(DrawSurface surface, Point start, Point end, Color color) {
        surface.setColor(color);
        surface.drawLine(
                (int) start.getX(),
                (int) start.getY(),
                (int) end.getX(),
                (int) end.getY());
    }

    /**
     * Draws a filled point as a small circle.
     *
     * @param surface drawing surface.
     * @param point point location.
     * @param color fill color.
     */
    private void drawPoint(DrawSurface surface, Point point, Color color) {
        surface.setColor(color);
        surface.fillCircle((int) point.getX(), (int) point.getY(), POINT_RADIUS);
    }

    /**
     * Draws a line segment in black.
     *
     * @param line line segment to draw.
     * @param surface drawing surface.
     */
    private void drawLine(Line line, DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawLine(
                (int) line.start().getX(),
                (int) line.start().getY(),
                (int) line.end().getX(),
                (int) line.end().getY());
    }

    /**
     * Generates a random non-degenerate line segment inside the drawing bounds.
     *
     * @return random line segment.
     */
    private Line generateRandomLine() {
        int x1 = random.nextInt(WIDTH) + 1;
        int y1 = random.nextInt(HEIGHT) + 1;
        int x2 = random.nextInt(WIDTH) + 1;
        int y2 = random.nextInt(HEIGHT) + 1;

        while (x1 == x2 && y1 == y2) {
            x2 = random.nextInt(WIDTH) + 1;
            y2 = random.nextInt(HEIGHT) + 1;
        }

        return new Line(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Program entry point.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        AbstractArtDrawing example = new AbstractArtDrawing();
        example.drawRandomLines();
    }
}
