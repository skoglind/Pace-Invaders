import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * MouseInputHandler
 * - Catching mouse input
 * @author Fredrik Skoglind
 */
public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
    private GraphicsHandler graphics;
    private HashMap<Integer, Button> buttons;
    private Dimension mousePosition;
    private boolean mouseOnScreen;

    private class Button {
        private boolean buttonDown = true;
        private int pressCount = 1;
        private int downTime = 0;

        public boolean getButtonDown() { return buttonDown; }
        public int getPressCount() { return pressCount; }
        public int getPressTime() { return downTime; }

        public void pressButton() { if(!buttonDown) { buttonDown = true; pressCount++; } }
        public void releaseButton() { buttonDown = false; downTime = 0; }

        public void tick() { if(buttonDown) { downTime++; } }
    }

    public MouseInputHandler(GraphicsHandler graphics) {
        buttons = new HashMap<>();
        mousePosition = new Dimension(0,0);

        this.graphics = graphics;
        this.addListener();
    }

    /**
     * Activate mouse listener
     */
    public void addListener() {
        this.graphics.addMouseListener(this);
        this.graphics.addMouseMotionListener(this);
        this.graphics.addMouseWheelListener(this);
    }

    /**
     * Disable mouse listener
     */
    public void removeListener() {
        this.graphics.removeMouseListener(this);
        this.graphics.removeMouseMotionListener(this);
        this.graphics.removeMouseWheelListener(this);
    }

    /**
     * Ticker
     * - Add to game ticker to count presstime
     */
    public void tick() {
        for (HashMap.Entry<Integer, Button> entry : buttons.entrySet()) {
            Integer button = entry.getKey();
            buttons.get(button).tick();
        }
    }

    /**
     * Returns if button is pressed
     * @param button            Button (eg. MouseEvent.BUTTON1)
     * @return                  True/False if key is pressed
     */
    public boolean isButtonDown(int button) {
        if(buttons.containsKey(button)) {
            return buttons.get(button).getButtonDown();
        } else { return false; }
    }

    /**
     * Returns for how long the button has been pressed
     * @param button            Button (eg. MouseEvent.BUTTON1)
     * @return                  Int
     */
    public int getButtonPressTime(int button) {
        if(buttons.containsKey(button)) {
            return buttons.get(button).getPressTime();
        } else { return 0; }
    }

    /**
     * Returns how many time the button have been pressed
     * @param button            Button (eg. MouseEvent.BUTTON1)
     * @return                  Int
     */
    public int getButtonPressCount(int button) {
        if(buttons.containsKey(button)) {
            return buttons.get(button).getPressCount();
        } else { return 0; }
    }

    /**
     * Returns the mouse position
     * @return                  Dimension
     */
    public Dimension getMousePosition() {
        return mousePosition;
    }

    /**
     * Returns if the mouse on the screen
     * @return                  Boolean
     */
    public boolean getMouseOnScreen() {
        return mouseOnScreen;
    }

    /**
     * Release all buttons, setting every button to false
     */
    public void releaseAllButtons() {
        for (HashMap.Entry<Integer, Button> entry : buttons.entrySet()) {
            Integer button = entry.getKey();
            buttons.get(button).releaseButton();
        }
    }

    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if(buttons.containsKey(button)) {
            buttons.get(button).pressButton();
        } else {
            buttons.put(button, new Button());
        }
    }

    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if(buttons.containsKey(button)) {
            buttons.get(button).releaseButton();
        }
    }

    public void mouseEntered(MouseEvent e) {
        mouseOnScreen = true;
    }

    public void mouseMoved(MouseEvent e) {
        mousePosition.setSize(e.getX(), e.getY());
    }

    public void mouseExited(MouseEvent e) {
        mouseOnScreen = false;

    }

    public void mouseWheelMoved(MouseWheelEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
}
