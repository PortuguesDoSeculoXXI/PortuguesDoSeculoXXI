package gui;

import database.DataController;
import java.util.List;
import java.util.Observable;
import logic.Category;
import logic.Player;

public class Controller extends Observable {
    
    DataController dataController;
    
    public Controller() {
        dataController = new DataController();
        List<Category> categories = dataController.getAllCategories();
        List<Player> players = dataController.getAllPlayers();
        
        for(int i = 0; i < categories.size(); i++){
            System.out.println("ID: " + categories.get(i).getId() + " " + categories.get(i).getName());
        }
        for(int i = 0; i < players.size(); i++){
            System.out.println("Player: " + players.get(i).getId() + " " + players.get(i).getName());
        }
    }
    
    public void update() {
        setChanged();
        notifyObservers();
    }
    
}
