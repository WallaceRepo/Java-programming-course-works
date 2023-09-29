import java.util.ArrayList;
import java.util.List;

public class SportsTeam {
    private String teamName;
    private List< Player> teamMember = new ArrayList<>(); // Generic
    private int totalWins = 0;
    private int totalLosses = 0;
    private int totalTied = 0;

    public SportsTeam(String teamName) {
        this.teamName = teamName;
    }
    public void addTeamMember(Player player) {
        if( !teamMember.contains(player)) {
            teamMember.add(player);
        }
    }
    public void listTeamMembers() {
        System.out.println(teamName + " Roster:");
        System.out.println(teamMember);
    }
    public int ranking() {
        return (totalLosses * 2) + totalTied + 1;
    }
    public  String setScore( int ourScore, int theirScore) {
        String message = "lost to";
        if(ourScore > theirScore ) {
            totalWins++;
            message = "beat";
        } else if(ourScore == theirScore ) {
            totalTied++;
            message = "tied";
        } else {
            totalLosses++;
        }
        return message;
    }

    @Override
    public String toString() {
        return teamName + " (Ranked " + ranking() + ")";

    }
}
