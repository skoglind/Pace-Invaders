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

    private HitboxType hitboxType;
    public Color hitboxColor = Color.BLUE;

    public enum HitboxType {
        CIRCLE, RECTANGLE
    }

    // GETTERS
    public Vector2D getPosition() { return position; }
    public double getPositionX() { return position.getX(); }
    public double getPositionY() { return position.getY(); }

    public Dimension getSize() { return size; }

    public String getActiveSpriteSet() { return this.activeSpriteSet; }

    public HitboxType getHitboxType() { return this.hitboxType; }
    public Rectangle getHitboxRectangle() { return new Rectangle((int)position.getX(), (int)position.getY(), (int)size.getWidth(), (int)size.getHeight()); }
    public Circle2D getHitboxCircle() { return new Circle2D((position.getX() + size.width/2), (position.getY() + size.height/2), size.width/2); }

    // SETTERS
    public void setPosition(Vector2D position) { this.position = position; }
    public void setPosition(double x, double y) { position.setVector(x, y); }
    public void setPositionX(double x) { position.setVector(x, getPositionY()); }
    public void setPositionY(double y) { position.setVector(getPositionX(), y); }

    public void setSize(Dimension size) { this.size = size; }

    public void setActiveSpriteSet(String name) {
        if(spriteSets.containsKey(name)) {
            this.activeSpriteSet = name;
            this.spriteSets.get(name).startAnimation();
        }
    }

    public void setHitboxType(HitboxType hitboxType) { this.hitboxType = hitboxType; }

    public Entity(Game game) {
        this.game = game;
        this.spriteSets = new HashMap<>();
        this.position = new Vector2D();
        this.position.setVector(0,0);
        this.size = new Dimension(0,0);
        this.activeSpriteSet = null;
        this.hitboxType = HitboxType.RECTANGLE;
    }

    public void tick() {
        this.updateSpriteSet();
    }

    public void Draw(Graphics2D canvas) {
        //if(spriteSets.containsKey(activeSpriteSet)) {
        //    canvas.drawImage(spriteSets.get(activeSpriteSet).getSprite(), (int)getPositionX(), (int)getPositionY(), game.getGraphicsHandler());
        //} else {
            canvas.setColor(hitboxColor);
            switch(hitboxType) {
                case RECTANGLE:
                    canvas.fillRect((int)getPositionX(), (int)getPositionY(), (int)getSize().getWidth(), (int)getSize().getHeight());
                    break;
                case CIRCLE:
                    canvas.fillOval((int)getPositionX(), (int)getPositionY(), (int)getSize().getWidth(), (int)getSize().getWidth());
                    break;
            }
        //}
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

    public boolean hasCollision(Entity entityB) {
        Entity entityA = this;

        switch(entityA.hitboxType) {
            case RECTANGLE:
                switch(entityB.hitboxType) {
                    case RECTANGLE:
                        return entityA.getHitboxRectangle().intersects(entityB.getHitboxRectangle());
                    case CIRCLE:
                        return false;
                }
                return false;
            case CIRCLE:
                switch(entityB.hitboxType) {
                    case RECTANGLE:
                        return false;
                    case CIRCLE:
                        return entityA.getHitboxCircle().intersects(entityB.getHitboxCircle());
                }
                return false;
        }
        return false;
    }
}
