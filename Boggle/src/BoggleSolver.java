import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.SET;

import java.util.HashMap;

public class BoggleSolver {
    private final HashMap<String, Integer> dict;

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
        HashMap<Character, SET<Integer>> hash = createDigraph(board, col, row);

        for (String str : dict.keySet()) {
            int points = countPoints(str, hash, col, row);
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

    private int countPoints(String str, HashMap<Character, SET<Integer>> hash, int col, int row) {
        int path = str.length() - 1;
        int points = length2point(str.length());
        boolean isQu = false;

        Digraph G = new Digraph(col * row);

        for (int i = 0; i < str.length(); i++) {
            if (!hash.containsKey(str.charAt(i)))
                return 0;

            char c = str.charAt(i);
            if (i > 0) {
                char cv;
                if (isQu)
                    cv = str.charAt(i - 2);
                else
                    cv = str.charAt(i - 1);

                isQu = false;

                for (int v : hash.get(cv)) {
                    for (int w : hash.get(c)) {
                        int vX = v % col;
                        int vY = v / col;
                        int wX = w % col;
                        int wY = w / col;
                        if ((vX == wX && Math.abs(vY - wY) == 1)
                        || (vY == wY && Math.abs(vX - wX) == 1)
                        || (Math.abs(vX - wX) == 1 && Math.abs(vY - wY) == 1))
                            G.addEdge(v, w);
                    }
                }
            }

            if (c == 'Q') {
                if (i < str.length() - 1 && str.charAt(i + 1) == 'U') {
                    i++;
                    isQu = true;
                } else
                    return 0;
            }
        }

        if (str.compareTo("QUERIES") == 0) {
            int a = 0;
            a++;
        }

        for (int s : hash.get(str.charAt(0))) {
            BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);
            for (int t : hash.get(str.charAt(str.length() - 1))) {
                if (bfs.hasPathTo(t))
                    if (bfs.distTo(t) == path)
                        return points;
            }
        }

        return 0;
    }

    private HashMap<Character, SET<Integer>> createDigraph(BoggleBoard board, int col, int row) {
        HashMap<Character, SET<Integer>> G = new HashMap<>();

        for (int x = 0; x < col; x++) {
            for (int y = 0; y < row; y++) {
                int v = x + y * col;

                Character c = board.getLetter(y, x);
                if (!G.containsKey(c))
                    G.put(c, new SET<>());

                G.get(c).add(v);
            }
        }

        return G;
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
                { "board-q.txt", "2" }
        };

        String[] dict = { "SIT", "QUERIES", "EQUATION" };
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
