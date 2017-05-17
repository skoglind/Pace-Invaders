import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * SpriteSet Class
 * - Single sprites
 * @author Fredrik Skoglind
 */
public class SpriteSet {
    public static final int INFINITE = -1;
    private BufferedImage[] frameSet;
    private int activeFrame;
    private boolean animate;

    private double animationSpeed;
    private int defaultAnimationSpeed = 11;

    private int loopCount;
    private int onLoop = 0;

    private int numTicks;

    public SpriteSet(BufferedImage... frameSet) {
        this(true, INFINITE, 0.0, frameSet);
    }

    public SpriteSet(boolean startAnimation, int loopCount, double animationSpeed, BufferedImage... frameSet) {
        this.frameSet = frameSet;
        this.activeFrame = 0;
        this.loopCount = loopCount;

        this.animationSpeed = animationSpeed;
        if(this.animationSpeed < -1.0) { this.animationSpeed = -1.0; }
        if(this.animationSpeed > 1.0) { this.animationSpeed = 1.0; }

        if(startAnimation) { this.animate = true; }
    }

    public void tick() {
        int tickModulo = (int)((-10 * animationSpeed)) + defaultAnimationSpeed;
        numTicks++;

        if(numTicks % tickModulo == 0) {
            nextFrame();
        }
    }

    public void setAnimationSpeed(double animationSpeed) {
        this.animationSpeed = animationSpeed;
        if(this.animationSpeed < -1.0) { this.animationSpeed = -1.0; }
        if(this.animationSpeed > 1.0) { this.animationSpeed = 1.0; }
    }

    public void setLoopCount(int loopCount) {
        this.loopCount = loopCount;
    }

    private void nextFrame() {
        if(animate) {
            if (loopCount != INFINITE && onLoop >= loopCount) {
                stopAnimation();
                return;
            }

            activeFrame++;
            if (activeFrame > (frameSet.length - 1)) {
                onLoop++;
                activeFrame = 0;
            }
        }
    }

    public void startAnimation() {
        animate = true;
        activeFrame = 0;
        onLoop = 0;
    }

    public void stopAnimation() {
        animate = false;
        activeFrame = 0;
    }

    public BufferedImage getSprite() {
        return frameSet[activeFrame];
    }
}
