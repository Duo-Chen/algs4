import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        BreadthFirstDirectedPaths bfd_v = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfd_w = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfd_v.hasPathTo(i) && bfd_w.hasPathTo(i)) {
                int dist = bfd_v.distTo(i) + bfd_w.distTo(i);
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
        BreadthFirstDirectedPaths bfd_v = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfd_w = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfd_v.hasPathTo(i) && bfd_w.hasPathTo(i)) {
                int dist = bfd_v.distTo(i) + bfd_w.distTo(i);
                if (dist < length)
                    x = i;
            }
        }

        return x;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths bfd_v = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfd_w = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfd_v.hasPathTo(i) && bfd_w.hasPathTo(i)) {
                int dist = bfd_v.distTo(i) + bfd_w.distTo(i);
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
        BreadthFirstDirectedPaths bfd_v = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfd_w = new BreadthFirstDirectedPaths(digraph, w);

        int length = Integer.MAX_VALUE;
        int x = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfd_v.hasPathTo(i) && bfd_w.hasPathTo(i)) {
                int dist = bfd_v.distTo(i) + bfd_w.distTo(i);
                if (dist < length)
                    x = i;
            }
        }

        return x;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
