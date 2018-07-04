import edu.princeton.cs.algs4.StdOut;

public class Main {
    public static int find3logN(int[] a, int x) {
        int index;
        int peak = findPeak(a);
        index = binarySearch(a, 0, peak, 1, x);
        if (index < 0)
            index = binarySearch(a, peak + 1, a.length - 1, -1, x);
        return index;
    }

    public static int find2logN(int[] a, int x) {
        int s = 0, e = a.length - 1;
        while (s <= e) {
            int p = s + (e - s) / 2;

            if (a[p] == x)
                return p;
            else if (a[p - 1] < a[p] && a[p] < a[p + 1]) { // increase
                if (x < a[p]) {
                    int index = binarySearch(a, 0, p - 1, 1, x);
                    if (index >= 0)
                        return index;
                }

                s = p + 1;
            } else if (a[p - 1] > a[p] && a[p] > a[p + 1]) { // decrease
                if (x < a[p]) {
                    int index = binarySearch(a, p + 1, a.length - 1, -1, x);
                    if (index >= 0)
                        return index;
                }

                e = p - 1;
            } else { // peak
                int index = binarySearch(a, 0, p - 1, 1, x);
                if (index >= 0)
                    return index;

                return binarySearch(a, p + 1, a.length - 1, -1, x);
            }
        }

        return -1;
    }

    private static int findPeak(int[] a) {
        int index = a.length / 2;
        int rangeS = 0, rangeE = a.length - 1;
        while (true) {
            if (a[index] > a[index - 1] && a[index] > a[index + 1])
                break;
            else if (a[index] > a[index - 1] && a[index] < a[index + 1])
                rangeS = index;
            else if (a[index] < a[index - 1] && a[index] > a[index + 1])
                rangeE = index;

            index = (rangeE + rangeS) / 2;
        }
        return index;
    }
    
    private static int binarySearch(int[] a, int start, int end, int d, int x) {
        int s = start, e = end;
        while (s <= e) {
            int p = s + (e - s) / 2;

            if (a[p] == x)
                return p;
            else if (a[p] * d > x * d)
                e = p - 1;
            else
                s = p + 1;
        }

        return -1;
    }

    public static void main(String[] args) {
	    int[] a = {1, 3, 5, 9, 14, 7, 6, 0, -9};
	    int[] b = {3, -9, 8, 1};

	    StdOut.println("peak is " + findPeak(a) + "(4)");
	    StdOut.println("Use 3 log N complexity");
	    for (int i = 0; i < b.length; i++) {
            StdOut.println("try to find " + b[i] + ": " + find3logN(a, b[i]));
        }

        StdOut.println("Use 2 log N complexity");
        for (int i = 0; i < b.length; i++) {
            StdOut.println("try to find " + b[i] + ": " + find2logN(a, b[i]));
        }
    }
}
