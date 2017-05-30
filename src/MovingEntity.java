/**
 * MovingEntity Class
 * - Moving Entities
 * @author Fredrik Skoglind
 */
public class MovingEntity extends Entity {
    private Vector2D maxVelocity;
    private Vector2D currentVelocity;
    private Vector2D acceleration;
    private double friction;

    public enum MovingDirectionX {
        LEFT, RIGHT, NONE
    }

    public enum MovingDirectionY {
        UP, DOWN, NONE
    }

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

    public double getFriction() { return friction; }

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

    public void setFriction(double friction) { this.friction = friction; }

    public MovingEntity(Game game) {
        super(game);
        this.maxVelocity = new Vector2D();
        this.currentVelocity = new Vector2D();
        this.acceleration = new Vector2D();
        this.friction = 0;
    }

    public void accelerateX(double constant) {
        setAccelerationX( getAccelerationX() + constant );
    }

    public void accelerateY(double constant) {
        setAccelerationY( getAccelerationY() + constant );
    }

    public void updateMovement() {
        double velocityX = getCurrentVelocityX() * friction + getAccelerationX();
        double velocityY = getCurrentVelocityY() * friction + getAccelerationY();
        double positionX = getPositionX();
        double positionY = getPositionY();
        double newPositionX;
        double newPositionY;

        // Don't leave it on small numbers
        if(Math.abs(velocityX) < 0.1) { velocityX = 0; }
        if(Math.abs(velocityY) < 0.1) { velocityY = 0; }

        // Cap velocity (positive)
        if(velocityX > getMaxVelocityX()) { velocityX = getMaxVelocityX(); }
        if(velocityY > getMaxVelocityY()) { velocityY = getMaxVelocityY(); }

        // Cap velocity (negative)
        if(velocityX < -getMaxVelocityX()) { velocityX = -getMaxVelocityX(); }
        if(velocityY < -getMaxVelocityY()) { velocityY = -getMaxVelocityY(); }

        // Update position
        newPositionX = positionX + velocityX;
        newPositionY = positionY + velocityY;

        // Set values to object
        setAccelerationX( getAccelerationX() * friction );
        setAccelerationY( getAccelerationY() * friction );
        setCurrentVelocityX( velocityX );
        setCurrentVelocityY( velocityY );
        setPositionX( newPositionX );
        setPositionY( newPositionY );
    }

    public void move(MovingDirectionX directionX, MovingDirectionY directionY) {
        switch(directionX) {
            case LEFT:
                setPositionX( getPositionX() - getMaxVelocityX() );
                break;
            case RIGHT:
                setPositionX( getPositionX() + getMaxVelocityX() );
                break;
        }

        switch(directionY) {
            case UP:
                setPositionY( getPositionY() - getMaxVelocityY() );
                break;
            case DOWN:
                setPositionY( getPositionY() + getMaxVelocityY() );
                break;
        }
    }

    public void tick() {
        super.tick();
    }
}
