import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    public BoggleSolver(String[] dictionary) {
        throw new IllegalArgumentException("not implemented");
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        throw new IllegalArgumentException("not implemented");
    }

    public int scoreOf(String word) {
        throw new IllegalArgumentException("not implemented");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dict = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dict);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
