import java.awt.*;

/**
 * Tile class
 * @author Fredrik Skoglind
 */
public class Tile extends Entity {
    private Vector2D velocity;

    // GETTERS
    public Vector2D getVelocity() { return velocity; }

    // SETTERS
    public void setVelocity(Vector2D velocity) { this.velocity = velocity; }

    public Tile(Game game, Vector2D position, Dimension size) {
        this(game, position, size, true, new Vector2D(0,0));
    }

    public Tile(Game game, Vector2D position, Dimension size, boolean opaque) {
        this(game, position, size, opaque, new Vector2D(0,0));
    }

    public Tile(Game game, Vector2D position, Dimension size, boolean opaque, Vector2D velocity) {
        super(game);
        setPosition(position);
        setSize(size);
        setVelocity(velocity);

        if(opaque) { setEntityType(EntityType.OPAQUE); }
        else { setEntityType(EntityType.TRANSPARENT); }
    }

    public void tick() {
        setPositionY(getPositionY()+getVelocity().getY());
        setPositionX(getPositionX()+getVelocity().getX());
    }
}