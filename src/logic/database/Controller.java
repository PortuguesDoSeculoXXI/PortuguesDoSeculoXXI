package logic.database;

import database.DataController;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import logic.Category;
import logic.Player;

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
     */
    public Controller() {
        dataController = new DataController();
        categories = dataController.getAllCategories();
        players = dataController.getAllPlayers();
        
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Player: " + players.get(i).getId() + " " + players.get(i).getName());
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
     * Getter of DataController.
     * @return DataController instance.
     */
    public DataController getDataController() {
        return dataController;
    }

    /**
     * Getter of categories.
     * @return list of categories.
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Getter of players.
     * @return list of players.
     */
    public List<Player> getPlayers() {
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
}
