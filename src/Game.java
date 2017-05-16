import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * GAME
 * @author Fredrik Skoglind
 */
public class Game {
    // CONSTANTS
    public static final String GAME_TITLE = "Pace Invaders";
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // HANDLERS
    private GraphicsHandler graphics;
    private AudioHandler audio;
    private KeyInputHandler keyInput;
    private MouseInputHandler mouseInput;

    // GETTERS
    public GraphicsHandler getGraphicsHandler() {  return graphics; }
    public AudioHandler getAudioHandler() {  return audio; }
    public KeyInputHandler getKeyInputHandler() {  return keyInput; }
    public MouseInputHandler getMouseInputHandler() {  return mouseInput; }

    /**
     * Main-method
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.init();
    }

    /**
     * Initialize game
     */
    public void init() {
        // HANDLERS
        graphics = new GraphicsHandler();
        keyInput = new KeyInputHandler(graphics);
        mouseInput = new MouseInputHandler(graphics);
        audio = new AudioHandler("sound/");

        this.run();
    }

    /**
     * Run game
     */
    private void run() {
        while(true) {
            double startTime = System.nanoTime();
            this.update(1.0);
            this.render();
            try { Thread.sleep(100); } catch (Exception e) {}
        }
    }

    /**
     * Tick method, all tickers
     */
    private void tick() {
        keyInput.tick();
        mouseInput.tick();
    }

    /**
     * Update method (game logics)
     */
    public void update(double delta) {
        this.tick();

        //System.out.println( keyInput.isKeyDown(KeyEvent.VK_ESCAPE) + "=" +
        //        keyInput.getKeyPressTime(KeyEvent.VK_ESCAPE) + "=" +
        //        keyInput.getKeyPressCount(KeyEvent.VK_ESCAPE) );

        //System.out.println( mouseInput.isButtonDown(MouseEvent.BUTTON1) + "=" +
        //        mouseInput.getButtonPressTime(MouseEvent.BUTTON1) + "=" +
        //        mouseInput.getButtonPressCount(MouseEvent.BUTTON1) );

        //System.out.println(mouseInput.getMousePosition().toString() + "=" + mouseInput.getMouseOnScreen());
    }

    /**
     * Render method (game graphics)
     */
    public void render() {
        Graphics2D canvas = graphics.getCanvas();
        graphics.renderCanvas();
    }
}