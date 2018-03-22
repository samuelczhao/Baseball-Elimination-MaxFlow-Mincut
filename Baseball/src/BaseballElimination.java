import java.util.Scanner;

public class BaseballElimination
{
	public BaseballElimination(String filename)
	{
		In in = new In(filename);
		
		// TODO: USE THIS SOMEHOW MAYBE: Count of teams:
		int cTeams = Integer.parseInt(in.readLine());
		
		for (int iTeam = 0; iTeam < cTeams; iTeam++)
		{
			String line = in.readLine();
			Scanner lineScanner = new Scanner(line);
			
			// TODO: USE THIS SOMEHOW MAYBE: Name of team
			lineScanner.next();
			
			// TODO: USE THIS SOMEHOW MAYBE: Number of wins for the team
			lineScanner.nextInt();
			
			// TODO: USE THIS SOMEHOW MAYBE: Number of losses for the team
			lineScanner.nextInt();
			
			// TODO: USE THIS SOMEHOW MAYBE: Number of total remaining games for the team
			lineScanner.nextInt();
			
			for (int iAgainst = 0; iAgainst < cTeams; iAgainst++)
			{
				// TODO: USE THIS SOMEHOW MAYBE: Number of games remaining between iTeam and iAgainst
				lineScanner.nextInt();
			}
		}
	}

	public int numberOfTeams()
	{
		throw new UnsupportedOperationException();
	}

	public Iterable<String> teams()
	{
		throw new UnsupportedOperationException();
	}

	public int wins(String team)
	{
		throw new UnsupportedOperationException();
	}

	public int losses(String team)
	{
		throw new UnsupportedOperationException();
	}

	public int remaining(String team)
	{
		throw new UnsupportedOperationException();
	}

	public int against(String team1, String team2)
	{
		throw new UnsupportedOperationException();
	}

	public boolean isEliminated(String team)
	{
		throw new UnsupportedOperationException();
	}

	public Iterable<String> certificateOfElimination(String team)
	{
		throw new UnsupportedOperationException();
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
