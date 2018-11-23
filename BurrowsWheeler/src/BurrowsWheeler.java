import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        StringBuilder sb = new StringBuilder();
        int index = -1;
        for (int i = 0; i < csa.length(); i++) {
            int x = csa.index(i);
            sb.append(s.charAt(x > 0 ? x - 1 : csa.length() - 1));
            if (x == 0)
                index = i;
        }

        StdOut.println(index);
        StdOut.println(sb.toString());
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int index = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();

        char[] chars = s.toCharArray();
        Arrays.sort(chars);

        int num = s.length();
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

        StringBuilder sb = new StringBuilder();
        int p = index;
        do {
            sb.append(chars[p]);
            p = next[p];
        } while (p != index);

        StdOut.println(sb.toString());
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
