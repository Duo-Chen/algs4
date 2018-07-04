import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double Z = 1.96;
    private final double meanSites;
    private final double stddevSites;
    private final double delta;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException("Wrong arguments");

        double num = n * n;

        double[] openedSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            openedSites[i] = runPercolate(n) / num;
        }

        meanSites = StdStats.mean(openedSites);
        stddevSites = StdStats.stddev(openedSites);
        delta = Z * stddevSites / Math.sqrt(openedSites.length);
    }

    public double mean() {
        return meanSites;
    }

    public double stddev() {
        return stddevSites;
    }

    public double confidenceLo() {
        return meanSites - delta;
    }

    public double confidenceHi() {
        return meanSites + delta;
    }

    private int runPercolate(int n) {
        Percolation p = new Percolation(n);
        // percolate
        while (!p.percolates()) {
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            p.open(row, col);
        }

        return p.numberOfOpenSites();
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, t);
        StdOut.println("mean                    = " + p.mean());
        StdOut.println("stddev                  = " + p.stddev());
        StdOut.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }
}
