import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static class KdNode {
        private final Point2D point;
        private KdNode leftNode;
        private KdNode rightNode;
        private final boolean vertical;

        public KdNode(final Point2D p, KdNode leftNode, KdNode rightNode,
                      final boolean vertical) {
            this.point = p;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.vertical = vertical;
        }

        public int compareTo(RectHV rect) {
            double x = this.point.x();
            double y = this.point.y();
            if (this.vertical) {
                if (x > rect.xmax()) {
                    return 1;
                }
                if (x < rect.xmin()) {
                    return -1;
                }
            }
            else {
                if (y > rect.ymax()) {
                    return 1;
                }
                if (y < rect.ymin()) {
                    return -1;
                }
            }
            return 0;
        }


    }

    private static final RectHV BOX = new RectHV(0, 0, 1, 1);
    private KdNode rootNode;
    private int size;

    public KdTree() {
        this.rootNode = null;
        this.size = 0;

    }                               // construct an empty set of points

    public boolean isEmpty() {
        return rootNode == null;
    }                    // is the set empty?

    public int size() {
        return size;
    }                        // number of points in the set

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        this.rootNode = insert(rootNode, p, true);


    }

    private KdNode insert(KdNode currentNode, Point2D p, boolean isVertical) {
        if (currentNode == null) {
            this.size++;
            return new KdNode(p, null, null, isVertical);
        }
        else {
            if (currentNode.point.x() == p.x() && currentNode.point.y() == p.y())
                return currentNode;
            else {
                if ((currentNode.vertical && p.x() < currentNode.point.x()) || (
                        !currentNode.vertical && p.y() < currentNode.point.y())) {
                    currentNode.leftNode = insert(currentNode.leftNode, p, !currentNode.vertical);
                }
                else
                    currentNode.rightNode = insert(currentNode.rightNode, p,
                                                   !currentNode.vertical);
            }
            return currentNode;
        }
    }
    // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return contains(this.rootNode, p, true);
    }

    private boolean contains(KdNode currentNode, Point2D p, boolean isVertical) {
        if (p == null) throw new IllegalArgumentException();
        if (currentNode == null) {
            return false;
        }

        if (p.x() == currentNode.point.x() && p.y() == currentNode.point.y()) {
            return true;
        }
        else {
            if (currentNode.vertical && p.x() < currentNode.point.x()
                    || !currentNode.vertical && p.y() < currentNode.point.y()) {
                return contains(currentNode.leftNode, p, !isVertical);
            }
            else {
                return contains(currentNode.rightNode, p, !isVertical);
            }
        }


    }


    public void draw() {
        StdDraw.setScale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        BOX.draw();
        draw(this.rootNode, BOX);
    }

    private void draw(final KdNode currentNode, final RectHV rect) {
        if (currentNode == null)
            return;
        else {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            currentNode.point.draw();
            if (currentNode.vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(currentNode.point.x(), rect.ymin(), currentNode.point.x(),
                             rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(rect.xmin(), currentNode.point.y(), rect.xmax(),
                             currentNode.point.y());
            }
        }
        draw(currentNode.leftNode, leftRectangle(currentNode, rect));
        draw(currentNode.rightNode, rightRectangle(currentNode, rect));
    }

    private RectHV leftRectangle(KdNode currentNode, final RectHV rect) {
        if (currentNode.vertical) {
            return new RectHV(rect.xmin(), rect.ymin(), currentNode.point.x(), rect.ymax());
        }
        else
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), currentNode.point.y());
    }

    private RectHV rightRectangle(KdNode currentNode, final RectHV rect) {
        if (currentNode.vertical) {
            return new RectHV(currentNode.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }
        else
            return new RectHV(rect.xmin(), currentNode.point.y(), rect.xmax(), rect.ymax());
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Stack<Point2D> pointsContained = new Stack<Point2D>();
        range(this.rootNode, rect, pointsContained);
        return pointsContained;

    }

    private void range(KdNode currentNode, RectHV rect,
                       Stack<Point2D> pointsContained) {
        if (currentNode != null) {

            if (currentNode.compareTo(rect) > 0) {
                range(currentNode.leftNode, rect, pointsContained);
            }
            else if (currentNode.compareTo(rect) < 0) {
                range(currentNode.rightNode, rect, pointsContained);
            }

            else {
                if (rect.contains(currentNode.point)) {
                    pointsContained.push(currentNode.point);
                }
                range(currentNode.leftNode, rect, pointsContained);
                range(currentNode.rightNode, rect, pointsContained);
            }
        }
    }


    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return searchNearest(this.rootNode, BOX, p, null);


    }

    private Point2D searchNearest(KdNode currentNode, RectHV rect, Point2D p, Point2D nearest) {
        if (currentNode == null) {
            return nearest;
        }
        double disToNode = 0.0;
        double disToRec = 0.0;
        RectHV left = null;
        RectHV right = null;

        if (nearest != null) {
            disToNode = nearest.distanceSquaredTo(p);
            disToRec = rect.distanceSquaredTo(p);
        }
        if (nearest == null || disToRec < disToNode) {
            if (nearest == null || disToNode > currentNode.point.distanceSquaredTo(p))
                nearest = currentNode.point;
            if (currentNode.vertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), currentNode.point.x(), rect.ymax());
                right = new RectHV(currentNode.point.x(), rect.ymin(), rect.xmax(), rect.ymax());

                if (p.x() < currentNode.point.x()) {
                    nearest = searchNearest(currentNode.leftNode, left, p, nearest);
                    nearest = searchNearest(currentNode.rightNode, right, p, nearest);
                }
                else {
                    nearest = searchNearest(currentNode.rightNode, right, p, nearest);
                    nearest = searchNearest(currentNode.leftNode, left, p, nearest);
                }
            }
            else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), currentNode.point.y());
                right = new RectHV(rect.xmin(), currentNode.point.y(), rect.xmax(), rect.ymax());

                if (p.y() < currentNode.point.y()) {
                    nearest = searchNearest(currentNode.leftNode, left, p, nearest);
                    nearest = searchNearest(currentNode.rightNode, right, p, nearest);
                }
                else {
                    nearest = searchNearest(currentNode.rightNode, right, p, nearest);
                    nearest = searchNearest(currentNode.leftNode, left, p, nearest);
                }
            }
        }
        return nearest;
    }

    public static void main(String[] args) {


    }

    // unit testing of the methods (optional)
}
