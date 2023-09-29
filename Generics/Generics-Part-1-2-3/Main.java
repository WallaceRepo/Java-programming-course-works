mport java.beans.beancontext.BeanContextServiceAvailableEvent;

interface Player {
    // remember, any method we add without a method body, is implicitly
    //public and static on an interface.
    String name(); // abstract method;
}
record BaseBallPlayer(String name, String position) implements Player { }
record FootballPlayer(String name, String position) implements Player { }
record VolleyballPlayer( String name, String position) implements Player {}

public class Main {
    public static void main ( String[] args) {
        var philly = new Affiliation("city", "Dallas, TX", " US");
        BaseballTeam phillies1 = new BaseballTeam("Dallas team");
        BaseballTeam aster1 = new BaseballTeam((" Aster Team"));
        scoreResult(phillies1, 3, aster1, 6);

        SportsTeam phillies2 = new SportsTeam("Dallas team");
        SportsTeam aster2 = new SportsTeam((" Aster Team"));
        scoreResult(phillies2, 3, aster2, 6);

        Team <BaseBallPlayer, Affiliation> phillies = new Team<BaseBallPlayer, Affiliation>("Dallas team", philly);
        Team <BaseBallPlayer, Affiliation> aster = new Team<BaseBallPlayer, Affiliation>(" Aster Team");
        scoreResult(phillies, 3, aster, 6);

        var harper = new BaseBallPlayer("B Harper", "right Fielder");
        var marsh = new BaseBallPlayer("Marsh", "Right Fielder");
        phillies.addTeamMember(harper);
        phillies.addTeamMember(marsh);

        phillies.listTeamMembers();
        // Dallas team Roster:
        //[BaseBallPlayer[name=B Harper, position=right Fielder], BaseBallPlayer[name=Marsh, position=Right Fielder]]

        SportsTeam afc1 = new SportsTeam(" ribba");
        Team <FootballPlayer, String> afc = new Team<FootballPlayer, String>(" ribba","City of Gunbarrel, TX, in US" );
        var tex = new FootballPlayer("tex walker", "center half forward");
        afc.addTeamMember(tex);
        afc.listTeamMembers();

        Team<VolleyballPlayer, Affiliation> adelaide = new Team<>("Adelaite Storm");
        adelaide.addTeamMember(new VolleyballPlayer("N Roberts", "Setter"));
        adelaide.listTeamMembers();

        var canberra = new Team<VolleyballPlayer, Affiliation >("canberra heat");
        canberra.addTeamMember(new VolleyballPlayer("B Black", " Setter"));
        canberra.listTeamMembers();
        scoreResult(canberra, 0, adelaide, 1);


        /// above has a problem that we can add any player to the team
        // for example;
        var john = new BaseBallPlayer("John James", "Center Fielder");
        var rory = new FootballPlayer("Rory Laird", "Midfield");
        afc.addTeamMember(rory);
        // afc.addTeamMember(john);
        afc.listTeamMembers();
    }
    public static void scoreResult(BaseballTeam team1, int t1_score, BaseballTeam team2, int t2_score) {
        String message = team1.setScore(t1_score, t2_score);
        team2.setScore(t2_score, t1_score);
        System.out.printf("%s %s %s %n", team1, message, team2);
    }

    public static void scoreResult(SportsTeam team1, int t1_score, SportsTeam team2, int t2_score) {
        String message = team1.setScore(t1_score, t2_score);
        team2.setScore(t2_score, t1_score);
        System.out.printf("%s %s %s %n", team1, message, team2);
    }
 //   ribba Roster:
  //          [FootballPlayer[name=tex walker, position=center half forward], BaseBallPlayer[name=John James, position=Center Fielder]]
    public static void scoreResult(Team team1, int t1_score, Team team2, int t2_score) {
     String message = team1.setScore(t1_score, t2_score);
     team2.setScore(t2_score, t1_score);
     System.out.printf("%s %s %s %n", team1, message, team2);
 }


}

// Dallas team (Ranked 3) lost to  Aster Team (Ranked 1)
