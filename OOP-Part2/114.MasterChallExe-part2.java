// Polymorphysm, encapsulation, and composition used together
public class Main {
    public static void main(String[] args) {

        Item coke = new Item("drink", "coke", 1.50);
        coke.printItem();
        coke.setSize("LARGE");
        coke.printItem();

        Item avocado = new Item("Topping", "avocado", 1.50);
        avocado.printItem();

        Burger burger = new Burger("regular", 4.00);
        burger.addToppings("BACON", "CHEESE", "MAYO");
        burger.printItem();

        MealOrder regularMeal = new MealOrder();
        regularMeal.printItemizedList();
        regularMeal.addBurgerToppings("BACON", "CHEESE", "MAYO");
        regularMeal.setDrinkSize("LARGE");
        regularMeal.printItemizedList();

        MealOrder secondMeal = new MealOrder("turkey", "7-up", "chili");
        secondMeal.addBurgerToppings("LETTUCE", "CHEESE", "MAYO");
        secondMeal.setDrinkSize("SMALL");
        secondMeal.printItemizedList();
    }


}
public class Burger extends Item{
    private Item extra1;
    private Item extra2;
    private Item extra3;

    public Burger(String name, double price) {
        super("Burger", name, price);
    }

    @Override
    public String getName() {
        return super.getName() + "BURGER";
    }

    @Override
    public double getAdjustedPrice() {
        return getBasePrice() +
                ((extra1 == null) ? 0 : extra1.getAdjustedPrice()) +
                ((extra2 == null) ? 0 : extra2.getAdjustedPrice()) +
                ((extra3 == null) ? 0 : extra3.getAdjustedPrice());

    }
    public double getExtraPrice(String toppingName) {
        return switch (toppingName.toUpperCase()){
            case "AVOCADO", "CHEESE" -> 1.0;
            case "BACON", "HAM", "SALAMI" -> 1.5;
            default -> 0.0;
        };
    }
    public void addToppings(String extra1, String extra2, String extra3) {
         this.extra1 = new Item("TOPPING", extra1, getExtraPrice(extra1));
         this.extra2 = new Item("TOPPING", extra2, getExtraPrice(extra2));
         this.extra3 = new Item("TOPPING", extra3, getExtraPrice(extra3));
    }

    @Override
    public void printItem() {
        printItemizedList();
        System.out.println("-".repeat(30));
        super.printItem();
    }

    public void printItemizedList(){
        printItem("BASE BURGER", getBasePrice());
        if(extra1 != null) {
            extra1.printItem();
        }
        if(extra2 != null) {
            extra2.printItem();
        }
        if(extra3 != null) {
            extra3.printItem();
        }
    }
}
public class MealOrder {
     private Burger burger;
     private Item drink;
     private Item side;
     public MealOrder(){
         this("regular", "coke", "fries");
     }
     public MealOrder(String burgerType, String drinkType, String sideType) {
         this.burger = new Burger(burgerType, 4.0);
         this.drink = new Item("drink", drinkType, 1.00);
         this.side = new Item("side", sideType, 1.50);
     }
     public double getTotalPrice(){
         return side.getAdjustedPrice() + drink.getAdjustedPrice()+burger.getAdjustedPrice();
     }
     public void printItemizedList(){
         burger.printItem();
         drink.printItem();
         side.printItem();
         System.out.println("-".repeat(30));
         Item.printItem("TOTAL PRICE", getTotalPrice());
     }
     public void addBurgerToppings(String extra1, String extra2, String extra3){
         burger.addToppings(extra1, extra2, extra3);
     }
     public void setDrinkSize(String size) {
         drink.setSize(size);
     }
}
public class Item {
    private String type;
    private String name;
    private double price;
    private String size = "MEDIUM";

    public Item(String type, String name, double price) {
        this.type = type.toUpperCase();
        this.name = name.toUpperCase();
        this.price = price;
    }

    public String getName() {
        if (type.equals("SIDE") || type.equals("DRINK")){
            return size + " " + name;
        }
        return name;
    }

    public double getBasePrice() {
        return price;
    }
    public double getAdjustedPrice(){
        return switch (size) {
            case "SMALL" -> getBasePrice() - 0.5;
            case "LARGE" -> getBasePrice() + 1;
            default -> getBasePrice();
        };
    }

    public void setSize(String size) {
        this.size = size;
    }
    public static void printItem(String name, double price) {
        System.out.printf("%20s:%6.2f%n", name, price);
    }
    public void printItem(){
        printItem(getName(), getAdjustedPrice());
    }
}

