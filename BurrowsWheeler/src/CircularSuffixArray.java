import java.util.Arrays;

public class CircularSuffixArray {
    private final int len;
    private final Integer[] indices;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException("");

        len = s.length();
        indices = new Integer[len];
        for (int i = 0; i < len; i++)
            indices[i] = i;

        Arrays.sort(indices, (Integer first, Integer second) -> {
            int firstIndex = first;
            int secondIndex = second;
            for (int i = 0; i < len; i++) {
                if (firstIndex > len - 1)
                    firstIndex = 0;
                if (secondIndex > len - 1)
                    secondIndex = 0;

                if (s.charAt(firstIndex) < s.charAt(secondIndex))
                    return -1;
                else if (s.charAt(firstIndex) > s.charAt(secondIndex))
                    return 1;

                firstIndex++;
                secondIndex++;
            }

            return 0;
        });
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