import edu.princeton.cs.algs4.Point2D;
        import edu.princeton.cs.algs4.RectHV;
        import edu.princeton.cs.algs4.Stack;

public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, boolean isVertical, RectHV rect) {
            this.p = p;
            lb = null;
            rt = null;
            if (isVertical) {
                this.rect = new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
            } else {
                this.rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
            }
        }
    }

    private Node root;
    private int count;

    public KdTree() {
        root = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("insert p shouldn't be null.");

        if (root == null) {
            root = new Node(p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
        } else {
            put(root, p, false);
        }
        count++;
    }

    private void put(Node node, Point2D p, boolean isVertical) {
        if (node.rect.contains(p)) {
            if (node.lb == null)
                node.lb = new Node(p, isVertical, node.rect);
            else
                put(node.lb, p, !isVertical);
        } else {
            if (node.rt == null)
                node.rt = new Node(p, isVertical, node.rect);
            else
                put(node.rt, p, !isVertical);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("contains p shouldn't be null.");

        return find(root, p);
    }

    private boolean find(Node node, Point2D p) {
        if (node == null)
            return false;

        int res = node.p.compareTo(p);
        if (res == 0)
            return true;
        else if (node.rect.contains(p))
            return find(node.lb, p);
        else
            return find(node.rt, p);
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node == null)
            return;

        node.p.draw();

        draw(node.lb);
        draw(node.rt);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("RectHV shouldn't be null.");

        Stack<Point2D> points = new Stack<>();
        range(points, rect, root);
        if (points.size() == 0)
            return null;

        return points;
    }

    private void range(Stack<Point2D> points, RectHV rect, Node p) {
        if (p == null || points == null)
            return;

        if (rect.contains(p.p))
            points.push(p.p);

        range(points, rect, p.lb);
        range(points, rect, p.rt);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("nearest p shouldn't be null.");

        Double distance = Double.MAX_VALUE;
        Point2D q = null;
        nearest(root, p, q, distance);
        return q;
    }

    private void nearest(Node node, Point2D p, Point2D q, Double distance) {
        if (node == null)
            return;

        Double d = node.p.distanceTo(p);
        if (Double.compare(d, distance) < 0) {
            q = node.p;
            distance = d;
        }

        nearest(node.rt, p, q, distance);
        nearest(node.lb, p, q, distance);
    }

    public static void main(String[] args) {

    }
}
