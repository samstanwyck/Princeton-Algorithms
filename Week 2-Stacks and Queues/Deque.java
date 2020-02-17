import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node forward;
        Node backward;
    }

    public Deque() {
        first = null;
        last = null;
        size = 0;

    }


    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert a null item");
        }
        Node old = first;
        first = new Node();
        first.item = item;
        first.forward = old;
        first.backward = null;
        size++;
        if (size == 1) {
            last = first;
        }
        else {
            old.backward = first;
        }
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert a null item");
        }

        Node old = last;
        last = new Node();
        last.item = item;
        last.backward = old;
        last.forward = null;
        size++;
        if (size == 1) {
            first = last;
        }
        else {
            old.forward = last;
        }
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove from empty list");
        }
        Item out = first.item;

        if (size() == 1) {
            first = null;
            last = null;
            size--;
            return out;
        }

        first = first.forward;
        first.backward = null;
        size--;
        return out;

    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException(("Cannot remove from empty list"));
        }
        Item out = last.item;

        if (size() == 1) {
            first = null;
            last = null;
            size--;
            return out;
        }
        last = last.backward;
        last.forward = null;
        size--;
        return out;


    }

    public Iterator<Item> iterator() {
        return new DequeIterator();

    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            try {
                Node attempt = current.forward;
            }
            catch (NullPointerException e) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.forward;
            return item;

        }

    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.isEmpty();
        deque.addFirst("test1");
        deque.addFirst("test2");
        deque.addLast("test3");
        String value = deque.removeFirst();
        System.out.println(value);
        String value2 = deque.removeLast();
        System.out.println(value2);
        for (String s : deque) {
            System.out.println(s);
        }

    }
}

