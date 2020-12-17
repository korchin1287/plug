package immitaition;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Thread.sleep;

public class Main {
    static Long nowTime = new Date().getTime();
    final static String userName = "c##x5";
    final static String password = "c##x5";
    final static String connectionURL = "jdbc:oracle:thin:@//192.168.14.53:1522/orcl.localdomain";
    final static String changeId = "IDC2D620524153zdzPWAoX9OFgW4UB";

    public static int parseSQL() throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try (Connection connection = DriverManager.getConnection(connectionURL, userName, password);
             Statement statement = connection.createStatement())
        {
            ResultSet resultSet = statement.executeQuery("select ID, CREATE_DATE, HEADER from TICKET where STATE_ID=-1");
            while (resultSet.next()) {
                long createDate = resultSet.getLong("CREATE_DATE");
                int id = resultSet.getInt("ID");
                String header = resultSet.getString("HEADER");
                if (createDate > nowTime){
                    System.out.println(id+","+id+","+header+",Case Description, 3, 1, 106,"+createDate+", 0, ASKO");
                    statement.executeUpdate("UPDATE TICKET SET STATE_ID=1 where ID="+id);
                    statement.executeUpdate("INSERT INTO TASK (ID, TICKET_ID, HEADER, TEXT, PRIORITY_ID, STATE_ID, CLIENT_ID, CREATE_DATE, SYNC_MASK, EXTERNAL_SYSTEM) " +
                            "VALUES ("+id+","+id+",'"+header+"','Case Description', 3, 1, 106,"+createDate+", 0, 'ASKO')");
                    nowTime = createDate;
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            return 0;
        }

    }
    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {

        while (true) {
            if (parseSQL() == 1){
                return;
            }
            Thread.sleep(1000);
        }
    }
}
