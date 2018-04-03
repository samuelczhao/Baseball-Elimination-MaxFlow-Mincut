import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BaseballElimination
{

	private String[] names;
	private boolean[] eliminated, tried;
	private int numOfTeam;
	private int[] win, lose, remain;
	private int[][] match;
	private Iterable<String>[] certificate;

	public BaseballElimination(String filename)
	{
		In in = new In(filename);

		// TODO: USE THIS SOMEHOW MAYBE: Count of teams:
		int cTeams = Integer.parseInt(in.readLine());
		numOfTeam = cTeams;
		names = new String[numOfTeam];
		eliminated = new boolean[numOfTeam];
		tried = new boolean[numOfTeam];
		win = new int[numOfTeam];
		lose = new int[numOfTeam];
		remain = new int[numOfTeam];
		match = new int[numOfTeam][numOfTeam];
		certificate = new Iterable[numOfTeam];

		for (int i = 0; i < cTeams; i++)
		{
			String line = in.readLine();
			Scanner lineScanner = new Scanner(line);

			// TODO: USE THIS SOMEHOW MAYBE: Name of team
			names[i] = lineScanner.next();

			// TODO: USE THIS SOMEHOW MAYBE: Number of wins for the team
			win[i] = lineScanner.nextInt();

			// TODO: USE THIS SOMEHOW MAYBE: Number of losses for the team
			lose[i] = lineScanner.nextInt();

			// TODO: USE THIS SOMEHOW MAYBE: Number of total remaining games for the team
			remain[i] = lineScanner.nextInt();

			for (int iAgainst = 0; iAgainst < cTeams; iAgainst++)
			{
				// TODO: USE THIS SOMEHOW MAYBE: Number of games remaining between iTeam and
				// iAgainst
				match[i][iAgainst] = lineScanner.nextInt();
			}
		}
	}

	public int numberOfTeams()
	{
		return numOfTeam;
	}

	private int getTeam(String name)
	{
		for (int i = 0; i < numOfTeam; i++)
			if (names[i].equals(name))
				return i;
		throw new IllegalArgumentException();
	}

	public Iterable<String> teams()
	{
		return Arrays.asList(names);
	}

	public int wins(String team)
	{
		return win[getTeam(team)];
	}

	public int losses(String team)
	{
		return lose[getTeam(team)];
	}

	public int remaining(String team)
	{
		return remain[getTeam(team)];
	}

	public int against(String team1, String team2)
	{
		return match[getTeam(team1)][getTeam(team2)];
	}

	public boolean isEliminated(String team)
	{
		int t = getTeam(team);
		if (tried[t])
			return eliminated[t];
		tried[getTeam(team)] = true;
		// trivial
		for (int i = 0; i < numOfTeam; i++)
		{
			if (win[t] + remain[t] < win[i])
			{
				certificate[t] = Arrays.asList(new String[] { names[i] });
				return eliminated[t] = true;
			}
		}
		// graph
		FlowNetwork flow = new FlowNetwork(numOfTeam * numOfTeam + numOfTeam + 2);
		double total = 0;
		for (int i = 0; i < numOfTeam; i++)
			for (int j = i + 1; j < numOfTeam; j++)
			{
				if (i == t || j == t)
					continue;
				total += match[i][j];
				flow.addEdge(new FlowEdge(0, map(i, j), match[i][j]));
				flow.addEdge(new FlowEdge(map(i, j), map(i), Double.POSITIVE_INFINITY));
				flow.addEdge(new FlowEdge(map(i, j), map(j), Double.POSITIVE_INFINITY));
			}
		for (int i = 0; i < numOfTeam; i++)
		{
			if (i == t)
				continue;
			flow.addEdge(new FlowEdge(map(i), 1, win[t] + remain[t] - win[i]));
		}
		FordFulkerson fordFulkerson = new FordFulkerson(flow, 0, 1);
		if (fordFulkerson.value() == total)
			return eliminated[t] = false;
		ArrayList<String> c = new ArrayList<>();
		for (int i = 0; i < numOfTeam; i++)
		{
			if (i == t)
				continue;
			if (!fordFulkerson.inCut(map(i)))
				c.add(names[i]);
		}
		certificate[t] = c;
		return eliminated[t] = true;
	}

	private int map(int i, int j)
	{
		if (i > j)
			return j * numOfTeam + i + numOfTeam + 2;
		return i * numOfTeam + j + numOfTeam + 2;
	}

	private int map(int i)
	{
		return numOfTeam + 2;
	}

	public Iterable<String> certificateOfElimination(String team)
	{
		isEliminated(team);
		return certificate[getTeam(team)];
	}

	public static void main(String[] args)
	{
		BaseballElimination division = new BaseballElimination("testInput/teams4.txt");
		for (String team : division.teams())
		{
			if (division.isEliminated(team))
			{
				StdOut.print(team + " is eliminated by the subset R = { ");
				for (String t : division.certificateOfElimination(team))
				{
					StdOut.print(t + " ");
				}
				StdOut.println("}");
			}
			else
			{
				StdOut.println(team + " is not eliminated");
			}
		}
	}
}
