import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    // ENTITIES
    private Player player;
    private ArrayList<Level> levels;

    // STATUS
    private Level activeLevel;
    private int levelID;

    public GameController(Game game) {
        super(game);
    }

    public void dispose() {
        activeLevel.dispose();
        levels = null;
        player = null;
    }

    public void init() {
        levels = new ArrayList<>();

        player = new Player(game);
        player.setPosition(new Vector2D(game.SCREEN_WIDTH/2 - player.getSize().getWidth()/2, game.SCREEN_HEIGHT - player.getSize().getHeight()));
        levelID = 0;

        Level level1 = new Level(this, "level1.lvl");
        levels.add(level1);

        activeLevel = levels.get(levelID);
        activeLevel.init();
    }

    public void tick() {
        super.tick();
        player.tick();
        activeLevel.tick();

        game.setDevData("Enemies", activeLevel.getEnemies().size());
        game.setDevData("Tiles", activeLevel.getTiles().size());
        game.setDevData("Enemy Shots", activeLevel.getEnemyShots().size());
        game.setDevData("Player Shots", activeLevel.getPlayerShots().size());
    }

    public void update(double delta) {
        super.update(delta);

        player.updateMovement(activeLevel.getPlayerBoundaries(), activeLevel.getTiles());

        // Calculate data
        /*int numTiles = 0;
        if(activeLevel.getTiles() != null) { numTiles += activeLevel.getTiles().size(); }
        game.setNumTiles(numTiles);

        int numEnemies = 0;
        if(activeLevel.getEnemies() != null) { numEnemies += activeLevel.getEnemies().size(); }
        game.setNumEnemies(numEnemies);

        int numEnemyShots = 0;
        if(activeLevel.getEnemyShots() != null) { numEnemyShots += activeLevel.getEnemyShots().size(); }
        game.setNumEnemyShots(numEnemyShots);*/
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        activeLevel.Draw(canvas);
        player.Draw(canvas);

        game.drawDevData(canvas);
        graphics.renderCanvas();
    }
}
