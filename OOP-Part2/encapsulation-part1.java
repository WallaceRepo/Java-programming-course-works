// When class is not using encapsulation: 
public class Player {
    public String name;
    public int health;
    public String weapon;

    public void loseHealth(int damage) {
        health = health - damage;
        if(health <= 0) {
            System.out.println("Player knocked out of game");
        }
    }
    public int healthRemaining(){
        return health;
    }
    public void restoreHealth(int extraHealth){
        health = health + extraHealth;
        if ( health > 100) {
            System.out.println("Player health is restored");
            health = 100;
        }
    }
}
public class Main {
    public static void main(String[] args) {
        //    Player player = new Player();
//            player.name = "Tim";
//            player.health = 20;
//            player.weapon = "sword";
//
//            int damage = 10;
//            player.loseHealth(damage);
//            System.out.println("Remaining health = "  + player.healthRemaining());
//
//            player.health = 200;
//            player.loseHealth(damage);
//            System.out.println("Remaining health is = " + player.healthRemaining());


    }
}
