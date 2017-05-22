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

        int tileSize = 5;
        tiles = new ArrayList<>();
        for(int x = 0; x < game.SCREEN_WIDTH/tileSize; x++) {
            for(int y = 0; y < game.SCREEN_HEIGHT/tileSize; y++) {
                Entity entity = new Entity(game);
                entity.setPosition(new Vector2D(x * (tileSize+1), y * (tileSize+1)));
                entity.setSize(new Dimension(tileSize, tileSize));
                entity.setEntityType(Entity.EntityType.OPAQUE);
                entity.setHitboxType(Entity.HitboxType.RECTANGLE);

                tiles.add(entity);
            }
        }

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

        Iterator<Entity> itTiles = tiles.iterator();
        while(itTiles.hasNext()) {
            Entity tile = itTiles.next();
            if (player.hasCollision(tile)) {
                player.stopMovement(MovingEntity.MovementDirection.DOWN);
                //itTiles.remove();
            }
        }

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
