import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    private final int[] data;
    private final int n;
    private final int numHamming;
    private final int numManhattan;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new IllegalArgumentException("Invalid input");

        int[][] buff = blocks.clone();
        n = buff.length;
        data = new int[n * n];
        for (int i = 0; i < n; i++) {
            if (buff[i] == null || buff[i].length != n)
                throw new IllegalArgumentException("Invalid input");

            for (int j = 0; j < n; j++)
                data[i * n + j] = buff[i][j];
        }

        int[] check = data.clone();
        Arrays.sort(check);
        for (int i = 0; i < check.length; i++)
            if (check[i] != i)
                throw new IllegalArgumentException("Invalid input");

        int h = 0;
        int m = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == i + 1 || data[i] == 0)
                continue;

            int nowR = i / n;
            int nowC = i % n;
            int realR = (data[i] - 1) / n;
            int realC = (data[i] - 1) % n;
            h++;
            m += Math.abs(nowR - realR) + Math.abs(nowC - realC);
        }

        numHamming = h;
        numManhattan = m;
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        return numHamming;
    }

    public int manhattan() {
        return numManhattan;
    }

    public boolean isGoal() {
        return numHamming == 0 && numManhattan == 0;
    }

    public Board twin() {
        if (data[0] != 0 && data[1] != 0)
            return createNeighbor(0, 1);

        return createNeighbor(n * n - 2, n * n - 1);
    }

    public boolean equals(Object y) {
        if (y == null)
            return false;

        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (that.data.length != data.length)
            return false;

        return Arrays.equals(this.data, that.data);
    }

    public Iterable<Board> neighbors() {
        int index = -1;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) {
                index = i;
                break;
            }
        }

        int r = index / n;
        int c = index % n;
        Stack<Board> boards = new Stack<>();
        if (r != 0)
            boards.push(createNeighbor(index, index - n));
        if (r + 1 != n)
            boards.push(createNeighbor(index, index + n));
        if (c != 0)
            boards.push(createNeighbor(index, index - 1));
        if (c + 1 != n)
            boards.push(createNeighbor(index, index + 1));

        return boards;
    }

    private Board createNeighbor(int p, int q) {
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = data[i * n + j];

        swapBlocks(blocks, p, q);
        return new Board(blocks);
    }

    private void swapBlocks(int[][] block, int p, int q) {
        int rP = p / n;
        int cP = p % n;
        int rQ = q / n;
        int cQ = q % n;
        int t = block[rP][cP];
        block[rP][cP] = block[rQ][cQ];
        block[rQ][cQ] = t;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                s.append(String.format(" %2d", data[i * n + j]));

            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        int[][] a = {
                {4, 2, 7},
                {3, 0, 1},
                {5, 6, 8}
        };

        Board b = new Board(a);

        StdOut.println(b);
        StdOut.println("dimension : " + b.dimension());
        StdOut.println("Hamming : " + b.hamming());
        StdOut.println("Manhattan : " + b.manhattan());
        StdOut.println("Is goal : " + b.isGoal());
        StdOut.println("twin : " + b.twin().equals(b));
        StdOut.println(b.twin());

        int i = 1;
        for (Board bb :b.neighbors()) {
            StdOut.println("neighbor " + i++);
            StdOut.println(bb);
        }
    }
}