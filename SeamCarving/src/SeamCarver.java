import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private static final double BORDER_ENERGY = 1000.0;
    private int width, height;
    private int[][] rgb;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("no picture for constructor");

        width = picture.width();
        height = picture.height();
        rgb = new int[width][height];

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                rgb[x][y] = picture.getRGB(x, y);
    }

    private double getPixelEnergy(int x, int y) {
        int rgbX1 = rgb[x - 1][y];
        int rgbX2 = rgb[x + 1][y];
        int rgbY1 = rgb[x][y - 1];
        int rgbY2 = rgb[x][y + 1];

        int rX1 = (rgbX1 >> 16) & 0xFF;
        int rX2 = (rgbX2 >> 16) & 0xFF;
        int gX1 = (rgbX1 >> 8) & 0xFF;
        int gX2 = (rgbX2 >> 8) & 0xFF;
        int bX1 = rgbX1 & 0xFF;
        int bX2 = rgbX2 & 0xFF;

        int rY1 = (rgbY1 >> 16) & 0xFF;
        int rY2 = (rgbY2 >> 16) & 0xFF;
        int gY1 = (rgbY1 >> 8) & 0xFF;
        int gY2 = (rgbY2 >> 8) & 0xFF;
        int bY1 = rgbY1 & 0xFF;
        int bY2 = rgbY2 & 0xFF;

        double deltaX = Math.pow(rX1 - rX2, 2) + Math.pow(gX1 - gX2, 2) + Math.pow(bX1 - bX2, 2);
        double deltaY = Math.pow(rY1 - rY2, 2) + Math.pow(gY1 - gY2, 2) + Math.pow(bY1 - bY2, 2);

        return Math.sqrt(deltaX + deltaY);
    }

    // current picture
    public Picture picture() {
        Picture pic = new Picture(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                pic.setRGB(x, y, rgb[x][y]);

        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw new IllegalArgumentException(x + " or " + y + " is out of range.");

        if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
            return BORDER_ENERGY;

        return getPixelEnergy(x, y);
    }

    private double[][] getEnergy() {
        double[][] energy = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
                    energy[x][y] = BORDER_ENERGY;
                else
                    energy[x][y] = getPixelEnergy(x, y);
            }
        }

        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energy = getEnergy();

        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0) {
                    distTo[x][y] = 0.0;
                    pq.insert(x + y * width, 0.0);
                } else {
                    distTo[x][y] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // relax vertices in order of distance from s
        while (!pq.isEmpty()) {
            int key = pq.delMin();
            int x = key % width;
            int y = key / width;
            for (int i = -1; i < 2; i++) {
                if (y + i < 0 || y + i >= height || x + 1 >= width)
                    continue;

                // relax edge e and update pq if changed
                if (distTo[x + 1][y + i] > distTo[x][y] + energy[x + 1][y + i]) {
                    distTo[x + 1][y + i] = distTo[x][y] + energy[x + 1][y + i];
                    edgeTo[x + 1][y + i] = key;
                    int w = x + 1 + (y + i) * width;
                    if (pq.contains(w))
                        pq.decreaseKey(w, distTo[x + 1][y + i]);
                    else
                        pq.insert(w, distTo[x + 1][y + i]);
                }
            }
        }

        int[] seam = new int[width];
        double sp = Double.POSITIVE_INFINITY;
        for (int y = 0; y < height; y++) {
            if (distTo[width - 1][y] < sp) {
                sp = distTo[width - 1][y];
                seam[width - 1] = y;
            }
        }

        int y = seam[width - 1];
        int x = width - 1;
        while (x > 0) {
            int w = edgeTo[x][y];
            y = w / width;
            seam[x - 1] = y;
            x--;
        }

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = getEnergy();

        double[][] distTo = new double[width][height];
        int[][] edgeTo = new int[width][height];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(width * height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (y == 0) {
                    distTo[x][y] = 0.0;
                    pq.insert(x + y * width, 0.0);
                } else {
                    distTo[x][y] = Double.POSITIVE_INFINITY;
                }
            }
        }

        // relax vertices in order of distance from s
        while (!pq.isEmpty()) {
            int key = pq.delMin();
            int x = key % width;
            int y = key / width;
            for (int i = -1; i < 2; i++) {
                if (x + i < 0 || x + i >= width || y + 1 >= height)
                    continue;

                // relax edge e and update pq if changed
                if (distTo[x + i][y + 1] > distTo[x][y] + energy[x + i][y + 1]) {
                    distTo[x + i][y + 1] = distTo[x][y] + energy[x + i][y + 1];
                    edgeTo[x + i][y + 1] = key;
                    int w = x + i + (y + 1) * width;
                    if (pq.contains(w))
                        pq.decreaseKey(w, distTo[x + i][y + 1]);
                    else
                        pq.insert(w, distTo[x + i][y + 1]);
                }
            }
        }

        int[] seam = new int[height];
        double sp = Double.POSITIVE_INFINITY;
        for (int x = 0; x < width; x++) {
            if (distTo[x][height - 1] < sp) {
                sp = distTo[x][height - 1];
                seam[height - 1] = x;
            }
        }

        int x = seam[height - 1];
        int y = height - 1;
        while (y > 0) {
            int w = edgeTo[x][y];
            x = w % width;
            seam[y - 1] = x;
            y--;
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width || height <= 1)
            throw new IllegalArgumentException("");

        for (int x = 0; x < width; x++)
            for (int y = seam[x]; y < height - 1; y++)
                rgb[x][y] = rgb[x][y + 1];

        height--;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height || width < 1)
            throw new IllegalArgumentException("");

        for (int y = 0; y < height; y++)
            for (int x = seam[y]; x < width - 1; x++)
                rgb[x][y] = rgb[x + 1][y];

        width--;
    }
}
