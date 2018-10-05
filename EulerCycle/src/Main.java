import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
	// write your code here
        GraphEX G = new GraphEX(9);
        G.addEdge(0, 1);
        G.addEdge(0, 5);
        G.addEdge(0, 7);
        G.addEdge(0, 8);
        G.addEdge(1, 2);
        G.addEdge(1, 3);
        G.addEdge(1, 7);
        G.addEdge(2, 3);
        G.addEdge(4, 6);
        G.addEdge(4, 7);
        G.addEdge(5, 8);
        G.addEdge(6, 7);

        Iterable<Integer> cycle = eulerCycle(G);
        StringBuilder s = new StringBuilder();
        for (int p : cycle) {
            s.append(Integer.toString(p) + ", ");
        }

        StdOut.println(s.substring(0, s.length() - 2));
    }

    private static Iterable<Integer> eulerCycle(GraphEX G) {
        Stack<Integer> cycle = new Stack<Integer>();

        if (G.E() == 0)
            return null;

        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) % 2 != 0)
                return null;

        int p = 0;
        Stack<Integer> temp = new Stack<Integer>();
        while (G.E() != 0 || temp.size() != 0) {
            SET<Integer> set = G.adj(p);
            if (set.size() == 0) {
                int v = temp.pop();
                cycle.push(p);
                p = v;
            } else {
                int v = set.iterator().next();
                temp.push(p);
                G.delEdge(p, v);
                p = v;
            }
        }

        return cycle;
    }
}
