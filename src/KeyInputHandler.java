import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * KeyInputHandler Class
 * - Catching key input
 * @author Fredrik Skoglind
 */
public class KeyInputHandler implements KeyListener {
    private GraphicsHandler graphics;
    private HashMap<Integer, Key> keys;

    private class Key {
        private boolean keyDown = true;
        private int pressCount = 1;
        private int downTime = 0;
        private boolean keyLock = false;

        public boolean getKeyDown() { return keyDown; }
        public int getPressCount() { return pressCount; }
        public int getPressTime() { return downTime; }

        public void pressKey() {
            if(!keyLock) {
                if (!keyDown) {
                    keyDown = true;
                    pressCount++;
                }
            }
        }
        public void releaseKey() { keyDown = false; downTime = 0; }

        public void setKeyLock() { keyLock = true; }
        public void releaseKeyLock() { keyLock = false; }

        public void tick() { if(keyDown) { downTime++; } }
    }

    public KeyInputHandler(GraphicsHandler graphics) {
        keys = new HashMap<>();

        this.graphics = graphics;
        this.addListener();
    }

    /**
     * Activate key listener
     */
    public void addListener() {
        this.graphics.addKeyListener(this);
    }

    /**
     * Disable key listener
     */
    public void removeListener() {
        this.graphics.removeKeyListener(this);
    }

    /**
     * Ticker
     * - Add to game ticker to count presstime
     */
    public void tick() {
        // ConcurrentModificationException

        for (HashMap.Entry<Integer, Key> entry : keys.entrySet()) {
            Integer keyCode = entry.getKey();
            keys.get(keyCode).tick();
        }
    }

    /**
     * Returns if key is pressed
     * @param keyCode           Keycode (eg. KeyEvent.VK_SPACE)
     * @return                  True/False if key is pressed
     */
    public boolean isKeyDown(int keyCode) {
        if(keys.containsKey(keyCode)) {
            return keys.get(keyCode).getKeyDown();
        } else { return false; }
    }

    /**
     * Returns if key is pressed, and release
     * @param keyCode           Keycode (eg. KeyEvent.VK_SPACE)
     * @return                  True/False if key is pressed
     */
    public boolean isKeyDownAndRelease(int keyCode) {
        if(this.isKeyDown(keyCode)) {
            keys.get(keyCode).releaseKey();
            keys.get(keyCode).setKeyLock();
        } else { return false; }
        return true;
    }

    /**
     * Release key
     * @param keyCode           Keycode (eg. KeyEvent.VK_SPACE)
     */
    public void releaseKey(int keyCode) {
        if(keys.containsKey(keyCode)) {
            keys.get(keyCode).releaseKey();
        }
    }

    /**
     * Returns for how long the key has been pressed
     * @param keyCode           Keycode (eg. KeyEvent.VK_SPACE)
     * @return                  Int
     */
    public int getKeyPressTime(int keyCode) {
        if(keys.containsKey(keyCode)) {
            return keys.get(keyCode).getPressTime();
        } else { return 0; }
    }

    /**
     * Returns how many time the key have been pressed
     * @param keyCode           Keycode (eg. KeyEvent.VK_SPACE)
     * @return                  Int
     */
    public int getKeyPressCount(int keyCode) {
        if(keys.containsKey(keyCode)) {
            return keys.get(keyCode).getPressCount();
        } else { return 0; }
    }

    /**
     * Release all keys, setting every key to false
     */
    public void releaseAllKeys() {
        for (HashMap.Entry<Integer, Key> entry : keys.entrySet()) {
            Integer keyCode = entry.getKey();
            keys.get(keyCode).releaseKey();
            keys.get(keyCode).releaseKeyLock();
        }
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keys.containsKey(keyCode)) {
            keys.get(keyCode).pressKey();
        } else {
            keys.put(keyCode, new Key());
        }
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keys.containsKey(keyCode)) {
            keys.get(keyCode).releaseKey();
            keys.get(keyCode).releaseKeyLock();
        }
    }

    public void keyTyped(KeyEvent e) {}
}
