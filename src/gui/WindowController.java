package gui;

import database.DataController;
import java.util.Observable;
import java.util.Observer;
import logic.Challenge;
import logic.ChallengeModel;
import logic.database.Controller;
import states.WaitAnswer;
import states.WaitConfiguration;
import states.WaitScore;

/**
 * JFrames class.
 * Is intended that all JFrames are initiated, but only the desired ones are
 * shown.
 * 
 * Default visible JFrame is FrameMain.
 * 
 * @author PTXXI
 */
public class WindowController implements Observer {
    
    /**
     * Models/Logic
     */
    private final Controller controller;
    private final ChallengeModel challengeModel;
    
    /**
     * Windows
     */
    private final EntranceWindow entranceWindow;
    private final ConfigurationWindow configurationWindow;
    private final GameModeWindow gameModeWindow;
    //private final GameScoreWindow gameScoreWindow;
    private ScoresWindow scoresWindow;
    
    /**
     * Constructor.
     * Creates the only Controller and ChalengeModel instance.
     * Also creates every JFrame.
     */
    public WindowController() {
        this.controller = new Controller(new DataController());
        
        this.challengeModel = new ChallengeModel(new Challenge(controller, null));
        this.challengeModel.addObserver(this);
        
        this.entranceWindow = new EntranceWindow(this.controller, this.challengeModel);
        
        this.configurationWindow = new ConfigurationWindow(this.controller, this.challengeModel);
        this.configurationWindow.setVisible(false);
        
        this.gameModeWindow = new GameModeWindow(this.controller, this.challengeModel);
        this.gameModeWindow.setVisible(false);
        
        //this.gameScoreWindow = new GameScoreWindow(this.controller, this.challengeModel);
        //this.gameScoreWindow.setVisible(false);
        
        this.scoresWindow = new ScoresWindow(this.controller, this.challengeModel);
        this.scoresWindow.setVisible(false);
    }

    /**
     * Updates interface when a notification occurs.
     * @param o
     * @param o1
     */
    @Override
    public void update(Observable o, Object o1) {
        if (challengeModel.isScoreWindow()) {
            // Scores table
            entranceWindow.setVisible(false);
            configurationWindow.setVisible(false);
            gameModeWindow.setVisible(false);
            //gameScoreWindow.setVisible(false);
            if (scoresWindow == null)
                scoresWindow = new ScoresWindow(this.controller, this.challengeModel);
            scoresWindow.setVisible(true);
        }
        else if (challengeModel.getChallenge() == null) {
            // Entrance
            entranceWindow.setVisible(true);
            configurationWindow.setVisible(false);
            gameModeWindow.setVisible(false);
            //gameScoreWindow.setVisible(false);
            scoresWindow.setVisible(false);
        }
        else if (challengeModel.getChallenge().getCurrentState() instanceof WaitConfiguration && scoresWindow != null) {
            // Configuration
            entranceWindow.setVisible(false);
            configurationWindow.setVisible(true);
            gameModeWindow.setVisible(false);
            //gameScoreWindow.setVisible(false);
            scoresWindow.setVisible(false);
        }
        else if (challengeModel.getChallenge().getCurrentState() instanceof WaitAnswer) {
            // Verify if the game has already started
            if (gameModeWindow.isVisible()) {
                // Refresh
                gameModeWindow.update(o, o1);
            }
            else {
                // Start the game!
                entranceWindow.setVisible(false);
                configurationWindow.setVisible(false);
                gameModeWindow.setVisible(true);
                //gameScoreWindow.setVisible(false);
                scoresWindow.setVisible(false);
            }
        }
        else if (challengeModel.getChallenge().getCurrentState() instanceof WaitScore) {
            // Show final score
            entranceWindow.setVisible(false);
            configurationWindow.setVisible(false);
            gameModeWindow.setVisible(false);
            //gameScoreWindow.setVisible(true);
            scoresWindow.setVisible(false);
        }
    }
}
