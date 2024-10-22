import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

            int newOrder = addOrder(conn, new String[]{"shoes", "shirt", "socks"});
            System.out.println("New Order = " +  newOrder);
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

    private static int addOrder( Connection conn, String[] items ) {
        int orderId = -1;
        String insertOrder = "INSERT INTO storefront.order ( order_date) VALUES ('%s')";
        String insertDetail = "INSERT INTO storefront.order_details " + "( order_id, item_description) values (%d, %s)";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String orderDateTime = LocalDateTime.now().format(dtf);
        System.out.println(orderDateTime);
        String formattedString = insertOrder.formatted(orderDateTime);
        System.out.println(formattedString);

        String insertOrderAlternative = "INSERT INTO storefront.order (order_date) " + " VALUES ('%1$tF %1$tT')";
        System.out.println(insertOrderAlternative.formatted(LocalDateTime.now()));

        return orderId;
    }

}

/*
2
2024-10-21 18:05:54
INSERT INTO storefront.order ( order_date) VALUES ('2024-10-21 18:05:54')
INSERT INTO storefront.order (order_date)  VALUES ('2024-10-21 18:05:54')
New Order = -1

Process finished with exit code 0
*/
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

            int newOrder = addOrder(conn, new String[]{"shoes", "shirt", "socks"});
            System.out.println("New Order = " +  newOrder);
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

    private static int addOrder( Connection conn, String[] items ) throws SQLException {
        int orderId = -1;
        String insertOrder = "INSERT INTO storefront.order ( order_date) VALUES ('%s')";
        String insertDetail = "INSERT INTO storefront.order_details " + "( order_id, item_description) values (%d, %s)";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String orderDateTime = LocalDateTime.now().format(dtf);
        System.out.println(orderDateTime);
        String formattedString = insertOrder.formatted(orderDateTime);
        System.out.println(formattedString);

        String insertOrderAlternative = "INSERT INTO storefront.order (order_date) " + " VALUES ('%1$tF %1$tT')";
        System.out.println(insertOrderAlternative.formatted(LocalDateTime.now()));

        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            int inserts = statement.executeUpdate(formattedString, Statement.RETURN_GENERATED_KEYS);

            if (inserts == 1 ) {
                var rs = statement.getGeneratedKeys();
                if ( rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
            int count = 0;
            for ( var item : items ) {
                formattedString = insertDetail.formatted(orderId, statement.enquoteLiteral(item));
                inserts = statement.executeUpdate(formattedString);
                count += inserts;
            }
            if ( count != items.length ) {
                orderId =  -1;
                System.out.println("Number of records inserted doesn't equal items received");
                conn.rollback();
            } else {
                conn.commit();
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            conn.rollback();
            throw new RuntimeException(e);
        }

        return orderId;
    }

}

/*
2
2024-10-22 09:27:07
INSERT INTO storefront.order ( order_date) VALUES ('2024-10-22 09:27:07')
INSERT INTO storefront.order (order_date)  VALUES ('2024-10-22 09:27:07')
New Order = 1

Process finished with exit code 0

*/
