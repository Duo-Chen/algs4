import edu.princeton.cs.algs4.DijkstraSP;
import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
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
            return 1000.0;

        return getPixelEnergy(x, y);
    }

    private double[][] getEnergy() {
        double[][] energy = new double[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
                    energy[x][y] = 1000.0;
                else
                    energy[x][y] = getPixelEnergy(x, y);
            }
        }

        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        double[][] energy = getEnergy();

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(width * height);
        for (int w = 0; w < width - 1; w++) {
            for (int h = 0; h < height; h++) {
                int v = w + h * width;
                G.addEdge(new DirectedEdge(v, v + 1, energy[w][h]));
                if (h != 0)
                    G.addEdge(new DirectedEdge(v, v - width + 1, energy[w][h]));
                if (h != height - 1)
                    G.addEdge(new DirectedEdge(v, v + width + 1, energy[w][h]));
            }
        }

        int[] seam = new int [width];
        double path = Double.MAX_VALUE;
        for (int i = 0; i < height; i++) {
            DijkstraSP sp = new DijkstraSP(G, i);

            for (int j = 0; j < height; j++) {
                double dist = sp.distTo(j);
                if (dist < path) {
                    path = dist;
                    for (DirectedEdge e : sp.pathTo(j)) {
                        seam[j] = e.from() / width;
                    }
                }
            }
        }

        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energy = getEnergy();

        EdgeWeightedDigraph G = new EdgeWeightedDigraph(width * height);
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height - 1; h++) {
                int v = w + h * width;
                G.addEdge(new DirectedEdge(v, v + width, energy[w][h]));
                if (w != 0)
                    G.addEdge(new DirectedEdge(v, v + width - 1, energy[w][h]));
                if (w != width - 1)
                    G.addEdge(new DirectedEdge(v, v + width + 1, energy[w][h]));
            }
        }

        int[] seam = new int [height];
        double path = Double.MAX_VALUE;
        for (int i = 0; i < width; i++) {
            DijkstraSP sp = new DijkstraSP(G, i);

            for (int j = 0; j < width; j++) {
                double dist = sp.distTo(j);
                if (dist < path) {
                    path = dist;
                    for (DirectedEdge e : sp.pathTo(j)) {
                        seam[j] = e.from() / width;
                    }
                }
            }
        }

        return seam;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width || width <= 1)
            throw new IllegalArgumentException("");

        for (int x = 0; x < width; x++) {
            for (int y = seam[x]; y < height - 1; y++)
              rgb[x][y] = rgb[x][y + 1];
        }

        height--;
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height || height <= 1)
            throw new IllegalArgumentException("");

        for (int y = 0; y < height; y++) {
            for (int x = seam[y]; x < width - 1; y++)
                rgb[x][y] = rgb[x + 1][y];
        }

        width--;
    }
}
