package solo_game;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import solo_game.input.KeyListener;
import solo_game.input.MouseListener;
import solo_game.util.Time;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import javax.swing.JOptionPane;

/**
 * Window
 */
public class Window {

    private final int width;
    private final int height;
    private final String title;
    private long glfwWindow;

    private static Window window = null;

    private static Scene currentScene;

    public float r, g, b, a;

    private Window() {
        width = 1200;
        height = 900;
        title = "Solo Game";
        r = 0;
        g = 0;
        b = 0;
        a = 1;
    }

    public static float getAspectRatio() {
        return ((float) get().width) / ((float) get().height);
    }

    public static float getWidth() {
        return get().width;
    }

    public static float getHeight() {
        return get().height;
    }

    public static Window get() {
        if (window == null) {
            window = new Window();
        }

        return window;
    }

    public static void setWindowShouldClose() {
        glfwSetWindowShouldClose(get().glfwWindow, true);
    }

    public static void enableCursor() {
        glfwSetInputMode(get().glfwWindow, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public static void disableCursor() {
        glfwSetInputMode(get().glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + " !");
        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        // glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(width, height, title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallBack);

        // Disable mouse cursor
        glfwSetInputMode(glfwWindow, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);

        // Enable v-sync
        glfwSwapInterval(GLFW_TRUE);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // For bindings!!!!!
        GL.createCapabilities();

        // Set background color
        glClearColor(r, g, b, a);

        glEnable(GL_DEPTH_TEST);

        currentScene = new LevelEditorScene(getBoxCount());
        currentScene.init();

    }

    public static int getBoxCount() {
        boolean error;
        int ans = 6;

        do {
            error = false;
            try {

                String res = JOptionPane.showInputDialog(null, "Please enter an even number which is greater than 4",
                        JOptionPane.INFORMATION_MESSAGE);
                if (res == null) {
                    setWindowShouldClose();
                } else {
                    ans = Integer.parseInt(res);
                }

            } catch (NumberFormatException e) {
                error = true;
            }

        } while (error || ans % 2 != 0 || ans <= 4);
        return ans;

    }

    private void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;
        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(glfwWindow, true);
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }
            currentScene.render();

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        }

    }

}
