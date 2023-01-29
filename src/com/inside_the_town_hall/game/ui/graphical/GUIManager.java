package com.inside_the_town_hall.game.ui.graphical;

import com.inside_the_town_hall.game.controlls.GameController;
import com.inside_the_town_hall.game.io.FileHandler;
import com.inside_the_town_hall.game.log.LogHandler;
import com.inside_the_town_hall.game.log.LogMode;
import com.inside_the_town_hall.game.translation.LanguageManager;
import com.inside_the_town_hall.game.ui.graphical.font.EnhancedFont;
import com.inside_the_town_hall.game.ui.graphical.font.IFont;
import com.inside_the_town_hall.game.ui.graphical.peripheral.Keyboard;
import com.inside_the_town_hall.game.ui.graphical.peripheral.Mouse;
import com.inside_the_town_hall.game.ui.graphical.shader.ColorModel;
import com.inside_the_town_hall.game.ui.graphical.shader.Shader;
import com.inside_the_town_hall.game.ui.graphical.shader.Texture;
import com.inside_the_town_hall.game.ui.graphical.shader.TextureModel;
import com.inside_the_town_hall.game.utils.BufferCreator;
import com.inside_the_town_hall.game.utils.Time;
import com.inside_the_town_hall.game.utils.VertexUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Manages active window and shader settings etc.
 * Is a Singleton
 *
 * @author NekroQuest
 * @version 0.1
 */
public class GUIManager implements Runnable {
    private static GUIManager instance;
    private final LogHandler LOGGER = new LogHandler(GUIManager.class);

    private long window;
    private volatile boolean running;

    private final PrintStream errorStream;

    private boolean fullscreen;
    private int previousWidth;
    private int previousHeigh;

    private final Keyboard keyboard;
    private final Mouse mouse;
    private final Time time;

    private int width;
    private int height;
    private boolean focused = true;

    private int vao;

    private String title;

    private GUIScreen currentScreen;
    private GUIScreen lastScreen;
    private final GUIScreen nullScreen;

    private IFont defaultFont;
    private Shader defaultShader;
    private Shader colorShader;
    private Shader fontShader;

    private ColorModel rect;
    private TextureModel background;

    private final Queue<Runnable> tasks;

    public static GUIManager create(int width, int height, String title) {
        // TODO: Exception handler / Custom Exception
        if (instance != null) throw new RuntimeException("gui.init.twice");
        instance = new GUIManager(System.err, width, height, title);
        return instance;
    }

    public static GUIManager getInstance() {
        // TODO: Exception handler / Custom Exception
        if (instance == null) throw new RuntimeException("gui.init.null");
        return instance;
    }

    private GUIManager(PrintStream errorStream, int width, int height, String title) {
        this.tasks = new LinkedBlockingQueue<>();
        this.errorStream = errorStream;
        this.width = width;
        this.height = height;
        this.title = title;
        this.nullScreen = new GUIScreen() {
        };
        this.currentScreen = nullScreen;
        this.lastScreen = this.currentScreen;

        this.keyboard = new Keyboard(this);
        this.mouse = new Mouse(this);
        this.time = new Time();
    }

    /**
     * Creates the window and initialises
     * @throws RuntimeException if the initialisation failed
     */
    public void init() throws RuntimeException {
        GLFWErrorCallback.createPrint(errorStream).set();
        if (!glfwInit()) throw new RuntimeException("glfw.init.failed");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        this.window = glfwCreateWindow(this.width, this.height, this.title, 0, 0);
        if (window == NULL) throw new RuntimeException("glfw.window.failed");

        this.registerCallbacks();

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        this.vao = glGenVertexArrays();
        glBindVertexArray(vao);

        this.loadResources();

        this.setWindowIcon();

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    /**
     * GUI Loop
     */
    @Override
    public void run() {
        try {
            this.running = true;
            centerWindow();
            glfwShowWindow(window);
            // toggleFullscreen();

            while (this.isRunning()) {
                this.getTime().onFrame();
//                LOGGER.info("Frame-Time: " + this.getTime().getDeltaTime());
                glfwPollEvents();
                glClear(GL_COLOR_BUFFER_BIT);

                while (!this.tasks.isEmpty()) {
                    this.tasks.poll().run();
                }

                this.getCurrentScreen().draw(null);

                glfwSwapBuffers(window);
                if (glfwWindowShouldClose(window)) GameController.getInstance().shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
            GameController.getInstance().shutdown();
        } finally {
            glfwFreeCallbacks(this.window);
            glfwDestroyWindow(this.window);
            glfwTerminate();
        }
    }

    /**
     * Stop the gui loop
     */
    public void stop() {
        this.running = false;
    }

    private void registerCallbacks() {
        glfwSetKeyCallback(window, this.keyboard);
        glfwSetCharCallback(window, this.keyboard::invoke);
        glfwSetMouseButtonCallback(window, this.mouse);
        glfwSetCursorPosCallback(window, this.mouse.getCursor());
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> this.onResize(width, height));
        glfwSetWindowFocusCallback(window, this::onFocus);
    }

    /**
     * Loads all game ressources
     */
    private void loadResources() {
        this.defaultShader = new Shader("main", this);
        this.colorShader = new Shader("color", this);
        this.fontShader = new Shader("font", this);

        this.defaultFont = EnhancedFont.loadFromResource("default");

        this.rect = new ColorModel(new float[]{0.0f}, new byte[]{(byte) 0});
        this.background = new TextureModel(Texture.getTexture("background_blurred.png"), VertexUtils.getVertices(0, 0, 0, 0), VertexUtils.getVertices(0, 0, 1, 1));
    }

    /**
     * Sets the window icon
     */
    private void setWindowIcon() {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);
        GLFWImage icon = GLFWImage.malloc();
        GLFWImage.Buffer imageBuf = GLFWImage.malloc(1);
        try {
            // TODO: Make icon path constant (configurable)
            ByteBuffer image = STBImage.stbi_load_from_memory(BufferCreator.createByteBuffer(
                            FileHandler.getInstance().readFileAsBytes(
                                    "asset\\textures\\%s".formatted(GameController.properties.FAVICON_NAME()))),
                    width, height, comp, 4);
            assert image != null;
            icon.set(width.get(), height.get(), image);
            imageBuf.put(0, icon);
            glfwSetWindowIcon(window, imageBuf);
        } catch (Exception e) {
            LOGGER.deepLog(LogMode.RED, "ERROR.UI.IMAGE.FAVICON");
        }
    }

