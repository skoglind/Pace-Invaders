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

    // DATA
    private int currentFPS = 0;
    private int lastFrameTime = 0;

    // GETTERS
    public GraphicsHandler getGraphicsHandler() {  return graphics; }
    public AudioHandler getAudioHandler() {  return audio; }
    public KeyInputHandler getKeyInputHandler() {  return keyInput; }
    public MouseInputHandler getMouseInputHandler() {  return mouseInput; }
    public int getCurrentFPS() { return currentFPS; }

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
            this.startFrameTimer();

            this.tick();
            this.update(this.getDelta());
            this.render();

            this.stopFrameTimer();

            try { Thread.sleep(this.getFrameSleepTime()); } catch (Exception e) {}
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

    public void drawDevData(Graphics2D canvas) {
        Color textColor = Color.BLACK;
        Color backgroundColor = Color.WHITE;

        // Draw background
        canvas.setColor(backgroundColor);
        canvas.fillRect(2, 2, 60, 30);

        // Draw data
        canvas.setColor(textColor);
        canvas.drawString( "FPS: " + getCurrentFPS(), 10, 20);
    }

    /* FPS Delta */
        private double deltaFrameTime = 0.0;
        private long startFrameTime, stopFrameTime;
        private int frameCount = 0;
        private int msElapsed = 0;

        private void startFrameTimer() {
            startFrameTime = System.nanoTime();
        }

        private void stopFrameTimer() {
            stopFrameTime = System.nanoTime();
        }

        private int getFrameSleepTime() {
            if(this.msElapsed >= 1000) {
                this.currentFPS = this.frameCount;
                this.frameCount = 0;
                this.msElapsed = 0;
            }

            deltaFrameTime = (stopFrameTime - startFrameTime) / 1000000.0; // In ms
            int sleepTime = (1000/FPS) - (int)deltaFrameTime;
            if(sleepTime < 0) { sleepTime = 0; }

            this.lastFrameTime = ((int)deltaFrameTime + sleepTime);
            this.msElapsed += this.lastFrameTime;
            this.frameCount++;

            return sleepTime;
        }

        private double getDelta() {
            return this.deltaFrameTime;
        }
    /* - - - - */
}