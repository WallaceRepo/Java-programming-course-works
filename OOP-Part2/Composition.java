public class Product {
     private String model;
     private String manufacturer;
     private int width;
     private int height;
     private int depth;

    public Product(String model, String manufacturer) {
        this.model = model;
        this.manufacturer = manufacturer;
    }

}

class Monitor extends Product {

    private int size;
    private String resolution;
    public Monitor(String model, String manufacturer) {
        super(model, manufacturer);
    }
    public Monitor(String model, String manufacturer, int size, String resolution) {
        super(model, manufacturer);
        this.size = size;
        this.resolution = resolution;
    }
    public void drawPixelAt(int x, int y, String color) {
        System.out.println(String.format("Drawing pixel at %d, %d in color %s ", x, y, color));
    }

}
class Motherboard extends Product {
    private int ramSlots;
    private int cardSlots;
    private  String bios;

    public Motherboard(String model, String manufacturer, int ramSlots, int cardSlots, String bios) {
        super(model, manufacturer);
        this.ramSlots = ramSlots;
        this.cardSlots = cardSlots;
        this.bios = bios;
    }

    public Motherboard(String model, String manufacturer) {
        super(model, manufacturer);
    }
    public void loadProgram( String programName) {
        System.out.println("programm " + programName + " is now loaging...");
    }
}
class Computercase extends Product {
    private String powerSupply;
    public Computercase(String model, String manufacturer) {
        super(model, manufacturer);
    }

    public Computercase(String model, String manufacturer, String powerSupply) {
        super(model, manufacturer);
        this.powerSupply = powerSupply;
    }
    public void pressPowerButton(){
        System.out.println("Power button pressed");
    }
}

//// Assembling a Personal Computer from above classes
public class PersonalComputer extends Product{
    private Computercase computerCase;
    private Monitor monitor;
    private Motherboard motherboard;

    public PersonalComputer(String model, String manufacturer, Computercase computerCase, Monitor monitor, Motherboard motherboard) {
        super(model, manufacturer);
        this.computerCase = computerCase;
        this.monitor = monitor;
        this.motherboard = motherboard;
    }

    private void drawLogo() {
        monitor.drawPixelAt(1200, 50, "Yellow");
    }

    public void powerUp(){
        computerCase.pressPowerButton();
        drawLogo();
    }

//    public Computercase getComputerCase() {
//        return computerCase;
//    }
//
//    public Monitor getMonitor() {
//        return monitor;
//    }
//
//    public Motherboard getMotherboard() {
//        return motherboard;
//    }

}
/// 

public class Main {
    public static void main(String[] args) {

        Computercase theCase = new Computercase("2022", "Dell", "220");

        Monitor theMonitor = new Monitor("27 inch Monitor", "Acer", 27, "2360 x 1440");

        Motherboard theMotherBoard = new Motherboard("BH-200", "Asus", 4,8, "v3.1");

        PersonalComputer thePC = new PersonalComputer("2022", "Dell", theCase, theMonitor, theMotherBoard);

        // Accessing methods using composition architecture
//        thePC.getMonitor().drawPixelAt(10,10,"red");
//        thePC.getMotherboard().loadProgram("Windows OS");
//        thePC.getComputerCase().pressPowerButton();
      
      // Need encapsulation, so removed get methods, and created methods on PersonalComputer class
        thePC.powerUp();

    }

}
