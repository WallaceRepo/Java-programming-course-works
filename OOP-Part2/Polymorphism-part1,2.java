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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //
//         Movie movie = new Adventure("Star Wars");
//         movie.watchMovie();
         Movie movie = Movie.getMovie("Adventure", "Star Wars");
         movie.watchMovie(); // Star Wars is a Movie film

        Movie theMovie = Movie.getMovie("Science", "Star Wars");
        theMovie.watchMovie(); // Star Wars is a Movie film

        Scanner s = new Scanner(System.in);
        while(true) {
            System.out.print("Enter Type (A for adventure, C for Comedy, " + "S for Science Fiction, or Q to quit ): ");
            String type =  s.nextLine();
            if("Qq".contains(type)) {
                break;
            }
            System.out.print("Enter Movie Title: ");
            // read user input using .nextLine() of Scanner obj
            String title = s.nextLine();
            Movie otherMovie = Movie.getMovie(type, title); // returns Movie or subclass of Movie.
            otherMovie.watchMovie();
        }
    }
}
