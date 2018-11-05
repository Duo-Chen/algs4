import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String[][] strings = {
                { "dictionary-algs4.txt", "board4x4.txt", "33" },
                { "dictionary-algs4.txt", "board-q.txt", "84" }
        };

        for (String[] str : strings) {
            BoggleSolver.main(str);
            StdOut.println();
        }
    }
}
