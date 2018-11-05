import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;
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
        createDigraph(board, col, row);

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
        boolean isQu = false;
        Stack<Character> pat = new Stack<>();
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

            pat.push(c);
        }

        if (isValidPath(G, pat, 0, new SET<>()))
            return points;

        return 0;
    }

    private <T> Stack<T> copySeq(Stack<T> seq) {
        Stack<T> temp = new Stack<>();
        while (!seq.isEmpty())
            temp.push(seq.pop());

        Stack<T> res = new Stack<>();
        while (!temp.isEmpty()) {
            T t = temp.pop();
            seq.push(t);
            res.push(t);
        }

        return res;
    }

    private boolean isValidPath(Digraph G, Stack<Character> pat, int t, SET<Integer> seq) {
        if (pat.isEmpty())
            return true;

        boolean hasPath = false;
        Stack<Character> newPat = copySeq(pat);
        if (seq.isEmpty()) {
            for (int s : hash.get(newPat.pop())) {
                SET<Integer> newSeq = new SET<>(seq);
                newSeq.add(s);
                hasPath |= isValidPath(G, newPat, s, newSeq);
            }
        } else {
            for (int s : hash.get(newPat.pop())) {
                DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(G, s);
                if (dfs.hasPathTo(t) && !seq.contains(s)) {
                    SET<Integer> newSeq = new SET<>(seq);
                    newSeq.add(s);
                    hasPath |= isValidPath(G, newPat, s, newSeq);
                }
            }
        }

        return hasPath;
    }

    private void createDigraph(BoggleBoard board, int col, int row) {
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

        String[] dict = { "SIT", "QUERIES", "EQUATION", "ADAPT", "ADAPTED", "AIDED", "DEEP", "STATION" };
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
