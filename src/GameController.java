import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;

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
        testEntity = new MovingEntity(game);
        testEntity.setPosition(new Vector2D(50,50));
        testEntity.setSize(new Dimension(20,20));
        testEntity.setFriction(0.75);
        testEntity.setMaxVelocity(new Vector2D(12,12));
        testEntity.addSpriteSet(new SpriteSheet("media/spritesheet/green_anim.png"), "green", 20, 20, 5, 2);
        testEntity.addSpriteSet(new SpriteSheet("media/spritesheet/red_anim.png"), "red", 20, 20, 5, 1);
        testEntity.addSpriteSet(new SpriteSheet("media/spritesheet/violet_anim.png"), "violet", 20, 20, 5, 1);
        testEntity.addSpriteSet(new SpriteSheet("media/spritesheet/white_anim.png"), "white", 20, 20, 5);
        testEntity.setActiveSpriteSet("white");

        backgroundMusic = audio.playClip(game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void tick() {
        super.tick();
        testEntity.tick();
    }

    private int x;
    private int y;
    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("MENU");
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_E)) {
            testEntity.setActiveSpriteSet("green");
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_R)) {
            testEntity.setActiveSpriteSet("red");
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_T)) {
            testEntity.setActiveSpriteSet("violet");
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_Y)) {
            testEntity.setActiveSpriteSet("white");
        }

        if(keyInput.isKeyDown(KeyEvent.VK_LEFT)) { testEntity.accelerateX(-2.0); }
        if(keyInput.isKeyDown(KeyEvent.VK_RIGHT)) { testEntity.accelerateX(2.0); }
        if(keyInput.isKeyDown(KeyEvent.VK_UP)) { testEntity.accelerateY(-2.0); }
        if(keyInput.isKeyDown(KeyEvent.VK_DOWN)) { testEntity.accelerateY(2.0); }

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
