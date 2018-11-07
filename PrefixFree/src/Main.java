public class Main {
    private static class PrefixFree {
        private Node root;

        private static class Node {
            Node[] next = new Node[2];
            boolean isString;
        }

        public PrefixFree() {
        }

        public void add(String key) {
            if (key == null)
                throw new IllegalArgumentException("argument to add() is null");

            root = add(root, key, 0);
        }

        private Node add(Node x, String key, int d) {
            if (x == null)
                x = new Node();

            if (x.isString)
                throw new IllegalArgumentException("not prefix free");

            if (d == key.length()) {
                x.isString = true;
            } else {
                char c = key.charAt(d);
                int index = 0;
                if (c == '1')
                    index = 1;
                x.next[index] = add(x.next[index], key, d+1);
            }

            return x;
        }
    }

    public static void main(String[] args) {
	// write your code here
        String[][] input = {
                { "01", "10", "0010", "1111" },
                { "01", "10", "0010", "10100" }
        };

        for (String[] set : input) {
            PrefixFree pf = new PrefixFree();
            for (String s : set) {
                pf.add(s);
            }
        }
    }
}
