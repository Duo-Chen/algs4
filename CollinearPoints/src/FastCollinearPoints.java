import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] ls;

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Point[] shouldn't be null.");

        List<LineSegment> lines = new ArrayList<>();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException("Point shouldn't be null");

        Point[] ps = points.clone();
        Arrays.sort(ps);
        for (int i = 1; i < ps.length; i++)
            if (ps[i - 1].compareTo(ps[i]) == 0)
                throw new IllegalArgumentException("Repeat point");

        for (int i = 0; i + 3 < ps.length; i++) {
            Point[] temp = ps.clone();
            Arrays.sort(temp, ps[i].slopeOrder());

            int j = 1;
            while (j < temp.length - 2) {

                double s1 = temp[0].slopeTo(temp[j]);
                double s2 = temp[0].slopeTo(temp[j + 2]);
                if (Double.compare(s1, s2) != 0) {
                    j++;
                    continue;
                }

                int k = 3;
                while (j + k < temp.length) {
                    s2 = temp[0].slopeTo(temp[j + k]);
                    if (Double.compare(s1, s2) == 0)
                        k++;
                    else
                        break;
                }

                Point[] set = new Point[k];
                for (int l = 0; l < k; l++)
                    set[l] = temp[j + l];

                Arrays.sort(set);
                if (set[0].compareTo(temp[0]) > 0)
                    lines.add(new LineSegment(temp[0], set[k - 1]));

                j += k;
            }
        }

        ls = lines.toArray(new LineSegment[lines.size()]);
    }

    public int numberOfSegments() {
        return ls.length;
    }

    public LineSegment[] segments() {
        return ls.clone();
    }
}