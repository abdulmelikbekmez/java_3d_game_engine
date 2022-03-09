package solo_game.dataStructures;

/**
 * Node
 */
public class Node {

    public Node next;
    public Node child;
    public Data data;

    public Node(Data data) {
        this.data = data;
    }

    public Node() {
    }

}
