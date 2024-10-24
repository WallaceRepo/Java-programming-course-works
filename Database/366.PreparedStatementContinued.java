import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;

public class Main {
    private static String ARTIST_INSERT = "INSERT INTO music.artists (artist_name) VALUES (?)";
    private static String ALBUM_INSERT = "INSERT INTO music.albums (artist_id,album_name) VALUES (?,?)";
    private static String SONG_INSERT = "INSERT INTO music.songs( album_id, track_number, song_title) " + "VALUES ( ?, ? ,?)";

    public static void main(String[] args) {

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
        try(Connection connection = dataSource.getConnection(
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS")
        )) {

            addDataFromFile(connection);

            String sql = "SELECT * FROM music.albumview where artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();

            printRecords(resultSet);

        } catch (SQLException e ) {
            e.printStackTrace();
        }

    }
    private static boolean printRecords(ResultSet resultSet) throws SQLException {
        boolean foundData = false;

        var meta = resultSet.getMetaData();

        // Using generic to print data coming from var meta. Let's print only column names
        for( int i = 1; i <= meta.getColumnCount(); i++ ) {
            System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
        }
        System.out.println(); // After column names are printed, print new line here

        while(resultSet.next()){
            for ( int i = 1; i <= meta.getColumnCount(); i++ ) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
            foundData = true;
        }

        return foundData;
    }

