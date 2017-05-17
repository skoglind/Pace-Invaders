import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    SpriteSet spriteSet;

    public GameController(Game game) {
        super(game);
    }

    public void init() {
        SpriteSheet ss = new SpriteSheet("media/animation.png");

        BufferedImage bi1, bi2, bi3, bi4, bi5;
        bi1 = ss.getSprite(0, 0, 20, 20);
        bi2 = ss.getSprite(20, 0, 20, 20);
        bi3 = ss.getSprite(40, 0, 20, 20);
        bi4 = ss.getSprite(60, 0, 20, 20);
        bi5 = ss.getSprite(80, 0, 20, 20);

        spriteSet = new SpriteSet(bi1, bi2, bi3, bi4, bi5);
    }

    public void tick() {
        super.tick();
        spriteSet.tick();
    }

    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("MENU");
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_ENTER)) {
            spriteSet.startAnimation();
        }
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        for(int x = 0; x < Game.SCREEN_WIDTH; x += 20) {
            for(int y = 0; y < Game.SCREEN_HEIGHT; y +=20) {
                canvas.drawImage(spriteSet.getSprite(), x, y, graphics);
            }
        }

        graphics.renderCanvas();
    }
}
