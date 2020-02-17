import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {

    private int size = 0;
    private SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<Point2D>();

    }                            // construct an empty set of points

    public boolean isEmpty() {
        return size == 0;
    }                    // is the set empty?

    public int size() {
        return size;
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!pointSet.contains(p)) {
            pointSet.add(p);
            size += 1;
        }
    }             // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return pointSet.contains(p);
    }           // does the set contain point p?

    public void draw() {
        if (!pointSet.isEmpty()) {
            for (Point2D p : pointSet) {
                p.draw();
            }
        }
    }

    // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Stack<Point2D> inRectRange = new Stack<Point2D>();
        for (Point2D p : pointSet) {
            if (rect.contains(p)) {
                inRectRange.push(p);
            }
        }
        return inRectRange;

    }           // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (pointSet.isEmpty()) return null;
        Point2D nearest = null;
        for (Point2D q : pointSet) {
            if (nearest == null || q.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
                nearest = q;
            }


        }
        return nearest;
    }
    // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args) {

        // unit testing of the methods (optional)
    }

}
