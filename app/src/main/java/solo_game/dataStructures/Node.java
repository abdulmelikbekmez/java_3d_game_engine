package solo_game.dataStructures;

/**
 * Node
 *
 * @param <T>
 */
public class Node<T> {

    public Node<T> next;
    public Node<T> child;
    public T data;

    public Node(T data) {
        this.data = data;
    }

    public Node() {
    }

}
