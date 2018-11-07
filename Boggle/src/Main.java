import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
	// write your code here
        BoggleSolver.main(null);

        String[][] strings = {
                { "dictionary-algs4.txt", "board4x4.txt", "33" },
                { "dictionary-algs4.txt", "board-q.txt", "84" },
                { "dictionary-16q.txt", "board-16q.txt", "147" }
        };

        for (String[] str : strings) {
            In in = new In(str[0]);
            String[] dict = in.readAllStrings();
            BoggleSolver solver = new BoggleSolver(dict);
            BoggleBoard board = new BoggleBoard(str[1]);
            int score = 0;
            for (String word : solver.getAllValidWords(board)) {
                StdOut.println(word);
                score += solver.scoreOf(word);
            }
            StdOut.println("Score = " + score);
            if (score != Integer.parseInt(str[2]))
                throw new IllegalArgumentException("Wrong answer");
            StdOut.println();
        }

        In in = new In("dictionary-yawl.txt");
        BoggleSolver solver = new BoggleSolver(in.readAllStrings());
        BoggleBoard board = new BoggleBoard("board-q.txt");
        long count = 0;
        long t = 0;
        long s = System.currentTimeMillis();
        do {
            solver.getAllValidWords(board);
            count++;
            t = System.currentTimeMillis();
        } while (t - s < 5000);

        double callPerSecond = ((double)count) / ((double)(t - s)) * 1000.0;
        StdOut.println(callPerSecond);
    }
}
