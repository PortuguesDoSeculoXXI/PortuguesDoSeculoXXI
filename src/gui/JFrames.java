package gui;

import java.util.Observable;
import java.util.Observer;
import logic.Challenge;
import logic.ChallengeModel;
import logic.database.Controller;
import states.WaitConfiguration;

/**
 * JFrames class.
 * Is intended that all JFrames are initiated, but only the desired ones are
 * shown.
 * 
 * Default visible JFrame is FrameMain.
 * 
 * @author PTXXI
 */
public class JFrames implements Observer {
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private final FrameMain frameMain;
    private final JFrameChoosingWindow choosingWindow;
    private final JFrameScoreWindow scoreWindow;
    
    /**
     * Constructor.
     * Creates the only Controller and ChalengeModel instance.
     * Also creates every JFrame.
     */
    public JFrames(){
        this.controller = new Controller();
        
        this.challengeModel = new ChallengeModel(new Challenge(null));
        this.challengeModel.addObserver(this);
        
        this.frameMain = new FrameMain(this.controller, this.challengeModel);
        
        this.choosingWindow = new JFrameChoosingWindow(this.controller, this.challengeModel);
        this.choosingWindow.setVisible(false);
        
        this.scoreWindow = new JFrameScoreWindow(this.controller, this.challengeModel);
        this.scoreWindow.setVisible(false);
    }

    @Override
    public void update(Observable o, Object o1) {
        if (challengeModel.getChallenge() == null) {
            frameMain.setVisible(true);
            choosingWindow.setVisible(false);
        } else if (challengeModel.getChallenge().getCurrentState() instanceof WaitConfiguration) {
            frameMain.setVisible(false);
            choosingWindow.setVisible(true);
        }
    }
}
