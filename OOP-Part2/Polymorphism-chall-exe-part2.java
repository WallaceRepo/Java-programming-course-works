
public class Main {
    public static void main(String[] args) {

        Car car = new Car("2022 Bluew Ferrari 296 GTS");
        runRace(car);

        Car ferrari = new GasPoweredCar("2022 Blue Ferrari 296 GTS", 15.4, 6);
        runRace(ferrari);
        Car tesla = new ElectricCar("2022 Red Tesla Model 3", 568, 75);
        runRace(tesla);

        Car ferrariHybrid = new HybridCar("2022 Black Ferrari SF90 Strdale", 16, 8, 8);
    }
    public static void runRace(Car car) {
        car.startEngine();
        car.drive();
    }
  
public class Car {
    private String description;

    public Car(String description) {
        this.description = description;
    }

    public void startEngine(){
        System.out.println("Car -> startEngine");
    }
    protected void runEngine(){
        System.out.println("Car -> runEngine");
    }
    public void drive(){
        System.out.println("Car -> driving, type is " + getClass().getSimpleName()); // prints runtime instance type
        runEngine();          // getClass() called without this and gets runtime instance's class.
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
        System.out.printf("Gas  -> usage exceeds the average: %.2f %n", avgKmPerLitre);
    }
}
class ElectricCar extends Car {
    private double avgKmPerCharge;
    private int batterySize;
    public ElectricCar(String description, double avgKmPerCharge, int batterySize) {
        super(description);
        this.avgKmPerCharge = avgKmPerCharge;
        this.batterySize = batterySize;
    }
    @Override
    public void startEngine() {
        System.out.printf("BEV -> All %d kWh battery on are fired up, Ready! %n", batterySize);

    }

    @Override
    protected void runEngine() {
        System.out.printf("Electric  -> usage exceeds the average: %.2f %n", avgKmPerCharge);
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

    @Override
    public void startEngine() {
        System.out.printf("Hybrid car -> All %d cylenders are fired up, Ready! %n", cylinders);
        System.out.printf("Hybrid -> All %d kWh battery on are fired up, Ready! %n", batterySize);
    }

    @Override
    protected void runEngine() {
        System.out.printf("Hybrid  -> usage exceeds the average: %.2f %n", avgKmPerLitre);
    }

}
  
  
