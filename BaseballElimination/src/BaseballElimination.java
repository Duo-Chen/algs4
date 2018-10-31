import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class BaseballElimination {
    private final String[] teams;
    private final int[] win;
    private final int[] loss;
    private final int[] remain;
    private final int[][] against;
    private final boolean[] elimination;
    private final int[] flowFrom;
    private final Bag<String>[] subset;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int teamNum = Integer.parseInt(in.readLine());
        teams = new String[teamNum];
        win = new int[teamNum];
        loss = new int[teamNum];
        remain = new int[teamNum];
        against = new int[teamNum][teamNum];
        elimination = new boolean[teamNum];
        flowFrom = new int[teamNum];
        subset = (Bag<String>[]) new Bag[teamNum];

        for (int index = 0; index < teamNum; index++) {
            String name = in.readString();
            teams[index] = name;
            win[index] = in.readInt();
            loss[index] = in.readInt();
            remain[index] = in.readInt();
            for (int i = 0; i < teamNum; i++)
                against[index][i] = in.readInt();
        }

        int gamePair = (teamNum - 1) * (teamNum - 2) / 2;
        int nodes = teamNum + 1 + gamePair;
        for (int i = 0; i < teamNum; i++) {
            FlowNetwork fn = createFlowNetwork(teamNum, i, nodes);
            if (fn != null) {
                FordFulkerson ff = new FordFulkerson(fn, nodes - 1, i);
                elimination[i] = flowFrom[i] > ff.value();
                if (elimination[i]) {
                    subset[i] = new Bag<>();
                    for (int v = 0; v < teamNum; v++) {
                        if (v == i)
                            continue;

                        if (ff.inCut(v))
                            subset[i].add(teams[v]);
                    }
                }
            } else {
                elimination[i] = true;
            }
        }
    }

    private FlowNetwork createFlowNetwork(int teamNum, int i, int nodes) {
        flowFrom[i] = 0;
        FlowNetwork fn = new FlowNetwork(nodes);
        for (int j = 0; j < teamNum; j++) {
            if (j == i)
                continue;
            int c = win[i] + remain[i] - win[j];
            if (c < 0) {
                if (subset[i] == null)
                    subset[i] = new Bag<>();

                subset[i].add(teams[j]);
                return null;
            } else
                fn.addEdge(new FlowEdge(j, i, c));
        }

        int t = nodes - 1;
        for (int a = 0; a < teamNum; a++) {
            if (a == i)
                continue;

            for (int b = 0; b < a; b++) {
                if (b == i)
                    continue;

                flowFrom[i] += against[a][b];
                fn.addEdge(new FlowEdge(nodes - 1, --t, against[a][b]));
                fn.addEdge(new FlowEdge(t, a, Double.POSITIVE_INFINITY));
                fn.addEdge(new FlowEdge(t, b, Double.POSITIVE_INFINITY));
            }
        }

        return fn;
    }

    // number of teams
    public int numberOfTeams() {
        return win.length;
    }

    // all teams
    public Iterable<String> teams() {
        Queue<String> res = new Queue<>();
        for (String name : teams)
            res.enqueue(name);

        return res;
    }

    private int findTeamIndex(String name) {
        for (int i = 0; i < teams.length; i++)
            if (teams[i].compareTo(name) == 0)
                return i;

        throw new IllegalArgumentException("No this team " + name);
    }

    // number of wins for given team
    public int wins(String team) {
        return win[findTeamIndex(team)];
    }

    // number of losses for given team
    public int losses(String team) {
        return loss[findTeamIndex(team)];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        return remain[findTeamIndex(team)];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        return against[findTeamIndex(team1)][findTeamIndex(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return elimination[findTeamIndex(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        return subset[findTeamIndex(team)];
    }
}
