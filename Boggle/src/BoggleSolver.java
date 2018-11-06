import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TST;

import java.util.HashMap;

public class BoggleSolver {
    private final TST<Integer> dict;
    private HashMap<Character, SET<Integer>> hash;
    private int max_length;

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

        SET<String> res = new SET<>();
        int col = board.cols();
        int row = board.rows();
        createHash(board, col, row);

        for (String str : dict.keys()) {
            int points = countPoints(str, col, row);
            if (points > 0) {
                dict.put(str, points);
                res.add(str);
            }
        }

        return res;
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

    private int countPoints(String str, int col, int row) {
        if (str.length() > max_length)
            return 0;

        int points = length2point(str.length());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!hash.containsKey(c))
                return 0;

            if (c == 'Q')
                if (i + 1 < str.length())
                    if (str.charAt(i + 1) == 'U')
                        i++;
                    else
                        return 0;
                else
                    return 0;

            sb.append(c);
        }

        if (isValidPath(sb.toString(), 0, new SET<>(), col, row))
            return points;

        return 0;
    }

    private boolean isValidPath(String str, int s, SET<Integer> set, int col, int row) {
        if (str.length() == 0)
            return true;
        else if (set.size() == col * row)
            return false;

        char c = str.charAt(0);
        SET<Integer> next = hash.get(c);
        if (next == null)
            return false;

        if (set.isEmpty()) {
            for (int v : next) {
                SET<Integer> newSet = new SET<>(set);
                newSet.add(v);
                if (isValidPath(str.substring(1), v, newSet, col, row))
                    return true;
            }
        } else {
            for (int t : next) {
                int tX = t % col;
                int tY = t / col;
                int sX = s % col;
                int sY = s / col;
                int dX = Math.abs(tX - sX);
                int dY = Math.abs(tY - sY);
                if ((dX == 0 && dY == 1)
                        || (dX == 1 && dY == 0)
                        || (dX == 1 && dY == 1)) {
                    if (!set.contains(t)) {
                        SET<Integer> newSet = new SET<>(set);
                        newSet.add(t);
                        if (isValidPath(str.substring(1), t, newSet, col, row))
                            return true;
                    }
                }
            }
        }

        return false;
    }

    private void createHash(BoggleBoard board, int col, int row) {
        hash = new HashMap<>();
        max_length = 0;
        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                int v = x + y * col;

                char c = board.getLetter(y, x);
                if (!hash.containsKey(c))
                    hash.put(c, new SET<>());

                hash.get(c).add(v);
                max_length++;
                if (c == 'Q')
                    max_length++;
            }
        }
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
