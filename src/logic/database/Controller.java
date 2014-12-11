package logic.database;

import database.DataController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import logic.Category;
import logic.Challenge;
import logic.Level;
import logic.Player;
import logic.Question;
import logic.Score;

/**
 * Controller class.
 * This class is the bridge between the interface and the database logic.
 * 
 * @author PTXXI
 */
public class Controller extends Observable {
    /**
     * dataController is a database instance, has methods to interact with the database.
     */
    private final DataController dataController;
    /**
     * Is initialized with all available categories.
     */
    private List<Category> categories = new ArrayList<>();
    /**
     * Is initialized with all available profiles in database.
     * Contains player profiles.
     */
    private List<Player> players = new ArrayList<>();
    
    /**
     * Constructor.
     * Initialize dataController, categories and players
     * @param dataController
     */
    public Controller(DataController dataController) {
        this.dataController = dataController;
        
        if (hasDataController()) {
            categories = dataController.getAllCategories();
            players = dataController.getAllPlayers();
        }        
    }
    
    /**
     * Notify observers.
     */
    public void sendNotification() {
        setChanged();
        notifyObservers();
    }

    /**
     * Getter of categories.
     * @return list of categories.
     */
    public List<Category> getCategories() {
        if (categories == null)
            categories = new ArrayList<>();
        return categories;
    }

    /**
     * Getter of players.
     * @return list of players.
     */
    public List<Player> getPlayers() {
        if (players == null)
            players = new ArrayList<>();
        return players;
    }

    /**
     * Retrieves player profile by name.
     * 
     * @param name
     * @return Desired player. Or null if not found.
     */
    public Player getProfileOf(String name) {
        for (Player aux : players)
            if (aux.getName().equals(name))
                return aux;
        
        return null;
    }

    /**
     * Convert players to a String array, with their names.
     * @return 
     */
    public String[] getPlayersAsStrings() {
        String[] newArray = new String[players.size()];
        for (int i = 0; i < newArray.length; i++) 
            newArray[i] = players.get(i).getName();
        
        return newArray;
    }
    
    /**
     * Insert new player in database.
     * Updates players list.
     * This method assumes isValidName(...) has been called before.
     * 
     * @param name
     * @return 
     */
    public boolean insertPlayer(String name) {
        if (!hasDataController())
            return false;
        boolean result = dataController.insertPlayer(name);
        players = dataController.getAllPlayers();
        return result;
    }

    /**
     * Delete player from database.
     * 
     * @param player 
     */
    public void deletePlayer(Player player) {
        if (!hasDataController())
            return;
        dataController.deletePlayer(player.getId());
        players.remove(player);
    }
    
    /**
     * Update player name.
     * This assumes isValidName(...) has been called before.
     * 
     * @param player
     * @param newName 
     */
    public boolean updatePlayer(Player player, String newName) {
        if (!hasDataController())
            return false;
        boolean result = dataController.updatePlayer(player.getId(), newName);
        players = dataController.getAllPlayers();
        return result;
    }
    
    /**
     * Returns true if newName is unique, false otherwise.
     * Names are not case sensitive.
     * Updates players list.
     * 
     * @param newName
     * @return 
     */
    public boolean isValidName(String newName) {
        for (Player aux : players)
            if (aux.getName().equalsIgnoreCase(newName))
                return false;
        return true;
    }
    
    /**
     * Proxy to DataController.getScoreByPlayer.
     */
    public List<Score> getScoreByPlayer(int player) {
        if (!hasDataController())
            return null;
        return dataController.getScoreByPlayer(player);
    }
    
    /**
     * Proxy to DataController.getQuestionsByCategory.
     */
    public List<Question> getQuestionsByCategory(List<Challenge.Categories> categories) {
        if (!hasDataController())
            return null;
        return dataController.getQuestionsByCategory(categories, 15);
    }
    
    /**
     * Proxy to DataController.insertChallengeScore.
     */
    public void insertChallengeScore(Player player, Level level, Score score) {
        if (!hasDataController())
            return;
        
        if (score == null)
            return;
        
        dataController.insertChallengeScore(player.getId(), new Date(), score.getDuration(), level, score.getScorePoints(),
                score.getGold(), score.getSilver(), score.getBronze());
    }
    
    /**
     * Proxy to DataController.getRuleClarification.
     */
    public String getRuleClarification(Question question) {
        if (!hasDataController())
            return "";
        if (question == null)
            return "";        
        return dataController.getRuleClarification(question.getIdRule());
    }
    
    /**
     * Has access to persistent data.
     */
    public boolean hasDataController() {
        return dataController != null;
    }

}
