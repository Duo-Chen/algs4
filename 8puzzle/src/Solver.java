import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private final Stack<Board> solution;

    private class Node implements Comparable<Node> {
        private final Node prev;
        private final Board board;
        private final int move;
        private final int priority;
        private final boolean isTwin;

        public Node(Node prev, Board board, int move, boolean isTwin) {
            this.prev = prev;
            this.board = board;
            this.move = move;
            this.priority = move + board.manhattan();
            this.isTwin = isTwin;
        }

        public int compareTo(Node that) {
            if (this == that)
                return 0;
            if (this.priority > that.priority)
                return 1;
            if (this.priority < that.priority)
                return -1;
            return Integer.compare(this.board.manhattan(), that.board.manhattan());
        }
    }

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Invalid parameter");

        solution = new Stack<>();

        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(null, initial, 0, false));
        pq.insert(new Node(null, initial.twin(), 0, true));

        while (true) {
            Node node = pq.delMin();
            if (node.board.isGoal()) {
                if (!node.isTwin) {
                    while (node != null) {
                        solution.push(node.board);
                        node = node.prev;
                    }
                }
                break;
            }

            for (Board board : node.board.neighbors()) {
                if (node.prev == null || !board.equals(node.prev.board))
                    pq.insert(new Node(node, board, node.move + 1, node.isTwin));
            }
        }
    }

    public boolean isSolvable() {
        return solution.size() != 0;
    }

    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<Board> solution() {
        if (isSolvable())
            return solution;
        else
            return null;
    }

    public static void main(String[] args) {
        // create initial board from file
        int[][] blocks = {
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}
        };
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}