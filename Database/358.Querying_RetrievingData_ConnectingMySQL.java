import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"), StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String albumName ="Tapestry";
        String query = "SELECT * FROM music.albumview WHERE album_name='%s'".formatted(albumName);

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try (var connection = dataSource.getConnection(props.getProperty("user"), System.getenv("MYSQL_PASS")); Statement statement= connection.createStatement();){
           // System.out.println("Success!");
            ResultSet resultSet = statement.executeQuery(query);

//            while (resultSet.next()){
//                System.out.printf("%d %s %s %n", resultSet.getInt("track_number"),
//                        resultSet.getString("artist_name"),
//                        resultSet.getString("song_title")
//                );
//            }


            // In case if I dont know what's going to be result data

            var meta = resultSet.getMetaData();
            for ( int i = 1; i <= meta.getColumnCount(); i++ ) {
                System.out.printf("%d %s %s%n", i, meta.getColumnName(i), meta.getColumnTypeName(i));
            }
            System.out.println("======================");
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

// In the project root create file music.properties   then create env variable MYSQL_PASS through lesson.

// music.properties
serverName=localhost
port=3306
databaseName=music
user=devuser
password=******
/*
  1 album_name TEXT
2 artist_name TEXT
3 track_number INT
4 song_title TEXT
======================
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Tapestry       Carole King    1              I Feel The Earth Move
Tapestry       Carole King    2              Carole King - So Far Away
Tapestry       Carole King    3              It's Too Late  
Tapestry       Carole King    4              Home Again     
Tapestry       Carole King    5              Beautiful      
Tapestry       Carole King    6              Way Over Yonder
Tapestry       Carole King    7              You've Got A Friend
Tapestry       Carole King    8              Where You Lead 
Tapestry       Carole King    9              Will You Love Me Tomorrow
Tapestry       Carole King    10             Smackwater Jack
Tapestry       Carole King    11             Tapestry       
Tapestry       Carole King    12             (You Make Me Feel Like) A Natural Woman
Tapestry       Carole King    13             Out In The Cold (Previously unreleased)
Tapestry       Carole King    14             Smackwater Jack (Live)

Process finished with exit code 0
*/
  
