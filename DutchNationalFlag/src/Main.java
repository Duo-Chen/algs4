import edu.princeton.cs.algs4.StdOut;

public class Main {
    private String[] buckets;

    public Main(String[] input) {
        buckets = new String[input.length];
        for (int i = 0; i < input.length; i++) {
            buckets[i] = input[i];
        }
    }

    public void sort() {
        int lo = 0;
        int mid = 0;
        int hi = buckets.length - 1;

        while (mid <= hi) {
            switch (color(mid)) {
                case -1:
                {
                    swap(lo, mid);
                    lo++;
                    mid++;
                }
                    break;

                case 0:
                {
                    mid++;
                }
                    break;

                case 1:
                {
                    swap(mid, hi);
                    hi--;
                }
                    break;
            }
        }
    }

    private int color(int i) {
        if (buckets[i] == "r")
            return -1;
        if (buckets[i] == "w")
            return 0;

        return 1; // "b"
    }

    private void swap(int i, int j) {
        String t = buckets[i];
        buckets[i] = buckets[j];
        buckets[j] = t;
    }

    public void print() {
        for (int i = 0; i < buckets.length; i++) {
            StdOut.print(buckets[i] + " ");
        }

        StdOut.println();
    }

    public static void main(String[] args) {
	// write your code here
        String[] input = {"r", "w", "w", "r", "w", "b", "w", "b", "r", "r", "r", "w"};
        Main inst = new Main(input);
        inst.print();
        inst.sort();
        inst.print();
    }
}
