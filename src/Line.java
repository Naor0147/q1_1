/**
 * Represents a finite line segment between two points.
 */
public class Line {

    private static final double EPSILON = 1e-6;

    private final Point start;
    private final Point end;

    /**
     * Constructs a line segment from two points.
     *
     * @param start start point.
     * @param end end point.
     */
    public Line(Point start, Point end) {
        Point safeStart = start == null ? new Point(0, 0) : start;
        Point safeEnd = end == null ? safeStart : end;
        this.start = safeStart;
        this.end = safeEnd;
    }

    /**
     * Constructs a line segment from start and end coordinates.
     *
     * @param x1 x-coordinate of the start point.
     * @param y1 y-coordinate of the start point.
     * @param x2 x-coordinate of the end point.
     * @param y2 y-coordinate of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of this line segment.
     *
     * @return segment length.
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Returns the midpoint of this line segment.
     *
     * @return midpoint between start and end.
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * Returns the start point.
     *
     * @return start point.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end point.
     *
     * @return end point.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Checks whether this line segment intersects another line segment.
     *
     * @param other another line segment.
     * @return {@code true} if the segments intersect, including overlapping segments, {@code false} otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        }
        if (this.intersectionWith(other) != null) {
            return true;
        }
        double determinant = getDeterminant(other);
        if (Math.abs(determinant) < EPSILON) {
            return isPointOnSegment(this.end, other)
                    || isPointOnSegment(this.start, other)
                    || isPointOnSegment(other.start, this)
                    || isPointOnSegment(other.end, this);
        }
        return false;
    }

    /**
     * Checks whether this line intersects either of two given lines.
     *
     * @param other1 first line segment.
     * @param other2 second line segment.
     * @return {@code true} if this line intersects at least one of the given lines, {@code false} otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) || this.isIntersecting(other2);
    }

    /**
     * Returns the intersection point of this line and another line.
     *
     * @param other another line segment.
     * @return the intersection point if there is exactly one such point; otherwise {@code null}.
     */
    public Point intersectionWith(Line other) {
        if (other == null) {
            return null;
        }

        double determinant = getDeterminant(other);
        if (Math.abs(determinant) < EPSILON) {
            if (this.start.equals(other.start)
                    && !isPointOnSegment(this.end, other)
                    && !isPointOnSegment(other.end, this)) {
                return this.start;
            }
            if (this.start.equals(other.end)
                    && !isPointOnSegment(this.end, other)
                    && !isPointOnSegment(other.start, this)) {
                return this.start;
            }
            if (this.end.equals(other.start)
                    && !isPointOnSegment(this.start, other)
                    && !isPointOnSegment(other.end, this)) {
                return this.end;
            }
            if (this.end.equals(other.end)
                    && !isPointOnSegment(this.start, other)
                    && !isPointOnSegment(other.start, this)) {
                return this.end;
            }
            return null;
        }

        double tNum = (other.start().getX() - this.start.getX()) * (other.end().getY() - other.start().getY())
                - (other.start().getY() - this.start.getY()) * (other.end().getX() - other.start().getX());
        double uNum = (other.start().getX() - this.start.getX()) * (this.end.getY() - this.start.getY())
                - (other.start().getY() - this.start.getY()) * (this.end.getX() - this.start.getX());
        double t = tNum / determinant;
        double u = uNum / determinant;

        if (t >= -EPSILON && t <= 1.0 + EPSILON && u >= -EPSILON && u <= 1.0 + EPSILON) {
            double intersectionX = this.start.getX() + t * (this.end.getX() - this.start.getX());
            double intersectionY = this.start.getY() + t * (this.end.getY() - this.start.getY());
            return new Point(intersectionX, intersectionY);
        }
        return null;
    }

    /**
     * Checks if this line and another line represent the same segment, regardless of direction.
     *
     * @param other another line segment.
     * @return {@code true} if both segments are equal, {@code false} otherwise.
     */
    public boolean equals(Line other) {
        if (other == null) {
            return false;
        }
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Computes the determinant used in intersection calculations.
     *
     * @param other another line segment.
     * @return determinant of the two segment direction vectors.
     */
    private double getDeterminant(Line other) {
        return (this.end.getX() - this.start.getX()) * (other.end().getY() - other.start().getY())
                - (this.end.getY() - this.start.getY()) * (other.end().getX() - other.start().getX());
    }

    /**
     * Checks whether a point lies on a given segment.
     *
     * @param point point to check.
     * @param line segment on which to check.
     * @return {@code true} if the point lies on the segment, {@code false} otherwise.
     */
    private boolean isPointOnSegment(Point point, Line line) {
        if (point == null || line == null) {
            return false;
        }
        double d1 = point.distance(line.start());
        double d2 = point.distance(line.end());
        return Math.abs((d1 + d2) - line.length()) < EPSILON;
    }
}