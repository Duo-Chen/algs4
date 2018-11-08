import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    private Digraph graph;     // digraph of epsilon transitions
    private String regexp;     // regular expression
    private final int m;       // number of characters in regular expression

    public Main(String regexp) {
        this.regexp = regexp;
        m = regexp.length();
        Stack<Integer> ops = new Stack<>();
        graph = new Digraph(m+1);
        for (int i = 0; i < m; i++) {
            int lp = i;
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|')
                ops.push(i);
            else if (regexp.charAt(i) == ')') {
                int t = i;
                while (true) {
                    int or = ops.pop();
                    if (regexp.charAt(or) == '|') {
                        graph.addEdge(or, t);
                        t = or + 1;
                    }
                    else if (regexp.charAt(or) == '(') {
                        graph.addEdge(or, t);
                        lp = or;
                        break;
                    } else assert false;
                }
            }

            // closure operator (uses 1-character lookahead)
            if (i < m-1 && regexp.charAt(i+1) == '*') {
                graph.addEdge(lp, i+1);
                graph.addEdge(i+1, lp);
            }

            // + operator (uses 1-character lookahead)
            if (i < m-1 && regexp.charAt(i) == '+') {
                graph.addEdge(i, i+1);
                graph.addEdge(i-1, i);

                int j = i-1;
                while (j >= 0) {
                    if (regexp.charAt(j) == '(') {
                        graph.addEdge(i, j);
                        break;
                    }

                    j--;
                }
            }

            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')')
                graph.addEdge(i, i + 1);
        }
        if (ops.size() != 0)
            throw new IllegalArgumentException("Invalid regular expression");
    }

    public boolean recognizes(String txt) {
        DirectedDFS dfs = new DirectedDFS(graph, 0);
        Bag<Integer> pc = new Bag<>();
        for (int v = 0; v < graph.V(); v++)
            if (dfs.marked(v)) pc.add(v);

        // Compute possible NFA states for txt[i+1]
        for (int i = 0; i < txt.length(); i++) {
            if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || txt.charAt(i) == '(' || txt.charAt(i) == ')')
                throw new IllegalArgumentException("text contains the metacharacter '" + txt.charAt(i) + "'");

            Bag<Integer> match = new Bag<>();
            for (int v : pc) {
                if (v == m) continue;
                if ((regexp.charAt(v) == txt.charAt(i)) || regexp.charAt(v) == '.')
                    match.add(v+1);
            }
            dfs = new DirectedDFS(graph, match);
            pc = new Bag<>();
            for (int v = 0; v < graph.V(); v++)
                if (dfs.marked(v)) pc.add(v);

            // optimization if no states reachable
            if (pc.size() == 0) return false;
        }

        // check for accept state
        for (int v : pc)
            if (v == m) return true;
        return false;
    }

    public static void main(String[] args) {
        String[][] str = {
                { "AABAAB", "AABAAB" },
                { "AA|BAAB", "BAAB" },
                { "AB*A", "ABBBBBBBBA" },
                { "A(A|B)AAB", "AAAAB" },
                { "(AB)*A", "ABABABABABA" },
                { ".U.U.U.", "JUGULUM" }, // wildcard
                { "A(BC)+DE", "ABCBCDE" }, // + closure operator
                { "AA|BAAB|CD", "BAAB" }, // multiway or
        };

        for (String[] set : str) {
            String regexp = "(" + set[0] + ")";
            String txt = set[1];
            Main nfa = new Main(regexp);
            StdOut.println(regexp + " <--> " + txt + " : " + nfa.recognizes(txt));
        }
    }

}