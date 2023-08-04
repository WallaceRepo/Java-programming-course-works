import java.util.ArrayList;

record OrderItem(int qty, ProductForSale product){}

public class Store {
    private static ArrayList<ProductForSale> storeProducts = new ArrayList<>();

    public static void main(String[] args) {
        storeProducts.add(new ArtObject("Oil Painting", 1350, "Works by AGD in 2010"));
        storeProducts.add(new ArtObject("Sculpture", 2000, "Bronze work by JKF, produced in 1950"));
       listProducts();

       System.out.println("\nOrder 1");
       var order1 = new ArrayList<OrderItem>();
       addItemToOrder(order1,1,2);
       addItemToOrder(order1,0,1);
       printOrder(order1);
    }

    public static void listProducts(){
        for ( var item : storeProducts) {
            System.out.println("-".repeat(30));
            item.showDetails();
        }
    }
    public static void addItemToOrder(ArrayList<OrderItem> order, int orderIndex, int qty) {
        order.add( new OrderItem(qty, storeProducts.get(orderIndex)));
    }
    public static void printOrder(ArrayList<OrderItem> order) {
        double salesTotal = 0;
        for ( var item : order ) {
            item.product().printPricedItem(item.qty());
            salesTotal += item.product().getSalesPrice(item.qty());
        }
        System.out.printf("Sales Total = $%6.2f %n", salesTotal);
    }
}
////
public abstract class ProductForSale {
    protected String type;
    protected double price;
    protected String description;

    public ProductForSale(String type, double price, String description) {
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public double getSalesPrice(int qty){
        return qty * price;
    }
    public void printPricedItem(int qty) {
        System.out.printf("%2d qty at $%8.2f each, %-15s %n", qty, price, description);
    }
    public abstract void showDetails();

}
////
public class ArtObject extends ProductForSale{
    public ArtObject(String type, double price, String description) {
        super(type, price, description);
    }

    @Override
    public void showDetails() {
        System.out.println("This " + type + " is a beautiful reproduction");
        System.out.printf("The price of the piece is $%6.2f %n", price);
        System.out.println(description);
    }
}
/*
------------------------------
This Oil Painting is a beautiful reproduction
The price of the piece is $1350.00 
Works by AGD in 2010
------------------------------
This Sculpture is a beautiful reproduction
The price of the piece is $2000.00 
Bronze work by JKF, produced in 1950

Order 1
 2 qty at $ 2000.00 each, Bronze work by JKF, produced in 1950 
 1 qty at $ 1350.00 each, Works by AGD in 2010 
Sales Total = $5350.00 

*/
