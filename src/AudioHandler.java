import javafx.scene.media.AudioClip;
import java.io.File;
import java.util.HashMap;

/**
 * AudioHandler Class
 * - Load and play sound effects
 * @author Fredrik Skoglind
 */
public class AudioHandler {
    private HashMap<String, AudioClip> audioClips;
    private String filePath = "/";

    public AudioHandler(String filePath) {
        audioClips = new HashMap<>();
        this.filePath = filePath;
    }

    public boolean loadClip(String filename) {
        if(!audioClips.containsKey(filename)) {
            File file = new File(this.filePath + filename);
            if(file.exists()) {
                if(file.canRead()) {
                    AudioClip audioClip = new AudioClip(file.toURI().toString());
                    audioClips.put(filename, audioClip);
                } else { return false; }
            } else { return false; }
        } else { return false; }
        return true;
    }

    public AudioClip playClip(String filename) {
        return playClip(filename, 0.0, 0.0);
    }

    public AudioClip playClip(String filename, Double volume, Double pan) {
        if(!audioClips.containsKey(filename)) {
            AudioClip audioClip = audioClips.get(filename);
            audioClip.play(volume, 0.0, 1.0, pan, 0);
            return audioClip;
        } else { return null; }
    }
}
