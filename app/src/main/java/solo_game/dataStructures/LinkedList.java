
package solo_game.dataStructures;

/**
 * LinkedList
 */
public class LinkedList {
    public Node head;

    public void addFirst(Data data) {
        Node tmp = new Node(data);
        if (head != null) {
            tmp.next = head;
        }
        head = tmp;
    }

    public void addLast(Data data) {
        Node tmp = new Node(data);
        if (head == null) {
            head = tmp;
        } else {
            Node oldLast = head;
            while (oldLast.next != null) {
                oldLast = oldLast.next;
            }
            oldLast.next = tmp;
        }

    }

}
