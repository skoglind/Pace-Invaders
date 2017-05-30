import javafx.scene.media.AudioClip;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Level class
 * @author Fredrik Skoglind
 */
public class Level {
    private Game game;

    private String filename;
    private AudioClip backgroundMusic;

    private ArrayList<Tile> tiles;
    private ArrayList<Enemy> enemies;
    private ArrayList<Shot> enemyShots;
    private ArrayList<Shot> playerShots;

    private Rectangle playerBoundaries;

    private long onTick = 0;
    private boolean onTickRoll = true;
    private int tickRollTime = 300;

    // GETTERS
    public ArrayList<Tile> getTiles() { return tiles; }
    public ArrayList<Enemy> getEnemies() { return enemies; }
    public ArrayList<Shot> getEnemyShots() { return enemyShots; }
    public ArrayList<Shot> getPlayerShots() { return playerShots; }

    public Rectangle getPlayerBoundaries() { return playerBoundaries; }

    public Level(Game game, String filename) {
        this.game = game;
        this.filename = filename;
        loadLevelData();
    }

    private void loadLevelData() {
        // --
    }

    public void tick() {
        if(onTick >= tickRollTime) { onTickRoll = false; }

        doLevelAction();
        gcLevel();
        onTick++;

        if(tiles != null) { for(Tile tile: tiles) { tile.tick(); } }
        if(enemies != null) { for(Enemy enemy: enemies) { enemy.tick(); enemy.updateMovement(); } }
        if(enemyShots != null) { for(Shot enemyShot: enemyShots) { enemyShot.tick(); enemyShot.updateMovement(); } }
        if(playerShots != null) { for(Shot playerShot: playerShots) { playerShot.tick(); playerShot.updateMovement(); } }
    }

    public void doLevelAction() {
        int tileSize = 40;
        int speedY = 2;

        if(onTick % (tileSize/speedY) == 0) {
            tiles.add(new Tile(game, new Vector2D(0, -tileSize), new Dimension(tileSize, tileSize), true, new Vector2D(0,speedY)));
            tiles.add(new Tile(game, new Vector2D(game.SCREEN_WIDTH - tileSize, -tileSize), new Dimension(tileSize, tileSize), true, new Vector2D(0,speedY)));
        }

        if(onTick % 60 == 0) {
            long actionTick = onTick / 60;

            // Do action, actionTick
        }
    }

    public void gcLevel() {
        // TILES
        Iterator<Tile> itTiles = tiles.iterator();
        while(itTiles.hasNext()) {
            Tile tile = itTiles.next();
            if(!tile.isVisibleOnScreenOffset()) { itTiles.remove(); }
        }

        // ENEMIES
        Iterator<Enemy> itEnemies = enemies.iterator();
        while(itEnemies.hasNext()) {
            Enemy enemy = itEnemies.next();
            if(!enemy.isVisibleOnScreenOffset()) { itEnemies.remove(); }
        }

        // ENEMYSHOTS
        Iterator<Shot> itEnemyShots = enemyShots.iterator();
        while(itEnemyShots.hasNext()) {
            Shot shot = itEnemyShots.next();
            if(!shot.isVisibleOnScreenOffset()) { itEnemyShots.remove(); }
        }

        // PLAYERSHOTS
        Iterator<Shot> itPlayerShots = playerShots.iterator();
        while(itPlayerShots.hasNext()) {
            Shot shot = itPlayerShots.next();
            if(!shot.isVisibleOnScreenOffset()) { itPlayerShots.remove(); }
        }
    }

    public void init() {
        playerBoundaries = new Rectangle(0, 0, game.SCREEN_WIDTH, game.SCREEN_HEIGHT);

        tiles = new ArrayList<>();
        enemies = new ArrayList<>();
        enemyShots = new ArrayList<>();
        playerShots = new ArrayList<>();

        while(onTickRoll) { tick(); }

        //backgroundMusic = controller.audio.playClip(controller.game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void dispose() {
        onTick = 0;
        onTickRoll = true;

        tiles = null;
        enemies = null;
        enemyShots = null;
        playerShots = null;

        if(backgroundMusic.isPlaying()) { backgroundMusic.stop(); }
        backgroundMusic = null;
    }

    public void Draw(Graphics2D canvas) {
        if(tiles != null) { for(Tile tile: tiles) { tile.Draw(canvas); } }
        if(enemies != null) { for(Enemy enemy: enemies) { enemy.Draw(canvas); } }
        if(enemyShots != null) { for(Shot enemyShot: enemyShots) { enemyShot.Draw(canvas); } }
        if(playerShots != null) { for(Shot playerShot: playerShots) { playerShot.Draw(canvas); } }
    }
}
