// COntinue previous part
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
//                System.out.println(("Maybe we should add this record "));
//                // Calling insertRecord method here
//                insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
                insertArtistAlbum(statement, columnValue, columnValue);
            } else {
                // deleteRecord(statement, tableName, columnName, columnValue );
                updateRecord(statement, tableName, columnName, columnValue, columnName, columnValue.toUpperCase());
            }

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
        String albumInsert = "INSERT INTO music.albums (album_name, artist_id) VALUES (%s, %d)"
                .formatted(statement.enquoteLiteral(albumName), artistId);


        System.out.println(albumInsert);
        statement.execute(albumInsert, Statement.RETURN_GENERATED_KEYS);
        int albumId = (rs != null && rs.next()) ? rs.getInt(1) : -1;

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
}

/*

ARTIST_ID      ARTIST_NAME    
204            Bob Dylan      
UPDATE music.artists SET artist_name ='BOB DYLAN' WHERE artist_name='Bob Dylan'
ARTIST_ID      ARTIST_NAME    
204            BOB DYLAN      

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
                // deleteRecord(statement, tableName, columnName, columnValue );
                updateRecord(statement, tableName, columnName, columnValue, columnName, columnValue.toUpperCase());
                insertArtistAlbum(statement, columnValue, columnValue);
            }

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
}

/*
ARTIST_ID      ARTIST_NAME    
204            BOB DYLAN      
UPDATE music.artists SET artist_name ='BOB DYLAN' WHERE artist_name='Bob Dylan'
ARTIST_ID      ARTIST_NAME    
204            BOB DYLAN      
INSERT INTO music.artists (artist_name) VALUES ('Bob Dylan')
INSERT INTO music.albums (album_name, artist_id) VALUES ('Bob Dylan', 205)
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Bob Dylan      Bob Dylan      1              Your're No bad 
Bob Dylan      Bob Dylan      2              talking new york
Bob Dylan      Bob Dylan      3              in my time of living
Bob Dylan      Bob Dylan      4              man of constant thrill
Bob Dylan      Bob Dylan      5              fixing to live 
Bob Dylan      Bob Dylan      6              pretty peggy day
Bob Dylan      Bob Dylan      7              highway nice road

Process finished with exit code 0
*/
