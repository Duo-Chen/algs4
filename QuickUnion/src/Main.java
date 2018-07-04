import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Main {

    public static void main(String[] args) {
        //UF();
        SD();
    }

    public static void UF() {
        StdOut.print("Please entry number of union size: ");
        int n = StdIn.readInt();
        QuickUnion uf = new QuickUnion(n);

        try {
            while (!StdIn.isEmpty()) {
                int p = StdIn.readInt();
                int q = StdIn.readInt();
                if (uf.connected(p, q)) {
                    StdOut.println(p + " & " + q + " are already connected.");
                    continue;
                }

                uf.union(p, q);
                StdOut.println("Union(" + p + ", " + q + ")");
            }
        } catch (Exception ex) {

        }

        StdOut.println(uf.count() + " components");
        uf.print();
    }

    public static void SD() {
        StdOut.print("Please entry number of successor size: ");
        int n = StdIn.readInt();
        Successor sd = new Successor(n);
        StdOut.print("Enter number remove: ");

        try {
            while (!StdIn.isEmpty()) {
                int x = StdIn.readInt();
                sd.Remove(x);
                StdOut.print("Enter number remove: ");
            }
        } catch (Exception ex) {

        }

        sd.Print();
    }
}
