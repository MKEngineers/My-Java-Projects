import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // ✅ Your actual DB name must be correct
            String url = "jdbc:mysql://localhost:3306/cinema"; 
            String user = "root";
            String password = ""; //  Replace with your actual MySQL password

            // ✅ Optional: load JDBC driver (modern versions do this automatically)
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(url, user, password);
            // ✅ Optional: ensure auto-commit is enabled
            conn.setAutoCommit(true);

        } catch (ClassNotFoundException e) {
            System.out.println(" JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Failed to connect to database.");
            e.printStackTrace();
        }

        return conn;
    }
}
