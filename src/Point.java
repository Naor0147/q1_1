public class Point {

   public static final double EPSILON = 1e-6;

   private double x;
   private double y;

   // constructor
   public Point(double x, double y) {
      this.x = x;
      this.y = y;

   }

   // distance -- return the distance of this point to the other point
   public double distance(Point other) {
      double dx = this.x - other.x;
      double dy = this.y - other.y;
      return Math.sqrt(dx * dx + dy * dy);
   }

   // equals -- return true is the points are equal, false otherwise
   public boolean equals(Point other) {

      return (Math.abs(this.x - other.x) < EPSILON) && (Math.abs(this.y - other.y) < EPSILON);
   }

   // Return the x and y values of this point
   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }
}