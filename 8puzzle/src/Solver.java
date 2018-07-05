import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;

public class Solver {
    private final boolean solvable;
    private final Queue<Board> solution;

    private class Node implements Comparable<Node> {
        private final Node prev;
        private final Board board;
        private final int move;

        public Node(Node prev, Board board, int move) {
            this.prev = prev;
            this.board = board;
            this.move = move;
        }

        public int compareTo(Node that) {
            if (this == that)
                return 0;
            if (this.move > that.move)
                return 1;
            if (this.move < that.move)
                return -1;
            if (this.board.manhattan() < that.board.manhattan())
                return -1;
            if (this.board.manhattan() > that.board.manhattan())
                return 1;

            return 0;
        }
    }

    public Solver(Board initial) {
        solvable = (initial.hamming() > 0 || initial.manhattan() > 0);
        solution = new Queue<>();

        if (!solvable)
            return;

        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(null, initial, 0));

        while (true) {
            Node node = pq.delMin();
            if (node.board.isGoal()) {
                while (node != null) {
                    solution.enqueue(node.board);
                    node = node.prev;
                }
                break;
            }

            for (Board board : node.board.neighbors()) {
                if (node.prev == null || !board.equals(node.prev.board))
                    pq.insert(new Node(node, board, node.move + 1));
            }
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (solvable)
            return solution.size() - 1;

        return 0;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle/puzzle07.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}