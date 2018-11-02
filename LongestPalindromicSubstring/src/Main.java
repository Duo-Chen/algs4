public class Main {

    public static String findLPSubstring(String s) {
        int n = s.length();
        int N = n * 2 + 1;
        char[] t = new char[N + 1];
        int[] z = new int[N + 1];

        for (int i = 0; i < n; ++i) {
            t[i * 2 + 1] = s.charAt(i);
            t[i * 2] = '.';
        }

        int L = 0, R = 0;
        for (int i = 1; i < N; ++i) {
            z[i] = (R > i) ? Math.min(z[2 * L - i], R - i) : 1;
            while (i - z[i] >= 0 && i + z[i] < N && t[i - z[i]] == t[i + z[i]])
                z[i]++;

            if (i + z[i] > R) {
                L = i;
                R = i + z[i];
            }
        }

        int len = 0, p = 0;
        for (int i = 1; i < N; ++i)
            if (z[i] > len)
                len = z[p = i];

        StringBuilder sb = new StringBuilder();
        for (int i = p - z[p] + 1; i <= p + z[p] - 1; ++i)
            if ((i & 1) > 0)
                sb.append(t[i]);

        return sb.toString();
    }

    public static void main(String[] args) {
	// write your code here
        String[] strings = {
                "madam",
                "banana",
                "abbacddcbaa",
        };

        for (String s : strings) {
            System.out.println(s + " => " + findLPSubstring(s));
        }
    }
}
