import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

/**
 * GAME
 * @author Fredrik Skoglind
 */
public class Game {
    // CONSTANTS
    public static final String GAME_TITLE = "Pace Invaders";
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int FPS = 60;
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final String SFX_FOLDER = "media/sfx/";
    public static final String MUSIC_FOLDER = "media/music/";
    public static final String SPRITESHEETS_FOLDER = "media/spritesheet/";
    public static final boolean showHitbox = true;

    // HANDLERS
    private GraphicsHandler graphics;
    private AudioHandler audio;
    private KeyInputHandler keyInput;
    private MouseInputHandler mouseInput;

    // CONTROLLER
    private ControllerManager controllerManager;

    // DATA
    private HashMap<String, String> availableSFX;
    private HashMap<String, String> availableMusic;
    private HashMap<String, SpriteSheet> spriteSheets;
    private int currentFPS = 0;
    private int lastFrameTime = 0;
    private int numEntities = 0;

    public int numXHits = 0;
    public int numYHits = 0;

    // GETTERS
    public GraphicsHandler getGraphicsHandler() {  return graphics; }
    public AudioHandler getAudioHandler() {  return audio; }
    public KeyInputHandler getKeyInputHandler() {  return keyInput; }
    public MouseInputHandler getMouseInputHandler() {  return mouseInput; }
    public int getCurrentFPS() { return currentFPS; }
    public int getNumEntities() { return numEntities; }
    public String getSFX(String name) {
        if(availableSFX.containsKey(name)) { return availableSFX.get(name); }
        return null;
    }
    public String getMusic(String name) {
        if(availableMusic.containsKey(name)) { return availableMusic.get(name); }
        return null;
    }
    public SpriteSheet getSpriteSheet(String name) {
        if(spriteSheets.containsKey(name)) {
            return spriteSheets.get(name);
        } else { return null; }
    }

    // SETTERS
    public void setController(String identifier) {
        controllerManager.setActiveController(identifier);
    }
    public void loadData() { loadSpriteSheets(); loadMusic(); loadSFX(); loadFonts(); }
    public void setNumEntities(int numEntities) { this.numEntities = numEntities; }

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
        // Handlers
        graphics = new GraphicsHandler();
        keyInput = new KeyInputHandler(graphics);
        mouseInput = new MouseInputHandler(graphics);
        audio = new AudioHandler();

        // ControllerManager
        controllerManager = new ControllerManager();

        // Controllers
        controllerManager.addController("GAME", new GameController(this));
        controllerManager.addController("MENU", new MenuController(this));

        // Load gamedata
        loadData();

