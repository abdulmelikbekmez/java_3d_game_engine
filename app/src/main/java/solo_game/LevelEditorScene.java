package solo_game;

import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import solo_game.dataStructures.Data;
import solo_game.dataStructures.LinkedList;
import solo_game.dataStructures.Move;
import solo_game.dataStructures.MultiLinkedList;
import solo_game.dataStructures.Node;
import solo_game.input.MouseHandler;
import solo_game.input.MouseListener;

/*
 * LevelEditorScene
 */
import solo_game.renderer.Shader;

public class LevelEditorScene extends Scene implements MouseHandler {

    private Shader shader;
    private Camera camera;
    private MultiLinkedList boxList;
    private LinkedList<Move> availableMovesList;

    private Node<Data> selectedNode;
    private Node<Data> hoveredNode;

    private Node<Move> hoveredMove;

    private Vector3f collidedPos;

    public static int N = 6;

    @Override
    public void init() {
        camera = new Camera(new Vector3f(0, 0, 20));

        MouseListener.addMouseHandler(camera);
        MouseListener.addMouseHandler(this);
        shader = new Shader("coloredVertex.glsl", "coloredFragment.glsl");

        boxList = new MultiLinkedList(N);
        availableMovesList = new LinkedList<>();

    }

    private void updateHover() {
        updateHoverBoxes();
        updateHoverMoves();
        updateCollidedPos();
    }

    private void updateHoverBoxes() {
        for (Node<Data> node : boxList) {
            if (node.data.box.isCollided(camera)) {
                collidedPos = node.data.box.collidedPos;
                if (node.equals(selectedNode)) {
                    return;
                }
                hoveredNode = node;
                return;
            }
        }

        hoveredNode = null;
    }

    private void updateHoverMoves() {
        for (Node<Move> node : availableMovesList) {
            if (node.data.getBox().isCollided(camera)) {
                collidedPos = node.data.getBox().collidedPos;
                hoveredMove = node;
                return;
            }
        }
        hoveredMove = null;
    }

    private void updateCollidedPos() {
        if (hoveredMove == null && hoveredNode == null) {
            collidedPos = null;
        }
    }

    public LevelEditorScene() {
    }

    @Override
    public void update(float dt) {
        camera.update(dt);
        updateHover();
        updateAvailableMoves();

    }

    private void updateAvailableMoves() {
        if (selectedNode != null) {

            Move pos;
            if ((pos = boxList.right(selectedNode)) != null) {
                availableMovesList.addLast(pos);
            }
            if ((pos = boxList.left(selectedNode)) != null) {
                availableMovesList.addLast(pos);
            }
            if ((pos = boxList.up(selectedNode)) != null) {
                availableMovesList.addLast(pos);
            }
            if ((pos = boxList.down(selectedNode)) != null) {
                availableMovesList.addLast(pos);
            }
        }
    }

    private void renderBox(ColoredBox box) {
        if (hoveredNode != null && box.equals(hoveredNode.data.box)) {
            box.renderWithColor(shader, camera, ColoredBox.hoveredColor);
        } else if (selectedNode != null && box.equals(selectedNode.data.box)) {
            box.renderWithColor(shader, camera, ColoredBox.selectedColor);
        } else {
            box.render(shader, camera);
        }
    }

    @Override
    public void render() {
        if (collidedPos != null) {
            new ColoredBox(collidedPos, .3f, new Vector3f(0, 0, 128f)).render(shader, camera);
        }

        for (Node<Data> node : boxList) {
            renderBox(node.data.box);
        }

        for (Node<Move> move : availableMovesList) {
            move.data.getBox().render(shader, camera);
        }

    }

    @Override
    public void onMouseMove() {
    }

    private void onMoveClicked() {
        if (hoveredMove != null) {
            hoveredMove.data.action();
        }
    }

    @Override
    public void onMouseClick() {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            if (hoveredNode != null) {
                selectedNode = hoveredNode;
                hoveredNode = null;
            }
            onMoveClicked();
        }

        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
            hoveredNode = null;
            hoveredMove = null;
            selectedNode = null;
        }

        availableMovesList.erase();

    }
}
