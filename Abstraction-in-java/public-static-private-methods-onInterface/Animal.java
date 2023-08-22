package myInterface;
//both records and enums can implement interfaces.
//
//But they can't extend classes, abstract or not.

enum FlightStages implements Trackable { GROUNDED, LAUNCH, CRUISE, DATA_COLLECTION;

    @Override
    public void track() {
        if (this != GROUNDED) {
            System.out.println("Monitoring " + this );
        }
    }
    public FlightStages getNextStage(){
        FlightStages[] allStages = values();
        return allStages[(ordinal() + 1) % allStages.length];

    }
}
record DragonFly(String name, String type) implements FlightEnabled {

    @Override
    public void takeOff() {

    }

    @Override
    public void land() {

    }

    @Override
    public void fly() {

    }

}
class Satellite implements OrbitEarth {
    FlightStages stage = FlightStages.GROUNDED;

    // implementing the all method of interface
    public void achieveOrbit() {
        transition("orbit achieved!");

    }

    @Override
    public void takeOff() {
       transition("Taking off");
    }

    @Override
    public void land() {
      transition("Landing");
    }

    @Override
    public void fly() {
       achieveOrbit();
       transition("Data Collection while Orbiting");
    }

    @Override
    public void track() {

    }
    public void transition(String description) {
        System.out.println(description);
        stage = transition(stage);
        stage.track();
    }
}
//Unlike a class, an interface can use the extends expression with multiple interfaces:
//
//An interface doesn't implement another interface, so the code on this slide won't compile.
interface OrbitEarth extends FlightEnabled, Trackable {
    void achieveOrbit();

    static void log(String description) {
        var today = new java.util.Date();
        System.out.println(today + ": " + description);
    }

    private void logStage(FlightStages stage, String description) {
        description = stage + ": " + description;
        log(description);
    }

    @Override
    default FlightStages transition(FlightStages stage) {
       FlightStages nextStage = FlightEnabled.super.transition(stage);
       logStage(stage, "beginning Transition to " + nextStage);
       return nextStage;
    }
}
interface FlightEnabled {
    // implitcitly fields are public and final so they are really constants.
    double MILES_TO_KM = 1.60934;
    double KM_TO_MILES = 0.621371;
    // Static constant means we access it via the type name.
    


    public abstract void takeOff();
    abstract void land();
    // we don't have to declare the interface type abstract, because this modifier is implicitly declared, for all interfaces.
    // Any method declared without body is really implicitly declared both public and abstract.
    void fly();

    default FlightStages transition(FlightStages stage){
//        System.out.println("transition not implemented on " + this.getClass().getName());
//        return null;
        FlightStages nextStage = stage.getNextStage();
        System.out.println("Transitioning from " + stage + " to " + nextStage);
        return nextStage;
    }
}
interface Trackable {
    void track();
    
}
public abstract class Animal {
    // abstract method on abstract class has to be declared with "abstract" keyword
     public abstract void move();

}
