public class Main {
    public static void main(String[] args) {

         Animal animal = new Animal( "General Animal", "Huge", 400);
         doAnimalStuff(animal, "slow");

         Dog dog = new Dog();
         doAnimalStuff(dog, "fast");
    }

    public static void  doAnimalStuff(Animal animal, String speed) {
        animal.makeNoise();
        animal.move(speed);
        System.out.println(animal);
        System.out.println("______");
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
public class Dog extends Animal {

    public Dog(){
       // super();     // is a lot like this(); Like this(), it has to be the first constructor.
        super("Mutt", "big", 50);
    }

}

