import java.awt.*;

public class Game {
    // CONSTANTS
    public static final String GAME_TITLE = "Pace Invaders";
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // HANDLERS
    private GraphicsHandler graphics;
    private AudioHandler audio;
    //private InputHandler input;

    // GETTERS
    public GraphicsHandler getGraphicsHandler() {  return graphics; }
    public AudioHandler getAudioHandler() {  return audio; }
    //public InputHandler getInputHandler() {  return input; }

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
        graphics = new GraphicsHandler();
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
            try { Thread.sleep(1); } catch (Exception e) {}
        }
    }

    /**
     * Update method (game logics)
     */
    public void update(double delta) {

    }

    /**
     * Render method (game graphics)
     */
    public void render() {
        Graphics2D canvas = graphics.getCanvas();
        graphics.renderCanvas();
    }
}