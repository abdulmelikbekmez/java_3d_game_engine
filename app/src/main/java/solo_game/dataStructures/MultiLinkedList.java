package solo_game.dataStructures;

import java.util.Iterator;

import org.joml.Vector3f;
import solo_game.ColoredBox;
import solo_game.LevelEditorScene;

public class MultiLinkedList implements Iterable<Node<Data>> {

    private Node<Data> head;

    public static int xMargin = 2;
    public static int yMargin = 2;

    public MultiLinkedList(int n) {
        Node<Data> lastChild = null;
        Node<Data> lastNext = null;

        for (int x = 0; x < n; x++) {
            for (int y = n - 1; y >= 0; y--) {
                Data d = new Data(x + 1, y + 1,
                        new ColoredBox(new Vector3f((x - n / 2) * xMargin, (y - n / 2) * yMargin, 0)));
                Node<Data> toAdd = new Node<>(d);
                if (head == null) {
                    head = toAdd;
                    lastChild = head;
                    lastNext = head;
                } else if (y == n - 1) {
                    lastChild.child = toAdd;
                    lastNext = toAdd;
                    lastChild = toAdd;
                } else {
                    lastNext.next = toAdd;
                    lastNext = toAdd;
                }
            }
        }

        deleteAfter((n / 2) + 1, n / 2 + 2);
        deleteAfter((n / 2) + 1, n / 2 + 2);
        deleteAfter((n / 2), n / 2 + 2);
        deleteAfter((n / 2), n / 2 + 2);
    }

    public final boolean deleteAfter(int row, int col) {
        Node<Data> searched;
        for (Node<Data> node : this) {
            if (!node.data.equal(row, col)) {
                continue;
            }

            searched = node;
            if (searched.next == null) {
                return false;
            }

            Node<Data> next = searched.next;
            if (next.next != null) {
                searched.next = next.next;
            } else {
                searched.next = null;
            }
            return true;
        }
        return false;
    }

    public final boolean add(Node<Data> nodeToAdd) {

        if (head == null) {
            head = nodeToAdd;
            return true;
        }

        // find parent
        Node<Data> parent = head;
        while (parent != null && nodeToAdd.data.x != parent.data.x) {
            parent = parent.child;
        }

        // there is no parent
        if (parent == null) {
            // left
            if (head.data.x > nodeToAdd.data.x) {
                nodeToAdd.child = head;
                head = nodeToAdd;
                return true;
            }

            parent = head;
            while (parent.child != null && parent.child.data.x < nodeToAdd.data.x) {
                parent = parent.child;
            }

            if (parent.child != null) {
                // middle
                nodeToAdd.child = parent.child;
                parent.child = nodeToAdd;
            } else {
                // right
                parent.child = nodeToAdd;

            }

            return true;
        }

        // top of column
        if (nodeToAdd.data.y > parent.data.y) {
            Node<Data> parentOfParent = null;
            for (Node<Data> node : this) {
                if (node.child != null && node.child.equals(parent)) {
                    parentOfParent = node;
                }
            }

            if (parentOfParent != null) {
                parentOfParent.child = nodeToAdd;
                nodeToAdd.next = parent;
            } else {
                nodeToAdd.next = parent;
            }

            if (parent.child != null) {
                nodeToAdd.child = parent.child;
            }

            return true;
        }

        while (parent.next != null && parent.next.data.y > nodeToAdd.data.y) {
            parent = parent.next;
        }

        if (parent.next != null) {
            nodeToAdd.next = parent.next;
            parent.next = nodeToAdd;
        } else {
            parent.next = nodeToAdd;
        }

        return true;
    }

