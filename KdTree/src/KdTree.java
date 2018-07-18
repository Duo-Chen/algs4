import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private static final double LINE_RADIUS = 0.001;
    private Node root;
    private int count;

    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            lb = null;
            rt = null;
            this.rect = rect;
        }
    }

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
            root = new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0));
            count++;
        } else {
            put(root, p, true);
        }
    }

    private void put(Node node, Point2D p, boolean isVertical) {
        if (node.p.equals(p))
            return;

        if (isVertical) {
            double px = node.p.x();
            if (Double.compare(p.x(), px) < 0) {
                if (node.lb == null) {
                    node.lb = new Node(p, new RectHV(node.rect.xmin(), node.rect.ymin(), px, node.rect.ymax()));
                    count++;
                } else {
                    put(node.lb, p, !isVertical);
                }
            } else {
                if (node.rt == null) {
                    node.rt = new Node(p, new RectHV(px, node.rect.ymin(), node.rect.xmax(), node.rect.ymax()));
                    count++;
                } else {
                    put(node.rt, p, !isVertical);
                }
            }
        } else {
            double py = node.p.y();
            if (Double.compare(p.y(), py) < 0) {
                if (node.lb == null) {
                    node.lb = new Node(p, new RectHV(node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), py));
                    count++;
                } else {
                    put(node.lb, p, !isVertical);
                }
            } else {
                if (node.rt == null) {
                    node.rt = new Node(p, new RectHV(node.rect.xmin(), py, node.rect.xmax(), node.rect.ymax()));
                    count++;
                } else {
                    put(node.rt, p, !isVertical);
                }
            }
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

        if (node.p.equals(p))
            return true;

        if (!node.rect.contains(p))
            return false;

        return find(node.lb, p) || find(node.rt, p);
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical) {
        if (node == null)
            return;

        StdDraw.setPenRadius(LINE_RADIUS);

        if (isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        }
        
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        node.p.draw();

        draw(node.lb, !isVertical);
        draw(node.rt, !isVertical);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("RectHV shouldn't be null.");

        Stack<Point2D> points = new Stack<>();
        range(points, rect, root, true);
        return points;
    }

    private void range(Stack<Point2D> points, RectHV rect, Node p, boolean isVertical) {
        if (p == null || points == null)
            return;

        if (rect.contains(p.p))
            points.push(p.p);

        RectHV rectLB;
        RectHV rectRT;
        if (isVertical) {
            rectLB = new RectHV(p.rect.xmin(), p.rect.ymin(), p.p.x(), p.rect.ymax());
            rectRT = new RectHV(p.p.x(), p.rect.ymin(), p.rect.xmax(), p.rect.ymax());
        } else {
            rectLB = new RectHV(p.rect.xmin(), p.rect.ymin(), p.rect.xmax(), p.p.y());
            rectRT = new RectHV(p.rect.xmin(), p.p.y(), p.rect.xmax(), p.rect.ymax());
        }

        if (rectLB.intersects(rect))
            range(points, rect, p.lb, !isVertical);
        if (rectRT.intersects(rect))
            range(points, rect, p.rt, !isVertical);
    }

    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("nearest p shouldn't be null.");

        return nearest(root, p, null);
    }

    private Point2D nearest(Node node, Point2D p, Point2D q) {
        if (node == null)
            return q;

        if (node.p.compareTo(p) == 0)
            return p;

        double distance = Double.POSITIVE_INFINITY;
        if (q != null)
            distance = p.distanceSquaredTo(q);

        double dRect = node.rect.distanceSquaredTo(p);
        if (Double.compare(dRect, distance) >= 0)
            return q;

        double d = node.p.distanceSquaredTo(p);
        if (Double.compare(d, distance) < 0)
            q = node.p;

        q = nearest(node.rt, p, q);
        q = nearest(node.lb, p, q);
        return q;
    }

    public static void main(String[] args) {
        // for test
        Point2D[] points = {
            new Point2D(0.372, 0.497),
            new Point2D(0.564, 0.413),
            new Point2D(0.226, 0.577),
            new Point2D(0.144, 0.179),
            new Point2D(0.083, 0.51),
            new Point2D(0.32, 0.708),
            new Point2D(0.471, 0.362),
            new Point2D(0.862, 0.825),
            new Point2D(0.785, 0.725),
            new Point2D(0.499, 0.208),
            new Point2D(0.372, 0.497),
        };

        KdTree kt = new KdTree();
        for (int i = 0; i < points.length; i++)
            kt.insert(points[i]);

        StdOut.println("Size : " + kt.size());

        Point2D q = new Point2D(0.428, 0.056);
        StdOut.println("Nearest : " + kt.nearest(q));

        StdDraw.enableDoubleBuffering();

        kt.draw();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        q.draw();

        StdDraw.show();
    }
}
