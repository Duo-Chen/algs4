import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] length = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            for (int j = 0; j < nouns.length; j++) {
                if (i == j)
                    continue;

                int dist = wordnet.distance(nouns[i], nouns[j]);
                length[i] += dist;
                length[j] += dist;
            }
        }

        int max = -1;
        int p = -1;
        for (int i = 0; i < nouns.length; i++) {
            if (length[i] > max) {
                max = length[i];
                p = i;
            }
        }

        return nouns[p];
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int i = 2; i < args.length; i++) {
            In in = new In(args[i]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[i] + " : " + outcast.outcast(nouns));
        }
    }
}
