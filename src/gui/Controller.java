package gui;

import database.DataController;
import java.util.Observable;

public class Controller extends Observable {
    
    DataController dataController;
    
    public Controller() {
        dataController = new DataController();
    }
    
    public void update() {
        setChanged();
        notifyObservers();
    }
    
}
