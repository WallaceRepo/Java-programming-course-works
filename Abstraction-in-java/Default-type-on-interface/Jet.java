package myInterface;

public class Jet implements FlightEnabled, Trackable {
    @Override
    public void takeOff() {
        System.out.println(getClass().getSimpleName() + " is taking off");
    }

    @Override
    public void land() {
        System.out.println(getClass().getSimpleName() + " is landing");
    }

    @Override
    public void fly() {
        System.out.println(getClass().getSimpleName() + " is flying");
    }

    @Override
    public void track() {
        System.out.println(getClass().getSimpleName() + "'s coordinates recorded");
    }
//So like overriding a method on a class, you have three choices, when
//
//you override a default method on an interface. You can choose not to override it at all. You
//
//can override the method and write code for it, so that the interface method isn't executed. Or
//
//you can write your own code, and invoke the method on the interface, as part of your implementation.
    @Override
    public FlightStages transition(FlightStages stage) {
      //  return FlightEnabled.super.transition(stage);
        System.out.println(getClass().getSimpleName() + " transitioning");
       // return FlightStages.CRUISE;
        return FlightEnabled.super.transition(stage);
    }
}
