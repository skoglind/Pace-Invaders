import javafx.scene.media.AudioClip;

import java.awt.*;
import java.util.ArrayList;

/**
 * Level class
 * @author Fredrik Skoglind
 */
public class Level {
    private Controller controller;

    private String filename;
    private AudioClip backgroundMusic;

    private ArrayList<Tile> tiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Shot> enemyShots;
    private ArrayList<Shot> playerShots;

    private Rectangle playerBoundaries;

    // GETTERS
    public ArrayList<Tile> getTiles() { return tiles; }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Shot> getEnemyShots() { return enemyShots; }
    public ArrayList<Shot> getPlayerShots() { return playerShots; }

    public Rectangle getPlayerBoundaries() { return playerBoundaries; }

    public Level(Controller controller, String filename) {
        this.controller = controller;
    }

    public void tick() {
        int tileSize = 20;
        tiles.add(new Tile(controller.game, new Vector2D(0, -tileSize), new Dimension(tileSize, tileSize)));
        tiles.add(new Tile(controller.game, new Vector2D(controller.game.SCREEN_WIDTH - tileSize, -tileSize), new Dimension(tileSize, tileSize)));

        if(tiles != null) { for(Tile tile: tiles) { tile.tick(); } }
        if(enemies != null) { for(Enemy enemy: enemies) { enemy.tick(); enemy.updateMovement(); } }
        if(enemyShots != null) { for(Shot enemyShot: enemyShots) { enemyShot.tick(); enemyShot.updateMovement(); } }
        if(playerShots != null) { for(Shot playerShot: playerShots) { playerShot.tick(); playerShot.updateMovement(); } }
    }

    public void init() {
        playerBoundaries = new Rectangle(0, 0, controller.game.SCREEN_WIDTH, controller.game.SCREEN_HEIGHT);

        tiles = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyShots = new ArrayList<>();
        playerShots = new ArrayList<>();

        backgroundMusic = controller.audio.playClip(controller.game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void dispose() {
        tiles = null;
        enemies = null;
        enemyShots = null;

        if(backgroundMusic.isPlaying()) { backgroundMusic.stop(); }
        backgroundMusic = null;
    }

    public void Draw(Graphics2D canvas) {
        if(tiles != null) { for(Tile tile: tiles) { tile.Draw(canvas); } }
        if(tiles != null) { for(Enemy enemy: enemies) { enemy.Draw(canvas); } }
        if(enemyShots != null) { for(Shot enemyShot: enemyShots) { enemyShot.Draw(canvas); } }
        if(playerShots != null) { for(Shot playerShot: playerShots) { playerShot.Draw(canvas); } }
    }
}
