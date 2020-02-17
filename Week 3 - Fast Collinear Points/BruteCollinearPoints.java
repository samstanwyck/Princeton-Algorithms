import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] lineSegmentArr;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {

        if (points == null)
            throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException();
            }
        }

        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);

        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        for (int p = 0; p < copy.length - 3; p++) {
            for (int q = p + 1; q < copy.length - 2; q++) {
                for (int r = q + 1; r < copy.length - 1; r++) {
                    for (int s = r + 1; s < copy.length; s++) {
                        if (copy[p].slopeTo(copy[q]) == copy[p].slopeTo(copy[r])
                                &&
                                copy[p].slopeTo(copy[q]) == copy[p]
                                        .slopeTo(copy[s])) {
                            lineSegments.add(new LineSegment(copy[p], copy[s]));
                        }
                    }
                }
            }
        }

        lineSegmentArr = lineSegments.toArray(new LineSegment[0]);
    }

    public int numberOfSegments()        // the number of line segments
    {
        return lineSegmentArr.length;
    }

    public LineSegment[] segments()                // the line segments
    {
        return Arrays.copyOf(lineSegmentArr, numberOfSegments());
    }

    public static void main(String[] args) {

    }

}
