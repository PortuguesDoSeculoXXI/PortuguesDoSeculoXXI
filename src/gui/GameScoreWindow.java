package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import logic.ChallengeModel;
import logic.database.Controller;
import org.jfree.chart.ChartPanel;

/**
 * Score Game Window.
 * Score of the game.
 * 
 * @author PTXXI
 */
public class GameScoreWindow extends JFrame implements Observer {
    
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    
    /**
     * Constructor.
     * Initializes model variables and define layout parameters.
     * 
     * @param controller
     * @param challengeModel 
     */
    public GameScoreWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 550);
    }
    
    public GameScoreWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;
        
        init();

        setLocation(x, y);
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Instantiates content panes and register component listeners
     */
    private void init() {
        // Init components/panels
        this.mainContainer = getContentPane();
        this.mainContainer.setLayout(new BorderLayout());
        this.mainContainer.validate();

        // Layout
        createLayout();

        // Observers
        this.controller.addObserver(this);
        this.challengeModel.addObserver(this);

        // Listeners
        registerListeners();
    }

    private void createLayout() {
        jPanelCenter();
        jPanelSouth();
    }
    
    /**
     * Method instantiates <b>Center</b> components and its characteristics.
     */
    private void jPanelCenter() {
        final JPanel jPanelCenter = new JPanel(new FlowLayout());

        jPanelCenter.add(new JLabel("<HTML><B><FONT SIZE=64>Pontuação</FONT></B></HTML>"));
        
        mainContainer.add(jPanelCenter);
    }

    /**
     * Method instantiates <b>South</b> components and its characteristics.
     */
    private void jPanelSouth() {

    }
    
    /**
     * Static listeners.
     */
    private void registerListeners() {

    }
    
    /**
     * Update method - Observer.
     * Defines this frame behavior according to the current state on ChallengeModel
     * 
     * @param o
     * @param o1 
     */
    @Override
    public void update(Observable o, Object o1) {
        
    }
}
