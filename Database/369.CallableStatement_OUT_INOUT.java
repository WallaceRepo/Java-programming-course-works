import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
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


        var dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPort(3306);

        dataSource.setDatabaseName("music");
        try {
            dataSource.setContinueBatchOnError(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // get connection
        try (Connection connection = dataSource.getConnection(
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS")
        )) {
            CallableStatement cs = (CallableStatement) connection.prepareCall("CALL music.addAlbumReturnCounts(?, ?, ?, ?)");

            albums.forEach((artist, albumMap) -> {
                albumMap.forEach((album, songs ) -> {
                    try {
                        cs.setString(1, artist );
                        cs.setString(2, album );
                        cs.setString(3, songs);
                        cs.registerOutParameter(4, Types.INTEGER);
                        cs.execute();
                        System.out.printf("%d songs were added for %s%n", cs.getInt(4), album);
                    } catch (SQLException e) {
                        System.err.println(e.getErrorCode() + " " + e.getMessage());
                    }
                });

            });

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();

            MusicDML.printRecords(resultSet);

        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

}

/*
Bob Dylan : ["You're No Good", "Talkin' New York", "In My Time of Dyin'", "Man of Constant Sorrow", "Fixin' to Die", "Pretty Peggy-O", "Highway 51 Blues", "Gospel Plow", "Baby Let Me Follow You Down", "House of the Risin' Sun", "Freight Train Blues", "Song to Woody", "See That My Grave Is Kept Clean"]
Blonde on Blonde : ["Rainy Day Women", "Pledging My Time", "Visions of Johanna", "One of Us Must Know (Sooner or Later)", "I Want You", "Stuck Inside of Mobile with the Memphis Blues Again", "Leopard-Skin Pill-Box Hat", "Just Like a Woman", "Most Likely You Go Your Way (And I'll Go Mine)", "Temporary Like Achilles", "Absolutely Sweet Marie", "Fourth Time Around", "Obviously Five Believers", "Sad-Eyed Lady of the Lowlands"]
13 songs were added for Bob Dylan
14 songs were added for Blonde on Blonde
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Blonde on BlondeBob Dylan      1              Rainy Day Women
Blonde on BlondeBob Dylan      2              Pledging My Time
Blonde on BlondeBob Dylan      3              Visions of Johanna
Blonde on BlondeBob Dylan      4              One of Us Must Know (Sooner or Later)
Blonde on BlondeBob Dylan      5              I Want You     
Blonde on BlondeBob Dylan      6              Stuck Inside of Mobile with the Memphis Blues Again
Blonde on BlondeBob Dylan      7              Leopard-Skin Pill-Box Hat
Blonde on BlondeBob Dylan      8              Just Like a Woman
Blonde on BlondeBob Dylan      9              Most Likely You Go Your Way (And I'll Go Mine)
Blonde on BlondeBob Dylan      10             Temporary Like Achilles
Blonde on BlondeBob Dylan      11             Absolutely Sweet Marie
Blonde on BlondeBob Dylan      12             Fourth Time Around
Blonde on BlondeBob Dylan      13             Obviously Five Believers
Blonde on BlondeBob Dylan      14             Sad-Eyed Lady of the Lowlands
Bob Dylan      Bob Dylan      1              You're No Good 
Bob Dylan      Bob Dylan      2              Talkin' New York
Bob Dylan      Bob Dylan      3              In My Time of Dyin'
Bob Dylan      Bob Dylan      4              Man of Constant Sorrow
Bob Dylan      Bob Dylan      5              Fixin' to Die  
Bob Dylan      Bob Dylan      6              Pretty Peggy-O 
Bob Dylan      Bob Dylan      7              Highway 51 Blues
Bob Dylan      Bob Dylan      8              Gospel Plow    
Bob Dylan      Bob Dylan      9              Baby Let Me Follow You Down
Bob Dylan      Bob Dylan      10             House of the Risin' Sun
Bob Dylan      Bob Dylan      11             Freight Train Blues
Bob Dylan      Bob Dylan      12             Song to Woody  
Bob Dylan      Bob Dylan      13             See That My Grave Is Kept Clean

Process finished with exit code 0
*/
