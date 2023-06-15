public class HealthyBurger extends Hamburger {
    private String healthyExtra1Name;
    private double healthyExtra1Price;
    private String healthyExtra2Name;
    private double healthyExtra2Price;

    public HealthyBurger(String meat, double price) {
        super("Healthy", meat, price,"Brown rye");
    }
    public void addHealthyAddition1(String name, double price){
        healthyExtra1Name = name;
        healthyExtra1Price = price;
    }
    public void addHealthyAddition2(String name, double price){
         healthyExtra2Name = name;
         healthyExtra2Price = price;
    }

    @Override
    public double itemizeHamburger() {
        double total = super.itemizeHamburger() + healthyExtra1Price + healthyExtra2Price;
        if( healthyExtra1Price > 0 ) {
            System.out.printf("Added %s for an extra %.2f %n", healthyExtra1Name, healthyExtra1Price);
        }
        if( healthyExtra2Price > 0 ) {
            System.out.printf("Added %s for an extra %.2f %n", healthyExtra2Name, healthyExtra2Price);
        }
        return total;
    }
}
public class DeluxeBurger extends Hamburger {
    public DeluxeBurger() {
        super("Deluxe" , "Sausage and Bacon", 19.10, "White");
    }

    @Override
    public void addHamburgerAddition1(String addition1Name, double addition1Price) {
        System.out.println("No additional items can be added to a deluxe burger");
    }

    @Override
    public void addHamburgerAddition2(String addition2Name, double addition2Price) {
        System.out.println("No additional items can be added to a deluxe burger");
    }

    @Override
    public void addHamburgerAddition3(String addition3Name, double addition3Price) {
        System.out.println("No additional items can be added to a deluxe burger");
    }

    @Override
    public void addHamburgerAddition4(String addition4Name, double addition4Price) {
        System.out.println("No additional items can be added to a deluxe burger");
    }

}
public class BurgerMain {
    public static void main(String[] args) {
        Hamburger hamburger = new Hamburger("Basic", "Sausage", 3.56, "White");
        hamburger.addHamburgerAddition1("Tomato", 0.27);
        hamburger.addHamburgerAddition2("Lettuce", 0.75);
        hamburger.addHamburgerAddition3("Cheese", 1.13);
        System.out.println("Total Burger price is " + hamburger.itemizeHamburger());
 
        HealthyBurger healthyBurger = new HealthyBurger("Bacon", 5.67);
        healthyBurger.addHamburgerAddition1("Egg", 5.43);
        healthyBurger.addHealthyAddition1("Lentils", 3.41);
        System.out.println("Total Healthy Burger price is  " + healthyBurger.itemizeHamburger());
 
        DeluxeBurger db = new DeluxeBurger();
        db.addHamburgerAddition3("Should not do this", 50.53);
        System.out.println("Total Deluxe Burger price is " + db.itemizeHamburger());
    }
}
