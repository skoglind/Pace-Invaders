import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Player Class
 * @author Fredrik Skoglind
 */
public class Player extends MovingEntity {
    private static final double ACCELERATE_SPEED = 0.4;
    private static final double MAX_SPEED = 14.0;
    private static final double FRICTION = 0.85;
    private static final Dimension HITBOXSIZE = new Dimension(40,40);

    private int keyUp = KeyEvent.VK_UP;
    private int keyDown = KeyEvent.VK_DOWN;
    private int keyLeft = KeyEvent.VK_LEFT;
    private int keyRight = KeyEvent.VK_RIGHT;
    private int keyFire = KeyEvent.VK_SPACE;

    public Player(Game game) {
        super(game);

        setSize(HITBOXSIZE);
        setFriction(FRICTION);
        setMaxVelocity(new Vector2D(MAX_SPEED,MAX_SPEED));
        setEntityType(Entity.EntityType.PLAYER);

        /*addSpriteSet(game.getSpriteSheet("player_green"), "green", 20, 20, 5, 2);
        addSpriteSet(game.getSpriteSheet("player_red"), "red", 20, 20, 5, 1);
        addSpriteSet(game.getSpriteSheet("player_violet"), "violet", 20, 20, 5, 1);
        addSpriteSet(game.getSpriteSheet("player_white"), "white", 20, 20, 5);
        setActiveSpriteSet("white");*/
    }

    public Rectangle getHitboxX() {
        int minOffset = (int)ACCELERATE_SPEED + 2;
        int maxOffset = (int)getSize().getHeight() - 2;

        int offset = (int)Math.abs(getCurrentVelocityY() + getAccelerationY());
        if(offset < minOffset) { offset = minOffset; }
        if(offset > maxOffset) { offset = maxOffset; }

        int offsetVel = (int)Math.abs(getCurrentVelocityX() + getAccelerationX());

        int x = (int)getPositionX() - offsetVel;
        int y = (int)getPositionY() + offset;
        int width = (int)getSize().getWidth() + (offsetVel*2);
        int height = (int)getSize().getHeight() - (offset*2);

        if( width < 2 ) { width = 2; x = (int)getPositionX() + (int)getSize().getWidth()/2 - 1; }
        if( height < 2 ) { height = 2; y = (int)getPositionY() + (int)getSize().getHeight()/2 - 1; }

        return new Rectangle(x, y, width, height);
    }

    public Rectangle getHitboxY() {
        int minOffset = (int)ACCELERATE_SPEED + 2;
        int maxOffset = (int)getSize().getWidth() - 2;

        int offset = (int)Math.abs(getCurrentVelocityX() + getAccelerationX());
        if(offset < minOffset) { offset = minOffset; }
        if(offset > maxOffset) { offset = maxOffset; }

        int offsetVel = (int)Math.abs(getCurrentVelocityY() + getAccelerationY());

        int x = (int)getPositionX() + offset;
        int y = (int)getPositionY() - offsetVel;
        int width = (int)getSize().getWidth() - (offset*2);
        int height = (int)getSize().getHeight() + (offsetVel*2);

        if( width < 2 ) { width = 2; x = (int)getPositionX() + (int)getSize().getWidth()/2 - 1; }
        if( height < 2 ) { height = 2; y = (int)getPositionY() + (int)getSize().getHeight()/2 - 1; }

        return new Rectangle(x, y, width, height);
    }

    public void Draw(Graphics2D canvas) {
        super.Draw(canvas);

        canvas.setColor(Color.ORANGE);
        canvas.drawRect((int)getHitboxX().getX(), (int)getHitboxX().getY(),
                (int)getHitboxX().getWidth(), (int)getHitboxX().getHeight());

        canvas.setColor(Color.ORANGE);
        canvas.drawRect((int)getHitboxY().getX(), (int)getHitboxY().getY(),
                (int)getHitboxY().getWidth(), (int)getHitboxY().getHeight());
    }

    public void tick() {
        super.tick();
        this.updateInput();
    }

