// using instanceof operator
// polymorphism to execute different behavior, for different types, which are determined at runtime.

public class Movie {
    private String title;

    public Movie(String title) {
        this.title = title;
    }
    public void watchMovie() {
        String instanceType = this.getClass().getSimpleName();
        System.out.println(title + " is a " + instanceType + " film");
    }


      //  Below is: Design Pattern Factory Method way is to create an object without having to know the details / subclassess
    public static Movie getMovie(String type, String title) {
        return switch (type.toUpperCase().charAt(0)){
            case 'A' -> new Adventure(title);
            case 'C' -> new Comedy(title);
            case 'S' -> new ScienceFiction(title);
            default -> new Movie(title);
        };
    }

}
class Adventure extends Movie {
    public Adventure(String title) {
        super(title);
    }

    @Override
    public void watchMovie() {
        super.watchMovie();
        System.out.printf("..%s%n".repeat(3),
                "Pleasant Scene",
                "Scary Music",
                "Something Bad happens"
                );
    }
}
class Comedy extends Movie {
    public Comedy(String title) {
        super(title);
    }
    public void watchAdventure(){
        System.out.println("Watching an Adventure!");
    }
    @Override
    public void watchMovie() {
        super.watchMovie();
        System.out.printf("..%s%n".repeat(3),
                "Something funny happens",
                "Something even happier happens",
                "Happy Ending"
        );
    }
    public void watchComedy(){
        System.out.println("Watching an Comedy!");
    }
}
class ScienceFiction extends Movie {
    public ScienceFiction(String title) {
        super(title);
    }

    @Override
    public void watchMovie() {
        super.watchMovie();
        System.out.printf("..%s%n".repeat(3),
                "Bad Aliens do Bad Stuff",
                "Space Guy chase Aliens",
                "Planet blows up"
        );
    }
    public void watchScienceFiction(){
        System.out.println("Watching an Science Fiction thriller!");
    }
}
public class NextMain {
    public static void main(String[] args) {
        Movie otherMovie = Movie.getMovie("A", "FairtyTale"); // returns Movie or subclass of Movie.
        otherMovie.watchMovie();

        // Adventure lordofrings = Movie.getMovie("A", "LORD");
        // lets cast it
        Adventure lordofrings = (Adventure) Movie.getMovie("A", "LORD");

        Object comedy = Movie.getMovie("C", "Airplane");
        // comedy.watchMovie();
        Movie comefyMovie = (Movie)  comedy;
        // comefyMovie.watchComedy();
        // casting to more specific type
        Comedy comedyMovie = (Comedy)  comedy;
         comedyMovie.watchComedy();

          // var is Local Variable Type inference, or telling Java to figure out the compile-time type for us
         var airplane = Movie.getMovie("C", "Fidler on the roof");

         var plane = new Comedy("Airplane");
         plane.watchComedy();

         Object unknownObj = Movie.getMovie("S", "Airplane");
         if(unknownObj.getClass().getSimpleName() == "Comedy") {
             Comedy c = (Comedy) unknownObj;
             c.watchComedy();
         } else if( unknownObj instanceof Adventure){
             ((Adventure) unknownObj).watchMovie();
             //     Pattern Matching for the instanceof Operator
             // JVM can identify that the object matches the type, it can extract data from the object, without casting
             // for this operator, the object can be assigned to a binding variable, which here is called syfy.
         } else if( unknownObj instanceof ScienceFiction syfy) {
             syfy.watchScienceFiction();
         }

    }
}
