package myInterface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bird bird = new Bird();
        Animal animal = bird;
        FlightEnabled flier = bird;
        Trackable tracked = bird;

        animal.move();
//        flier.move();
//        tracked.move();
        flier.takeOff();
        flier.fly();
        tracked.track();
        flier.land();
//        Flaps wings
//        Bird is taking off
//        Bird is flying
//        Bird's coordinates recorded
//        Bird is landing

        inFlight(flier);
//        Bird is taking off
//        Bird is flying
//        Bird's coordinates recorded
//        Bird is landing

        inFlight(new Jet());
//        Jet is taking off
//        Jet is flying
//        Jet's coordinates recorded
//        Jet is landing
        Trackable truck = new Truck();
        truck.track();
       // Truck's coordinates recorded

        double kmsTraveled = 100;
        double milesTraveled = kmsTraveled * FlightEnabled.KM_TO_MILES;
        System.out.printf("The truck traveled %.2f km or %.2f miles%n", kmsTraveled, milesTraveled);

// The truck traveled 100.00 km or 62.14 miles
     //   interface never gets instantiated, and won't have a subclass that gets instantiated either.
        // ArrayList<FlightEnabled> fliers = new ArrayList<>();
        LinkedList<FlightEnabled> fliers = new LinkedList<>();
        fliers.add(bird);
        // But ArrayList implements the List interface, and its recommended, to use the interface type for
        //
        //the declared variable.
        // List as the reference type
        List<FlightEnabled> betterFliers = new ArrayList<>();
        betterFliers.add(bird);
        triggerFliers(fliers);
        flyFliers(fliers);
        landFliers(fliers);
        // We can't pass a List to a method,
        //
        //where an ArrayList is declared. So that's one problem.
        //
        //Our method parameters are very specific to the ArrayList type.
        triggerFliers(betterFliers);
        flyFliers(betterFliers);
        landFliers(betterFliers);
    }
    private static void inFlight(FlightEnabled flier) {
        flier.takeOff();
        flier.fly();
        // If the object is a class that implements Trackable, then a local variable is set up,
        //
        //named tracked, with the type Trackable And because of that, we can call the track
        // we can call the track method on that variable, tracked, that the instanceof operator populated for us.
        if (flier instanceof Trackable tracked ) {
            tracked.track();
        }
        flier.land();
    }
    //  Below 3 methods change ArrayList to just the List type, which is the interface type.
    private static void triggerFliers(List<FlightEnabled> fliers) {
        for ( var flier : fliers) {
            flier.takeOff();
        }
    }
    private static void flyFliers(List<FlightEnabled> fliers) {
        for ( var flier : fliers) {
            flier.fly();
        }
    }
    private static void landFliers(List<FlightEnabled> fliers) {
        for ( var flier : fliers) {
            flier.land();
        }
    }

    // Both interfaces and abstract classes are abstracted reference types.
    //
    //And we know Reference types can be used in code, as variable types,
    //
    //method parameters, and return types, list types, and so on.
    //
    //When you use an abstracted reference type, this is referred to as coding to an interface
    //
    //This means your code doesn't use specific types, but more
    //
    //generalized ones, usually an interface type. This technique is preferred, because it allows
    //
    //many runtime instances of various classes, to be processed uniformly, by the same code.
    //
    //It also allows for substitutions of some other class or object,
    //
    //that still implements the same interface, without forcing a major refactor of your code.
    //
    //Using interface types as the reference type, is considered a best practice.
    //
    //In my coding examples to date, I didn't demonstrate this,
    //
    //and that was on purpose, because we hadn't yet reviewed interfaces.


}
