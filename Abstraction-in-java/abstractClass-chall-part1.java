import java.util.ArrayList;

public class Store {
    private static ArrayList<ProductForSale> storeProducts = new ArrayList<>();

    public static void main(String[] args) {
        storeProducts.add(new ArtObject("Oil Painting", 1350, "Works by AGD in 2010"));
        storeProducts.add(new ArtObject("Sculpture", 2000, "Bronze work by JKF, produced in 1950"));
       listProducts();
    }

    public static void listProducts(){
        for( var item : storeProducts) {
            System.out.println("-".repeat(30));
            item.showDetails();
        }
    }
}

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
        System.out.printf("%2d qty at $%8.2f each, %-15s %-35s %/n", qty, price, description);
    }
    public abstract void showDetails();

}
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
*/
