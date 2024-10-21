import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static String USE_SCHEMA ="USE storefront";

    private static int MYSQL_DB_NOT_FOUND = 1049;

    public static void main ( String[] args ) {
        var dataSource = new MysqlDataSource();
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setUser(System.getenv("MYSQL_USER"));
            dataSource.setPassword(System.getenv("MYSQL_PASS"));

        try (Connection conn = dataSource.getConnection()) {

            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.getSQLStateType());

            if (!checkSchema(conn)) {
                System.out.println("storefront schema does not exist");

                setUpSchema(conn);
            }
        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }

    private static boolean checkSchema ( Connection conn )  throws SQLException {
        try (Statement statement = conn.createStatement() ) {
            statement.execute(USE_SCHEMA);

        } catch (SQLException e ) {
            e.printStackTrace();
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());

            if (conn.getMetaData().getDatabaseProductName().equals("MySQL") && e.getErrorCode() == MYSQL_DB_NOT_FOUND){
                return false;
            } else throw e;
        }
        return true;
    }

    private static void setUpSchema ( Connection conn ) throws SQLException {
        String createSchema = "CREATE SCHEMA storefront";
        String createOrder = """
                CREATE TABLE storefront.order (
                order_id int NOT NULL AUTO_INCREMENT, 
                order_date DATETIME NOT NULL, 
                PRIMARY KEY (order_id )
                 )""";
        String createOrderDetails = """
                CREATE TABLE storefront.order_details (
                order_detail_id int NOT NULL AUTO_INCREMENT,
                item_description text,
                order_id int DEFAULT NULL,
                PRIMARY KEY (order_detail_id),
                KEY FK_ORDERID (order_id),
                CONSTRAINT FK_ORDERID FOREIGN KEY (order_id)
                REFERENCES storefront.order(order_id) ON DELETE CASCADE
                )""";

        try ( Statement statement = conn.createStatement()) {
            System.out.println("Creating storefront Database");
            statement.execute(createSchema);
            if (checkSchema(conn)) {
                statement.execute(createOrder);
                System.out.println("Successfully created Order ");
                statement.execute(createOrderDetails);
                System.out.println("Successfully created OrderDetails");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

/*
  2
java.sql.SQLSyntaxErrorException: Unknown database 'storefront'
	at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112)
	at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114)
	at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:875)
	at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:723)
	at Main.checkSchema(Main.java:37)
	at Main.main(Main.java:25)
SQLState: 42000
Error Code: 1049
Message: Unknown database 'storefront'
storefront schema does not exist
Creating storefront Database
Successfully created Order 
Successfully created OrderDetails

Process finished with exit code 0   */
