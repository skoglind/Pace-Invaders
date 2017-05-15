import java.awt.*;

public class Game {
    public static final String GAME_TITLE = "Pace Invaders";
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    private GraphicsHandler graphics;
    //private SoundHandler sound;
    //private InputHandler input;

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
    }

    public void init() {
        graphics = new GraphicsHandler();
        this.run();
    }

    private void run() {
        while(true) {
            double startTime = System.nanoTime();
            this.update(1.0);
            this.render();
            try { Thread.sleep(1); } catch (Exception e) {}
        }
    }

    public void update(double delta) {

    }

    public void render() {
        Graphics2D canvas = graphics.getCanvas();
        graphics.renderCanvas();
    }
}