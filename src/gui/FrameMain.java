package gui;

import java.awt.Container;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

public class FrameMain extends JFrame implements Observer {
    
    private Controller controller;
    private Container mainContainer;
    
    public FrameMain(Controller controller) {
        this(controller, 150, 0, 1000, 850);
    }
    
    public FrameMain(Controller controller, int x, int y, int width, int height) {
        // Cabeçalho
        super("Português do Século XXI");
        this.controller = controller;
        
        mainContainer = getContentPane();
        
        init();
        validate();
        
        setLocation(x,y);
        setSize(width, height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // READY
        controller.notifyObservers();
    }
    
    protected void init() {
        // Init components/panels
     
        // Observers
        
        // Layout
        createLayout();
        // Listeners
        registerListeners();
    }
    
    protected void createLayout() {
        
    }
    
    protected void registerListeners() {
        
    }
    
    @Override
    public void update(Observable t, Object o) {

    }
    
}
