import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GraphicsHandler Class
 * - Prepare and render canvas
 * @author Fredrik Skoglind
 */
public class GraphicsHandler extends JFrame {
    private Graphics2D screen;
    private BufferedImage canvas;
    private Dimension frameSize = new Dimension();

    public GraphicsHandler() {
        frameSize.setSize(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
        init();
    }

    /**
     * Initialize
     */
    private void init() {
        // WINDOW
        setTitle(Game.GAME_TITLE);
        setSize(frameSize);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);

        // CANVAS
        canvas = new BufferedImage(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        canvas.setAccelerationPriority(1);
    }

    /**
     * Returns CANVAS
     * @return              CANVAS as a Graphics2D object
     */
    public Graphics2D getCanvas() {
        return canvas.createGraphics();
    }

    /**
     * Render CANVAS to screen
     */
    public void renderCanvas() {
        screen = (Graphics2D)getGraphics();
        screen.drawImage(canvas, getInsets().left, getInsets().top, this);
        canvas.flush();
    }
}
