import javafx.scene.media.AudioClip;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * GameController
 * @author Fredrik Skoglind
 */
public class GameController extends Controller {
    SpriteSet spriteSet;
    MovingEntity testEntityA;
    MovingEntity testEntityB;

    AudioClip backgroundMusic;

    public GameController(Game game) {
        super(game);
    }

    public void dispose() {
        if(backgroundMusic.isPlaying()) { backgroundMusic.stop(); }
        backgroundMusic = null;
    }

    public void init() {
        testEntityA = new MovingEntity(game);
        testEntityA.setPosition(new Vector2D(50,50));
        testEntityA.setSize(new Dimension(40,40));
        testEntityA.setFriction(0.75);
        testEntityA.setMaxVelocity(new Vector2D(12,12));
        testEntityA.addSpriteSet(new SpriteSheet("media/spritesheet/green_anim.png"), "green", 20, 20, 5, 2);
        testEntityA.addSpriteSet(new SpriteSheet("media/spritesheet/red_anim.png"), "red", 20, 20, 5, 1);
        testEntityA.addSpriteSet(new SpriteSheet("media/spritesheet/violet_anim.png"), "violet", 20, 20, 5, 1);
        testEntityA.addSpriteSet(new SpriteSheet("media/spritesheet/white_anim.png"), "white", 20, 20, 5);
        testEntityA.setActiveSpriteSet("white");
        testEntityA.setHitboxType(Entity.HitboxType.CIRCLE);

        testEntityB = new MovingEntity(game);
        testEntityB.setPosition(new Vector2D(100,100));
        testEntityB.setSize(new Dimension(40,40));
        testEntityB.setFriction(0.75);
        testEntityB.setMaxVelocity(new Vector2D(12,12));
        testEntityB.setHitboxType(Entity.HitboxType.CIRCLE);

        backgroundMusic = audio.playClip(game.getMusic("lasers_amsterdam"), 0.5, 0.0, AudioClip.INDEFINITE);
    }

    public void tick() {
        super.tick();
        testEntityA.tick();
        testEntityB.tick();
    }

    private int x;
    private int y;
    public void update(double delta) {
        super.update(delta);

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_SPACE)) {
            game.setController("MENU");
        }

        if(keyInput.isKeyDown(KeyEvent.VK_LEFT)) { testEntityA.accelerateX(-2.0); }
        if(keyInput.isKeyDown(KeyEvent.VK_RIGHT)) { testEntityA.accelerateX(2.0); }
        if(keyInput.isKeyDown(KeyEvent.VK_UP)) { testEntityA.accelerateY(-2.0); }
        if(keyInput.isKeyDown(KeyEvent.VK_DOWN)) { testEntityA.accelerateY(2.0); }

        if(testEntityA.hasCollision(testEntityB)) {
            testEntityA.hitboxColor = Color.YELLOW;
            testEntityB.hitboxColor = Color.YELLOW;
        } else {
            testEntityA.hitboxColor = Color.BLUE;
            testEntityB.hitboxColor = Color.BLUE;
        }

        if(keyInput.isKeyDownAndRelease(KeyEvent.VK_L)) {
            audio.playClip(game.getSFX("shoot"));
        }
    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas(Game.BACKGROUND_COLOR);

        if(x > 0 && x < game.SCREEN_WIDTH && y > 0 && y < game.SCREEN_HEIGHT) {
            canvas.drawImage(spriteSet.getSprite(), x, y, graphics);
        }

        testEntityA.Draw(canvas);
        testEntityB.Draw(canvas);

        game.drawDevData(canvas);
        graphics.renderCanvas();
    }
}
