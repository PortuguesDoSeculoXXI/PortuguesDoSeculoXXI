package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import logic.Category;
import logic.Player;
import logic.Question;

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
    
    private Connection connection = null;
    
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
        
        try {
            if(connection == null){
                // Create a database connection
                connection = DriverManager.getConnection(DATABASE_URI);
            }
            
        } catch (SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
    }
    
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
    }
    
    public List<Category> getAllCategories(){
        List<Category> categories = new ArrayList<>();
        connect();
        try{
            
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  // set timeout to 30 sec.
            
            // Test
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("category").selectField("*");            
            
            System.out.println("Query: "+builder.getSelectQuery());
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            while (rs.next()) {
                // Read the result set
                //System.out.println("id = " + rs.getInt("ID_CATEGORY"));
                //System.out.println("name = " + rs.getString("CATEGORY_NAME"));
                categories.add(new Category(rs.getInt("ID_CATEGORY"), rs.getString("CATEGORY_NAME")));
            }  
        }catch(SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }finally{
            disconnect();
        }
        return categories;
    }
    
    public List<Player> getAllPlayers(){
        List<Player> players = new ArrayList<>();
        connect();
        
        try{
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  // set timeout to 30 sec.
            
            // Test
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("player").selectField("*");           
            
            System.out.println("Query: "+builder.getSelectQuery());
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            while (rs.next()) {
                // Read the result set
                players.add(new Player(rs.getInt("ID_PLAYER"), rs.getString("NAME")));
            }
        }catch(SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }finally{
            disconnect();
        }        
        return players;
    }
    
    public List<Integer> getScoreByPlayer(int idPlayer){
        // Esta errado, so estou a ir buscar o ID_SCORE, e retorno um array com os ids_scores de cada palyer
        List<Integer> scoresPlayer= new ArrayList<>();
        connect();
        
        try{
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  // set timeout to 30 sec.
            
            // Test
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("challenge").selectField("ID_SCORE").whereField("ID_PLAYER", Integer.toString(idPlayer));           
            
            System.out.println("Query: "+builder.getSelectQuery());
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            
            while (rs.next()) {
                scoresPlayer.add(rs.getInt("ID_SCORES"));
            }
        }catch(SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }finally{
            disconnect();
        }        
        return scoresPlayer;
    }
    
    
    
}
