import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.Arrays;


public class BoggleSolver {
    private final TST<Integer> dict;
    private BoggleBoard board;
    private SET<String> validStrings;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException("");

        dict = new TST<>();
        for (String str : dictionary) {
            if (str.length() < 3)
                continue;

            dict.put(str, length2point(str.length()));
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null)
            throw new IllegalArgumentException("");

        this.board = board;
        validStrings = new SET<>();
        int col = board.cols();
        int row = board.rows();

        for (int x = 0; x < col; x++) {
            for (int y  = 0; y < row; y++) {
                boolean[][] marked = new boolean[col][row];
                findValidString(marked, x, y, new String());
            }
        }

        return validStrings;
    }

    public boolean[][] deepCopy(boolean[][] original) {
        if (original == null) {
            return null;
        }

        final boolean[][] result = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    private void findValidString(boolean[][] marked, int x, int y, String str) {
        if (marked[x][y])
            return;

        char c = board.getLetter(y, x);
        if (c == 'Q')
            str += "QU";
        else
            str += c;

        if (!dict.keysWithPrefix(str).iterator().hasNext())
            return;

        if (str.length() > 2 && dict.contains(str))
            validStrings.add(str);

        boolean[][] newMarked = deepCopy(marked);
        newMarked[x][y] = true;

        if (x > 0) // left
            findValidString(newMarked, x - 1, y, str);
        if (x < board.cols() - 1) // right
            findValidString(newMarked, x + 1, y, str);
        if (y > 0) // top
            findValidString(newMarked, x, y - 1, str);
        if (y < board.rows() - 1) // bottom
            findValidString(newMarked, x, y + 1, str);
        if (x > 0 && y > 0) // top-left
            findValidString(newMarked, x - 1, y - 1, str);
        if (x < board.cols() - 1 && y > 0) // top-right
            findValidString(newMarked, x + 1, y - 1, str);
        if (x > 0 && y < board.rows() - 1) // bottom-left
            findValidString(newMarked, x - 1, y + 1, str);
        if (x < board.cols() - 1 && y <board.rows() - 1) // bottom-right
            findValidString(newMarked, x + 1, y + 1, str);
    }

    private int length2point(int length) {
        if (length < 3)
            return 0;
        else if (length < 5)
            return 1;
        else if (length < 7)
            return length - 3;
        else if (length < 8)
            return 5;
        else
            return 11;
    }

    public int scoreOf(String word) {
        if (word == null)
            throw new IllegalArgumentException("");

        if (word.length() < 3)
            return 0;

        if (dict.contains(word))
            return dict.get(word);

        return 0;
    }

    public static void main(String[] args) {
        String[][] test = {
                { "board4x4.txt", "1" },
                { "board-q.txt", "3" },
                { "board-16q.txt", "0" }
        };

        String[] dict = { "SIT", "QUERIES", "EQUATION", "STATION" };
        BoggleSolver solver = new BoggleSolver(dict);

        for (String[] str : test) {
            BoggleBoard board = new BoggleBoard(str[0]);
            int count = 0;
            for (String s : solver.getAllValidWords(board)) {
                StdOut.println(s);
                count++;
            }

            if (count != Integer.parseInt(str[1]))
                throw new IllegalArgumentException("Wrong answer with " + str[0]);
        }
    }
}
