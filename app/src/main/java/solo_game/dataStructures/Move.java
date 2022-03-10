package solo_game.dataStructures;

import org.joml.Vector3f;
import solo_game.ColoredBox;
import solo_game.LevelEditorScene;

/**
 * @author abdulmelik
 */
public class Move {

    private final Node<Data> deleteNode;
    private final Node<Data> baseNode;

    private final MultiLinkedList mainList;

    private final ColoredBox box;

    public Move(Node<Data> deleteNode, Node<Data> baseNode, ColoredBox box, MultiLinkedList mainList) {
        this.deleteNode = deleteNode;
        this.baseNode = baseNode;
        this.box = box;
        this.mainList = mainList;
    }

    public Move(Node<Data> deleteNode, Node<Data> baseNode, Vector3f pos, MultiLinkedList mainList) {
        this.deleteNode = deleteNode;
        this.baseNode = baseNode;
        this.box = new ColoredBox(pos, new Vector3f(255, 255, 255));
        this.mainList = mainList;
    }

    public Node<Data> getDeleteNode() {
        return deleteNode;
    }

    public ColoredBox getBox() {
        return box;
    }

    public void action() {
        mainList.delete(deleteNode);
        mainList.delete(baseNode);
        moveBox();
        if (!mainList.add(baseNode)) {
            System.out.println("hataaa!!!");
        }
;

    }

    private void moveBox() {
        baseNode.data.box.setPos(box.getPos());
        baseNode.data.x = (int) (box.getPos().x / mainList.xMargin + 1 + (LevelEditorScene.N / 2));
        baseNode.data.y = (int) (box.getPos().y / mainList.yMargin + 1 + (LevelEditorScene.N / 2));

    }

}
