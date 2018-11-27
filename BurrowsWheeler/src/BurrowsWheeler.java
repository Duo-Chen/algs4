import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {
    private static final int R = 256;

    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int index = -1;
        for (int i = 0; i < csa.length(); i++) {
            int x = csa.index(i);
            if (x == 0) {
                index = i;
                break;
            }
        }

        BinaryStdOut.write(index);

        for (int i = 0; i < csa.length(); i++) {
            int x = csa.index(i);
            BinaryStdOut.write(s.charAt(x > 0 ? x - 1 : csa.length() - 1));
        }

        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int index = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        int num = s.length();

        char[] chars = new char[s.length()];
        int[] count = new int[R];
        for (int i = 0; i < num; i++)
            count[s.charAt(i)]++;

        int d = 0;
        for (int i = 0; i < R; i++) {
            while (count[i] > 0) {
                chars[d++] = (char) i;
                count[i]--;
            }
        }

        int[] next = new int[num];
        boolean[] t = new boolean[num];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                if (!t[j] && s.charAt(j) == chars[i]) {
                    next[i] = j;
                    t[j] = true;
                    break;
                }
            }
        }

        int p = index;
        for (int i = 0; i < num; i++) {
            BinaryStdOut.write(chars[p]);
            p = next[p];
        }

        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args == null)
            throw new IllegalArgumentException("");

        if (args.length < 1)
            throw new IllegalArgumentException("");

        switch (args[0].charAt(0)) {
            case '+':
                inverseTransform();
                break;

            case '-':
                transform();
                break;

            default:
                throw new IllegalArgumentException("");
        }
    }
}
