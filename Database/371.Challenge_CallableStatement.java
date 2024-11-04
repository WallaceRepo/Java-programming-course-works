import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

import java.util.*;

record OrderDetail ( int orderDetailId, String itemDescription, int qty ) {
    public OrderDetail ( String itemDescription, int qty ) {
        this(-1, itemDescription, qty );
    }
    public String toJSON() {
        return new StringJoiner(", ", "{", "}")
                .add("\"itemDescription\":\"" + itemDescription + "\"")
                        .add("\"qty\":" + qty)
                        .toString();
    }
}
record Order ( int orderId, String dateString, List<OrderDetail> details ) {
    public Order ( String dateString ) {
        this( -1, dateString, new ArrayList<>());
    }
    public void addDetails ( String itemDescription, int qty ) {
        OrderDetail item = new OrderDetail(itemDescription, qty );
        details.add(item);
    }
    public String getDetailsJson() {
        StringJoiner jsonString = new StringJoiner(",", "[", "]");
        details.forEach((d) -> jsonString.add(d.toString()));
        return jsonString.toString();
    }
}
public class Main {
    private static String ORDER_INSERT = "INSERT INTO storefront.order (order_date) VALUES (?)";
    private static String ORDER_DETAILS_INSERT = "INSERT INTO storefront.order_details( order_id, item_description, quantity) " + "VALUES ( ?, ? ,?)";

