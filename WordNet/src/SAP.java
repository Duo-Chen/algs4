import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfdV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdW = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdV.hasPathTo(i) && bfdW.hasPathTo(i)) {
                int dist = bfdV.distTo(i) + bfdW.distTo(i);
                if (dist < length) {
                    x = i;
                    length = dist;
                }
            }
        }

        return x == -1 ? -1 : length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bfdV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdW = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdV.hasPathTo(i) && bfdW.hasPathTo(i)) {
                int dist = bfdV.distTo(i) + bfdW.distTo(i);
                if (dist < length) {
                    x = i;
                    length = dist;
                }
            }
        }

        return x;
    }

    private void verifyArg(Iterable<Integer> v) {
        if (v == null)
            throw new IllegalArgumentException("");

        for (Integer x : v)
            if (x == null)
                throw new IllegalArgumentException("");
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        verifyArg(v);
        verifyArg(w);

        BreadthFirstDirectedPaths bfdV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdW = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdV.hasPathTo(i) && bfdW.hasPathTo(i)) {
                int dist = bfdV.distTo(i) + bfdW.distTo(i);
                if (dist < length) {
                    x = i;
                    length = dist;
                }
            }
        }

        return x == -1 ? -1 : length;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        verifyArg(v);
        verifyArg(w);

        BreadthFirstDirectedPaths bfdV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfdW = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfdV.hasPathTo(i) && bfdW.hasPathTo(i)) {
                int dist = bfdV.distTo(i) + bfdW.distTo(i);
                if (dist < length) {
                    x = i;
                    length = dist;
                }
            }
        }

        return x;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        class SapTest
        {
            private final String file;
            private final int v;
            private final int w;
            private final int length;
            private final int ancestor;
            private final SAP sap;

            public SapTest(String file, int v, int w, int length, int ancestor) {
                this.file = file;
                this.v = v;
                this.w = w;
                this.length = length;
                this.ancestor = ancestor;

                In in = new In(this.file);
                Digraph G = new Digraph(in);
                sap = new SAP(G);
            }

            public boolean isCorrect() {
                boolean res = sap.length(v, w) == length
                        && sap.ancestor(v, w) == ancestor;

                if (!res)
                    StdOut.println("SAP.java " + file + " failed!");
                return res;
            }
        }

        SapTest[] tests = new SapTest[] {
                new SapTest("digraph2.txt", 2, 3, 1, 3),
                new SapTest("digraph3.txt", 9, 0, 3, 11),
                new SapTest("digraph4.txt", 3, 2, 1, 3),
                new SapTest("digraph5.txt", 9, 9, 0, 9),
                new SapTest("digraph6.txt", 7, 1, 3, 3),
                new SapTest("digraph9.txt", 7, 5, 4, 4),
        };

        for (SapTest tester : tests) {
           tester.isCorrect();
        }
    }
}