    private void updateInput() {
        KeyInputHandler keyInput = game.getKeyInputHandler();
        if(keyInput.isKeyDown(keyLeft)) { this.accelerateX(-ACCELERATE_SPEED); }
        if(keyInput.isKeyDown(keyRight)) { this.accelerateX(ACCELERATE_SPEED); }
        if(keyInput.isKeyDown(keyUp)) { this.accelerateY(-ACCELERATE_SPEED); }
        if(keyInput.isKeyDown(keyDown)) { this.accelerateY(ACCELERATE_SPEED); }

        AudioHandler audio = game.getAudioHandler();
        if(keyInput.isKeyDownAndRelease(keyFire)) {
            audio.playClip(game.getSFX("shoot"));
        }
    }

    public void updateMovement(Rectangle boundaries, ArrayList<Tile> tiles) {
        super.updateMovement();

        double newPositionX = getPositionX();
        double newPositionY = getPositionY();

        // X - Boundaries
        if(this.getPositionX() < boundaries.getX()) {
            newPositionX = boundaries.getX();
            setCurrentVelocityX(0);
            setAccelerationX(0);
        } else if(this.getPositionX()+this.getSize().getWidth() > boundaries.getX() + boundaries.getWidth()) {
            newPositionX = boundaries.getX() + boundaries.getWidth() - this.getSize().getWidth();
            setCurrentVelocityX(0);
            setAccelerationX(0);
        }

        // Y - Boundaries
        if(this.getPositionY() < boundaries.getY()) {
            newPositionY = boundaries.getY();
            setCurrentVelocityY(0);
            setAccelerationY(0);
        } else if(this.getPositionY()+this.getSize().getHeight() > boundaries.getY() + boundaries.getHeight()) {
            newPositionY = boundaries.getY() + boundaries.getHeight() - this.getSize().getHeight();
            setCurrentVelocityY(0);
            setAccelerationY(0);
        }

        // Collision check
        if(tiles != null) {
            // X - Collision check
            for (Tile tile : tiles) {
                if(tile.getEntityType() == EntityType.OPAQUE) {
                    Rectangle hitbox = this.getHitboxX();
                    if (hitbox.intersects(tile.getHitbox())) {
                        double thisCenterX = getPositionX() + (getSize().getWidth() / 2);
                        double tileCenterX = tile.getPositionX() + (tile.getSize().getWidth() / 2);
                        double deltaX = thisCenterX - tileCenterX;

                        if (deltaX < 0) {
                            newPositionX = tile.getPositionX() - getSize().getWidth();
                        } else {
                            newPositionX = tile.getPositionX() + tile.getSize().getWidth() + 1;
                        }

                        setCurrentVelocityX(0);
                        setAccelerationX(0);
                    }
                }
            }

            // Y - Collision check
            for (Tile tile : tiles) {
                if(tile.getEntityType() == EntityType.OPAQUE) {
                    Rectangle hitbox = this.getHitboxY();
                    if (hitbox.intersects(tile.getHitbox())) {
                        double thisCenterY = getPositionY() + (getSize().getHeight() / 2);
                        double tileCenterY = tile.getPositionY() + (tile.getSize().getHeight() / 2);
                        double deltaY = thisCenterY - tileCenterY;

                        if (deltaY < 0) {
                            newPositionY = tile.getPositionY() - getSize().getHeight();
                        } else {
                            newPositionY = tile.getPositionY() + tile.getSize().getHeight() + 1;
                        }

                        setCurrentVelocityY(0);
                        setAccelerationY(0);
                    }
                }
            }
        }

        setPositionX(newPositionX);
        setPositionY(newPositionY);

        game.setDevData("Pos X", (int)getPositionX());
        game.setDevData("Pos Y", (int)getPositionY());

        game.setDevData("Vel X", (int)getCurrentVelocityX());
        game.setDevData("Vel Y", (int)getCurrentVelocityY());

        game.setDevData("Acc X", (int)getAccelerationX());
        game.setDevData("Acc Y", (int)getAccelerationY());
    }
}
