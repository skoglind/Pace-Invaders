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
    public static final int FPS = 60;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

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

        // Set Active Controller
        controllerManager.setActiveController("GAME");
        this.run();
    }

    /**
     * Run game
     */
    private void run() {
        while(true) {
            this.startTimer();

            this.tick();
            this.update(this.getDelta());
            this.render();

            try { Thread.sleep(this.getSleepTime()); } catch (Exception e) {}
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
    public void update(double delta) { controllerManager.update(delta); }

    /**
     * Render method (game graphics)
     */
    public void render() { controllerManager.render(); }

    long startTime;
    double runTime = 0.0;

    private void startTimer() {
        startTime = System.nanoTime();
    }

    private double getDelta() {
        return runTime;
    }

    private int getSleepTime() {
        int sleepTime = 0;
        long stopTime, deltaTime;

        stopTime = System.nanoTime();
        deltaTime = (stopTime - startTime);
        runTime = deltaTime/1000000.0;
        sleepTime = (int)((1000/FPS) - runTime);
        if(sleepTime < 0) { sleepTime = 0; }

        return sleepTime;
    }
}