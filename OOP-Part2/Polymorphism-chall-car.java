
public class Main {
    public static void main(String[] args) {

         Car car = new Car("2022 Bluew Ferrari 296 GTS");
         runRace(car);

         Car ferrari = new GasPoweredCar("2022 Blue Ferrari 296 GTS", 15.4, 6);
         runRace(ferrari);

    }
    public static void runRace(Car car) {
        car.startEngine();
        car.drive();
    }


}
public class Car {
    private String description;

    public Car(String description) {
        this.description = description;
    }

    public void startEngine(){
      System.out.println("Car -> startEngine");
    }
    public void drive(){
        System.out.println("Car -> driving, type is " + getClass().getSimpleName());
        runEngine();
    }
    protected void runEngine(){
        System.out.println("Car -> runEngine");
    }
}
class GasPoweredCar extends Car {
    private double avgKmPerLitre;
    private int cylinders;
    public GasPoweredCar(String description, double avgKmPerLitre, int cylinders) {
        super(description);
        this.avgKmPerLitre = avgKmPerLitre;
        this.cylinders = cylinders;
    }

    @Override
    public void startEngine() {
        System.out.printf("Gas -> All %d cylenders are fired up, Ready! %n", cylinders);

    }

    @Override
    protected void runEngine() {
        System.out.printf("Gas  -> usage exceeds the average: %,2f %n", avgKmPerLitre);
    }
}
class ElectricCar extends Car {
    private double avgKmPerLitre;
    private int batterySize;
    public ElectricCar(String description, double avgKmPerLitre) {
        super(description);
        this.avgKmPerLitre = avgKmPerLitre;
    }
}
class HybridCar extends Car {
    private double avgKmPerLitre;
    private int cylinders;
    private int batterySize;
    public HybridCar(String description, double avgKmPerLitre, int cylinders, int batterySize) {
        super(description);
        this.avgKmPerLitre = avgKmPerLitre;
        this.cylinders = cylinders;
        this.batterySize = batterySize;
    }

}
