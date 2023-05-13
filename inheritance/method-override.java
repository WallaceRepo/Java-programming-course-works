// 3 usages for method override from subclass on same method super / parent class
// - to create completely different behavior
// - it's just redundant to call super() on sub class method
// - call super() and add some more behavior in the sub class method 
public class Dog extends Animal {

    private String earShape;
    private String tailShape;

    public Dog(String type, double weight ){
        this(type, weight, "Perky", "Curled");
    }
    public Dog(String type,  double weight, String earShape, String tailShape) {
        super(type, weight < 15 ? "small" : (weight < 35 ? "medium" : "large"), weight);  // nested ternary operator
        this.earShape = earShape;
        this.tailShape = tailShape;
    }


    public Dog(){
       // super();     // is a lot like this(); Like this(), it has to be the first constructor.
        super("Mutt", "big", 50);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "earShape='" + earShape + '\'' +
                ", tailShape='" + tailShape + '\'' +
                "} " + super.toString();   // here is super is more like this to access super class's method
    }

    @Override
    public void move(String speed) {
        super.move(speed);
        System.out.println("Dogs walk, run and wag their tail");
    }

    public void makeNoise(){

    }
}
public class Animal {
    private String type;
    private String size;
    private double weight;

    public Animal(){

    }
    public Animal(String type, String size, double weight) {
        this.type = type;
        this.size = size;
        this.weight = weight;
    }
    public String toString() {
        return "Animal {" +
                "type= '" + type + '\'' +
                ", size= '"+ size + '\'' +
                ", weight= '"+ size + '\'' +
                '}';
    }
    public void move(String speed) {
        System.out.println(type + " moves " + speed);
    }
    public void makeNoise() {
        System.out.println(type + " makes some kind of noise ");
    }

}
public class Main {
    public static void main(String[] args) {

         Animal animal = new Animal( "General Animal", "Huge", 400);
         doAnimalStuff(animal, "slow");

         Dog dog = new Dog();
         doAnimalStuff(dog, "fast");

         Dog yorkie = new Dog("Yorkie", 15);
         doAnimalStuff(yorkie, "fast");

         Dog retriever = new Dog("Labrador Retriever", 67, "Floppy", "Swagger");
         doAnimalStuff(retriever, "slow");
    }

    public static void  doAnimalStuff(Animal animal, String speed) {
        animal.makeNoise();
        animal.move(speed);
        System.out.println(animal);
        System.out.println("______");
    }
}


