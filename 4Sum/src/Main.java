import edu.princeton.cs.algs4.*;

public class Main {

     private static class SumSet {
        private final Integer i;
        private final Integer j;
        private final Integer k;
        private final Integer l;

        public SumSet(int a, int b, int c, int d) {
            if (a + b != c + d)
                throw new IllegalArgumentException("not 4 sum");

            i = a;
            j = b;
            k = c;
            l = d;
        }

        public boolean equals(Object that) {
            if (that == null)
                return false;

            if (this == that)
                return true;

            if (!(that instanceof SumSet))
                return false;

            SumSet b = (SumSet)that;
            return i == b.i && j == b.j && k == b.k && l == b.l;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append(" + ");
            sb.append(j);
            sb.append(" = ");
            sb.append(k);
            sb.append(" + ");
            sb.append(l);
            return sb.toString();
        }
    }

    public static Iterable<SumSet> find4Sum(Integer[] array) {
        Quick.sort(array);
        Queue<SumSet> set = new Queue<>();

        for (int i = 0; i < array.length - 3; i++) {
            for (int j = array.length - 1; j > i + 2; j--) {
                int sum = array[i] + array[j];
                for (int k = i + 1; k < j - 1; k++) {
                    for (int l = j - 1; l > k; l-- ) {
                        if (array[k] + array[l] == sum)
                            set.enqueue(new SumSet(array[i], array[j], array[k], array[l]));
                    }
                }
            }
        }

        return set;
    }

    public static void main(String[] args) {
	// write your code here
        int n = 100;
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++)
            array[i] = i + 1;

        StdRandom.shuffle(array);
        StdOut.println(array);
        for (SumSet x : find4Sum(array)) {
            StdOut.println(x);
        }
    }
}
