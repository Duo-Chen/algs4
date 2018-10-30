import edu.princeton.cs.algs4.StdOut;

public class Main {
    private static final String folder = "D:\\algs4\\BaseballElimination\\src\\";

    public static void main(String[] args) {
	// write your code here
        String[] files = { "teams4.txt", "teams5.txt" };
        for (String file : files) {
            StdOut.println("====== " + file + " ======");
            runElimination(folder + file);
            StdOut.println();
        }
    }

    private static void runElimination(String file) {
        BaseballElimination division = new BaseballElimination(file);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