        // Set Active Controller
        controllerManager.setActiveController("GAME");
        this.run();
    }

    /**
     * Load SFX Files
     */
    public void loadSFX() {
        availableSFX = getFilesInFolder(SFX_FOLDER, "wav");
        if(availableSFX != null) {
            for (HashMap.Entry<String, String> entry : availableSFX.entrySet()) {
                String fullFilepath = entry.getValue();
                audio.loadClip(fullFilepath);
            }
        }
    }

    /**
     * Load Music Files
     */
    public void loadMusic() {
        availableMusic = getFilesInFolder(MUSIC_FOLDER, "mp3");
        if(availableMusic != null) {
            for (HashMap.Entry<String, String> entry : availableMusic.entrySet()) {
                String fullFilepath = entry.getValue();
                audio.loadClip(fullFilepath);
            }
        }
    }

    /**
     * Load SpriteSheet
     */
    public void loadSpriteSheets() {
        String spriteSheetFolder = "media/spritesheet/";
        spriteSheets = new HashMap<>();

        spriteSheets.put("player_green", new SpriteSheet(spriteSheetFolder + "green_anim.png"));
        spriteSheets.put("player_red", new SpriteSheet(spriteSheetFolder + "red_anim.png"));
        spriteSheets.put("player_violet", new SpriteSheet(spriteSheetFolder + "violet_anim.png"));
        spriteSheets.put("player_white", new SpriteSheet(spriteSheetFolder + "white_anim.png"));
    }

    /**
     * Load Fonts
     */
    public void loadFonts() {

    }


    /**
     * Run game
     */
    private void run() {
        while(true) {
            this.startFrameTimer();

            this.tick();
            this.update(this.getDelta());
            this.render();

            this.stopFrameTimer();

            try { Thread.sleep(this.getFrameSleepTime()); } catch (Exception e) { }
        }
    }

    /**
     * Tick method, all tickers
     */
    private void tick() {
        keyInput.tick();
        mouseInput.tick();
        controllerManager.tick();
    }

    /**
     * Update method (game logics)
     */
    public void update(double delta) { controllerManager.update(delta); }

    /**
     * Render method (game graphics)
     */
    public void render() { controllerManager.render(); }

    /**
     * Draw developer data to screen
     */
    public void drawDevData(Graphics2D canvas) {
        Color textColor = Color.BLACK;
        Color backgroundColor = Color.WHITE;

        // Draw background
        canvas.setColor(backgroundColor);
        canvas.fillRect(2, 2, 110, 70);

        // Draw data
        canvas.setColor(textColor);
        canvas.drawString( "FPS: " + getCurrentFPS(), 10, 20);
        canvas.drawString( "Entities: " + getNumEntities(), 10, 34);

        canvas.drawString( "Hits X: " + numXHits, 10, 48);
        canvas.drawString( "Hits Y: " + numYHits, 10, 62);
    }

    /**
     * Get the filename extension
     * @param filename              Filename
     * @return                      String with the extension (in lowercase)
     */
    private String getFilenameExtension(String filename) {
        String fileNameExtension = "";
        int i = filename.lastIndexOf('.');
        if (i >= 0) { fileNameExtension = filename.substring(i+1); }
        return fileNameExtension.toLowerCase();
    }

    /**
     * Return a HashMap with all files in a folder, filtered by extension
     * @param seekFolder            Folder to seek
     * @param extension             Extension to filter by
     * @return                      HashMap with all files found
     */
    private HashMap<String, String> getFilesInFolder(String seekFolder, String extension) {
        File folder = new File(seekFolder);
        File[] listOfFiles = folder.listFiles();
        String fileExtension = extension;

        HashMap<String, String> allFiles = new HashMap<>();

        if(listOfFiles != null) {
            for (File file : listOfFiles) {
                String filepath = folder.getPath();
                String filename = file.getName();
                String filenameExt = getFilenameExtension(filename);
                String fullFilepath = filepath + "/" + filename;
                String filenameWithoutExt = (filename.substring(0, (filename.length() - (filenameExt.length()+1)))).toLowerCase();

                if(file.isFile() && file.canRead() && fileExtension.compareToIgnoreCase(filenameExt) == 0) {
                    allFiles.put(filenameWithoutExt, fullFilepath);
                }
            }
        }

        return allFiles;
    }

    /* FPS Delta */
        private double deltaFrameTime = 0.0;
        private long startFrameTime, stopFrameTime;
        private int frameCount = 0;
        private int msElapsed = 0;

        private void startFrameTimer() {
            startFrameTime = System.nanoTime();
        }

        private void stopFrameTimer() {
            stopFrameTime = System.nanoTime();
        }

        private int getFrameSleepTime() {
            if(this.msElapsed >= 1000) {
                this.currentFPS = this.frameCount;
                this.frameCount = 0;
                this.msElapsed = 0;
            }

            deltaFrameTime = (stopFrameTime - startFrameTime) / 1000000.0; // In ms
            int sleepTime = (1000/FPS) - (int)deltaFrameTime;
            if(sleepTime < 0) { sleepTime = 0; }

            this.lastFrameTime = ((int)deltaFrameTime + sleepTime);
            this.msElapsed += this.lastFrameTime;
            this.frameCount++;

            return sleepTime;
        }

        private double getDelta() {
            return this.deltaFrameTime;
        }
    /* - - - - */
}