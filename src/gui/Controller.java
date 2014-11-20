package gui;

import java.util.Observable;

public class Controller extends Observable {
    
    public Controller() {
        
    }
    
    public void update() {
        setChanged();
        notifyObservers();
    }
    
}
