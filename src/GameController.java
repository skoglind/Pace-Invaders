import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    // ENTITIES
    Player player;
    Entity entity;

    // MUSIC & SFX
    AudioClip backgroundMusic;

    public GameController(Game game) {
        super(game);
    }

    public void dispose() {
        if(backgroundMusic.isPlaying()) { backgroundMusic.stop(); }
        backgroundMusic = null;
    }

    public void init() {
        player = new Player(game);
        player.setPosition(new Vector2D(50, 50));

        entity = new MovingEntity(game);
        entity.setPosition(new Vector2D(100,100));
        entity.setSize(new Dimension(80,80));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);

        backgroundMusic = audio.playClip(game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void tick() {
        super.tick();
        player.tick();
        entity.tick();
    }

    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_ENTER)) {
            game.setController("MENU");
        }

        if(player.hasCollision(entity)) {
            player.hitboxColor = Color.YELLOW;
            entity.hitboxColor = Color.YELLOW;
        } else {
            player.hitboxColor = Color.BLUE;
            entity.hitboxColor = Color.BLUE;
        }
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        player.Draw(canvas);
        entity.Draw(canvas);

        game.drawDevData(canvas);
        graphics.renderCanvas();
    }
}
