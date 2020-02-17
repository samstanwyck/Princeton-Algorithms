import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        else if (that.y - this.y == 0) {
            return +0;
        }
        else if (that.x - this.x == 0) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            return ((double) that.y - (double) this.y) / ((double) that.x - (double) this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {
        if (that.y > this.y) {
            return -1;
        }
        else if (that.y < this.y) {
            return 1;
        }
        else {
            if (that.x > this.x) {
                return -1;
            }
            else if (that.x < this.x) {
                return 1;
            }
            else {
                return 0;
            }
        }
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (o1, o2) -> Double.compare(slopeTo(o1), slopeTo(o2));
    }


    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(2, 3);
        Point point3 = new Point(2, 4);
        System.out.println(point1.slopeTo(point2));
        System.out.println(point3.slopeTo(point1));
        System.out.println(point2.compareTo(point3));
        Comparator<Point> slopeOrder = point1.slopeOrder();
        System.out.println(slopeOrder.compare(point3, point2));
    }
}