    public final boolean delete(Node<Data> nodeToDelete) {
        Node<Data> beforeNext = null;
        Node<Data> parent = null;

        if (nodeToDelete.equals(head)) {

            if (head.next != null) {
                if (head.child != null) {
                    head.next.child = head.child;
                }

                head = head.next;
            } else {
                if (head.child != null) {
                    head = head.child;
                } else {
                    head = null;
                }

            }

        }

        for (Node<Data> node : this) {
            if (node.next != null && node.next.equals(nodeToDelete)) {
                beforeNext = node;
                break;
            }
            if (node.child != null && node.child.equals(nodeToDelete)) {
                parent = node;
                break;
            }
        }

        if (beforeNext == null && parent == null) {
            return false;
        }

        if (beforeNext != null) {

            // middle
            if (nodeToDelete.next != null) {
                beforeNext.next = nodeToDelete.next;
            } // end
            else {
                beforeNext.next = null;
            }

        } else if (parent != null) {
            if (nodeToDelete.next != null) {
                parent.child = nodeToDelete.next;

                if (nodeToDelete.child != null) {
                    parent.child.child = nodeToDelete.child;
                }

            } else {
                if (nodeToDelete.child != null) {
                    parent.child = nodeToDelete.child;

                } else {
                    parent.child = null;
                }
            }

        }
        nodeToDelete.next = null;
        nodeToDelete.child = null;
        return true;

    }

    public Move right(Node<Data> base) {
        Node<Data> next = null;
        for (Node<Data> node : this) {
            if (node.data.equal(base.data.x + 1, base.data.y)) {
                next = node;
                break;
            }
        }
        // if there is no next return null
        if (next == null) {
            return null;
        }

        // if next has other next return null
        for (Node<Data> node : this) {
            if (node.data.equal(next.data.x + 1, next.data.y)) {
                return null;
            }
        }

        // if next is last element return null
        if (next.data.x == LevelEditorScene.N) {
            return null;
        }

        return new Move(next, base, next.data.box.getPos().add(2, 0, 0), this);
    }

    public Move left(Node<Data> base) {
        Node<Data> next = null;
        for (Node<Data> node : this) {
            if (node.data.equal(base.data.x - 1, base.data.y)) {
                next = node;
                break;
            }
        }
        // if there is no next return null
        if (next == null) {
            return null;
        }

        // if next has other next return null
        for (Node<Data> node : this) {
            if (node.data.equal(next.data.x - 1, next.data.y)) {
                return null;
            }
        }

        // if next is last element return null
        if (next.data.x == 1) {
            return null;
        }
        return new Move(next, base, next.data.box.getPos().add(-2, 0, 0), this);
    }

    public Move up(Node<Data> base) {
        Node<Data> next = null;
        for (Node<Data> node : this) {
            if (node.data.equal(base.data.x, base.data.y + 1)) {
                next = node;
                break;
            }
        }
        // if there is no next return null
        if (next == null) {
            return null;
        }

        // if next has other next return null
        for (Node<Data> node : this) {
            if (node.data.equal(next.data.x, next.data.y + 1)) {
                return null;
            }
        }

        // if next is last element return null
        if (next.data.y == LevelEditorScene.N) {
            return null;
        }

        return new Move(next, base, next.data.box.getPos().add(0, 2, 0), this);
    }

    public Move down(Node<Data> base) {
        Node<Data> next = null;
        for (Node<Data> node : this) {
            if (node.data.equal(base.data.x, base.data.y - 1)) {
                next = node;
                break;
            }
        }
        // if there is no next return null
        if (next == null) {
            return null;
        }

        // if next has other next return null
        for (Node<Data> node : this) {
            if (node.data.equal(next.data.x, next.data.y - 1)) {
                return null;
            }
        }

        // if next is last element return null
        if (next.data.y == 1) {
            return null;
        }

        return new Move(next, base, next.data.box.getPos().add(0, -2, 0), this);
    }

    @Override
    public Iterator<Node<Data>> iterator() {

        return new Iterator<>() {
            Node<Data> lastNext = null;
            Node<Data> lastChild = null;
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
                return lastNext.next != null || lastChild.child != null;
            }

            @Override
            public Node<Data> next() {
                if (lastNext == null && head != null) {
                    lastNext = lastChild = head;
                    return head;
                }
                if (lastNext.next == null) {

                    // lastChild.child is valid because of hasNext method
                    Node<Data> n = lastChild.child;
                    lastNext = lastChild = n;
                    return n;
                }

                Node<Data> n = lastNext.next;
                lastNext = n;
                return n;
            }
        };
    }
}
