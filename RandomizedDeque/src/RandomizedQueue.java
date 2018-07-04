import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int count;
    private int numSlots;
    private Item[] slots;

    public RandomizedQueue() {
        count = 0;
        numSlots = 1;
        slots = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Invalid input");

        if (count == numSlots)
            resize(numSlots * 2);

        slots[count++] = item;
    }

    public Item dequeue() {
        if (count == 0)
            throw new NoSuchElementException("randomized queue is empty");

        randomSwap(slots, count);
        Item item = slots[--count];
        slots[count] = null;

        if (count < numSlots / 4)
            resize(numSlots / 2);

        return item;
    }

    public Item sample() {
        if (count == 0)
            throw new NoSuchElementException("randomized queue is empty");

        return slots[randomSwap(slots, count)];
    }

    private int randomSwap(Item[] refItems, int refSize) {
        int x = StdRandom.uniform(refSize);
        if (x < refSize - 1) {
            Item t = refItems[x];
            refItems[x] = refItems[refSize - 1];
            refItems[refSize - 1] = t;
        }

        return x;
    }

    private void resize(int n) {
        Item[] t = (Item[]) new Object[n];
        for (int i = 0; i < count; i++) {
            t[i] = slots[i];
        }

        numSlots = n;
        slots = t;
    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        private final Item[] items;
        private int index;

        public RandomIterator() {
            items = (Item[]) new Object[count];
            index = 0;

            if (count == 0)
                return;

            items[0] = slots[0];
            for (int i = 1; i < count; i++) {
                items[i] = slots[i];
                randomSwap(items, i + 1);
            }
        }

        public boolean hasNext() {
            return index < items.length && items.length != 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("can't remove");
        }

        public Item next() {
            if (index == items.length || items.length == 0)
                throw new NoSuchElementException("iterator has no next item");
            Item item = items[index];
            items[index] = null;
            index++;
            return item;
        }
    }

    public static void main(String[] args) {
        // test
    }
}
