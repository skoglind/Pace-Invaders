import java.util.HashMap;

/**
 * ControllerManager Class
 * - Keeping track of controllers
 * @author Fredrik Skoglind
 */
public class ControllerManager {
    private HashMap<String, Controller> controllers;
    private Controller activeController;

    public ControllerManager() {
        controllers = new HashMap<>();
    }

    public void addController(String identifier, Controller controller) {
        if(!controllers.containsKey(identifier)) {
            controllers.put(identifier, controller);
        } else {
            controllers.remove(identifier);
            controllers.put(identifier, controller);
        }
    }

    public void removeController(String identifier) {
        if(controllers.containsKey(identifier)) {
            controllers.remove(identifier);
        }
    }

    public boolean setActiveController(String identifier) {
        if(controllers.containsKey(identifier)) {
            if(this.activeController != null) { this.activeController.dispose(); }
            this.activeController = this.controllers.get(identifier);
            this.activeController.init();
        } else { return false; }
        return true;
    }

    public void tick() {
        if(activeController != null) {
            activeController.tick();
        }
    }

    public void update(double delta) {
        if(activeController != null) {
            activeController.update(delta);
        }
    }

    public void render() {
        if(activeController != null) {
            activeController.render();
        }
    }
}
