import java.util.TreeMap;

public class CircularSuffixArray {
    private final int len;
    private final int[] indices;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException("");

        len = s.length();
        indices = new int[len];
        TreeMap<String, Integer> set = new TreeMap<>();
        for (int i = 0; i < len; i++) {
            String buff = s.substring(i) + s.substring(0, i);
            set.put(buff, i);
        }

        int index = 0;
        for (String key : set.keySet()) {
            indices[index++] = set.get(key);
        }
    }

    // length of s
    public int length() {
        return len;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= len)
            throw new IllegalArgumentException("out of range");

        return indices[i];
    }

    // unit testing (required)
    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("ABRACADABRA!");
        int[] ans = { 11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2 };
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] != csa.index(i))
                throw new IllegalArgumentException("wrong answer");
        }
    }
}