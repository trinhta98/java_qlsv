import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectMySQL {
    protected static Connection con = null;
    public ConnectMySQL() throws SQLException {
        try {
            //nap driver
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/qlsv", "root", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
