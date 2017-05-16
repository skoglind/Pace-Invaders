import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Sprite Class
 * - Single sprites
 * @author Fredrik Skoglind
 */
public class Sprite {
    private BufferedImage[] frames;
    private int activeFrame;

    private int animationSpeed;
    private int numTicks;

    public Sprite(BufferedImage... frames) {
        this.frames = frames;
        this.activeFrame = 0;
        this.animationSpeed = 6;
    }

    public void tick() {
        this.numTicks++;
        if(this.numTicks > 10000) { this.numTicks = 0; }

        if(this.numTicks % this.animationSpeed == 0) {
            this.nextFrame();
        }
    }

    public void nextFrame() {
        this.activeFrame++;
        if(this.activeFrame > (frames.length-1)) { activeFrame = 0; }
    }

    public BufferedImage getSprite() {
        return frames[this.activeFrame];
    }
}
