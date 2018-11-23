import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {
    private static final int ASCII_SIZE = 256;

    private static int[] createCodeMap() {
        int[] res = new int[ASCII_SIZE];
        for (int i = 0; i < ASCII_SIZE; i++)
            res[i] = i;

        return res;
    }

    private static int findIndex(int[] codeMap, int num, int c) {
        int index = c;
        if (codeMap[index] == c)
            return index;

        for (int i = 0; i < num; i++)
            if (codeMap[i] == c)
                return i;

        for (int i = Math.max(num, index - num); i < Math.min(ASCII_SIZE, index + num + 1); i++)
            if (codeMap[i] == c)
                return i;

        return -1;
    }

    private static void shift(int[] codeMap, int index) {
        int temp = codeMap[index];
        for (int i = index; i > 0; i--)
            codeMap[i] = codeMap[i - 1];

        codeMap[0] = temp;
    }

    private static int move2Front(int[] codeMap, int num, int c) {
        // find index
        int index = findIndex(codeMap, num, c);

        // shift
        shift(codeMap, index);

        return index;
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] codeMap = createCodeMap();
        int num = 0;

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write(String.format("%02X", move2Front(codeMap, num, c)) + " ");
            num++;
        }

        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] codeMap = createCodeMap();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            BinaryStdOut.write((char) codeMap[c]);
            shift(codeMap, c);
        }

        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args == null)
            throw new IllegalArgumentException("");

        if (args.length < 1)
            throw new IllegalArgumentException("");

        switch (args[0].charAt(0)) {
            case '+':
                decode();
                break;

            case '-':
                encode();
                break;

            default:
                throw new IllegalArgumentException("");
        }
    }
}
