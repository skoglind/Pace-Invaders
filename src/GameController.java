import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    SpriteSet spriteSet;
    MovingEntity testEntity;

    AudioClip backgroundMusic;

    public GameController(Game game) {
        super(game);
    }

    public void dispose() {
        if(backgroundMusic.isPlaying()) { backgroundMusic.stop(); }
        backgroundMusic = null;
    }

    public void init() {
        SpriteSheet ss = new SpriteSheet("media/spritesheet/animation.png");

        BufferedImage bi1, bi2, bi3, bi4, bi5;
        bi1 = ss.getSprite(0, 0, 20, 20);
        bi2 = ss.getSprite(20, 0, 20, 20);
        bi3 = ss.getSprite(40, 0, 20, 20);
        bi4 = ss.getSprite(60, 0, 20, 20);
        bi5 = ss.getSprite(80, 0, 20, 20);

        spriteSet = new SpriteSet(bi1, bi2, bi3, bi4, bi5);

        testEntity = new MovingEntity();
        testEntity.setPosition(new Vector2D(50,50));
        testEntity.setSize(new Dimension(20,20));

        backgroundMusic = audio.playClip(game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void tick() {
        super.tick();
        spriteSet.tick();
        testEntity.tick();
    }

    private int x;
    private int y;
    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("MENU");
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_ENTER)) {
            spriteSet.startAnimation();
        }

        if(keyInput.isKeyDown(KeyEvent.VK_LEFT)) { testEntity.setAccelerationX( testEntity.getAccelerationX()-0.01 ); }
        if(keyInput.isKeyDown(KeyEvent.VK_RIGHT)) { testEntity.setAccelerationX( testEntity.getAccelerationX()+0.01 ); }
        if(keyInput.isKeyDown(KeyEvent.VK_UP)) { testEntity.setAccelerationY( testEntity.getAccelerationY()-0.01 ); }
        if(keyInput.isKeyDown(KeyEvent.VK_DOWN)) { testEntity.setAccelerationY( testEntity.getAccelerationY()+0.01 ); }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_L)) {
            audio.playClip(game.getSFX("shoot"));
        }
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        if(x > 0 && x < game.SCREEN_WIDTH && y > 0 && y < game.SCREEN_HEIGHT) {
            canvas.drawImage(spriteSet.getSprite(), x, y, graphics);
        }

        testEntity.Draw(canvas);

        game.drawDevData(canvas);
        graphics.renderCanvas();
    }
}
