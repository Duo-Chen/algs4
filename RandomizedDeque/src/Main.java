import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileInputStream;
import java.util.Iterator;

public class Main {
    public static void testPermutation(String[] args) {
        try {
            Permutation.main(args);
        } catch (Exception ex) {

        }
    }

    public static void testRandomUniform() {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        int[] count = new int[rq.size()];
        for (int i = 0; i < rq.size() * 1200; i++) {
            String ss = rq.sample();
            switch (ss) {
                case "A":
                    count[0]++;
                    break;
                case "B":
                    count[1]++;
                    break;
                case "C":
                    count[2]++;
                    break;
                case "D":
                    count[3]++;
                    break;
                case "E":
                    count[4]++;
                    break;
                case "F":
                    count[5]++;
                    break;
                case "G":
                    count[6]++;
                    break;
                case "H":
                    count[7]++;
                    break;
                case "I":
                    count[8]++;
                    break;
            }
        }

        for (int i = 0; i < 9; i++) {
            StdOut.println(count[i]);
        }
    }

    public static void testIterator(boolean isEnqueue) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        if (isEnqueue) {
            while (!StdIn.isEmpty()) {
                rq.enqueue(StdIn.readString());
            }
        }

        Iterator<String> iter = rq.iterator();
        while (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }

    public static void testQueue() {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        while (!rq.isEmpty()) {
            StdOut.println(rq.dequeue());
        }
    }

    public static void main(String[] args) {
        try {
            String folder = "C:\\Users\\Alienware17\\Downloads\\algorithms\\RandomizedDeque\\src\\";
            String[] wrapArgs = new String[1];
            wrapArgs[0] = "3";

            System.setIn(new FileInputStream(folder + "distinct.txt"));
            //testQueue();
            //testPermutation(wrapArgs);
            //testRandomUniform();
            testIterator(true);
        } catch (Exception ex) {
            StdOut.println(ex.toString());
        }
    }
}
