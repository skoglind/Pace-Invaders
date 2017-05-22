import java.awt.*;
import java.awt.event.KeyEvent;

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

        addSpriteSet(game.getSpriteSheet("player_green"), "green", 20, 20, 5, 2);
        addSpriteSet(game.getSpriteSheet("player_red"), "red", 20, 20, 5, 1);
        addSpriteSet(game.getSpriteSheet("player_violet"), "violet", 20, 20, 5, 1);
        addSpriteSet(game.getSpriteSheet("player_white"), "white", 20, 20, 5);
        setActiveSpriteSet("white");
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
}
