import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
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
}
/*
ALBUM_NAME     ARTIST_NAME    TRACK_NUMBER   SONG_TITLE     
Carolina County BallELF            1              Carolina County Ball
Carolina County BallELF            2              L.A. 59        
Carolina County BallELF            3              Ain't It All Amusing
Carolina County BallELF            4              Happy          
Carolina County BallELF            5              Annie New Orleans
Carolina County BallELF            6              Rocking Chair Rock 'n Roll Blues
Carolina County BallELF            7              Rainbow        
Carolina County BallELF            8              Do The Same Thing
Carolina County BallELF            9              Blanche        
Trying To Burn The SunELF            1              Black Swampy Water
Trying To Burn The SunELF            2              Prentice Wood  
Trying To Burn The SunELF            3              When She Smiles
Trying To Burn The SunELF            4              Good Time Music
Trying To Burn The SunELF            5              Liberty Road   
Trying To Burn The SunELF            6              Shotgun Boogie 
Trying To Burn The SunELF            7              Wonderworld    
Trying To Burn The SunELF            8              Streetwalker   

Process finished with exit code 0
*/
