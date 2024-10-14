import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        //Promt user to enter an album name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an Album Name: ");

        String albumName = scanner.nextLine();
        String query = "SELECT * FROM music.albumview WHERE album_name='%s'".formatted(albumName);

        try (var connection = dataSource.getConnection(props.getProperty("user"), System.getenv("MYSQL_PASS")); Statement statement= connection.createStatement();){
           // System.out.println("Success!");
            ResultSet resultSet = statement.executeQuery(query);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
/*

Enter an Album Name: 
Bad Company
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Bad Company    Bad Company    1              Can't Get Enough
Bad Company    Bad Company    2              Rock Steady    
Bad Company    Bad Company    3              Ready For Love 
Bad Company    Bad Company    4              Don't Let Me Down
Bad Company    Bad Company    5              Bad Company    
Bad Company    Bad Company    6              The Way I Choose
Bad Company    Bad Company    7              Movin' On      
Bad Company    Bad Company    8              Seagull        

Process finished with exit code 0  */

// SQL Injection vulnerability example
public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        //Promt user to enter an album name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an Artist Id: ");

        String artistId = scanner.nextLine();
        String query = "SELECT * FROM music.artists WHERE artist_id=%s".formatted(artistId);

        try (var connection = dataSource.getConnection(props.getProperty("user"), System.getenv("MYSQL_PASS")); Statement statement= connection.createStatement();){
           // System.out.println("Success!");
            ResultSet resultSet = statement.executeQuery(query);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}
/*
Enter an Artist Id: 
7 or artist_id=8
ARTIST_ID      ARTIST_NAME    
7              Rory Gallagher 
8              Iron Maiden    

Process finished with exit code 0  */

// Fixing above impurity on input
public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        //Promt user to enter an album name
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an Artist Id: ");

        String artistId = scanner.nextLine();
        int  artistid = Integer.parseInt(artistId);

        String query = "SELECT * FROM music.artists WHERE artist_id=%d".formatted(artistid);

        try (var connection = dataSource.getConnection(props.getProperty("user"), System.getenv("MYSQL_PASS")); Statement statement= connection.createStatement();){
           // System.out.println("Success!");
            ResultSet resultSet = statement.executeQuery(query);
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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}

/*
Enter an Artist Id: 
7 or artist_id=8
Exception in thread "main" java.lang.NumberFormatException: For input string: "7 or artist_id=8"
	at java.base/java.lang.NumberFormatException.forInputString(NumberFormatException.java:67)
	at java.base/java.lang.Integer.parseInt(Integer.java:668)
	at java.base/java.lang.Integer.parseInt(Integer.java:786)
	at Main.main(Main.java:34)

Process finished with exit code 1

*/
