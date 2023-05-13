public class Fish extends Animal {
     private int gills;
     private int fins;

     public Fish(String type, double weight, int gills, int fins) {
         super(type, "small", weight);
         this.gills = gills;
         this.fins = fins;
     }
     private void moveMuscles(){
         System.out.println("muscles moving");
     }
     private void moveBackFin(){
        System.out.println("backfin moving");
    }

    @Override
    public void move(String speed) {
        super.move(speed);
        moveMuscles();
        if(speed == "fast") {
            moveBackFin();
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Fish{" +
                "gills=" + gills +
                ", fins=" + fins +
                "} " + super.toString();
    }
}
public class Animal {
    protected String type;
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

//          Animal animal = new Animal( "General Animal", "Huge", 400);
//          doAnimalStuff(animal, "slow");

//          Dog dog = new Dog();
//          doAnimalStuff(dog, "fast");

//          Dog yorkie = new Dog("Yorkie", 15);
//          doAnimalStuff(yorkie, "fast");

//          Dog retriever = new Dog("Labrador Retriever", 67, "Floppy", "Swagger");
//          doAnimalStuff(retriever, "slow");

//          Dog wolf = new Dog("Wolf", 40);
//          doAnimalStuff(wolf, "slow");
         
         Fish goldie = new Fish("goldfish", 0.25, 2, 3);
         doAnimalStuff(goldie, "fast");

    }

    public static void  doAnimalStuff(Animal animal, String speed) {
        animal.makeNoise();
        animal.move(speed);
        System.out.println(animal);
        System.out.println("______");
    }
}  
