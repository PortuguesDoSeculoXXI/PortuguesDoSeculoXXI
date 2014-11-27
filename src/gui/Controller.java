package gui;

import database.DataController;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import logic.Category;

public class Controller extends Observable {
    
    DataController dataController;
    
    public Controller() {
        dataController = new DataController();
        List<Category> categories = dataController.getAllCategories();
        
        
        for(int i = 0; i < categories.size(); i++){
            System.out.println("ID: " + categories.get(i).getId() + " " + categories.get(i).getName());
        }
    }
    
    public void update() {
        setChanged();
        notifyObservers();
    }
    
}
