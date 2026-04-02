/**
 * Represents a point in a two-dimensional Cartesian plane.
 */
public class Point {

   private static final double EPSILON = 1e-6;

   private final double x;
   private final double y;

   /**
    * Constructs a point with the given coordinates.
    *
    * @param x x-coordinate.
    * @param y y-coordinate.
    */
   public Point(double x, double y) {
      this.x = x;
      this.y = y;
   }

   /**
    * Returns the distance from this point to another point.
    *
    * @param other another point.
    * @return the Euclidean distance to {@code other}; {@link Double#NaN} if {@code other} is {@code null}.
    */
   public double distance(Point other) {
      if (other == null) {
         return Double.NaN;
      }
      double dx = this.x - other.x;
      double dy = this.y - other.y;
      return Math.sqrt(dx * dx + dy * dy);
   }

   /**
    * Checks if this point and the other point represent the same location.
    *
    * @param other another point.
    * @return {@code true} if both coordinates are equal up to a small threshold, {@code false} otherwise.
    */
   public boolean equals(Point other) {
      if (other == null) {
         return false;
      }
      return Math.abs(this.x - other.x) < EPSILON && Math.abs(this.y - other.y) < EPSILON;
   }

   /**
    * Returns the x-coordinate.
    *
    * @return x-coordinate.
    */
   public double getX() {
      return this.x;
   }

   /**
    * Returns the y-coordinate.
    *
    * @return y-coordinate.
    */
   public double getY() {
      return this.y;
   }
}