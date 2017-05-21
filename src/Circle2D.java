/**
 * Vector2D Class
 * @author Fredrik Skoglind
 */
public class Circle2D {
    private double x;
    private double y;
    private double radius;

    public Circle2D() {}

    public Circle2D(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getRadius() { return radius; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setRadius(double radius) { this.radius = radius; }
    public void setCircle(double x, double y, double radius) { this.x = x; this.y = y; this.radius = radius; }

    public boolean intersects(Circle2D circleB) {
        Circle2D circleA = this;
        double distance = Math.pow(circleA.getX()-circleB.getX(), 2) + Math.pow(circleA.getY()-circleB.getY(),2);
        double intersectDistance = Math.pow((circleA.getRadius()) + (circleB.getRadius()),2);
        return distance <= intersectDistance;
    }
}
