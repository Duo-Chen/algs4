import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private final SET<Point2D> sets;

    public PointSET() {
        sets = new SET<>();
    }

    public boolean isEmpty() {
        return sets.isEmpty();
    }

    public int size() {
        return sets.size();
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("insert p shouldn't be null.");

        if (!contains(p))
            sets.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("contains p shouldn't be null.");

        return sets.contains(p);
    }

    public void draw() {
        for (Point2D p : sets) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("RectHV shouldn't be null.");

        Stack<Point2D> stack = new Stack<>();
        for (Point2D p : sets) {
            if (rect.contains(p))
                stack.push(p);
        }

        return stack;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("nearest p shouldn't be null.");

        double distance = Double.POSITIVE_INFINITY;
        Point2D q = null;
        for (Point2D t : sets) {
            double d = t.distanceSquaredTo(p);
            if (Double.compare(d, distance) < 0) {
                distance = d;
                q = t;
            }
        }

        return q;
    }

    public static void main(String[] args) {
        // for test
    }
}
