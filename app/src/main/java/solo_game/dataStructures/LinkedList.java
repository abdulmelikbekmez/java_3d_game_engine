package solo_game.dataStructures;

import java.util.Iterator;

public class LinkedList<T> implements Iterable<Node<T>> {

    protected Node<T> head;

    public Node<T> getHead() {
        return head;
    }

    private void addFirst(T data) {
        Node<T> tmp = new Node<>(data);
        if (head != null) {
            tmp.next = head;
        }
        head = tmp;
    }

    public void erase() {
        head = null;
    }

    public void addLast(T data) {
        Node<T> tmp = new Node<>(data);
        if (head == null) {
            head = tmp;
        } else {
            Node<T> oldLast = head;
            while (oldLast.next != null) {
                oldLast = oldLast.next;
            }
            oldLast.next = tmp;
        }
    }


    @Override
    public Iterator<Node<T>> iterator() {

        return new Iterator<>() {
            Node<T> lastNext = null;
            boolean started = false;

            @Override
            public boolean hasNext() {
                if (head == null) {
                    return false;
                }
                if (!started) {
                    started = true;
                    return true;
                }
                return lastNext.next != null;
            }

            @Override
            public Node<T> next() {
                if (lastNext == null && head != null) {
                    lastNext = head;
                    return head;
                }

                Node<T> n = lastNext.next;
                lastNext = n;
                return n;
            }
        };
    }
}
