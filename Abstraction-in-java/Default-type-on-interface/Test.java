package myInterface;

public class Test {
    public static void main(String[] args) {
        inFlight(new Jet());
    }
    private static void inFlight(FlightEnabled flier) {
        flier.takeOff();
        flier.fly();
        // If the object is a class that implements Trackable, then a local variable is set up,
        //
        //named tracked, with the type Trackable And because of that, we can call the track
        // we can call the track method on that variable, tracked, that the instanceof operator populated for us.

        flier.transition(FlightStages.LAUNCH);
        // transition not implemented on myInterface.Jet

        // Jet transitioning

        if (flier instanceof Trackable tracked ) {
            tracked.track();
        }
        flier.land();
    }
}