    /**
     * Executes a runnable in the gui Thread
     *
     * @param runnable
     */
    public void addTask(Runnable runnable) {
        this.tasks.add(runnable);
    }

    private void centerWindow() {
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2
            );
        }
    }

    public void changeDimensions(int width, int height) {
        glfwSetWindowSize(window, width, height);
    }

    private void onResize(int width, int height) {
        this.width = width;
        this.height = height;
        this.update();
    }

    public void update() {
        glViewport(0, 0, width, height);
        this.getCurrentScreen().update();
    }

    private void onFocus(long l, boolean b) {
        if (l == window) {
            this.focused = b;
        }
    }

    public void drawRect(int x, int y, int width, int height, Color color) {
        this.rect.setVertices(new float[]{
                (float) x, (float) y,
                (float) x + width, (float) y,
                (float) x + width, (float) y + height,
                (float) x, (float) y + height
        });
        this.rect.setColor(color);
        this.rect.render();
    }

    public void drawBackground() {
        int[] center = this.getCenter();
        final float imageRatio = (float) background.getTexture().getWidth() / background.getTexture().getHeight();
        final float newWidth = this.width > this.height * imageRatio ? this.width : this.height * imageRatio;
        final float newHeight = this.height;
        final float newX = center[0] - (newWidth / 2);
        final float newY = 0;

        this.background.setVertices(VertexUtils.getVertices(newX, newY, newWidth, newHeight));
        this.background.render();
        this.drawRect(0, 0, this.width, this.height, new Color(0, 0, 0, 136));
    }

    /**
     * Toggles the full screen
     */
    public void toggleFullscreen() {
        if (this.isFullscreen()) {
            glfwSetWindowSize(window, previousWidth, previousHeigh);
            centerWindow();
            glfwSetWindowAttrib(window, GLFW_DECORATED, GLFW_TRUE);

            updateWindowData();

            this.fullscreen = false;
        } else {
            previousWidth = this.width;
            previousHeigh = this.height;

            glfwSetWindowAttrib(window, GLFW_DECORATED, GLFW_FALSE);
            var mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, 0, 0);
            glfwSetWindowSize(window, mode.width(), mode.height());

            updateWindowData();

            this.fullscreen = true;
        }
        this.update();
    }

    /**
     * Updates window scales
     */
    public void updateWindowData() {
        int[] width = new int[1];
        int[] height = new int[1];
        glfwGetWindowSize(window, width, height);
        this.width = width[0];
        this.height = height[0];
    }

    // Getter
    public int[] getCenter() {
        return new int[]{this.getWidth() / 2, this.getHeight() / 2};
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public GUIScreen getCurrentScreen() {
        return Objects.requireNonNullElse(this.currentScreen, this.nullScreen);
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Time getTime() {
        return time;
    }

    public long getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isFocused() {
        return focused;
    }

    public Shader getDefaultShader() {
        return defaultShader;
    }

    public Shader getColorShader() {
        return colorShader;
    }

    public Shader getFontShader() {
        return fontShader;
    }

    public IFont getDefaultFont() {
        return defaultFont;
    }

    public boolean isRunning() {
        return running;
    }

    public GUIScreen getLastScreen() {
        return this.lastScreen;
    }

    // Setter
    public void setLastScreen() {
        this.lastScreen = this.currentScreen;
    }

    public void updateTitle() {
        glfwSetWindowTitle(this.window, LanguageManager.getInstance()
                .use("window.title", new HashMap<>() {{
                    put("version", "0.1");
                }}));
    }

    public void setCurrentScreen(GUIScreen screen) {
        this.getCurrentScreen().exit();
        if (screen != null) {
            screen.init();
            screen.update();
        }
        this.currentScreen = screen;
    }
}
