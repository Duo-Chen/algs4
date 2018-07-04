import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Quick;

public class Main {
    public static int count(Comparable[] a) {
        int result = 0;

        Quick.sort(a);

        for (int i = 0; i < a.length - 2; i++) {
            int x = (int) a[i];
            int j = i + 1;
            int k = a.length - 1;

            while (j < k) {
                int y = (int) a[j];
                int z = (int) a[k];
                int sum = x + y + z;

                if (sum == 0) {
                    result++;
                    j++;
                    k--;

                    while (j < k && y == (int) a[j + 1])
                        j++;
                    while (j < k && z == (int) a[k - 1])
                        k--;
                } else if (sum < 0) {
                    j++;
                } else {
                    k--;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Comparable[] a = {16, 0, 10, -10, 8, 2, 4, -20};
        StdOut.println("# of number 3 sum : " + count(a));
        StdOut.print(" in {");
        for (int i = 0; i < a.length; i++) {
            StdOut.print(" " + a[i] + ",");
        }
        StdOut.println("}");
    }
}
