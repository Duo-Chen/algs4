import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Main {
    public static void testBrute(Point[] points) {
        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println("Brute segments : " + bcp.numberOfSegments());
        LineSegment[] segments = bcp.segments();
        for (int i = 0; i < segments.length; i++) {
            StdOut.println(segments[i].toString());
            segments[i].draw();
        }
    }

    public static void testFast(Point[] points) {
        FastCollinearPoints fcp = new FastCollinearPoints(points);
        StdOut.println("Fast segments : " + fcp.numberOfSegments());
        LineSegment[] segments = fcp.segments();
        for (int i = 0; i < segments.length; i++) {
            StdOut.println(segments[i].toString());
            segments[i].draw();
        }
    }

    public static void main(String[] args) {
	// write your code here
        In input = new In("input80.txt");
        int number = input.readInt();
        Point[] points = new Point[number];
        for (int i = 0; i < number; i++) {
            points[i] = new Point(input.readInt(), input.readInt());
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(0, 32768);
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLUE);

        testBrute(points);
        //testFast(points);

        StdDraw.show();
    }
}
