import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Following won't compile, animal is abstract. Even if it has fields and constructor method,
        // Abstract class can't instantiate
        //  Animal animal = new Animal("animal", "big", 100);
        Dog dog = new Dog("Wolf", "big", 100);
        dog.makeNoise();
        doAnimalStuff(dog);

        // Abstract class can't instantiate
        // However, we can use abstract type in methods, declarations and lists
        // Let's create an array list of an Abstracted type
        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(dog);
        animals.add( new Dog("German Shepard" , "big", 150));
        animals.add( new Fish("Goldfish", "small", 1));
        animals.add(new Fish("Barracuda", "big", 75));
        animals.add( new Dog("Pug", "small", 20));

        animals.add(new Horse("Clydesdale", "large", 1000));

         // It does polymorphism at the run time. Executes code specific to the concrete type
        for( Animal animal : animals) {
            doAnimalStuff(animal);
            if( animal instanceof Mammal currentMammal) {
                currentMammal.shedHair();
            }
        }
    }
    public static void doAnimalStuff(Animal animal) {
        animal.makeNoise();
        animal.move("slow");
    }

}

//        Howling!
//        Howling!
//        Wolf walking
//        Howling!
//        Wolf walking
//        Woof!
//        German Shepard walking
//        swish
//        Goldfish lazily swimming
//        splash
//        Barracuda lazily swimming
//        Woof!
//        Pug walking
//Horse (Clydesdale)
//walks

// 
public abstract class Animal {
     protected String type; // Animal classes subclasses can access directly, doesn't need Getter method
     private String size;
     private double weight;

    public Animal(String type, String size, double weight) {
        this.type = type;
        this.size = size;
        this.weight = weight;
    }
     // Abstract methods can't be private. Because it's subclass can't even see it.
    public abstract void move(String speed); // Abstract methods cant have body
    public abstract void makeNoise();

    // Abstract class can have a concrete type method inside it
    // final method can not be overriden in subclasses
    public final String getExplicitType(){
        return getClass().getSimpleName() + " (" + type + ")";
    }
}

// let's extend Animal abstract class to abstract subclass
// Abstract subclass doesn't have to implement abstract methods
// -It can implement ALL of the parent's abstract methods.
// -It can implement SOME of them.
// -It can implement NONE of them.
// -It can also include additional abstract methods, which will force subclasses to implement both Animal's
// abstract methods, as well as Mammal's.
abstract class Mammal extends Animal {

     public Mammal(String type, String size, double weight) {
         super(type, size, weight);
     }

    @Override
    public void move(String speed) {
        System.out.println(getExplicitType() + " ");
        System.out.println(speed.equals("slow") ? "walks" : "runs");
    }

    public abstract void shedHair();
}

// 
public class Horse extends Mammal {

    public Horse(String type, String size, double weight) {
        super(type, size, weight);
    }

    @Override
    public void makeNoise() {

    }

    @Override
    public void shedHair() {

        System.out.println(getExplicitType() + "sheds in the spring");
    }
}

//
public class Dog extends Animal {

    public Dog(String type, String size, double weight) {
        super(type, size, weight);
    }

    @Override
    public void move(String speed) {
       // Abstract class forces to have all method in its subclass
        // We have an option for this method to leave empty.
        if(speed.equals("slow")) {
            System.out.println(type + " walking" + getExplicitType());
        } else {
            System.out.println(type + "running" + getExplicitType());
        }
    }

    @Override
    public void makeNoise() {

         if( type == "Wolf") {
             System.out.println("Howling! ");
         } else {
             System.out.println("Woof!");
         }
    }
}

// 
public class Fish extends Animal {

    public Fish(String type, String size, double weight) {
        super(type, size, weight);
    }

    @Override
    public void move(String speed) {
        // Abstract class forces to have all method in its subclass
        // We have an option for this method to leave empty.
        if(speed.equals("slow")) {
            System.out.println(type + " lazily swimming" +  getExplicitType());
        } else {
            System.out.println(type + " darting frantically" + getExplicitType());
        }
    }

    @Override
    public void makeNoise() {

        if( type == "Goldfish") {
            System.out.println("swish ");
        } else {
            System.out.println("splash ");
        }
    }

    public static void main(String[] args) {
        // Following won't compile, animal is abstract. Even if it has fields and constructor method,
        // Abstract class can't instantiate
        //  Animal animal = new Animal("animal", "big", 100);
        Dog dog = new Dog("Wolf", "big", 100);
        dog.makeNoise();
        doAnimalStuff(dog);
    }

    private static void doAnimalStuff(Animal animal) {
        animal.makeNoise();
        animal.move("slow");
    }
    // Abstract class can't instantiate
    // However, we can use abstract type in methods, declarations and lists

}


