import edu.princeton.cs.algs4.StdOut;

public class Successor {
    private int[] seq;

    public Successor(int n) {
        seq = new int[n];

        for (int i = 0; i < n; i++) {
            seq[i] = i;
        }
    }

    public void Remove(int x) {
        seq[x] = Find(x + 1);
    }

    public int Find(int p) {
        int n = seq.length;
        if (p == n)
            return n;
        if (p < 0)
            return -1;
        else if (p == seq[p])
            return p;
        else
            return seq[p] = Find(p + 1);
    }

    public void Print() {
        for (int i = 0; i < seq.length; i++) {
            StdOut.println(i + " : " + seq[i]);
        }
    }
}
