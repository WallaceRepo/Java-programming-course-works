import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class MusicCallableStatement {

    private static final int ARTIST_COLUMN = 0;
    private static final int ALBUM_COLUMN = 1;
    private static final int SONG_COLUMN = 3;

    public static void main(String[] args) {
        Map<String, Map<String, String>> albums = null;

        try (var lines = Files.lines(Path.of("NewAlbums.csv"))) {

            albums = lines.map( s -> s.split(",")).collect(Collectors.groupingBy(s -> s[ARTIST_COLUMN],
                                                                 Collectors.groupingBy(s -> s[ALBUM_COLUMN],
                                                                         Collectors.mapping(s->s[SONG_COLUMN],
                                                                                 Collectors.joining("\", \"",
                                                                                                     "[\"",
                                                                                                      "\"]")))
                                                                ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        albums.forEach((artist, artistAlbum ) -> {
            artistAlbum.forEach((key, value) -> {
                System.out.println(key + " : " + value );
            });
        });
    }
}

/*
Bob Dylan : ["You're No Good", "Talkin' New York", "In My Time of Dyin'", "Man of Constant Sorrow", "Fixin' to Die", "Pretty Peggy-O", "Highway 51 Blues", "Gospel Plow", "Baby Let Me Follow You Down", "House of the Risin' Sun", "Freight Train Blues", "Song to Woody", "See That My Grave Is Kept Clean"]
Blonde on Blonde : ["Rainy Day Women", "Pledging My Time", "Visions of Johanna", "One of Us Must Know (Sooner or Later)", "I Want You", "Stuck Inside of Mobile with the Memphis Blues Again", "Leopard-Skin Pill-Box Hat", "Just Like a Woman", "Most Likely You Go Your Way (And I'll Go Mine)", "Temporary Like Achilles", "Absolutely Sweet Marie", "Fourth Time Around", "Obviously Five Believers", "Sad-Eyed Lady of the Lowlands"]

Process finished with exit code 0
  */
