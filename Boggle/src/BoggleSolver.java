import edu.princeton.cs.algs4.SET;

import java.util.HashMap;

public class BoggleSolver {
    private final HashMap<String, Integer> dict;
    private HashMap<Character, SET<Integer>> hash;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException("");

        dict = new HashMap<>();
        for (String str : dictionary) {
            if (str.length() < 3)
                continue;

            dict.put(str.toUpperCase(), 0);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null)
            throw new IllegalArgumentException("");

        SET<String> res = new SET<>();
        int col = board.cols();
        int row = board.rows();
        createHash(board, col, row);

        for (String str : dict.keySet()) {
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
        int points = length2point(str.length());
        if (isValidPath(str, 0, new SET<>(), col, row))
            return points;

        return 0;
    }

    private boolean isValidPath(String str, int s, SET<Integer> set, int col, int row) {
        if (str.length() == 0)
            return true;

        boolean hasPath = false;
        char c = str.charAt(0);
        int b = c == 'Q' ? 2 : 1;
        SET<Integer> next = hash.get(c);
        if (next == null)
            return false;

        if (set.isEmpty()) {
            for (int v : next) {
                SET<Integer> newSet = new SET<>(set);
                newSet.add(v);
                hasPath |= isValidPath(str.substring(b), v, newSet, col, row);
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
                        hasPath |= isValidPath(str.substring(b), t, newSet, col, row);
                    }
                }
            }
        }

        return hasPath;
    }

    private void createHash(BoggleBoard board, int col, int row) {
        hash = new HashMap<>();

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                int v = x + y * col;

                Character c = board.getLetter(y, x);
                if (!hash.containsKey(c))
                    hash.put(c, new SET<>());

                hash.get(c).add(v);
            }
        }
    }

    public int scoreOf(String word) {
        if (word == null)
            throw new IllegalArgumentException("");

        if (word.length() < 3)
            return 0;

        String key = word.toUpperCase();
        if (dict.containsKey(key))
            return dict.get(key);

        return 0;
    }

    public static void main(String[] args) {
        String[][] test = {
                { "board4x4.txt", "1" },
                { "board-q.txt", "3" }
        };

        String[] dict = { "SIT", "QUERIES", "EQUATION", "STATION" };
        BoggleSolver solver = new BoggleSolver(dict);

        for (String[] str : test) {
            BoggleBoard board = new BoggleBoard(str[0]);
            int count = 0;
            for (String s : solver.getAllValidWords(board))
                count++;

            if (count != Integer.parseInt(str[1]))
                throw new IllegalArgumentException("Wrong answer with " + str[0]);
        }
    }
}
