import java.util.ArrayList;
import java.util.List;
 // Type parameters reserved keys are:
// E - Element from Collections framework
// k - key for mapped key
// N - number
// T - type
// V - value
record  Affiliation( String name, String type, String countryCode) {
     @Override
     public String toString() {
         return "Affiliation{" +
                 "name='" + name + '\'' +
                 ", type='" + type + '\'' +
                 ", countryCode='" + countryCode + '\'' +
                 '}';
     }
 }
public class Team <T extends Player, S > { // This is saying the parameterized type T, has to be a Player, or a subtype of Player.
    private String teamName;
    private List< T> teamMember = new ArrayList<>(); // Generic
    private int totalWins = 0;
    private int totalLosses = 0;
    private int totalTied = 0;
    private S affiliation;

     public Team(String teamName, S affiliation) {
         this.teamName = teamName;
         this.affiliation = affiliation;
     }

     public Team(String teamName) {
        this.teamName = teamName;
    }
    public void addTeamMember(T player) {
        if( !teamMember.contains(player)) {
            teamMember.add(player);
        }
    }
    public void listTeamMembers() {
        System.out.println(teamName + " Roster:");
        System.out.println((affiliation == null ? "" : " AFFILIATION: " + affiliation));
        for( T t : teamMember) {
            System.out.println(t.name());
        }
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
