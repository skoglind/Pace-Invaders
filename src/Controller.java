import java.util.Random;

/**
 * Controller Class
 * @author Fredrik Skoglind
 */
public class Controller {
    protected Game game;
    protected GraphicsHandler graphics;
    protected AudioHandler audio;
    protected KeyInputHandler keyInput;
    protected MouseInputHandler mouseInput;
    protected Random rnd;

    public Controller(Game game) {
        this.game = game;
        this.graphics = this.game.getGraphicsHandler();
        this.audio = this.game.getAudioHandler();
        this.keyInput = this.game.getKeyInputHandler();
        this.mouseInput = this.game.getMouseInputHandler();
        this.rnd = new Random();
    }

    public void dispose() {}
    public void init() {}
    public void tick() {}
    public void update(double delta) {}
    public void render() {}
}