    private static int addArtist (PreparedStatement ps, Connection conn, String artistName ) throws SQLException {
        int artistId = -1;
        ps.setString(1, artistName );
        int insertCount = ps.executeUpdate();
        if ( insertCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                artistId = generatedKeys.getInt ( 1);
                System.out.println("Auto-increment ID: " + artistId );
            }
        }
        return artistId;
    }

    private static int addAlbum (PreparedStatement ps, Connection conn, int artistId, String albumName) throws SQLException {
        int albumId = -1;
        ps.setInt( 1, artistId );
        ps.setString(2, albumName );
        int insertCount = ps.executeUpdate();
        if ( insertCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                albumId = generatedKeys.getInt ( 1);
                System.out.println("Auto-increment ID: " + albumId );
            }
        }
        return albumId;
    }

    private static int addSong (PreparedStatement ps, Connection conn, int albumId, int trackNo, String songTitle ) throws SQLException {
        int songId = -1;
        ps.setInt( 1, albumId);
        ps.setInt(2, trackNo );
        ps.setString(3, songTitle );
        int insertedCount = ps.executeUpdate();
        if ( insertedCount > 0) {
            ResultSet generatedKeys = ps. getGeneratedKeys();
            if ( generatedKeys.next()) {
                songId = generatedKeys.getInt(1);
                System.out.println("Auto - incremented ID: " + songId );
            }
        }
        return songId;
    }
    private static void addDataFromFile ( Connection conn ) throws SQLException {
        List<String> records = null;
        try {
            records = Files.readAllLines( Path.of("NewAlbums.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String lastAlbum = null;
        String lastArtist = null;
        int artistId = -1;
        int albumId = -1;
        try (PreparedStatement psArtist = conn.prepareStatement( ARTIST_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psAlbum = conn.prepareStatement(ALBUM_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psSong = conn.prepareStatement(SONG_INSERT, Statement.RETURN_GENERATED_KEYS )
            ) {
         conn.setAutoCommit(false);

         for ( String record : records) {
             String[] columns = record.split(",");
             if (lastArtist == null || !lastArtist.equals(columns[0])) {
                 lastArtist = columns[0];
                 artistId = addArtist(psArtist, conn, lastArtist );
              }
             if ( lastAlbum == null || !lastAlbum.equals(columns[1])) {
                 lastAlbum = columns[1];
                 albumId = addAlbum( psAlbum, conn, artistId, lastAlbum );
             }
             addSong(psSong, conn, albumId, Integer.parseInt(columns[2]), columns[3]);
         }

         conn.commit();
         conn.setAutoCommit(true);
        } catch (SQLException e ) {
           conn.rollback();
           throw new RuntimeException(e);
        }


    }

}

/*

Auto-increment ID: 210
Auto-increment ID: 883
Auto - incremented ID: 5380
Auto - incremented ID: 5381
Auto - incremented ID: 5382
Auto - incremented ID: 5383
Auto - incremented ID: 5384
Auto - incremented ID: 5385
Auto - incremented ID: 5386
Auto - incremented ID: 5387
Auto - incremented ID: 5388
Auto - incremented ID: 5389
Auto - incremented ID: 5390
Auto - incremented ID: 5391
Auto - incremented ID: 5392
Auto-increment ID: 884
Auto - incremented ID: 5393
Auto - incremented ID: 5394
Auto - incremented ID: 5395
Auto - incremented ID: 5396
Auto - incremented ID: 5397
Auto - incremented ID: 5398
Auto - incremented ID: 5399
Auto - incremented ID: 5400
Auto - incremented ID: 5401
Auto - incremented ID: 5402
Auto - incremented ID: 5403
Auto - incremented ID: 5404
Auto - incremented ID: 5405
Auto - incremented ID: 5406
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
/// adding songs using batch as one trip to db
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static String ARTIST_INSERT = "INSERT INTO music.artists (artist_name) VALUES (?)";
    private static String ALBUM_INSERT = "INSERT INTO music.albums (artist_id,album_name) VALUES (?,?)";
    private static String SONG_INSERT = "INSERT INTO music.songs( album_id, track_number, song_title) " + "VALUES ( ?, ? ,?)";

    public static void main(String[] args) {

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
        try(Connection connection = dataSource.getConnection(
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS")
        )) {

            addDataFromFile(connection);

            String sql = "SELECT * FROM music.albumview where artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();

            printRecords(resultSet);

        } catch (SQLException e ) {
            e.printStackTrace();
        }

    }
    private static boolean printRecords(ResultSet resultSet) throws SQLException {
        boolean foundData = false;

        var meta = resultSet.getMetaData();

        // Using generic to print data coming from var meta. Let's print only column names
        for( int i = 1; i <= meta.getColumnCount(); i++ ) {
            System.out.printf("%-15s", meta.getColumnName(i).toUpperCase());
        }
        System.out.println(); // After column names are printed, print new line here

        while(resultSet.next()){
            for ( int i = 1; i <= meta.getColumnCount(); i++ ) {
                System.out.printf("%-15s", resultSet.getString(i));
            }
            System.out.println();
            foundData = true;
        }

        return foundData;
    }

    private static int addArtist (PreparedStatement ps, Connection conn, String artistName ) throws SQLException {
        int artistId = -1;
        ps.setString(1, artistName );
        int insertCount = ps.executeUpdate();
        if ( insertCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                artistId = generatedKeys.getInt ( 1);
                System.out.println("Auto-increment ID: " + artistId );
            }
        }
        return artistId;
    }

    private static int addAlbum (PreparedStatement ps, Connection conn, int artistId, String albumName) throws SQLException {
        int albumId = -1;
        ps.setInt( 1, artistId );
        ps.setString(2, albumName );
        int insertCount = ps.executeUpdate();
        if ( insertCount > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                albumId = generatedKeys.getInt ( 1);
                System.out.println("Auto-increment ID: " + albumId );
            }
        }
        return albumId;
    }

//    private static int addSong (PreparedStatement ps, Connection conn, int albumId, int trackNo, String songTitle ) throws SQLException {
//        int songId = -1;
//        ps.setInt( 1, albumId);
//        ps.setInt(2, trackNo );
//        ps.setString(3, songTitle );
//        int insertedCount = ps.executeUpdate();
//        if ( insertedCount > 0) {
//            ResultSet generatedKeys = ps. getGeneratedKeys();
//            if ( generatedKeys.next()) {
//                songId = generatedKeys.getInt(1);
//                System.out.println("Auto - incremented ID: " + songId );
//            }
//        }
//        return songId;
//    }

    // adding batch on addSongs
    private static void addSong (PreparedStatement ps, Connection conn, int albumId, int trackNo, String songTitle ) throws SQLException {

        ps.setInt( 1, albumId);
        ps.setInt(2, trackNo );
        ps.setString(3, songTitle );
        ps.addBatch();
    }
    private static void addDataFromFile ( Connection conn ) throws SQLException {
        List<String> records = null;
        try {
            records = Files.readAllLines( Path.of("NewAlbums.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String lastAlbum = null;
        String lastArtist = null;
        int artistId = -1;
        int albumId = -1;
        try (PreparedStatement psArtist = conn.prepareStatement( ARTIST_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psAlbum = conn.prepareStatement(ALBUM_INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psSong = conn.prepareStatement(SONG_INSERT, Statement.RETURN_GENERATED_KEYS )
            ) {
         conn.setAutoCommit(false);

         for ( String record : records) {
             String[] columns = record.split(",");
             if (lastArtist == null || !lastArtist.equals(columns[0])) {
                 lastArtist = columns[0];
                 artistId = addArtist(psArtist, conn, lastArtist );
              }
             if ( lastAlbum == null || !lastAlbum.equals(columns[1])) {
                 lastAlbum = columns[1];
                 albumId = addAlbum( psAlbum, conn, artistId, lastAlbum );
             }
             addSong(psSong, conn, albumId, Integer.parseInt(columns[2]), columns[3]);
         }
         int[] inserts = psSong.executeBatch();
         int totalInserts = Arrays.stream(inserts).sum();
         System.out.printf("%d song records added %n", inserts.length );

         conn.commit();
         conn.setAutoCommit(true);
        } catch (SQLException e ) {
           conn.rollback();
           throw new RuntimeException(e);
        }


    }

}
/*
Auto-increment ID: 212
Auto-increment ID: 887
Auto-increment ID: 888
27 song records added 
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Blonde on BlondeBob Dylan      1              Rainy Day Women
Blonde on BlondeBob Dylan      1              Rainy Day Women
Blonde on BlondeBob Dylan      2              Pledging My Time
Blonde on BlondeBob Dylan      2              Pledging My Time
Blonde on BlondeBob Dylan      3              Visions of Johanna
Blonde on BlondeBob Dylan      3              Visions of Johanna
Blonde on BlondeBob Dylan      4              One of Us Must Know (Sooner or Later)
Blonde on BlondeBob Dylan      4              One of Us Must Know (Sooner or Later)
Blonde on BlondeBob Dylan      5              I Want You     
Blonde on BlondeBob Dylan      5              I Want You     
Blonde on BlondeBob Dylan      6              Stuck Inside of Mobile with the Memphis Blues Again
Blonde on BlondeBob Dylan      6              Stuck Inside of Mobile with the Memphis Blues Again
Blonde on BlondeBob Dylan      7              Leopard-Skin Pill-Box Hat
Blonde on BlondeBob Dylan      7              Leopard-Skin Pill-Box Hat
Blonde on BlondeBob Dylan      8              Just Like a Woman
Blonde on BlondeBob Dylan      8              Just Like a Woman
Blonde on BlondeBob Dylan      9              Most Likely You Go Your Way (And I'll Go Mine)
Blonde on BlondeBob Dylan      9              Most Likely You Go Your Way (And I'll Go Mine)
Blonde on BlondeBob Dylan      10             Temporary Like Achilles
Blonde on BlondeBob Dylan      10             Temporary Like Achilles
Blonde on BlondeBob Dylan      11             Absolutely Sweet Marie
Blonde on BlondeBob Dylan      11             Absolutely Sweet Marie
Blonde on BlondeBob Dylan      12             Fourth Time Around
Blonde on BlondeBob Dylan      12             Fourth Time Around
Blonde on BlondeBob Dylan      13             Obviously Five Believers
Blonde on BlondeBob Dylan      13             Obviously Five Believers
Blonde on BlondeBob Dylan      14             Sad-Eyed Lady of the Lowlands
Blonde on BlondeBob Dylan      14             Sad-Eyed Lady of the Lowlands
Bob Dylan      Bob Dylan      1              You're No Good 
Bob Dylan      Bob Dylan      1              You're No Good 
Bob Dylan      Bob Dylan      2              Talkin' New York
Bob Dylan      Bob Dylan      2              Talkin' New York
Bob Dylan      Bob Dylan      3              In My Time of Dyin'
Bob Dylan      Bob Dylan      3              In My Time of Dyin'
Bob Dylan      Bob Dylan      4              Man of Constant Sorrow
Bob Dylan      Bob Dylan      4              Man of Constant Sorrow
Bob Dylan      Bob Dylan      5              Fixin' to Die  
Bob Dylan      Bob Dylan      5              Fixin' to Die  
Bob Dylan      Bob Dylan      6              Pretty Peggy-O 
Bob Dylan      Bob Dylan      6              Pretty Peggy-O 
Bob Dylan      Bob Dylan      7              Highway 51 Blues
Bob Dylan      Bob Dylan      7              Highway 51 Blues
Bob Dylan      Bob Dylan      8              Gospel Plow    
Bob Dylan      Bob Dylan      8              Gospel Plow    
Bob Dylan      Bob Dylan      9              Baby Let Me Follow You Down
Bob Dylan      Bob Dylan      9              Baby Let Me Follow You Down
Bob Dylan      Bob Dylan      10             House of the Risin' Sun
Bob Dylan      Bob Dylan      10             House of the Risin' Sun
Bob Dylan      Bob Dylan      11             Freight Train Blues
Bob Dylan      Bob Dylan      11             Freight Train Blues
Bob Dylan      Bob Dylan      12             Song to Woody  
Bob Dylan      Bob Dylan      12             Song to Woody  
Bob Dylan      Bob Dylan      13             See That My Grave Is Kept Clean
Bob Dylan      Bob Dylan      13             See That My Grave Is Kept Clean

Process finished with exit code 0
*/
