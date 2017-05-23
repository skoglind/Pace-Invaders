import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Player Class
 * @author Fredrik Skoglind
 */
public class Player extends MovingEntity {
    private int keyUp = KeyEvent.VK_UP;
    private int keyDown = KeyEvent.VK_DOWN;
    private int keyLeft = KeyEvent.VK_LEFT;
    private int keyRight = KeyEvent.VK_RIGHT;
    private int keyFire = KeyEvent.VK_SPACE;

    public Player(Game game) {
        super(game);

        setSize(new Dimension(20,20));
        setFriction(0.75);
        setMaxVelocity(new Vector2D(15,15));
        setEntityType(Entity.EntityType.PLAYER);
        setHitboxType(Entity.HitboxType.RECTANGLE);

        /*addSpriteSet(game.getSpriteSheet("player_green"), "green", 20, 20, 5, 2);
        addSpriteSet(game.getSpriteSheet("player_red"), "red", 20, 20, 5, 1);
        addSpriteSet(game.getSpriteSheet("player_violet"), "violet", 20, 20, 5, 1);
        addSpriteSet(game.getSpriteSheet("player_white"), "white", 20, 20, 5);
        setActiveSpriteSet("white");*/
    }

    public Rectangle getHitboxX() {
        return new Rectangle((int)getPositionX(), (int)(getPositionY() + getMaxVelocityY()/2), (int)getSize().getWidth(), (int)(getSize().getHeight() - getMaxVelocityY()));
    }

    public Rectangle getHitboxY() {
        return new Rectangle((int)(getPositionX() + getMaxVelocityX()/2), (int)getPositionY(), (int)(getSize().getWidth() - getMaxVelocityX()), (int)getSize().getHeight());
    }

    public void Draw(Graphics2D canvas) {
        super.Draw(canvas);

        canvas.setColor(Color.ORANGE);
        canvas.drawRect((int)getHitboxX().getX(), (int)getHitboxX().getY(), (int)getHitboxX().getWidth(), (int)getHitboxX().getHeight());

        canvas.setColor(Color.ORANGE);
        canvas.drawRect((int)getHitboxY().getX(), (int)getHitboxY().getY(), (int)getHitboxY().getWidth(), (int)getHitboxY().getHeight());
    }

    public void tick() {
        super.tick();
        this.updateInput();
    }

    private void updateInput() {
        KeyInputHandler keyInput = game.getKeyInputHandler();
        if(keyInput.isKeyDown(keyLeft)) { this.accelerateX(-2.5); }
        if(keyInput.isKeyDown(keyRight)) { this.accelerateX(2.5); }
        if(keyInput.isKeyDown(keyUp)) { this.accelerateY(-2.5); }
        if(keyInput.isKeyDown(keyDown)) { this.accelerateY(2.5); }

        AudioHandler audio = game.getAudioHandler();
        if(keyInput.isKeyDownAndRelease(keyFire)) {
            audio.playClip(game.getSFX("shoot"));
        }
    }

    public void updateMovement(ArrayList<Entity> tiles) {
        super.updateMovement();
        double newPositionX = getPositionX();
        double newPositionY = getPositionY();

        for(Entity tile: tiles) {
            tile.hitboxColor = Color.BLUE;
        }

        // X - Collision check
        for(Entity tile: tiles) {
            Rectangle hitbox = this.getHitboxX();

            if(hitbox.intersects(tile.getHitboxRectangle())) {
                double thisCenterX = getPositionX() + (getSize().getWidth() / 2);
                double tileCenterX = tile.getPositionX() + (tile.getSize().getWidth() / 2);
                double deltaX = thisCenterX - tileCenterX;

                if (deltaX < 0) {
                    newPositionX = tile.getPositionX() - getSize().getWidth();
                } else {
                    newPositionX = tile.getPositionX() + tile.getSize().getWidth() + 1;
                }

                setCurrentVelocityX(0);
                tile.hitboxColor = Color.YELLOW;
            }
        }

        // Y - Collision check
        for(Entity tile: tiles) {
            Rectangle hitbox = this.getHitboxY();

            if(hitbox.intersects(tile.getHitboxRectangle())) {
                double thisCenterY = getPositionY() + (getSize().getHeight() / 2);
                double tileCenterY = tile.getPositionY() + (tile.getSize().getHeight() / 2);
                double deltaY = thisCenterY - tileCenterY;

                if (deltaY < 0) {
                    newPositionY = tile.getPositionY() - getSize().getHeight();
                } else {
                    newPositionY = tile.getPositionY() + tile.getSize().getHeight() + 1;
                }

                setCurrentVelocityY(0);
                tile.hitboxColor = Color.YELLOW;
            }
        }

        setPositionX(newPositionX);
        setPositionY(newPositionY);
    }
}
