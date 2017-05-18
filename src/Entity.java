import javafx.geometry.Point2D;

import java.awt.*;

/**
 * Entity Class
 * - Game entities
 * @author Fredrik Skoglind
 */
public class Entity {
    private Vector2D position;
    private Dimension size;

    // GETTERS
    public Vector2D getPosition() { return position; }
    public double getPositionX() { return position.getX(); }
    public double getPositionY() { return position.getY(); }
    public Dimension getSize() { return size; }

    // SETTERS
    public void setPosition(Vector2D position) { this.position = position; }
    public void setPosition(double x, double y) { position.setVector(x, y); }
    public void setPositionX(double x) { position.setVector(x, getPositionY()); }
    public void setPositionY(double y) { position.setVector(getPositionX(), y); }
    public void setSize(Dimension size) { this.size = size; }

    public Entity() {
        this.position = new Vector2D();
        this.position.setVector(0,0);
        this.size = new Dimension(0,0);
    }

    public void tick() {}

    public void Draw(Graphics2D canvas) {
        canvas.setColor(Color.BLUE);
        canvas.fillRect((int)getPositionX(), (int)getPositionY(), (int)getSize().getWidth(), (int)getSize().getHeight());
    }
}
