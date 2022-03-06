package solo_game;

import org.joml.Vector3f;

import solo_game.input.MouseListener;

/**
 * LevelEditorScene
 */
import solo_game.renderer.Shader;

public class LevelEditorScene extends Scene {

    private Shader shader;
    private ColoredBox[] items;
    private Camera camera;
    int colCount = 0;

    @Override
    public void init() {
        camera = new Camera(new Vector3f(0, 2, 7));

        MouseListener.addMouseHandler(camera);
        // KeyListener.addKeyboardHandler(camera);

        // shader = new Shader("vertex.glsl", "fragment.glsl");
        shader = new Shader("coloredVertex.glsl", "coloredFragment.glsl");
        // items = new Box[] {
        // new Box(new Vector3f(0, 0, -5)),
        // new Box(new Vector3f(0, 0, -3)),
        // new Box(new Vector3f(0, 0, -1)),
        // new Box(new Vector3f(0, 0, 1)), };

        
        items = new ColoredBox[]{
            new ColoredBox(new Vector3f(0, 0, -5), new Vector3f(128, 0, 0)),
            new ColoredBox(new Vector3f(0, 0, -3), new Vector3f(128, 0, 0)),
            new ColoredBox(new Vector3f(0, 0, -1), new Vector3f(128, 0, 0)),
            new ColoredBox(new Vector3f(0, 0, 1), new Vector3f(128, 0, 0)),};
        
    }

    public LevelEditorScene() {
    }

    @Override
    public void update(float dt) {
        // Bind the shader program
        camera.update(dt);
        for (ColoredBox item : items) {
            item.render(shader, camera);
            if (item.isCollided(camera)) {
                new ColoredBox(item.collidedPos, new Vector3f(0, 0, 64)).render(shader, camera);
            }

        }

    }

}
