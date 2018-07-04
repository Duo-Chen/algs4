import edu.princeton.cs.algs4.StdOut;

public class QuickUnion {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components

    public QuickUnion(int n) {
        parent = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        validate(p);

        int max = p;
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
            if (max < parent[p])
                max = parent[p];
        }

        return max;
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;

        if (rootP > rootQ)
            parent[rootQ] = rootP;
        else
            parent[rootP] = rootQ;

        StdOut.println("root is " + rootQ);
        count--;
    }

    public void print() {
        for (int i = 0; i < parent.length; i++) {
            StdOut.println(i + ", " + parent[i]);
        }
    }
}