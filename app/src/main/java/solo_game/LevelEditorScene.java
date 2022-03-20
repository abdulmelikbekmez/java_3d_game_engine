package solo_game;

import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import javax.swing.JOptionPane;

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
    private int moveCount;

    public LevelEditorScene(int n) {
        N = n;
        camera = new Camera(new Vector3f(0, 0, 20));
        moveCount = 0;

        MouseListener.addMouseHandler(camera);
        MouseListener.addMouseHandler(this);
        shader = new Shader("coloredVertex.glsl", "coloredFragment.glsl");

        boxList = new MultiLinkedList(N);
        availableMovesList = new LinkedList<>();
        setAvailableMoveCount();
    }

    public LevelEditorScene() {
        this(N);
    }

    @Override
    public void init() {
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

    @Override
    public void update(float dt) {
        camera.update(dt);
        updateHover();
        updateAvailableMoves();
    }

    private void setAvailableMoveCount() {
        moveCount = 0;

        for (Node<Data> node : boxList) {

            Move pos;
            if ((pos = boxList.right(node)) != null)
                moveCount++;

            if ((pos = boxList.left(node)) != null)
                moveCount++;

            if ((pos = boxList.up(node)) != null)
                moveCount++;

            if ((pos = boxList.down(node)) != null)
                moveCount++;

        }

        if (moveCount == 0)
            onMoveCountZero();

    }

    private void onMoveCountZero() {
        Window.enableCursor();
        int answer = JOptionPane.showConfirmDialog(null, "Game finished, To restart press yes", "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            boxList = new MultiLinkedList(Window.getBoxCount());
            resetHover();
            Window.disableCursor();
            availableMovesList.erase();
        } else {
            Window.setWindowShouldClose();
        }

    }

    private void updateAvailableMoves() {
        if (selectedNode == null)
            return;

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

    @Override
    public void onMouseClick() {
        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            onSelectedClicked();
            onMoveClicked();
        }

        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
            resetHover();
        }

        availableMovesList.erase();

    }

    private void resetHover() {
        hoveredNode = null;
        hoveredMove = null;
        selectedNode = null;
    }

    private void onSelectedClicked() {
        if (hoveredNode != null) {
            selectedNode = hoveredNode;
            hoveredNode = null;
        }
    }

    private void onMoveClicked() {
        if (hoveredMove != null) {
            hoveredMove.data.action();
            setAvailableMoveCount();
        }
    }
}
