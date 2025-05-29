package connection;
import java.sql.*;

public class ConnectionProvider {
    public static Connection getCon() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); 
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/seminiproject","root","12345");
            return con;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