    public static void main(String[] args) {
        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        List<Order> orders = readData();

        try (Connection conn = dataSource.getConnection()) {

//            String alterString = "ALTER TABLE storefront.order_details ADD COLUMN quantity INT";
//            Statement statement = conn.createStatement();
//            statement.execute(alterString);
          //  addOrders (conn, orders);
            orders.forEach((order) -> {
                System.out.println(order.getDetailsJson());
            });

        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }
    private static List <Order> readData() {

        List< Order > vals = new ArrayList<>();
        try (Scanner scanner = new Scanner(Path.of("Orders.csv"))) {
            scanner.useDelimiter("[,\\n]");
            var list = scanner.tokens().map(String::trim).toList();

            for ( int i = 0; i < list.size(); i++ ) {
                String value = list.get(i);
                if (value.equals("order")) {
                    var date = list.get(++i);
                    vals.add(new Order(date));
                } else if (value.equals("item")) {
                    var qty = Integer.parseInt(list.get(++i));
                    var description = list.get(++i);
                    Order order = vals.get(vals.size() - 1);
                    order.addDetails(description, qty);
                }
            }
            vals.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vals;
    }

    private static void addOrder ( Connection conn, PreparedStatement psOrder, PreparedStatement psDetails, Order order) throws  SQLException {
        try {
            conn.setAutoCommit(false);

            int orderId = -1;
            psOrder.setString(1, order.dateString());
            if ( psOrder.executeUpdate() == 1 ) {
                var rs = psOrder.getGeneratedKeys();
                if ( rs.next()) {
                    orderId = rs.getInt(1);
                    System.out.println("OrderId " + orderId);

                    if ( orderId > -1 ) {
                        psDetails.setInt( 1, orderId );
                        for (OrderDetail od : order.details()) {
                            psDetails.setString(2, od.itemDescription());
                            psDetails.setInt(3, od.qty());
                            psDetails.addBatch();
                        }
                        int[] data = psDetails.executeBatch();
                        int rowInserted = Arrays.stream(data).sum();
                        if ( rowInserted != order.details().size()) {
                            throw new SQLException("Inserts don't match ");
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e ) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }
    private static void addOrders ( Connection conn, List <Order> orders ) {
        String insertOrder = "INSERT INTO storefront.order ( order_date ) VALUES (?) ";
        String insertDetail = "INSERT INTO storefront.order_details (order_id, item_description, quantity ) values ( ?, ? ,?)";

        try (
                PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psDetail = conn.prepareStatement(insertDetail, Statement.RETURN_GENERATED_KEYS);
                ) {

            orders.forEach ((ord) -> {
               try {
                   addOrder(conn, psOrder, psDetail, ord);
               } catch (SQLException e ) {
                   System.out.printf("%d ( %s) %s%n", e.getErrorCode(), e.getSQLState(), e.getMessage());
                   System.out.println("Problem: " + psOrder);
                   System.out.println("Order: " + ord);
               }

            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

 }

 /*
 Order[orderId=-1, dateString=2023-11-01 06:01:00, details=[OrderDetail[orderDetailId=-1, itemDescription=Apple, qty=5], OrderDetail[orderDetailId=-1, itemDescription=Orange, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=3], OrderDetail[orderDetailId=-1, itemDescription=Turkey, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Milk, qty=1]]]
Order[orderId=-1, dateString=2023-11-01 06:02:00, details=[OrderDetail[orderDetailId=-1, itemDescription=Bunch Celery, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Onion, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=7], OrderDetail[orderDetailId=-1, itemDescription=Package Ground Beef, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Nuttin' Honey Cereal, qty=1]]]
Order[orderId=-1, dateString=2023-11-01 06:03:00, details=[OrderDetail[orderDetailId=-1, itemDescription=loaves of bread, qty=3], OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=juice, qty=2], OrderDetail[orderDetailId=-1, itemDescription=bag of dog food, qty=1], OrderDetail[orderDetailId=-1, itemDescription=cans of cat food, qty=10]]]
Order[orderId=-1, dateString=2023-11-31 05:04:00, details=[OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=dozen eggs, qty=1], OrderDetail[orderDetailId=-1, itemDescription=lettuce, qty=1], OrderDetail[orderDetailId=-1, itemDescription=cookies, qty=1]]]
Order[orderId=-1, dateString=2023-11-01 06:05:00, details=[OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=dozen eggs, qty=1], OrderDetail[orderDetailId=-1, itemDescription=lettuce, qty=1], OrderDetail[orderDetailId=-1, itemDescription=cookies, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Apple, qty=5], OrderDetail[orderDetailId=-1, itemDescription=Orange, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=3], OrderDetail[orderDetailId=-1, itemDescription=Turkey, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Bunch Celery, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Onion, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=7], OrderDetail[orderDetailId=-1, itemDescription=Package Ground Beef, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Nuttin' Honey Cereal, qty=1]]]
[OrderDetail[orderDetailId=-1, itemDescription=Apple, qty=5],OrderDetail[orderDetailId=-1, itemDescription=Orange, qty=2],OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=3],OrderDetail[orderDetailId=-1, itemDescription=Turkey, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Milk, qty=1]]
[OrderDetail[orderDetailId=-1, itemDescription=Bunch Celery, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Onion, qty=2],OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=7],OrderDetail[orderDetailId=-1, itemDescription=Package Ground Beef, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Nuttin' Honey Cereal, qty=1]]
[OrderDetail[orderDetailId=-1, itemDescription=loaves of bread, qty=3],OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1],OrderDetail[orderDetailId=-1, itemDescription=juice, qty=2],OrderDetail[orderDetailId=-1, itemDescription=bag of dog food, qty=1],OrderDetail[orderDetailId=-1, itemDescription=cans of cat food, qty=10]]
[OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1],OrderDetail[orderDetailId=-1, itemDescription=dozen eggs, qty=1],OrderDetail[orderDetailId=-1, itemDescription=lettuce, qty=1],OrderDetail[orderDetailId=-1, itemDescription=cookies, qty=1]]
[OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1],OrderDetail[orderDetailId=-1, itemDescription=dozen eggs, qty=1],OrderDetail[orderDetailId=-1, itemDescription=lettuce, qty=1],OrderDetail[orderDetailId=-1, itemDescription=cookies, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Apple, qty=5],OrderDetail[orderDetailId=-1, itemDescription=Orange, qty=2],OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=3],OrderDetail[orderDetailId=-1, itemDescription=Turkey, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Milk, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Bunch Celery, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Onion, qty=2],OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=7],OrderDetail[orderDetailId=-1, itemDescription=Package Ground Beef, qty=1],OrderDetail[orderDetailId=-1, itemDescription=Nuttin' Honey Cereal, qty=1]]

Process finished with exit code 0
*/

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

record OrderDetail ( int orderDetailId, String itemDescription, int qty ) {
    public OrderDetail ( String itemDescription, int qty ) {
        this(-1, itemDescription, qty );
    }
    public String toJSON() {
        return new StringJoiner(", ", "{", "}")
                .add("\"itemDescription\":\"" + itemDescription + "\"")
                .add("\"qty\":" + qty)
                .toString();
    }

}
record Order ( int orderId, String dateString, List<OrderDetail> details ) {
    public Order ( String dateString ) {
        this( -1, dateString, new ArrayList<>());
    }
    public void addDetails ( String itemDescription, int qty ) {
        OrderDetail item = new OrderDetail(itemDescription, qty );
        details.add(item);
    }
    public String getDetailsJson() {
        StringJoiner jsonString = new StringJoiner(",", "[", "]");
        details.forEach((d) -> jsonString.add(d.toJSON())); // Change `d.toString()` to `d.toJSON()`
        return jsonString.toString();
    }

}
public class Main {
    private static String ORDER_INSERT = "INSERT INTO storefront.order (order_date) VALUES (?)";
    private static String ORDER_DETAILS_INSERT = "INSERT INTO storefront.order_details( order_id, item_description, quantity) " + "VALUES ( ?, ? ,?)";

    public static void main(String[] args) {
        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        List<Order> orders = readData();

        try (Connection conn = dataSource.getConnection()) {

//            String alterString = "ALTER TABLE storefront.order_details ADD COLUMN quantity INT";
//            Statement statement = conn.createStatement();
//            statement.execute(alterString);
          //  addOrders (conn, orders);

            CallableStatement cs = conn.prepareCall(
                    "{ CALL storefront.addOrder(?, ?, ?, ?) }");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

            orders.forEach((order) -> {
               try {
                   LocalDateTime localDateTime = LocalDateTime.parse(order.dateString(), formatter);
                   Timestamp timestamp = Timestamp.valueOf(localDateTime);
                   cs.setTimestamp(1,timestamp);
                   cs.setString(2, order.getDetailsJson());
                   cs.registerOutParameter(3, Types.INTEGER);
                   cs.registerOutParameter(4, Types.INTEGER);
                   cs.execute();
                   System.out.printf("%d records inserted for %d (%s)%n",
                           cs.getInt(4),
                           cs.getInt(3),
                           order.dateString());
               } catch (Exception e ) {
                   System.out.printf("Problem with %s : %s%n", order.dateString(), e.getMessage());
               }
            });

        } catch ( SQLException e ) {
            throw new RuntimeException(e);
        }
    }
    private static List <Order> readData() {

        List< Order > vals = new ArrayList<>();
        try (Scanner scanner = new Scanner(Path.of("Orders.csv"))) {
            scanner.useDelimiter("[,\\n]");
            var list = scanner.tokens().map(String::trim).toList();

            for ( int i = 0; i < list.size(); i++ ) {
                String value = list.get(i);
                if (value.equals("order")) {
                    var date = list.get(++i);
                    vals.add(new Order(date));
                } else if (value.equals("item")) {
                    var qty = Integer.parseInt(list.get(++i));
                    var description = list.get(++i);
                    Order order = vals.get(vals.size() - 1);
                    order.addDetails(description, qty);
                }
            }
            vals.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return vals;
    }

    private static void addOrder ( Connection conn, PreparedStatement psOrder, PreparedStatement psDetails, Order order) throws  SQLException {
        try {
            conn.setAutoCommit(false);

            int orderId = -1;
            psOrder.setString(1, order.dateString());
            if ( psOrder.executeUpdate() == 1 ) {
                var rs = psOrder.getGeneratedKeys();
                if ( rs.next()) {
                    orderId = rs.getInt(1);
                    System.out.println("OrderId " + orderId);

                    if ( orderId > -1 ) {
                        psDetails.setInt( 1, orderId );
                        for (OrderDetail od : order.details()) {
                            psDetails.setString(2, od.itemDescription());
                            psDetails.setInt(3, od.qty());
                            psDetails.addBatch();
                        }
                        int[] data = psDetails.executeBatch();
                        int rowInserted = Arrays.stream(data).sum();
                        if ( rowInserted != order.details().size()) {
                            throw new SQLException("Inserts don't match ");
                        }
                    }
                }
            }
            conn.commit();
        } catch (SQLException e ) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(true);
        }
    }
    private static void addOrders ( Connection conn, List <Order> orders ) {
        String insertOrder = "INSERT INTO storefront.order ( order_date ) VALUES (?) ";
        String insertDetail = "INSERT INTO storefront.order_details (order_id, item_description, quantity ) values ( ?, ? ,?)";

        try (
                PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psDetail = conn.prepareStatement(insertDetail, Statement.RETURN_GENERATED_KEYS);
                ) {

            orders.forEach ((ord) -> {
               try {
                   addOrder(conn, psOrder, psDetail, ord);
               } catch (SQLException e ) {
                   System.out.printf("%d ( %s) %s%n", e.getErrorCode(), e.getSQLState(), e.getMessage());
                   System.out.println("Problem: " + psOrder);
                   System.out.println("Order: " + ord);
               }

            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

 }

/*
Order[orderId=-1, dateString=2023-11-01 06:01:00, details=[OrderDetail[orderDetailId=-1, itemDescription=Apple, qty=5], OrderDetail[orderDetailId=-1, itemDescription=Orange, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=3], OrderDetail[orderDetailId=-1, itemDescription=Turkey, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Milk, qty=1]]]
Order[orderId=-1, dateString=2023-11-01 06:02:00, details=[OrderDetail[orderDetailId=-1, itemDescription=Bunch Celery, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Onion, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=7], OrderDetail[orderDetailId=-1, itemDescription=Package Ground Beef, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Nuttin' Honey Cereal, qty=1]]]
Order[orderId=-1, dateString=2023-11-01 06:03:00, details=[OrderDetail[orderDetailId=-1, itemDescription=loaves of bread, qty=3], OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=juice, qty=2], OrderDetail[orderDetailId=-1, itemDescription=bag of dog food, qty=1], OrderDetail[orderDetailId=-1, itemDescription=cans of cat food, qty=10]]]
Order[orderId=-1, dateString=2023-11-31 05:04:00, details=[OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=dozen eggs, qty=1], OrderDetail[orderDetailId=-1, itemDescription=lettuce, qty=1], OrderDetail[orderDetailId=-1, itemDescription=cookies, qty=1]]]
Order[orderId=-1, dateString=2023-11-01 06:05:00, details=[OrderDetail[orderDetailId=-1, itemDescription=milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=dozen eggs, qty=1], OrderDetail[orderDetailId=-1, itemDescription=lettuce, qty=1], OrderDetail[orderDetailId=-1, itemDescription=cookies, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Apple, qty=5], OrderDetail[orderDetailId=-1, itemDescription=Orange, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=3], OrderDetail[orderDetailId=-1, itemDescription=Turkey, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Milk, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Bunch Celery, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Onion, qty=2], OrderDetail[orderDetailId=-1, itemDescription=Banana, qty=7], OrderDetail[orderDetailId=-1, itemDescription=Package Ground Beef, qty=1], OrderDetail[orderDetailId=-1, itemDescription=Nuttin' Honey Cereal, qty=1]]]
5 records inserted for 1 (2023-11-01 06:01:00)
5 records inserted for 2 (2023-11-01 06:02:00)
5 records inserted for 3 (2023-11-01 06:03:00)
4 records inserted for 4 (2023-11-31 05:04:00)
14 records inserted for 5 (2023-11-01 06:05:00)

Process finished with exit code 0
*/


