public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        throw new IllegalArgumentException("not implemented");
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        throw new IllegalArgumentException("not implemented");
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
