public class MusicDML {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
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
        System.out.println("AUTOMCOMMIT = " + conn.getAutoCommit());
    }

}

/*
ARTIST_ID      ARTIST_NAME    
204            BOB DYLAN      
205            Bob Dylan      
AUTOMCOMMIT = true
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Bob Dylan      Bob Dylan      1              Your're No bad 
Bob Dylan      Bob Dylan      2              talking new york
Bob Dylan      Bob Dylan      3              in my time of living
Bob Dylan      Bob Dylan      4              man of constant thrill
Bob Dylan      Bob Dylan      5              fixing to live 
Bob Dylan      Bob Dylan      6              pretty peggy day
Bob Dylan      Bob Dylan      7              highway nice road
ALBUM_ID       ALBUM_NAME     ARTIST_ID      
879            Bob Dylan      205            

Process finished with exit code 0
*/

public class MusicDML {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
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
        System.out.println("AUTOMCOMMIT = " + conn.getAutoCommit());

        String deleteSongs = """
                DELETE FROM music.songs WHERE album_id = (SELECT ALBUM_ID from music.albums WHERE album_name = '%s') """
                .formatted(albumName);
        int deletedSongs = statement.executeUpdate(deleteSongs);
        System.out.printf("Deleted %d rows from music.songs%n", deletedSongs);

        String deleteAlbums = "DELETE FROM music.albums WHERE album_name = '%s'".formatted(albumName);

        int deletedAlbums = statement.executeUpdate(deleteAlbums);
        System.out.printf("Deleted %d rows from music.albums%n", deletedAlbums);

        String deleteArtist = "DELETE FROM music.artists WHERE artist_name = '%s'".formatted(artistName);

        int deletedArtists = statement.executeUpdate(deleteArtist);
        System.out.printf("Deleted %d rows from music.artists%n", deletedArtists);
    }

}
/*
ARTIST_ID      ARTIST_NAME    
INSERT INTO music.artists (artist_name) VALUES ('Bob Dylan')
INSERT INTO music.albums (album_name, artist_id) VALUES ('Bob Dylan', 206)
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Bob Dylan      Bob Dylan      1              Your're No bad 
Bob Dylan      Bob Dylan      2              talking new york
Bob Dylan      Bob Dylan      3              in my time of living
Bob Dylan      Bob Dylan      4              man of constant thrill
Bob Dylan      Bob Dylan      5              fixing to live 
Bob Dylan      Bob Dylan      6              pretty peggy day
Bob Dylan      Bob Dylan      7              highway nice road
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Bob Dylan      Bob Dylan      1              Your're No bad 
Bob Dylan      Bob Dylan      2              talking new york
Bob Dylan      Bob Dylan      3              in my time of living
Bob Dylan      Bob Dylan      4              man of constant thrill
Bob Dylan      Bob Dylan      5              fixing to live 
Bob Dylan      Bob Dylan      6              pretty peggy day
Bob Dylan      Bob Dylan      7              highway nice road
ALBUM_ID       ALBUM_NAME     ARTIST_ID      
880            Bob Dylan      206            

Process finished with exit code 0
*/
public class MusicDML {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
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
        System.out.println("AUTOMCOMMIT = " + conn.getAutoCommit());
        // To do delete from all 3 tables data as one transaction; set Autocommit as false;
        conn.setAutoCommit(false);

        String deleteSongs = """
                DELETE FROM music.songs WHERE album_id = (SELECT ALBUM_ID from music.albums WHERE album_name = '%s') """
                .formatted(albumName);
        int deletedSongs = statement.executeUpdate(deleteSongs);
        System.out.printf("Deleted %d rows from music.songs%n", deletedSongs);

        String deleteAlbums = "DELETE FROM music.albums WHERE album_name = '%s".formatted(albumName);

        int deletedAlbums = statement.executeUpdate(deleteAlbums);
        System.out.printf("Deleted %d rows from music.albums%n", deletedAlbums);

        String deleteArtist = "DELETE FROM music.artists WHERE artist_name = '%s'".formatted(artistName);

        int deletedArtists = statement.executeUpdate(deleteArtist);
        System.out.printf("Deleted %d rows from music.artists%n", deletedArtists);

        conn.commit();
        conn.setAutoCommit(true);
    }

}

/*
ARTIST_ID      ARTIST_NAME    
206            Bob Dylan      
AUTOMCOMMIT = true
Deleted 7 rows from music.songs
java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ''Bob Dylan' at line 1
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112)
	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114)
	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1537)
	at com.mysql.cj.jdbc.StatementImpl.executeLargeUpdate(StatementImpl.java:2384)
	at com.mysql.cj.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1410)
	at MusicDML.deleteArtistAlbum(MusicDML.java:156)
	at MusicDML.main(MusicDML.java:18)
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
ALBUM_ID       ALBUM_NAME     ARTIST_ID      
880            Bob Dylan      206     

  */

public class MusicDML {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
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
                 DELETE FROM music.songs WHERE album_id = (SELECT ALBUM_ID from music.albums WHERE album_name = '%s') """
                 .formatted(albumName);
         int deletedSongs = statement.executeUpdate(deleteSongs);
         System.out.printf("Deleted %d rows from music.songs%n", deletedSongs);

         String deleteAlbums = "DELETE FROM music.albums WHERE album_name = '%s".formatted(albumName);

         int deletedAlbums = statement.executeUpdate(deleteAlbums);
         System.out.printf("Deleted %d rows from music.albums%n", deletedAlbums);

         String deleteArtist = "DELETE FROM music.artists WHERE artist_name = '%s'".formatted(artistName);

         int deletedArtists = statement.executeUpdate(deleteArtist);
         System.out.printf("Deleted %d rows from music.artists%n", deletedArtists);

         conn.commit();
     } catch (SQLException e) {
         e.printStackTrace();
         conn.rollback();

      }
        conn.setAutoCommit(true);
    }

}
/*
ARTIST_ID      ARTIST_NAME    
206            Bob Dylan      
AUTOMCOMMIT = true
Deleted 7 rows from music.songs
java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ''Bob Dylan' at line 1
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112)
	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114)
	at com.mysql.cj.jdbc.StatementImpl.executeUpdateInternal(StatementImpl.java:1537)
	at com.mysql.cj.jdbc.StatementImpl.executeLargeUpdate(StatementImpl.java:2384)
	at com.mysql.cj.jdbc.StatementImpl.executeUpdate(StatementImpl.java:1410)
	at MusicDML.deleteArtistAlbum(MusicDML.java:159)
	at MusicDML.main(MusicDML.java:18)
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Bob Dylan      Bob Dylan      1              Your're No bad 
Bob Dylan      Bob Dylan      2              talking new york
Bob Dylan      Bob Dylan      3              in my time of living
Bob Dylan      Bob Dylan      4              man of constant thrill
Bob Dylan      Bob Dylan      5              fixing to live 
Bob Dylan      Bob Dylan      6              pretty peggy day
Bob Dylan      Bob Dylan      7              highway nice road
ALBUM_ID       ALBUM_NAME     ARTIST_ID      
880            Bob Dylan      206            

Process finished with exit code 0
*/
// after fixing intentional syntax error
/*
ARTIST_ID      ARTIST_NAME    
206            Bob Dylan      
AUTOMCOMMIT = true
Deleted 7 rows from music.songs
Deleted 1 rows from music.albums
Deleted 1 rows from music.artists
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
ALBUM_ID       ALBUM_NAME     ARTIST_ID      

Process finished with exit code 0

*/
