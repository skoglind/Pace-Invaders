import java.awt.*;

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

    // CONTROLLER
    private ControllerManager controllerManager;

    // GETTERS
    public GraphicsHandler getGraphicsHandler() {  return graphics; }
    public AudioHandler getAudioHandler() {  return audio; }
    public KeyInputHandler getKeyInputHandler() {  return keyInput; }
    public MouseInputHandler getMouseInputHandler() {  return mouseInput; }

    // SETTERS
    public void setController(String identifier) {
        controllerManager.setActiveController(identifier);
    }

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
        // Handlers
        graphics = new GraphicsHandler();
        keyInput = new KeyInputHandler(graphics);
        mouseInput = new MouseInputHandler(graphics);
        audio = new AudioHandler("sound/");

        // ControllerManager
        controllerManager = new ControllerManager();

        // Controllers
        controllerManager.addController("GAME", new GameController(this));
        controllerManager.addController("MENU", new MenuController(this));

        controllerManager.setActiveController("GAME");

        // Run game
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
        controllerManager.tick();
    }

    /**
     * Update method (game logics)
     */
    public void update(double delta) {
        this.tick();
        controllerManager.update(delta);
    }

    /**
     * Render method (game graphics)
     */
    public void render() {
        controllerManager.render();
    }
}