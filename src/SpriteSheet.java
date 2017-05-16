import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * SpriteSheet Class
 * - Contains a spritesheet
 * @author Fredrik Skoglind
 */
public class SpriteSheet {
    private BufferedImage spriteSheet;

    public SpriteSheet(String filename) {
        File imageFile = new File(filename);

        if(imageFile.exists()) {
            if(imageFile.canRead()) {
                try { spriteSheet = ImageIO.read(imageFile); } catch (IOException e) { e.printStackTrace(); }
            }
        }
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }
}
