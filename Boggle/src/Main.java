public class Main {

    public static void main(String[] args) {
	// write your code here
        String[][] strings = {
                { "dictionary-algs4.txt", "board4x4.txt" },
                { "dictionary-algs4.txt", "board-q.txt" }
        };

        for (String[] str : strings)
            BoggleSolver.main(str);
    }
}
