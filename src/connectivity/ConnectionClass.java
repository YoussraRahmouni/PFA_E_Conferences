package connectivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionClass {
    public Connection connection;
    public  Connection getConnection(){


        //String dbName="trial";
        //String userName="root";
        //String password="";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/conferencebd?useTimezone=true&serverTimezone=UTC","root","");
            System.out.println("connection made");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return connection;
    }
}
