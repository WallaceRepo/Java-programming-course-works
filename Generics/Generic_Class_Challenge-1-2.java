import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Mappable {
     void render();
     static double[] stringToLatLon(String location){
         var splits = location.split(",");
         double lat = Double.valueOf(splits[0]);
         double lon = Double.valueOf(splits[1]);

         return new double[]{lat, lon}; // returning an array of string
     }
}
abstract class Point implements Mappable {
    private double[] location = new double[2];
    // Constructor
    public  Point (String location) {
        this.location = Mappable.stringToLatLon(location);
    }

    // Because this class is abstract, I don't actually have to implement the Mappable's abstract method.
    // Any class that extends this abstract class,
    // will be required to implement render, if I don't do it here.
    public void render(){
      System.out.println("Render " + this + "as POINT (" + location() + ")");
    }
    private String location(){
        return Arrays.toString(location);
    }
 }
abstract class Line implements Mappable {
    private double[][] locations;
    // Constructor -> This method takes a variable argument of String, so we can have zero to many Strings,
    // each representing the latitude and longitude, of a point in the line
    public Line (String... locations) {
        this.locations = new double[locations.length][];
        int index = 0;
        for ( var l : locations) {
            this.locations[index++] = Mappable.stringToLatLon(l);
        }
    }
    public void render(){
        System.out.println("Render " + this + "as LINE (" + locations() + ")");
    }
    private String locations(){
       return Arrays.deepToString(locations);
    }
}
class Park extends Point {
     private String name;

    public Park(String name, String location ) {
        super(location);
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " National Park";
    }
}

class River extends Line {
    private String name;
    public River(String name, String... locations ) {
        super(locations);
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " River";
    }
}
class Layer < T extends Mappable> {
    private List< T> layerElements;
// This constructor lets the calling code pass an array, to create a new Layer.
    public Layer(T[] layerElements) {
        this.layerElements = new ArrayList<T>(List.of(layerElements));
    }
    // And the variable argument uses the T parameter, and simply calls addAll,
    // a method on the list interface. This method takes a list of data, not a
    // variable argument, so I can generate a List, with the List dot of method, with variable arguments.
    public void addElements(T... elements) {  //  T var args and name that elements.
        layerElements.addAll(List.of(elements));
    }
    public void renderLayer() {
        for ( T t : layerElements) {
            t.render();
        }
    }

}

//////////////Main 
public class Main {
    public static void main ( String[] args) {


        var nationalParks = new Park[]{
                new Park("yellow stone", "44.4882, -110.5916"),
                new Park( "Grand Canyon", "36,1085, -112.0965"),
                new Park("Yosemite", "37.8855, -119.5360")
        };
        Layer <Park> parkLayer = new Layer<>(nationalParks);
        parkLayer.renderLayer();

       var majorRivers = new River[]{
               new River("Mississippi", "47.320, -95.2348", "29.1566, -89.2495", "35.1556, -90.0659"),
               new River("Nissouri", "45.9239, -111.4883", "38.8146, -90.1218")
       };
       Layer<River> riverLayer = new Layer<>(majorRivers);
       riverLayer.addElements(
               new River("Colorado", "40.4708, -105.8286", "31.7811, -114.7724"),
               new River("Delaware", "46.2323, -75,234", "38.67, -75.554")
       );
       riverLayer.renderLayer();
    }
