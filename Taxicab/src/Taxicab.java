import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Taxicab implements Comparable<Taxicab> {
    private final int i;
    private final int j;
    public final long sum;

    public Taxicab(int i, int j) {
        this.sum = (long) i * i * i + (long) j * j * j;
        this.i = i;
        this.j = j;
    }

    public int compareTo(Taxicab that) {
        if (this.sum < that.sum)
            return -1;
        else if (this.sum > that.sum)
            return 1;
        else if (this.i < that.i)
            return -1;
        else if (this.i > that.i)
            return 1;
        else
            return 0;
    }

    public String toString() {
        return i + "^3 + " + j + "^3";
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int root = (int) Math.pow(n, 1.0 / 3.0);

        MinPQ<Taxicab> pq = new MinPQ<>();
        for (int i = 1; i < root; i++)
            for (int j = i + 1; j <= root; j++)
                pq.insert(new Taxicab(i, j));

        while (!pq.isEmpty()) {
            Taxicab min = pq.delMin();
            int count = 1;
            Stack<Taxicab> buff = new Stack<>();
            while (!pq.isEmpty() && min.sum == pq.min().sum) {
                count++;
                buff.push(pq.delMin());
            }

            if (count != 2)
                continue;

            StdOut.println(min.sum + " = " + min + " = " + buff.pop());
        }
    }
}
