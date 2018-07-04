import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int count;

    private class Node {
        public Item data;
        public Node prev;
        public Node next;

        public Node(Item item) {
            data = item;
            prev = null;
            next = null;
        }
    }

    public Deque() {
        head = null;
        tail = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Invalid input");

        Node node = new Node(item);
        if (count == 0) {
            head = node;
            tail = node;
        } else {
            head.prev = node;
            node.next = head;
            head = node;
        }

        count++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Invalid iput");

        Node node = new Node(item);
        if (count == 0) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }

        count++;
    }

    public Item removeFirst() {
        Item item = null;

        if (count == 0)
            throw new NoSuchElementException("deque is empty");
        else if (count == 1) {
            item = head.data;
            head = null;
            tail = null;
        } else {
            item = head.data;
            head = head.next;
            head.prev = null;
        }

        count--;
        return item;
    }

    public Item removeLast() {
        Item item = null;

        if (count == 0)
            throw new NoSuchElementException("deque is empty");
        else if (count == 1) {
            item = head.data;
            head = null;
            tail = null;
        } else {
            item = tail.data;
            tail = tail.prev;
            tail.next = null;
        }

        count--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new OrderIterator();
    }

    private class OrderIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("can't remove");
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException("iterator has no next item");

            Item item = current.data;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        // test
    }
}
