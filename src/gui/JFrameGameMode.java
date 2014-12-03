package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import logic.ChallengeModel;
import logic.database.Controller;
import states.WaitConfiguration;

/**
 *
 * @author PTXXI
 */
public class JFrameGameMode extends JFrame implements Observer{
    
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    private JPanel jpanelNorth;
    
    public JFrameGameMode(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 550);
     }

    public JFrameGameMode(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;

        
        setLocation(x, y);
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
        protected void init() {
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
        //registerListeners();
    }
    
    
     private void createLayout(){
         this.jPanelNorth();
     }   
        
     private void jPanelNorth(){
        jpanelNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Box verticalBox = Box.createVerticalBox();
        JPanel horizontalFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        horizontalFlow.setPreferredSize(new Dimension(600, 30));
        horizontalFlow.add(new JLabel("Perfil atual: "));

       
        verticalBox.add(horizontalFlow);

        verticalBox.add(Box.createVerticalStrut(40));

        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel jb = new JLabel("<HTML><CENTER>Português<BR>do Século XXI</CENTER><HTML>");
        jb.setFont(jb.getFont().deriveFont(64f));

        jp.add(jb);

        verticalBox.add(jp);

        jpanelNorth.add(verticalBox);
        this.mainContainer.add(new JLabel("dgdf"), BorderLayout.NORTH);
     }
     
        
    @Override
    public void update(Observable t, Object o) {
        
        
    }
}
