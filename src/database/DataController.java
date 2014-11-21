package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataController {

    public DataController() {
        // Teste
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver don't exist.");
            return;
        }
        connect();
    }

    public void connect() {
        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:db/XXIv1");
            
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            
            if (connection.getAutoCommit())
                System.out.println("AutoCommit: on");
            
            ResultSet rs = statement.executeQuery("select * from category");
            while (rs.next()) {
                // read the result set
                System.out.println("id = " + rs.getInt("ID_CATEGORY"));
                System.out.println("name = " + rs.getString("CATEGORY_NAME"));
            }
            
        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
