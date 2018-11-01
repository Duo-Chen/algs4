import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class Main {

    private static Iterable<Integer> find2Sum(int[] numbers, int T) {
        Queue<Integer> res = new Queue<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++)
            map.put(numbers[i], i);

        for (int i = 0; i < numbers.length; i++) {
            int complete = T - numbers[i];
            if (map.containsKey(complete)) {
                res.enqueue(numbers[i]);
                res.enqueue(complete);

                map.remove(numbers[i]);
                map.remove(complete);
            }
        }

        return res;
    }

    public static void main(String[] args) {
	// write your code here
        int[] numbers = new int[64];
        for (int i = 0; i < 64; i++)
            numbers[i] = i + 1;

        boolean b = true;
        for (int a : find2Sum(numbers, 75)) {
            if (b)
                StdOut.print("(" + a + ", ");
            else
                StdOut.println(a + ")");

            b = !b;
        }
    }
}
