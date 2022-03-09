package solo_game.dataStructures;

/**
 * LinkedList
 */
public class LinkedList {

    private Node head;

    public Node getHead() {
        return head;
    }

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


    public boolean deleteAfter(int row, int col) {
        if (head == null) {
            return false;
        }
        Node searched = head;
        boolean finded = false;
        while (searched.next != null) {
            if (searched.data.equal(row, col)) {
                finded = true;
                break;
            }
            searched = searched.next;
        }
        if (!finded || searched.next == null)
            return false;

        Node next = searched.next;
        if (next.next != null)
            searched.next = next.next;
        else
            searched.next = null;
        return true;
    }
}
