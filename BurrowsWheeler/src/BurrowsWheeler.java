public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        throw new IllegalArgumentException("not implemented");
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        throw new IllegalArgumentException("not implemented");
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
