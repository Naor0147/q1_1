
public class Line {

    // constants
    final static double EPSILON = 1e-6;

    private Point start;
    private Point end;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        Point start = new Point(x1, y1);
        Point end = new Point(x2, y2);
        this.start = start;
        this.end = end;
    }

    // Return the length of the line
    public double length() {
        return this.start.distance(this.end);
    }

    // Returns the middle point of the line
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;

    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) != null) {
            return true;
        }
        double determinant = getDeterminant(other);
        if (Math.abs(determinant) < EPSILON) {
            if (isPointOnSegment(this.end, other) || isPointOnSegment(this.start, other)
                    || isPointOnSegment(other.start, this) || isPointOnSegment(other.end, this)) {
                return true;
            }
        }

        return false;
    }

    // Returns true if these 2 lines intersect with this line, false otherwise
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) || this.isIntersecting(other2);
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {

        // Calculate the determinant of the system of equations
        double determinant = getDeterminant(other);
        // If the determinant is zero, the lines are parallel
        // If the determinant is zero, the lines are parallel or collinear
        if (Math.abs(determinant) < EPSILON) {

            // Check if they share exactly ONE endpoint, and ensure they don't overlap
            // infinitely
            if (this.start.equals(other.start) && !isPointOnSegment(this.end, other)
                    && !isPointOnSegment(other.end, this)) {
                return this.start;
            }
            if (this.start.equals(other.end) && !isPointOnSegment(this.end, other)
                    && !isPointOnSegment(other.start, this)) {
                return this.start;
            }
            if (this.end.equals(other.start) && !isPointOnSegment(this.start, other)
                    && !isPointOnSegment(other.end, this)) {
                return this.end;
            }
            if (this.end.equals(other.end) && !isPointOnSegment(this.start, other)
                    && !isPointOnSegment(other.start, this)) {
                return this.end;
            }

            return null; // Lines are parallel and do not intersect, or they overlap infinitely
        }
        // Calculate the intersection point using Cramer's rule
        double tNum = (other.start().getX() - this.start.getX()) * (other.end().getY() - other.start().getY()) -
                (other.start().getY() - this.start.getY()) * (other.end().getX() - other.start().getX());
        double uNum = (other.start().getX() - this.start.getX()) * (this.end.getY() - this.start.getY()) -
                (other.start().getY() - this.start.getY()) * (this.end.getX() - this.start.getX());
        double t = tNum / determinant;
        double u = uNum / determinant;

        // Check if the mathematical intersection lies within both finite boundaries.
        // We use EPSILON to allow for edges touching at an angle.
        if (t >= -EPSILON && t <= 1.0 + EPSILON && u >= -EPSILON && u <= 1.0 + EPSILON) {

            // We found a valid intersection point!

            double intersectionX = this.start.getX() + t * (this.end.getX() - this.start.getX());
            double intersectionY = this.start.getY() + t * (this.end.getY() - this.start.getY());
            return new Point(intersectionX, intersectionY);
        }

        return null; // Lines do not intersect within the finite segments

    }

    private double getDeterminant(Line other) {
        return (this.end.getX() - this.start.getX()) * (other.end().getY() - other.start().getY()) -
                (this.end.getY() - this.start.getY()) * (other.end().getX() - other.start().getX());

    }

    // equals -- return true if the lines are equal, false otherwise
    public boolean equals(Line other) {
        return this.start.equals(other.start) && this.end.equals(other.end)
                || this.start.equals(other.end) && this.end.equals(other.start);
    }

    /**
     * A private helper method that checks if a specific point is physically located
     * on the current segment.
     * The check is done by comparing the sum of distances from the point to the
     * segment's
     * endpoints against the total length of the segment.
     *
     * @param point The point being checked.
     * @param line  The segment being checked.
     * @return true if the point is on the segment, false otherwise.
     */
    private boolean isPointOnSegment(Point point, Line line) {
        double d1 = point.distance(line.start());
        double d2 = point.distance(line.end());
        return Math.abs((d1 + d2) - line.length()) < EPSILON;
    }

}