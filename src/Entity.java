import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Entity Class
 * - Game entities
 * @author Fredrik Skoglind
 */
public class Entity {
    protected Game game;

    private Vector2D position;
    private Dimension size;

    private HashMap<String, SpriteSet> spriteSets;
    private String activeSpriteSet;

    public Color hitboxColor = Color.BLUE;

    private EntityType entityType;
    public enum EntityType {
        PLAYER,
        SHOT,
        ENEMY,
        OPAQUE,
        TRANSPARENT
    }

    // GETTERS
    public Vector2D getPosition() { return position; }
    public double getPositionX() { return position.getX(); }
    public double getPositionY() { return position.getY(); }

    public Dimension getSize() { return size; }
    public Dimension getRealSize() { return new Dimension((int)size.getWidth()+1, (int)size.getHeight()+1); }

    public String getActiveSpriteSet() { return this.activeSpriteSet; }

    public Rectangle getHitbox() { return new Rectangle((int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight()); }

    public EntityType getEntityType() { return this.entityType; }

    // SETTERS
    public void setPosition(Vector2D position) { this.position = position; }
    public void setPosition(double x, double y) { position.setVector(x, y); }
    public void setPositionX(double x) { position.setVector(x, getPositionY()); }
    public void setPositionY(double y) { position.setVector(getPositionX(), y); }

    public void setSize(Dimension size) { this.size = new Dimension((int)size.getWidth()-1, (int)size.getHeight()-1); }

    public void setActiveSpriteSet(String name) {
        if(spriteSets.containsKey(name)) {
            this.activeSpriteSet = name;
            this.spriteSets.get(name).startAnimation();
        }
    }

    public void setEntityType(EntityType entityType) { this.entityType = entityType; }

    public Entity(Game game) {
        this.game = game;
        this.spriteSets = new HashMap<>();
        this.position = new Vector2D();
        this.position.setVector(0,0);
        this.size = new Dimension(0,0);
        this.activeSpriteSet = null;
        this.entityType = EntityType.TRANSPARENT;
    }

    public void tick() {
        this.updateSpriteSet();
    }

    public void Draw(Graphics2D canvas) {
        if(spriteSets.containsKey(activeSpriteSet)) {
            canvas.drawImage(spriteSets.get(activeSpriteSet).getSprite(), (int)getPositionX(), (int)getPositionY(), game.getGraphicsHandler());
        }

        if(game.showHitbox) {
            canvas.setColor(hitboxColor);
            canvas.drawRect((int)getPositionX(), (int)getPositionY(), (int)getSize().getWidth(), (int)getSize().getHeight());
        }
    }

    private void updateSpriteSet() {
        if(spriteSets.containsKey(activeSpriteSet)) {
            spriteSets.get(activeSpriteSet).tick();
        }
    }

    public void addSpriteSet(SpriteSheet spritesheet, String name, int width, int height, int imageCount) {
        this.addSpriteSet(spritesheet, name, width, height, imageCount, true, SpriteSet.INFINITE, 0.0);
    }

    public void addSpriteSet(SpriteSheet spritesheet, String name, int width, int height, int imageCount, int loopCount) {
        this.addSpriteSet(spritesheet, name, width, height, imageCount, true, loopCount, 0.0);
    }

    public void addSpriteSet(SpriteSheet spritesheet, String name, int width, int height, int imageCount, boolean startAnimation, int loopCount, double animationSpeed) {
        BufferedImage[] images = new BufferedImage[imageCount];
        for(int i = 0; i < imageCount; i++) {
            images[i] = spritesheet.getSprite((i*width) ,0 ,width, height);
        }

        SpriteSet spriteset = new SpriteSet(startAnimation, loopCount, animationSpeed, images);

        spriteSets.put(name, spriteset);
    }

    public void removeSpriteSet(String name) {
        if(spriteSets.containsKey(name)) {
            spriteSets.remove(name);
            if(activeSpriteSet.compareTo(name) == 0) {
                activeSpriteSet = null;
            }
        }
    }
}
