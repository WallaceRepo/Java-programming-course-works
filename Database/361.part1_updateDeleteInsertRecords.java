// Adding delete method on previous file
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
            if( !executeSelect(statement, tableName, columnName, columnValue)){
                System.out.println(("Maybe we should add this record "));
                // Calling insertRecord method here
                insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
            } else {
                deleteRecord(statement, tableName, columnName, columnValue );
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
}
/*
ARTIST_ID      ARTIST_NAME    
203            Bob Dylan      
DELETE FROM music.artists WHERE artist_name='Bob Dylan'
ARTIST_ID      ARTIST_NAME    

Process finished with exit code 0

//    String columnValue = "Elf"; change. Error shows it cant deleted 
ARTIST_ID      ARTIST_NAME    
2              Elf            
DELETE FROM music.artists WHERE artist_name='Elf'
Exception in thread "main" java.lang.RuntimeException: java.sql.SQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`music`.`albums`, CONSTRAINT `FK_ARTISTID` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`))
	at MusicDML.main(MusicDML.java:23)
Caused by: java.sql.SQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`music`.`albums`, CONSTRAINT `FK_ARTISTID` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`artist_id`))
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:109)
	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114)
	at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:875)
	at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:723)
	at MusicDML.deleteRecord(MusicDML.java:79)
	at MusicDML.main(MusicDML.java:19)
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
            String columnValue = "Elf";
            if( !executeSelect(statement, tableName, columnName, columnValue)){
                System.out.println(("Maybe we should add this record "));
                // Calling insertRecord method here
                insertRecord(statement, tableName, new String[]{columnName}, new String[]{columnValue});
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
	/*
ARTIST_ID      ARTIST_NAME    
2              ELF            
UPDATE music.artists SET artist_name ='ELF' WHERE artist_name='Elf'
ARTIST_ID      ARTIST_NAME    
2              ELF            

Process finished with exit code 0 
	*/
