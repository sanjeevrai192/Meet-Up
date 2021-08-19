package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBhandler{

    Connection dbconnection;
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/genius","root","");
            System.out.println("Connected to database");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return dbconnection;
    }
}
