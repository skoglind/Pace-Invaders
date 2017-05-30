import java.awt.*;
import java.util.ArrayList;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    // ENTITIES
    private Player player;

    // STATUS
    private Level activeLevel;
    private int levelID;

    public GameController(Game game) {
        super(game);
    }

    public void dispose() {
        activeLevel.dispose();
        player = null;
    }

    public void init() {
        player = new Player(game);
        player.setPosition(new Vector2D(game.SCREEN_WIDTH/2 - player.getSize().getWidth()/2,
                game.SCREEN_HEIGHT - player.getSize().getHeight()));

        levelID = 0;
        activeLevel = game.getLevel(levelID);
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
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        activeLevel.Draw(canvas);
        player.Draw(canvas);

        game.drawDevData(canvas);
        graphics.renderCanvas();
    }
}
