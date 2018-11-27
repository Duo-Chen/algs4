import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
	// write your code here

        In in = new In("amendments.txt");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16000; i++)
            sb.append(in.readChar());

        long start = System.currentTimeMillis();
        int count = 0;
        while (true) {
            long t = System.currentTimeMillis();
            if (t > start + 10000)
                break;
            CircularSuffixArray csa = new CircularSuffixArray(sb.toString());
            for (int i = 0; i < csa.length(); i++) ;

            count++;
        }

        StdOut.println(count);
    }
}
