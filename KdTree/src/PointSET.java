import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;

public class PointSET {
    private Integer index;
    private RedBlackBST<Point2D, Integer> sets;

    public PointSET() {
        index = 0;
        sets = new RedBlackBST<>();
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

        sets.put(p, index++);
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("contains p shouldn't be null.");

        return sets.contains(p);
    }

    public void draw() {
        for (Point2D p : sets.keys()) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("RectHV shouldn't be null.");

        Stack<Point2D> stack = new Stack<>();
        for (Point2D p : sets.keys()) {
            if (rect.contains(p))
                stack.push(p);
        }

        if (stack.size() == 0)
            return null;

        return stack;
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("nearest p shouldn't be null.");

        Double distance = Double.MAX_VALUE;
        Point2D q = null;
        for (Point2D t : sets.keys()) {
            Double d = t.distanceTo(p);
            if (Double.compare(d, distance) < 0) {
                distance = d;
                q = t;
            }
        }

        return q;
    }

    public static void main(String[] args) {

    }
}
