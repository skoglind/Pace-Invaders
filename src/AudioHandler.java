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

    public AudioHandler() {
        audioClips = new HashMap<>();
    }

    /**
     * Loads AudioClip
     * @param filename      Filename (eg. clip.wav)
     * @return              True/False if file existed and could be red
     */
    public boolean loadClip(String filename) {
        if(!audioClips.containsKey(filename)) {
            File file = new File(filename);
            if(file.exists()) {
                if(file.canRead()) {
                    AudioClip audioClip = new AudioClip(file.toURI().toString());
                    audioClips.put(filename, audioClip);
                } else { return false; }
            } else { return false; }
        } else { return false; }
        return true;
    }

    /**
     * Plays AudioClip with standard settings
     * @param filename      Filename (eg. clip.wav)
     * @return              AudioClip as object
     */
    public AudioClip playClip(String filename) {
        return playClip(filename, 1.0, 0.0, 1);
    }

    /**
     * Plays AudioClip
     * @param filename      Filename (eg. clip.wav)
     * @param volume        0.0 <> 1.0
     * @param pan           -1.0 Left Channel, 1.0 Right Channel
     * @return              AudioClip as object
     */
    public AudioClip playClip(String filename, Double volume, Double balance, int loopCount) {
        if(audioClips.containsKey(filename)) {
            AudioClip audioClip = audioClips.get(filename);
            audioClip.setCycleCount(loopCount);
            audioClip.setBalance(balance);
            audioClip.setVolume(volume);
            audioClip.setPan(0.0);
            audioClip.play();
            return audioClip;
        } else { return null; }
    }
}
