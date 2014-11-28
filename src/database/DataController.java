package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import logic.Category;
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
        // Driver
        try {
            Class.forName(SQLITE_JDBC);
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver don't exist.");
            return;
        }
        sampleData();
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
    
    /**
     * Next Id from table.
     *
     * @param tableName name of table.
     * @param fieldName primary key from table.
     * @returns Next Id.
     */
    private int getNextId(String tableName, String fieldName) {
        int lastId = 0;
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.

            SQLBuilder builder = new SQLBuilder();
            builder.addTable(tableName).selectField("max(id_player)", "id_player");
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            while (rs.next()) {
                lastId = rs.getInt("id_player");
            }
            // Increment the last Id
            lastId = (lastId == 0 ? 1 : lastId + 1);
        } catch(SQLException e) {
            System.err.println(e.getMessage());   
        } finally {
            return lastId;
        }
    }
    
    /**
     * Check if exist a record.
     *
     * @param tableName name of table.
     * @param fieldName primary key from table.
     * @returns Next Id.
     */
    private boolean existRecord(String tableName, String fieldName, String value) {
        boolean exist = false;
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.

            SQLBuilder builder = new SQLBuilder();
            builder.addTable(tableName).whereField(fieldName, value);
            ResultSet rs = st.executeQuery(builder.getSelectQuery() + " LIMIT 1");
            while (rs.next()) {
                exist = true;
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());   
        } finally {
            return exist;
        }
    }
    
    /**
     * Insert a new player.
     *
     * @param name Player name.
     * @return true-successfully inserted, false-already exists.
     */
    public Boolean insertPlayer(String name) {
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            if (existRecord("player", "name", name))
                return false;
            
            int nextId = getNextId("player", "id_player");
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("player")
                .addField("name", name, SQLBuilder.DataType.asText)
                .addField("id_player", Integer.toString(nextId), SQLBuilder.DataType.asNum);
            
            System.out.println("Query: "+builder.getInsertQuery());
            st.execute(builder.getInsertQuery());
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
        return true;
    }
    
    /**
     * Update player.
     *
     * @param idPlayer Player identification.
     * @param newName New name.
     */
    public void updatePlayer(int idPlayer, String newName) {
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.


        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Delete player.
     *
     * @param idPlayer Player identification.
     */
    public void deletePlayer(int idPlayer) {
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("player")
                .selectField("id_player")
                .whereField("id_player", Integer.toString(idPlayer));
            
            System.out.println("Query: "+builder.getDeleteQuery());
            st.execute(builder.getDeleteQuery());
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
    }
            
    /**
     * Get the question clarification.
     *
     * @param idRule Question identification.
     * @return Question clarification
     */
    public String getRuleClarification(int idRule) {
        String result = "";
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("rule").whereField("id_rule", Integer.toString(idRule));
            ResultSet rs = st.executeQuery("SELECT * FROM rule WHERE id_rule = "+Integer.toString(idRule));
            while (rs.next()) {
                result = rs.getString("clarification");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
            return result;
        }
    }
    
    /**
     * Insert the score challenge of a player.
     *
     * @param idPlayer Player identification.
     * @param idPlayer Score date and time.
     * @param idPlayer Total duration of challenge.
     */
    public void insertChallengeScore(int idPlayer, Date dateTime, int duration, 
            int level, int medals) {
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Get random questions from a category.
     *
     * @param numberOfQuestions Number of questions to retrieve.
     */
    public ArrayList<Question> getQuestionsByCategory(int idCategory, int numberOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("question").whereField("ID_CATEGORY", Integer.toString(idCategory));
            ResultSet rs = st.executeQuery(builder.getSelectQuery()+" ORDER BY RANDOM() LIMIT "+numberOfQuestions);
            while (rs.next()) {
                questions.add(new Question());
                //result = rs.getString("clarification");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
        return questions;
    }
    
    /**
     * Get random questions from random categories.
     *
     * @param numberOfQuestions Number of questions to retrieve.
     */
    public ArrayList<Question> getRandomQuestions(int numberOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("question");
            ResultSet rs = st.executeQuery(builder.getSelectQuery()+" ORDER BY RANDOM() LIMIT "+numberOfQuestions);
            while (rs.next()) {
                questions.add(new Question());
                //result = rs.getString("clarification");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
        return questions;
    }
    
    public void sampleData() {
        insertPlayer("Ricardo Pereira");
        insertPlayer("Filipe Santos");
        deletePlayer(7);
        deletePlayer(2);
        System.out.println(getRuleClarification(2));
        System.out.println(getQuestionsByCategory(3,15).size());
        System.out.println(getRandomQuestions(15).size());
    }
}
