package myInterface;

public class Test {
    public static void main(String[] args) {
        inFlight(new Jet());

        OrbitEarth.log("Testing " + new Satellite());
        // Tue Aug 15 16:22:50 CDT 2023: Testing myInterface.Satellite@16b98e56
        orbit(new Satellite());

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
    private static void orbit(OrbitEarth flier) {
        flier.takeOff();
        flier.fly();
        // If the object is a class that implements Trackable, then a local variable is set up,
        //
        //named tracked, with the type Trackable And because of that, we can call the track
        // we can call the track method on that variable, tracked, that the instanceof operator populated for us.

        flier.land();
    }
}
/*
Taking off
Transitioning from GROUNDED to LAUNCH
Tue Aug 22 15:21:02 CDT 2023: GROUNDED: beginning Transition to LAUNCH
Monitoring LAUNCH
orbit achieved!
Transitioning from LAUNCH to CRUISE
Tue Aug 22 15:21:02 CDT 2023: LAUNCH: beginning Transition to CRUISE
Monitoring CRUISE
Data Collection while Orbiting
Transitioning from CRUISE to DATA_COLLECTION
Tue Aug 22 15:21:02 CDT 2023: CRUISE: beginning Transition to DATA_COLLECTION
Monitoring DATA_COLLECTION
Landing
Transitioning from DATA_COLLECTION to GROUNDED
Tue Aug 22 15:21:02 CDT 2023: DATA_COLLECTION: beginning Transition to GROUNDED
 */
