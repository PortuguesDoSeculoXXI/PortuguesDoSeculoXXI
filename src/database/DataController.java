package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import logic.Answer;
import logic.Category;
import logic.Challenge;
import logic.Level;
import logic.Player;
import logic.Question;
import logic.Score;

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
     * Database connection instance.
     */
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
    }
    
    /**
     * Connect to database.
     */
    public void connect() {
        try {
            if (connection == null) {
                // Create a database connection
                connection = DriverManager.getConnection(DATABASE_URI);
            }
        } catch (SQLException e) {
            // If the error message is "out of memory", 
            // It probably means no database file is found
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Disconnect from database.
     */
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            // Connection close failed.
            System.err.println(e);
        }
    }
    
    /**
     * Get all players from database.
     *
     * @returns List of Players.
     */
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("player");           
            
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            while (rs.next()) {
                // Read the result set
                players.add(new Player(rs.getInt("ID_PLAYER"), rs.getString("NAME")));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }        
        return players;
    }
    
    /**
     * Get challenge score by player.
     *
     * @returns List of Scores.
     */
    public List<Score> getScoreByPlayer(int idPlayer) {
        List<Score> scoresPlayer= new ArrayList<>();
        
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  // set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("ChallengeScores").whereField("id_player", Integer.toString(idPlayer));
            
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            
            Score item;
            while (rs.next()) {
                item = new Score(rs.getInt("id_player"),rs.getInt("score"));
                item.setDateTime(rs.getTimestamp("date"));
                item.setDuration(rs.getInt("duration"));
                item.setLevel(Level.values()[rs.getInt("level")]);
                item.setGold(rs.getInt("gold"));
                item.setSilver(rs.getInt("silver"));
                item.setBronze(rs.getInt("bronze"));
                scoresPlayer.add(item);
            }
        } catch(SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }        
        return scoresPlayer;
    }
    
    /**
     * Get challenge score by player.
     *
     * @returns List of Scores.
     */
    public List<Score> getAvgScoreByPlayer(int idPlayer) {
        List<Score> scoresPlayer= new ArrayList<>();
        
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  // set timeout to 30 sec.
            
            ResultSet rs = st.executeQuery("select ID_PLAYER, avg(score) as `score_avg`, date, DURATION, LEVEL, SCORE, GOLD, SILVER, BRONZE from challengescores where id_player = "+Integer.toString(idPlayer));
            
            Score item;
            while (rs.next()) {
                item = new Score(rs.getInt("id_player"),rs.getInt("score_avg"));
                item.setDateTime(rs.getTimestamp("date"));
                item.setDuration(rs.getInt("duration"));
                item.setLevel(Level.values()[rs.getInt("level")]);
                item.setGold(rs.getInt("gold"));
                item.setSilver(rs.getInt("silver"));
                item.setBronze(rs.getInt("bronze"));
                scoresPlayer.add(item);
            }
        } catch(SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }        
        return scoresPlayer;
    }
    
    /**
     * Get all the categories from database.
     *
     * @returns List of Categories.
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        connect();
        try {
            
            Statement st = connection.createStatement();
            st.setQueryTimeout(30);  // set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("category").selectField("*");            
            
            ResultSet rs = st.executeQuery(builder.getSelectQuery());
            while (rs.next()) {
                // Read the result set
                categories.add(new Category(rs.getInt("ID_CATEGORY"), rs.getString("CATEGORY_NAME")));
            }  
        } catch(SQLException e) {
            // if the error message is "out of memory", 
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
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
     * @return true-successfully inserted, false-already exists.
     */
    public Boolean updatePlayer(int idPlayer, String newName) {
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.

            // Se não existir, sai fora
            if (!existRecord("player", "id_player", Integer.toString(idPlayer))) {
                return false;
            }
            
            st.execute("update player set name='"+newName+"' where id_player="+idPlayer);
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return false;
        } finally {
            disconnect();
        }
        return true;
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
     * Get the player name.
     *
     * @param idPlayer Player identification.
     * @return Player name
     */
    public String getPlayerName(int idPlayer) {
        String result = "";
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("player").whereField("id_player", Integer.toString(idPlayer));
            ResultSet rs = st.executeQuery("SELECT * FROM player WHERE id_player = "+Integer.toString(idPlayer));
            while (rs.next()) {
                result = rs.getString("name");
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
            return result;
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
     * @param dateTime Score date and time.
     * @param duration Total duration of challenge.
     * @param level Game mode.
     * @param score Score points.
     * @param gold Gold medal.
     * @param silver Silver medal.
     * @param bronze Bronze medal.
     * @return true-successfully inserted, false-already exists.
     */
    public void insertChallengeScore(int idPlayer, Date dateTime, int duration, 
            Level level, int score, int gold, int silver, int bronze) {
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
                        
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("ChallengeScores")
                .addField("id_player", Integer.toString(idPlayer), SQLBuilder.DataType.asNum)
                .addField("date", Double.toString(dateTime.getTime()), SQLBuilder.DataType.asNum)
                .addField("duration", Integer.toString(duration), SQLBuilder.DataType.asNum)
                .addField("level", Integer.toString(level.ordinal()), SQLBuilder.DataType.asNum)
                .addField("score", Integer.toString(score), SQLBuilder.DataType.asNum)
                .addField("gold", Integer.toString(gold), SQLBuilder.DataType.asNum)
                .addField("silver", Integer.toString(silver), SQLBuilder.DataType.asNum)
                .addField("bronze", Integer.toString(bronze), SQLBuilder.DataType.asNum);
            
            System.out.println(builder.getInsertQuery());
            st.execute(builder.getInsertQuery());
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
    }
    
    /**
     * Get random questions from a category.
     *
     * @param categories list of categories.
     * @param numberOfQuestions Number of questions to retrieve.
     * @return List of Questions
     */
    public List<Question> getQuestionsByCategory(List<Challenge.Categories> categories, int numberOfQuestions) {        
        ArrayList<Question> questions = new ArrayList<>();
        if (categories == null || categories.isEmpty())
            return questions;
        
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            
            if (categories.size() == 1) {
                // Apenas uma categoria
                builder.addTable("question").whereField("ID_CATEGORY", Integer.toString(categories.get(0).ordinal()));
            }
            else {
                builder.addTable("question");
                for (int i=0; i<categories.size(); i++) {
                    builder.whereField("ID_CATEGORY", Integer.toString(categories.get(i).ordinal()), "OR");
                    // Ultimo
                    if (i == categories.size()-1)
                        builder.whereField("ID_CATEGORY", Integer.toString(categories.get(i).ordinal()));
                }
            }

            ResultSet rs = st.executeQuery(builder.getSelectQuery()+" ORDER BY RANDOM() LIMIT "+numberOfQuestions);
            while (rs.next()) {
                questions.add(new Question(Answer.values()[rs.getInt("RIGHT_ANSWER")],
                        rs.getString("ENUNCIATION"), 
                        rs.getString("ANSWER_A"),
                        rs.getString("ANSWER_B")));
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
     * @return List of Questions
     */
    public List<Question> getRandomQuestions(int numberOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        connect();
        try {
            Statement st = connection.createStatement();
            st.setQueryTimeout(30); //set timeout to 30 sec.
            
            SQLBuilder builder = new SQLBuilder();
            builder.addTable("question");
            ResultSet rs = st.executeQuery(builder.getSelectQuery()+" ORDER BY RANDOM() LIMIT "+numberOfQuestions);
            while (rs.next()) {
                questions.add(new Question(Answer.values()[rs.getInt("RIGHT_ANSWER")],
                        rs.getString("ENUNCIATION"), 
                        rs.getString("ANSWER_A"),
                        rs.getString("ANSWER_B")));
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnect();
        }
        return questions;
    }
    
    /**
     * Generate sample data for demo porpuse.
     */
    public void sampleData() {
        System.out.println(getRuleClarification(2));
                
        System.out.println(getPlayerName(10));
        
        ArrayList<Challenge.Categories> list = new ArrayList<>();
        list.add(Challenge.Categories.GRAPHIC_ACCENTS);
        list.add(Challenge.Categories.HYPHEN);
        List<Question> questions = getQuestionsByCategory(list,15);
        System.out.println(questions.size());
        System.out.println(questions.get(2).getQuestion());
        
        list = new ArrayList<>();
        list.add(Challenge.Categories.HYPHEN);
        System.out.println(getQuestionsByCategory(list,10).size());
        
        list = new ArrayList<>();
        System.out.println(getQuestionsByCategory(list,15).size());
        
        System.out.println(getRandomQuestions(15).size());
        
        //insertChallengeScore(2,new Date(),63,Level.MODE_HARD,1292,1,1,0);
        
        System.out.println(getScoreByPlayer(1).size());
        
        List<Score> scores = getScoreByPlayer(2);
        System.out.println(scores.get(0).getDateTime());
                
        scores = getAvgScoreByPlayer(2);
        System.out.println(scores.size());
        System.out.println(scores.get(0).getScore());
    }
}
