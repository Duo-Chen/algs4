import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private final SAP sap;

    private static class Node {
        public final String synset;
        public final String gloss;
        public Node(String a, String b) {
            synset = a;
            gloss = b;
        }
    }

    private final Node[] nodes;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inSynsets = new In(synsets);
        String[] sets = inSynsets.readAllLines();
        nodes = new Node[sets.length];
        for (String line : sets) {
            String[] strs = line.split(",");
            int id = Integer.parseInt(strs[0]);
            nodes[id] = new Node(strs[1], strs[2]);
        }

        In inHypernyms = new In(hypernyms);
        String[] lines = inHypernyms.readAllLines();
        Digraph G = new Digraph(lines.length);
        for (String line : lines) {
            String[] strIDs = line.split(",");
            int id = Integer.parseInt(strIDs[0]);
            for (int i = 1; i < strIDs.length; i++) {
                G.addEdge(id, Integer.parseInt(strIDs[i]));
            }
        }

        sap = new SAP(G);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        throw new IllegalArgumentException("not yet implemented");
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        throw new IllegalArgumentException("not yet implemented");
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        throw new IllegalArgumentException("not yet implemented");
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        throw new IllegalArgumentException("not yet implemented");
    }

    // do unit testing of this class
    public static void main(String[] args) {
        throw new IllegalArgumentException("not yet implemented");
    }
}
