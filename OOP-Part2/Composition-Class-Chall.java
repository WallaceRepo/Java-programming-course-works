public class Appliance {

    protected boolean hasWorkTodo;

    public Appliance(boolean hasWorkToDo) {
        this.hasWorkTodo = hasWorkToDo;

    }
}
class Refrigerator extends Appliance {
    public Refrigerator(boolean hasWorkToDo) {
        super(hasWorkToDo);
    }

    public void orderFood(){
        if(hasWorkTodo) {
            System.out.println("Ordering food");
            hasWorkTodo = false;
        }
     }
}
class DishWasher extends Appliance {
    public DishWasher(boolean hasWorkToDo) {
        super(hasWorkToDo);
    }

    public void doDishes(){
        if(hasWorkTodo) {
            System.out.println("Washing Dishes");
            hasWorkTodo = false;
        }
    }
}
class CoffeeMaker extends Appliance {
    public CoffeeMaker(boolean hasWorkToDo) {
        super(hasWorkToDo);
    }

    public void brewCoffee(){
       // hasWorkTodo = true;
        if(hasWorkTodo) {
            System.out.println("Brewing Coffee");
            hasWorkTodo = false;
        }
    }
}

/////
public class SmartKitchen {
    private CoffeeMaker brewMaster;
    private DishWasher dishWasher;
    private Refrigerator iceBox;


    public SmartKitchen (boolean coffeeFlag, boolean fridgeFlag, boolean dishWasherFlag) {
        brewMaster = new CoffeeMaker(coffeeFlag);
        iceBox = new Refrigerator(fridgeFlag);
        dishWasher = new DishWasher(dishWasherFlag);
    }

    public CoffeeMaker getBrewMaster() {
        return brewMaster;
    }

    public DishWasher getDishWasher() {
        return dishWasher;
    }

    public Refrigerator getIceBox() {
        return iceBox;
    }

    public void setUp(){
        brewMaster.brewCoffee();
        iceBox.orderFood();
        dishWasher.doDishes();
    }
}
/////

public class Main {
    public static void main(String[] args) {
         SmartKitchen kitchen = new SmartKitchen(true, true, true);

      

//         kitchen.getDishWasher().doDishes();
//         kitchen.getIceBox().orderFood();
//         kitchen.getBrewMaster().brewCoffee(); 
      // Hiding getters and returned object methods as well
      kitchen.setUp();
    }
}
