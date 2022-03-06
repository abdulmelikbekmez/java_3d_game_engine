
package solo_game.input;

import java.util.ArrayList;
import static org.lwjgl.glfw.GLFW.*;

/**
 * KeyListener
 */
public class KeyListener {
    private static KeyListener instance;
    private final boolean keyPressed[] = new boolean[350];
    private final ArrayList<KeyboardHandler> list;
    
    private KeyListener() {
        list = new ArrayList<>();
    }
    
    public static void addKeyboardHandler(KeyboardHandler h) {
        get().list.add(h);
    }

    public static KeyListener get() {
        if (instance == null)
            instance = new KeyListener();
        return instance;
    }

    public static void keyCallBack(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
        
        
        for (KeyboardHandler handler : get().list) {
            handler.onClick();
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }

}
