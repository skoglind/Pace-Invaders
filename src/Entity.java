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

    // GETTERS
    public Vector2D getPosition() { return position; }
    public double getPositionX() { return position.getX(); }
    public double getPositionY() { return position.getY(); }

    public Dimension getSize() { return size; }

    public String getActiveSpriteSet() { return this.activeSpriteSet; }

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

    public Entity(Game game) {
        this.game = game;
        this.spriteSets = new HashMap<>();
        this.position = new Vector2D();
        this.position.setVector(0,0);
        this.size = new Dimension(0,0);
        this.activeSpriteSet = null;
    }

    public void tick() {
        this.updateSpriteSet();
    }

    public void Draw(Graphics2D canvas) {
        if(spriteSets.containsKey(activeSpriteSet)) {
            canvas.drawImage(spriteSets.get(activeSpriteSet).getSprite(), (int)getPositionX(), (int)getPositionY(), game.getGraphicsHandler());
        } else {
            canvas.setColor(Color.BLUE);
            canvas.fillRect((int)getPositionX(), (int)getPositionY(), (int)getSize().getWidth(), (int)getSize().getHeight());
        }
    }

    private void updateSpriteSet() {
        if(spriteSets.containsKey(activeSpriteSet)) {
            spriteSets.get(activeSpriteSet).tick();
        }
    }

    public void addSpriteSet(SpriteSheet spritesheet, String name, int width, int height, int count) {
        this.addSpriteSet(spritesheet, name, width, height, count, true, SpriteSet.INFINITE, 0.0);
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
