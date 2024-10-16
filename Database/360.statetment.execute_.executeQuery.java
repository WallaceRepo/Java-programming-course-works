import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicDML {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement();
        ) {
            String artist = "Elf";
            String query = "SELECT * FROM artists WHERE artist_name='%s'".formatted(artist);
            boolean result = statement.execute(query);
            System.out.println("result = " + result);

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

/*
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
result = true

*/
public class MusicDML {
    public static void main(String[] args) {
        // Below is for older JDBC driver that needs declaring driver class, later versions does it automatically
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement();
        ) {
            String artist = "Elf";
            String query = "SELECT * FROM artists WHERE artist_name='%s'".formatted(artist);
            // statement.execute does always send true when used with SELECT, so it wont work to check record existance in table
            boolean result = statement.execute(query);
            System.out.println("result = " + result);
            // Instead check existance with resultSet
            var rs = statement.getResultSet();
            boolean found = (rs != null && rs.next());
            System.out.println("Artist was " + (found ? "found" :"not found"));

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
/*
result = true
Artist was found

Process finished with exit code 0
*/
public class MusicDML {
    public static void main(String[] args) {
        // Below is for older JDBC driver that needs declaring driver class, later versions does it automatically
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement();
        ) {
            String tableName = "music.artists";
            String columnName = "artist_name";
            String columnValue = "Elf";
            if( !executeSelect(statement, tableName, columnName, columnValue)){
                System.out.println(("Maybe we should add this record "));
            }

        }catch (SQLException e) {
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
}
/*
ARTIST_ID      ARTIST_NAME    
2              Elf            

Process finished with exit code 0

//     Changed->  String columnValue = "Neil Young"; on line 22
ARTIST_ID      ARTIST_NAME    
Maybe we should add this record 

Process finished with exit code 0
*/
public class MusicDML {
    public static void main(String[] args) {
        // Below is for older JDBC driver that needs declaring driver class, later versions does it automatically
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        try (Connection connection = DriverManager.getConnection(
                 "jdbc:mysql://localhost:3306/music",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
             Statement statement = connection.createStatement();
        ) {
            String tableName = "music.artists";
            String columnName = "artist_name";
            String columnValue = "Neil Young";
            if( !executeSelect(statement, tableName, columnName, columnValue)){
                System.out.println(("Maybe we should add this record "));
                // Calling insertRecord method here
                insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
            }

        }catch (SQLException e) {
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

}
/*
ARTIST_ID      ARTIST_NAME    
202            Neil Young     

Process finished with exit code 0

// Changed->  String columnValue = "Bob Dylan"; on line 22
ARTIST_ID      ARTIST_NAME    
Maybe we should add this record 
INSERT INTO music.artists (artist_name) VALUES ('Bob Dylan')
ARTIST_ID      ARTIST_NAME    
203            Bob Dylan      

Process finished with exit code 0
*/

