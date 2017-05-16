import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * MenuController
 * @author Fredrik Skoglind
 */
public class MenuController extends Controller {
    public MenuController(Game game) {
        super(game);
    }

    public void tick() {
        super.tick();
    }

    public void update(double delta) {
        super.update(delta);
        //System.out.println("CONTROLLER (MENU)");

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("GAME");
        }
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        graphics.renderCanvas();
    }
}
