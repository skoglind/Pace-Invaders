import java.awt.*;
import java.awt.geom.Point2D;

/**
 * MovingEntity Class
 * - Moving Entities
 * @author Fredrik Skoglind
 */
public class MovingEntity extends Entity {
    private Vector2D maxVelocity;
    private Vector2D currentVelocity;
    private Vector2D acceleration;

    // GETTERS
    public Vector2D getMaxVelocity() { return maxVelocity; }
    public double getMaxVelocityX() { return maxVelocity.getX(); }
    public double getMaxVelocityY() { return maxVelocity.getY(); }

    public Vector2D getCurrentVelocity() { return currentVelocity; }
    public double getCurrentVelocityX() { return currentVelocity.getX(); }
    public double getCurrentVelocityY() { return currentVelocity.getY(); }

    public Vector2D getAcceleration() { return acceleration; }
    public double getAccelerationX() { return acceleration.getX(); }
    public double getAccelerationY() { return acceleration.getY(); }

    // SETTERS
    public void setMaxVelocity(Vector2D velocity) { this.maxVelocity = velocity; }
    public void setMaxVelocity(double x, double y) { this.maxVelocity.setVector(x, y); }
    public void setMaxVelocityX(double x) { maxVelocity.setVector(x, getMaxVelocityY()); }
    public void setMaxVelocityY(double y) { maxVelocity.setVector(getMaxVelocityX(), y); }

    public void setCurrentVelocity(Vector2D velocity) { this.currentVelocity = velocity; }
    public void setCurrentVelocity(double x, double y) { this.currentVelocity.setVector(x, y); }
    public void setCurrentVelocityX(double x) { currentVelocity.setVector(x, getCurrentVelocityY()); }
    public void setCurrentVelocityY(double y) { currentVelocity.setVector(getCurrentVelocityX(), y); }

    public void setAcceleration(Vector2D acceleration) { this.acceleration = acceleration; }
    public void setAcceleration(double x, double y) { this.acceleration.setVector(x, y); }
    public void setAccelerationX(double x) { acceleration.setVector(x, getAccelerationY()); }
    public void setAccelerationY(double y) { acceleration.setVector(getAccelerationX(), y); }

    public MovingEntity() {
        this.maxVelocity = new Vector2D();
        this.currentVelocity = new Vector2D();
        this.acceleration = new Vector2D();
    }

    public void tick() {
        super.tick();
        setPositionX(getPositionX() + getCurrentVelocityX());
        setPositionY(getPositionY() + getCurrentVelocityY());

        setCurrentVelocityX(getCurrentVelocityX() + getAccelerationX());
        setCurrentVelocityY(getCurrentVelocityY() + getAccelerationY());
    }
}
