import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;

public class WordNet {
    private final SAP sap;
    private final RedBlackBST<String, SET<Integer>> bst;
    private final String[] nodes;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        In inSynsets = new In(synsets);
        String[] sets = inSynsets.readAllLines();
        nodes = new String[sets.length];
        bst = new RedBlackBST<>();
        for (String line : sets) {
            String[] strs = line.split(",");
            int id = Integer.parseInt(strs[0]);
            nodes[id] = strs[1];
            String[] nouns = strs[1].split(" ");
            for (String noun : nouns) {
                if (!bst.contains(noun))
                    bst.put(noun, new SET<>());

                bst.get(noun).add(id);
            }
        }

        In inHypernyms = new In(hypernyms);
        String[] lines = inHypernyms.readAllLines();
        Digraph G = new Digraph(nodes.length);
        for (String line : lines) {
            String[] strIDs = line.split(",");
            int id = Integer.parseInt(strIDs[0]);
            for (int i = 1; i < strIDs.length; i++) {
                G.addEdge(id, Integer.parseInt(strIDs[i]));
            }
        }

        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle())
            throw new IllegalArgumentException("No cycle");

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

        SET<Integer> idA = bst.get(nounA);
        SET<Integer> idB = bst.get(nounB);
        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!bst.contains(nounA) || !bst.contains(nounB))
            throw new IllegalArgumentException("WordNet.sap()");

        SET<Integer> idA = bst.get(nounA);
        SET<Integer> idB = bst.get(nounB);
        int id = sap.ancestor(idA, idB);
        return nodes[id];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn1 = new WordNet("synsets8.txt", "hypernyms8ManyAncestors.txt");
        WordNet wn2 = new WordNet("synsets11.txt", "hypernyms11ManyPathsOneAncestor.txt");
        WordNet wn3 = new WordNet("synsets8.txt", "hypernyms8WrongBFS.txt");
        WordNet wn5 = new WordNet("synsets15.txt", "hypernyms15Tree.txt");
        WordNet wn6 = new WordNet("synsets15.txt", "hypernyms15Path.txt");
    }
}
