import java.awt.*;

/**
 * Tile class
 * @author Fredrik Skoglind
 */
public class Tile extends Entity {
    public Tile(Game game, Vector2D position, Dimension size) {
        super(game);
        setPosition(position);
        setSize(size);
        setEntityType(EntityType.OPAQUE);
    }

    public void tick() {
        setPositionY(getPositionY() + 1);
    }
}