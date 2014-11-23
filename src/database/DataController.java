package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Data Controller.
 * This class implements the abstraction of persistent data.
 * Uses SQLite JDBC driver for data querying.
 * 
 * @author PTXXI
 */
public class DataController {
    /**
     * Library name of the SQLite driver.
     */
    private static String SQLITE_JDBC = "org.sqlite.JDBC";
    /**
     * Database address to establish connection with the binary file.
     */
    private static String DATABASE_URI = "jdbc:sqlite:db/XXIv1.db";

    /**
     * DataController Constructor.
     * Responsable for instance creation. Connects directly to the database.
     */
    public DataController() {
        
        // Teste
        try {
            Class.forName(SQLITE_JDBC);
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver don't exist.");
            return;
        }
        connect();
    }

    public void connect() {
        Connection connection = null;
        try {
            // Create a database connection
            connection = DriverManager.getConnection(DATABASE_URI);
            
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            
            if (connection.getAutoCommit())
                System.out.println("AutoCommit: on");
            
            // Test
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("category");
            builder.addSelectField("*");
            builder.addWhereField("id_category", "3", ">=");
            
            System.out.println("Query: "+builder.getSelectQuery());
            ResultSet rs = statement.executeQuery(builder.getSelectQuery());
            while (rs.next()) {
                // Read the result set
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
