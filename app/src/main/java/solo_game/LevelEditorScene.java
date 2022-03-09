package solo_game;

import org.joml.Vector3f;
import solo_game.dataStructures.Data;
import solo_game.dataStructures.LinkedList;
import solo_game.dataStructures.Node;

import solo_game.input.MouseListener;

/*
 * LevelEditorScene
 */
import solo_game.renderer.Shader;

public class LevelEditorScene extends Scene {

    private Shader shader;
    private Camera camera;
    private LinkedList[] linkedLists;
    private ColoredBox selectedBox;

    @Override
    public void init() {
        camera = new Camera(new Vector3f(0, 0, 20));

        MouseListener.addMouseHandler(camera);
        shader = new Shader("coloredVertex.glsl", "coloredFragment.glsl");

        int n = 8;
        initLinkedList(n);
    }

    private void initLinkedList(int n) {
        linkedLists = new LinkedList[n];
        Node prevHead = null;
        for (int i = 0; i < n; i++) {
            LinkedList l = new LinkedList();
            for (int j = 0; j < n; j++) {
                Data d = new Data(i + 1, j + 1, new ColoredBox(new Vector3f((i - n / 2) * 2, (j - n / 2) * 2, 0)));
                l.addLast(d);
            }
            if (prevHead != null) {
                prevHead.child = l.getHead();
            }
            prevHead = l.getHead();
            linkedLists[i] = l;
        }
        LinkedList a = linkedLists[n / 2 - 1];
        a.deleteAfter((n / 2), n / 2 - 1);
        a.deleteAfter((n / 2), (n / 2) - 1);

        LinkedList b = linkedLists[n / 2];
        b.deleteAfter((n / 2) + 1, n / 2 - 1);
        b.deleteAfter((n / 2) + 1, (n / 2) - 1);

    }

    private void checkCollisions() {

        for (LinkedList linkedList : linkedLists) {
            Node tmp = linkedList.getHead();
            tmp.data.box.isCollided(camera);
            while (tmp.next != null) {
                tmp = tmp.next;
                tmp.data.box.isCollided(camera);
            }
        }
    }

    public LevelEditorScene() {
    }

    @Override
    public void update(float dt) {
        // Bind the shader program
        camera.update(dt);
        checkCollisions();

        for (LinkedList linkedList : linkedLists) {
            Node tmp = linkedList.getHead();
            tmp.data.box.render(shader, camera);
            while (tmp.next != null) {
                tmp = tmp.next;
                tmp.data.box.render(shader, camera);
            }
        }

    }
}
