import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

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

        detectRoot(G);

        sap = new SAP(G);
    }

    private void detectRoot(Digraph G) {
        SET<Integer> roots = new SET<Integer>();
        for (int i = 0; i < G.V(); i++) {
            if (!G.adj(i).iterator().hasNext()) {
                roots.add(i);
            }
        }

        if (roots.size() == 0 || roots.size() > 1)
            throw new IllegalArgumentException("Not single root");
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
        class TestWordNet {
            private final String synsets;
            private final String hypernyms;

            public TestWordNet(String s, String h) {
                this.synsets = s;
                this.hypernyms = h;
            }

            public void verify() {
                WordNet wn = new WordNet(synsets, hypernyms);
            }

            public String toString() {
                return synsets + " " + hypernyms;
            }
        }

        TestWordNet[] test = new TestWordNet[] {
            new TestWordNet("synsets3.txt", "hypernyms3InvalidTwoRoots.txt"),
            new TestWordNet("synsets6.txt", "hypernyms6InvalidTwoRoots.txt"),
            new TestWordNet("synsets6.txt", "hypernyms6InvalidCycle+Path.txt"),
        };


        for (TestWordNet t : test) {
            boolean hasException = false;
            try {
                t.verify();
            } catch (IllegalArgumentException ex) {
                hasException = true;
            } finally {
                if (!hasException)
                    StdOut.println(t + "must throw exception!");
            }
        }
    }
}
