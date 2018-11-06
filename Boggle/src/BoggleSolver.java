import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class BoggleSolver {
    private static class PrefixTST<Value> {
        private int n;              // size
        private Node<Value> root;   // root of TST

        private static class Node<Value> {
            private char c;                        // character
            private Node<Value> left, mid, right;  // left, middle, and right subtries
            private Value val;                     // value associated with string
        }

        public PrefixTST() {
        }

        public int size() {
            return n;
        }

        public boolean contains(String key) {
            if (key == null) {
                throw new IllegalArgumentException("argument to contains() is null");
            }
            return get(key) != null;
        }

        public Value get(String key) {
            if (key == null) {
                throw new IllegalArgumentException("calls get() with null argument");
            }
            if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
            Node<Value> x = get(root, key, 0);
            if (x == null) return null;
            return x.val;
        }

        // return subtrie corresponding to given key
        private Node<Value> get(Node<Value> x, String key, int d) {
            if (x == null) return null;
            if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
            char c = key.charAt(d);
            if      (c < x.c)              return get(x.left,  key, d);
            else if (c > x.c)              return get(x.right, key, d);
            else if (d < key.length() - 1) return get(x.mid,   key, d+1);
            else                           return x;
        }

        public void put(String key, Value val) {
            if (key == null) {
                throw new IllegalArgumentException("calls put() with null key");
            }
            if (!contains(key)) n++;
            root = put(root, key, val, 0);
        }

        private Node<Value> put(Node<Value> x, String key, Value val, int d) {
            char c = key.charAt(d);
            if (x == null) {
                x = new Node<Value>();
                x.c = c;
            }
            if      (c < x.c)               x.left  = put(x.left,  key, val, d);
            else if (c > x.c)               x.right = put(x.right, key, val, d);
            else if (d < key.length() - 1)  x.mid   = put(x.mid,   key, val, d+1);
            else                            x.val   = val;
            return x;
        }

        public String longestPrefixOf(String query) {
            if (query == null) {
                throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
            }
            if (query.length() == 0) return null;
            int length = 0;
            Node<Value> x = root;
            int i = 0;
            while (x != null && i < query.length()) {
                char c = query.charAt(i);
                if      (c < x.c) x = x.left;
                else if (c > x.c) x = x.right;
                else {
                    i++;
                    if (x.val != null) length = i;
                    x = x.mid;
                }
            }
            return query.substring(0, length);
        }

        public Iterable<String> keys() {
            Queue<String> queue = new Queue<String>();
            collect(root, new StringBuilder(), queue);
            return queue;
        }

        public Iterable<String> keysWithPrefix(String prefix) {
            if (prefix == null) {
                throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
            }
            Queue<String> queue = new Queue<String>();
            Node<Value> x = get(root, prefix, 0);
            if (x == null) return queue;
            if (x.val != null) queue.enqueue(prefix);
            collect(x.mid, new StringBuilder(prefix), queue);
            return queue;
        }

        // all keys in subtrie rooted at x with given prefix
        private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue) {
            if (x == null) return;
            collect(x.left,  prefix, queue);
            if (x.val != null) queue.enqueue(prefix.toString() + x.c);
            collect(x.mid,   prefix.append(x.c), queue);
            prefix.deleteCharAt(prefix.length() - 1);
            collect(x.right, prefix, queue);
        }

        public Iterable<String> keysThatMatch(String pattern) {
            Queue<String> queue = new Queue<String>();
            collect(root, new StringBuilder(), 0, pattern, queue);
            return queue;
        }

        private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
            if (x == null) return;
            char c = pattern.charAt(i);
            if (c == '.' || c < x.c) collect(x.left, prefix, i, pattern, queue);
            if (c == '.' || c == x.c) {
                if (i == pattern.length() - 1 && x.val != null) queue.enqueue(prefix.toString() + x.c);
                if (i < pattern.length() - 1) {
                    collect(x.mid, prefix.append(x.c), i+1, pattern, queue);
                    prefix.deleteCharAt(prefix.length() - 1);
                }
            }
            if (c == '.' || c > x.c) collect(x.right, prefix, i, pattern, queue);
        }public boolean hasPrefix(String prefix) {
            Node prefixNode = get(root, prefix, 0);
            if (prefixNode == null)
                return false;
            if (prefixNode.val != null)
                return true;
            if (prefixNode.left == null && prefixNode.mid == null && prefixNode.right == null)
                return false;
            return true;
        }
    }

    private final PrefixTST<Integer> dict;
    private BoggleBoard board;
    private SET<String> validStrings;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException("");

        dict = new PrefixTST<>();
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

        if (!dict.hasPrefix(str))
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
