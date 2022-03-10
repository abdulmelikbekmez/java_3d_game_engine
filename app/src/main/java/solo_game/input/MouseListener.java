package solo_game.input;

import solo_game.Window;

import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

/**
 * MouseListener
 */
public class MouseListener {

    private static MouseListener instance;
    private double scrollX, scrollY;
    private double xPos, yPos, lastY, lastX;
    private final boolean[] mouseButtonPressed = new boolean[3];
    private final ArrayList<MouseHandler> mouseHandlers;

    private boolean isDragging;

    private MouseListener() {

        mouseHandlers = new ArrayList<>();
        scrollX = 0.0;
        scrollY = 0.0;
        xPos = Window.getWidth() / 2;
        yPos = Window.getHeight() / 2;
        lastX = Window.getWidth() / 2;
        lastY = Window.getHeight() / 2;
    }

    public static MouseListener get() {
        if (instance == null) {
            instance = new MouseListener();
        }
        return instance;
    }

    public static void addMouseHandler(MouseHandler h) {
        get().mouseHandlers.add(h);
    }

    public static void mousePosCallback(long window, double xpos, double ypos) {

        get().lastX = get().xPos;
        get().lastY = get().yPos;

        get().xPos = xpos;
        get().yPos = ypos;

        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];

        for (MouseHandler handler : get().mouseHandlers) {
            handler.onMouseMove();
        }
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {

        if (!(button < get().mouseButtonPressed.length)) {
            return;
        }

        if (action == GLFW_PRESS) {
            get().mouseButtonPressed[button] = true;
        } else if (action == GLFW_RELEASE) {
            get().mouseButtonPressed[button] = false;
            get().isDragging = false;
        }
        
        for (MouseHandler handler : get().mouseHandlers) {
            handler.onMouseClick();
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
    }

    public static float getX() {
        return (float) get().xPos;
    }

    public static float getY() {
        return (float) get().yPos;
    }

    public static float getDx() {
        return (float) (get().xPos - get().lastX);
    }

    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (!(button < get().mouseButtonPressed.length)) {
            return false;
        }
        return get().mouseButtonPressed[button];

    }
}
