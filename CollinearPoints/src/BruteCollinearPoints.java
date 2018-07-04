import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] ls;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Point[] shouldn't be null.");

        for (int i = 0; i < points.length; i++)
            if (points[i] == null)
                throw new IllegalArgumentException("Point shouldn't be null.");

        Point[] buff = points.clone();
        Arrays.sort(buff);
        for (int i = 1; i < buff.length; i++)
            if (buff[i].compareTo(buff[i - 1]) == 0)
                throw new IllegalArgumentException("Repeat point");

        List<Point> lines = new ArrayList<>();

        for (int a1 = 0; a1 < buff.length - 3; a1++) {
            for (int a2 = a1 + 1; a2 < buff.length - 2; a2++) {
                double s1 = buff[a1].slopeTo(buff[a2]);

                for (int a3 = a2 + 1; a3 < buff.length - 1; a3++) {
                    double s2 = buff[a1].slopeTo(buff[a3]);
                    if (Double.compare(s1, s2) != 0)
                        continue;

                    for (int a4 = a3 + 1; a4 < buff.length; a4++) {
                        double s3 = buff[a1].slopeTo(buff[a4]);
                        if (Double.compare(s2, s3) != 0)
                            continue;

                        // check with found lines
                        int isFound = -1;
                        for (int i = 0; i < lines.size() / 2; i++) {
                            if (compareWithSlope(lines.get(i * 2), buff[a1], s1)
                                && compareWithSlope(lines.get(i * 2), buff[a4], s1)
                                && compareWithSlope(lines.get(i * 2 + 1), buff[a1], s1)
                                && compareWithSlope(lines.get(i * 2 + 1), buff[a4], s1)) {
                                isFound = i;
                                break;
                            }
                        }

                        if (isFound < 0) {
                            lines.add(buff[a1]);
                            lines.add(buff[a4]);
                        } else {
                            if (lines.get(isFound * 2).compareTo(buff[a1]) > 0)
                                lines.set(isFound * 2, buff[a1]);
                            if (lines.get(isFound * 2).compareTo(buff[a4]) < 0)
                                lines.set(isFound * 2 + 1, buff[a4]);
                        }
                    }
                }
            }
        }

        int count = lines.size() / 2;
        ls = new LineSegment[count];
        for (int i = 0; i < count; i++)
            ls[i] = new LineSegment(lines.get(i * 2), lines.get(i * 2 + 1));
    }

    private boolean compareWithSlope(Point p, Point q, double slope) {
        if (p.compareTo(q) == 0)
            return true;

        return Double.compare(p.slopeTo(q), slope) == 0;
    }

    public int numberOfSegments() {
        return ls.length;
    }

    public LineSegment[] segments() {
        return ls.clone();
    }
}
