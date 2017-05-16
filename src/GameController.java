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
        System.out.println("CONTROLLER (GAME)");

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("MENU");
        }
    }

    public void render() {
        super.render();
        Graphics2D canvas = graphics.getCanvas();

        // Draw
        canvas.setColor(Color.YELLOW);
        canvas.fillRect(10,10,100,100);

        graphics.renderCanvas();
    }
}
