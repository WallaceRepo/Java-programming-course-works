import com.mysql.cj.jdbc.CallableStatement;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            CallableStatement cs = (CallableStatement) connection.prepareCall("CALL music.addAlbum(?, ?, ?)");

            albums.forEach((artist, albumMap) -> {
                albumMap.forEach((album, songs ) -> {
                    try {
                        cs.setString(1, artist );
                        cs.setString(2, album );
                        cs.setString(3, songs);
                        cs.execute();
                    } catch (SQLException e) {
                        System.err.println(e.getErrorCode() + " " + e.getMessage());
                    }
                });

            });

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet resultSet = ps.executeQuery();

            MusicDML.printRecords(resultSet); // printRecords is below; it's called from MusicDML.java

        } catch (SQLException e ) {
            e.printStackTrace();
        }
    }

}

/// MusicDML.java file 
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.util.Arrays;

public class MusicDML {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music?continueBatchOnError=false",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement();
        ) {
            String tableName = "music.artists";
            String columnName = "artist_name";
            String columnValue = "Bob Dylan";
            if( !executeSelect(statement, tableName, columnName, columnValue)) {
                insertArtistAlbum(statement, columnValue, columnValue);
            } else {
               try {
                   deleteArtistAlbum(connection, statement, columnValue, columnValue );
               } catch(SQLException e) {
                   e.printStackTrace();
                }
            }
            executeSelect(statement, "music.albumview", "album_name", columnValue );
            executeSelect(statement, "music.albums", "album_name", columnValue );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean printRecords(ResultSet resultSet) throws SQLException {
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

    private static boolean executeSelect(Statement statement, String table, String columnName, String columnValue ) throws SQLException {

          String query = "SELECT * FROM %s WHERE %s='%s'".formatted(table, columnName, columnValue);
          var rs = statement.executeQuery(query);
          if(rs != null) {
              return printRecords(rs);
          }
          return false;
    }

    private static boolean insertRecord(Statement statement, String table,
                                        String[] columnNames, String[] columnValues ) throws SQLException {
        String colNames = String.join(",", columnNames);
        String colValues =String.join("','", columnValues );
        String query = "INSERT INTO %s (%s) VALUES ('%s')".formatted(table, colNames, colValues);
        System.out.println(query);
        boolean insertResult=statement.execute(query);
//        System.out.println("insertResult = " + insertResult);
//        return insertResult;
        int recordInserted = statement.getUpdateCount();
        if(recordInserted > 0) {
            executeSelect(statement, table, columnNames[0], columnValues[0]);
        }
        return recordInserted > 0;
    }
    private static boolean deleteRecord (Statement statement, String table,
                                         String columnName, String columnValue ) throws SQLException {
        String query = "DELETE FROM %s WHERE %s='%s'".formatted(table, columnName, columnValue );
        System.out.println(query);
        statement.execute(query);
        int recordsDeleted = statement.getUpdateCount();
        if (recordsDeleted > 0 ) {
            executeSelect(statement, table, columnName, columnValue );
        }
        return recordsDeleted > 0;
    }
    private static boolean updateRecord (Statement statement, String table,
                                         String matchedColumn, String matchedValue,
                                         String updatedColumn, String updatedValue ) throws SQLException {
        String query = "UPDATE %s SET %s ='%s' WHERE %s='%s'".formatted(table, updatedColumn, updatedValue, matchedColumn, matchedValue );
        System.out.println(query);
        statement.execute(query);
        int recordsUpdated = statement.getUpdateCount();
        if (recordsUpdated > 0 ) {
            executeSelect(statement, table, updatedColumn, updatedValue );
        }
        return recordsUpdated > 0;
    }
    private static void insertArtistAlbum ( Statement statement, String artistName, String albumName ) throws SQLException {
        String artistInsert = "INSERT INTO music.artists (artist_name) VALUES (%s)".formatted(statement.enquoteLiteral(artistName));
        System.out.println(artistInsert);
        statement.execute(artistInsert, Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = statement.getGeneratedKeys();
        int artistId = (rs != null &&  rs.next()) ? rs.getInt(1) : -1;
        String albumInsert = "INSERT INTO music.albums (album_name, artist_id)" + " VALUES (%s, %d)"
                .formatted(statement.enquoteLiteral(albumName), artistId);


        System.out.println(albumInsert);

        statement.execute(albumInsert, Statement.RETURN_GENERATED_KEYS);
        ResultSet rsAlbum = statement.getGeneratedKeys();
        int albumId = (rsAlbum != null && rsAlbum.next()) ? rsAlbum.getInt(1) : -1;

      //  int albumId = (rs != null && rs.next()) ? rs.getInt(1) : -1;

        String[] songs = new String[]{
                "Your're No bad",
                "talking new york",
                "in my time of living",
                "man of constant thrill",
                "fixing to live",
                "pretty peggy day",
                "highway nice road"
        };
        String songInsert = "INSERT INTO music.songs " + "(track_number, song_title, album_id) VALUES (%d, %s, %d)";

        for ( int i = 0; i < songs.length; i++ ) {
            String songQuery = songInsert.formatted( i+ 1, statement.enquoteLiteral(songs[i]), albumId);

            statement.execute(songQuery);
        }

        executeSelect(statement, "music.albumview", "album_name", "Bob Dylan");
    }

    // Deleting data from all 3 tables as once
    private static void deleteArtistAlbum( Connection conn, Statement statement, String artistName, String albumName )
         throws SQLException {
     try {


         System.out.println("AUTOMCOMMIT = " + conn.getAutoCommit());
         // To do delete from all 3 tables data as one transaction; set Autocommit as false;
         conn.setAutoCommit(false);

         String deleteSongs = """
                 DELETE FROM music.songs WHERE album_id = (SELECT ALBUM_ID from music.albums WHERE album_name = '%s')"""
                 .formatted(albumName);
         String deleteAlbums = "DELETE FROM music.albums WHERE album_name = '%s'".formatted(albumName);

         String deleteArtist = "DELETE FROM music.artists WHERE artist_name = '%s'".formatted(artistName);

         // To save remote SQL operation expenses, we do changes in batch.
         // addBatch just adds to statement
         statement.addBatch(deleteSongs);
         statement.addBatch(deleteAlbums);
         statement.addBatch(deleteArtist);

         int[] results = statement.executeBatch();
         System.out.println(Arrays.toString(results));
          conn.commit();
     } catch (SQLException e) {
         e.printStackTrace();
         conn.rollback();

      }
        conn.setAutoCommit(true);
    }

}



/*
Bob Dylan : ["You're No Good", "Talkin' New York", "In My Time of Dyin'", "Man of Constant Sorrow", "Fixin' to Die", "Pretty Peggy-O", "Highway 51 Blues", "Gospel Plow", "Baby Let Me Follow You Down", "House of the Risin' Sun", "Freight Train Blues", "Song to Woody", "See That My Grave Is Kept Clean"]
Blonde on Blonde : ["Rainy Day Women", "Pledging My Time", "Visions of Johanna", "One of Us Must Know (Sooner or Later)", "I Want You", "Stuck Inside of Mobile with the Memphis Blues Again", "Leopard-Skin Pill-Box Hat", "Just Like a Woman", "Most Likely You Go Your Way (And I'll Go Mine)", "Temporary Like Achilles", "Absolutely Sweet Marie", "Fourth Time Around", "Obviously Five Believers", "Sad-Eyed Lady of the Lowlands"]
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
