import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;

public class WordNet {
    private final SAP sap;

    private static class Node {
        public final String synsets;
        public final int id;
        public final String gloss;
        public Node(String synsets, int id, String b) {
            this.synsets = synsets;
            this.id = id;
            gloss = b;
        }
    }

    private final RedBlackBST<String, Node> bst;
    private final Node[] nodes;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inSynsets = new In(synsets);
        String[] sets = inSynsets.readAllLines();
        nodes = new Node[sets.length];
        bst = new RedBlackBST<>();
        for (String line : sets) {
            String[] strs = line.split(",");
            int id = Integer.parseInt(strs[0]);
            Node node = new Node(strs[1], id, strs[2]);
            String[] nouns = strs[1].split(" ");
            for (String noun : nouns)
                bst.put(noun, node);

            nodes[id] = node;
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
        return bst.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return bst.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!bst.contains(nounA) || !bst.contains(nounB))
            throw new IllegalArgumentException("WordNet.distance()");

        int idA = bst.get(nounA).id;
        int idB = bst.get(nounB).id;
        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!bst.contains(nounA) || !bst.contains(nounB))
            throw new IllegalArgumentException("WordNet.sap()");

        int idA = bst.get(nounA).id;
        int idB = bst.get(nounB).id;
        int id = sap.ancestor(idA, idB);
        return nodes[id].synsets;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        throw new IllegalArgumentException("not yet implemented");
    }
}
