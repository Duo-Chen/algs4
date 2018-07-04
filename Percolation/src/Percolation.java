import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int length; // create N-by-N grid
    private final WeightedQuickUnionUF uf;
    private boolean[] open; // blocked: false, open: true
    private boolean[] connectTop;
    private boolean[] connectBottom;
    private int sites;
    private boolean percolateFlag;

    public Percolation(int n)  {             // create N-by-N grid, with all sites blocked
        if (n <= 0) {
            throw new IllegalArgumentException("N must be bigger than 0");
        }
        length = n;
        int num = n * n;
        sites = 0;
        uf = new WeightedQuickUnionUF(num);
        open = new boolean[num];
        connectTop = new boolean[num];
        connectBottom = new boolean[num];

        for (int i = 0; i < num; i++) {
            open[i] = false;
            connectTop[i] = false;
            connectBottom[i] = false;
        }
        percolateFlag = false;
    }

    public void open(int i, int j)  {
        int index = xyTo1D(i, j);
        if (open[index])
            return;

        open[index] = true;  //open
        sites++;

        boolean top = false;
        boolean bottom = false;
        int rootP, rootQ;

        if (i < length && open[index + length]) {
            rootP = uf.find(index);
            rootQ = uf.find(index + length);
            if (connectTop[rootP] || connectTop[rootQ]) {
                top = true;
            }
            if (connectBottom[rootP] || connectBottom[rootQ]) {
                bottom = true;
            }
            uf.union(index, index + length);
        }
        if (i > 1 && open[index - length]) {
            rootP = uf.find(index);
            rootQ = uf.find(index - length);
            if (connectTop[rootP] || connectTop[rootQ]) {
                top = true;
            }
            if (connectBottom[rootP] || connectBottom[rootQ]) {
                bottom = true;
            }
            uf.union(index, index - length);
        }
        if (j < length && open[index+1]) {
            rootP = uf.find(index);
            rootQ = uf.find(index + 1);
            if (connectTop[rootP] || connectTop[rootQ]) {
                top = true;
            }
            if (connectBottom[rootP] || connectBottom[rootQ]) {
                bottom = true;
            }
            uf.union(index, index+1);
        }
        if (j > 1 && open[index-1]) {
            rootP = uf.find(index);
            rootQ = uf.find(index - 1);
            if (connectTop[rootP] || connectTop[rootQ]) {
                top = true;
            }
            if (connectBottom[rootP] || connectBottom[rootQ]) {
                bottom = true;
            }
            uf.union(index, index-1);
        }
        if (i == 1) {
            top = true;
        }
        if (i == length) {
            bottom = true;
        }

        connectTop[uf.find(index)] = top;
        connectBottom[uf.find(index)] = bottom;
        if (connectTop[uf.find(index)] &&  connectBottom[uf.find(index)]) {
            percolateFlag = true;
        }
    }

    public int numberOfOpenSites() {
        return sites;
    }

    private int xyTo1D(int i, int j) {
        validateIJ(i, j);
        return j + (i-1) * length -1;
    }

    private void validateIJ(int i, int j) {
        if (i < 1 || i > length || j < 1 || j > length) {
            throw new IllegalArgumentException(
                    "(" + i + ", " + j + ") is not between 1 and " + length);
        }
    }

    public boolean isOpen(int i, int j) {
        return open[xyTo1D(i, j)];
    }

    public boolean isFull(int i, int j) {
        return connectTop[uf.find(xyTo1D(i, j))];
    }

    public boolean percolates() {
        return percolateFlag;
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(10);
        perc.open(2147483647, 2147483647);
    }
}