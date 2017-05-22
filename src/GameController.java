import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    // ENTITIES
    Player player;
    ArrayList<Entity> tiles;

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

        tiles = new ArrayList<>();
        Entity entity = new Entity(game);
        entity.setPosition(new Vector2D(100, 100));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        entity = new Entity(game);
        entity.setPosition(new Vector2D(150, 100));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        entity = new Entity(game);
        entity.setPosition(new Vector2D(100, 150));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        entity = new Entity(game);
        entity.setPosition(new Vector2D(150, 150));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        entity = new Entity(game);
        entity.setPosition(new Vector2D(200, 150));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        entity = new Entity(game);
        entity.setPosition(new Vector2D(250, 150));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        entity = new Entity(game);
        entity.setPosition(new Vector2D(300, 150));
        entity.setSize(new Dimension(50, 50));
        entity.setEntityType(Entity.EntityType.OPAQUE);
        entity.setHitboxType(Entity.HitboxType.RECTANGLE);
        tiles.add(entity);

        backgroundMusic = audio.playClip(game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void tick() {
        super.tick();
        player.tick();
        for(Entity tile: tiles) {
            tile.tick();
        }
    }

    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_ENTER)) {
            game.setController("MENU");
        }

        player.updateMovement(tiles);

        // Calculate entities
        int numEntities = 1 + tiles.size();
        game.setNumEntities(numEntities);
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        for(Entity tile: tiles) {
            tile.Draw(canvas);
        }
        player.Draw(canvas);

        game.drawDevData(canvas);
        graphics.renderCanvas();
    }
}
