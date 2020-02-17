import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] result;

    public FastCollinearPoints(Point[] points) {
        // check for null entries
        if (points == null) {
            throw new IllegalArgumentException("Null array");
        }
        for (Point p : points) {
            if (p == null)
                throw new IllegalArgumentException("Null point in array");
        }
        // sort array to check for duplicates
        Point[] tmpPoints = points.clone();
        Arrays.sort(tmpPoints);
        for (int i = 1; i < tmpPoints.length; i++) {
            if (tmpPoints[i].compareTo(tmpPoints[i - 1]) == 0)
                throw new IllegalArgumentException("Duplicate point");
        }


        List<LineSegment> ret = new ArrayList<>();
        int n = tmpPoints.length;
        for (int i = 0; i < n - 3; i++) {
            Point[] copy = tmpPoints.clone();
            Point curPnt = tmpPoints[i];
            Arrays.sort(copy, curPnt.slopeOrder());
            int count = 1;
            for (int j = 0; j < copy.length; count = 1) {
                boolean hasSmallerPoint = false;
                double curSlope;
                do {
                    curSlope = curPnt.slopeTo(copy[j]);
                    hasSmallerPoint = hasSmallerPoint || (copy[j].compareTo(curPnt) < 0);
                    count++;
                    j++;
                } while (j < copy.length && Double.compare(curPnt.slopeTo(copy[j]), curSlope) == 0);
                if (count >= 4 && !hasSmallerPoint) {
                    ret.add(new LineSegment(curPnt, copy[j - 1]));
                }
            }
        }
        result = ret.toArray(new LineSegment[0]);
    }    // finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return result.length;
    }       // the number of line segments

    public LineSegment[] segments() {
        return result.clone();
    }           // the line segments

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
