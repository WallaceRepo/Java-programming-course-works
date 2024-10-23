import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        private static String ARTIST_INSERT = "INSERT INTO music.artist (artist_name) VALUES (?)";
        private static String ALBUM_INSERT = "INSERT INTO music.albums (artist_id,album_name) VALUES (?,?)";
        private static String SONG_INSERT = "INSERT INTO music.songs( album_id, track_number, song_title) " + "VALUES ( ?, ? ,?)";

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
               String sql = "SELECT * FROM music.albumview where artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Elf");
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
            if ( generatedKeys.next() ) {
                artistId = generatedKey.getInt ( 1);
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
            if ( generatedKeys.next() ) {
                albumId = generatedKey.getInt ( 1);
                System.out.println("Auto-increment ID: " + albumId );
            }
        }
        return albumId;
    }

    private static int addSong (PreparedStatement ps, Connection conn, int AlbumId, int trackNo, String songTitle ) throws SQLException {
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

}
