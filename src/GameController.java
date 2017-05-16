import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    public GameController(Game game) {
        super(game);
    }

    public void tick() {
        super.tick();
    }

    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("MENU");
        }
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        graphics.renderCanvas();
    }
}
