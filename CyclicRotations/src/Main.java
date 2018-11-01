import edu.princeton.cs.algs4.BoyerMoore;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class Main {
    private static Iterable<String> findCylicRotationPair(String[] str) {
        HashMap<String, Integer> map = new HashMap<>();
        int L = str[0].length();
        int n = str.length;

        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < n; x++)
                if (x != i)
                    sb.append(str[x]);

            for (int j = 2; j < L; j++) {
                String prefix = str[i].substring(0, j);
                BoyerMoore bm = new BoyerMoore(prefix);
                int offset = bm.search(sb.toString());
                if (offset < L * (n - 1) && (offset + j) % L == 0) {
                    int y = (offset + j) / L;
                    if (y < i)
                        y--;
                    int b = L - offset % L;
                    if (str[i].substring(b).compareTo(str[y].substring(0, L - b)) == 0) {
                        if (!map.containsKey(str[i])) {
                            map.put(str[i], i);
                            map.put(str[y], y);
                        }
                    }
                }
            }
        }

        return map.keySet();
    }

    public static void main(String[] args) {
	// write your code here
        String[] str = {
                "algorithms", "polynomial", "sortsuffix", "boyermoore",
                "structures", "minimumcut", "suffixsort", "stackstack",
                "binaryheap", "digraphdfs", "stringsort", "digraphbfs",
        };

        boolean b = false;
        for (String s : findCylicRotationPair(str)) {
            if (!b)
                StdOut.print(s);
            else
                StdOut.println(" <=> " + s);

            b = !b;
        }
    }
}
