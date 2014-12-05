package gui;

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
public class JFrames implements Observer {
    
    /**
     * Models/Logic
     */
    private final Controller controller;
    private final ChallengeModel challengeModel;
    
    /**
     * Windows
     */
    private final FrameMain entranceWindow;
    private final JFrameChoosingWindow configurationWindow;
    private final JFrameGameMode gameModeWindow;
    private final JFrameScoreWindow gameScoreWindow;
    
    /**
     * Constructor.
     * Creates the only Controller and ChalengeModel instance.
     * Also creates every JFrame.
     */
    public JFrames() {
        this.controller = new Controller();
        
        this.challengeModel = new ChallengeModel(new Challenge(controller, null));
        this.challengeModel.addObserver(this);
        
        this.entranceWindow = new FrameMain(this.controller, this.challengeModel);
        
        this.configurationWindow = new JFrameChoosingWindow(this.controller, this.challengeModel);
        this.configurationWindow.setVisible(false);
        
        this.gameModeWindow = new JFrameGameMode(this.controller, this.challengeModel);
        this.gameModeWindow.setVisible(false);
        
        this.gameScoreWindow = new JFrameScoreWindow(this.controller, this.challengeModel);
        this.gameScoreWindow.setVisible(false);
    }

    /**
     * Updates interface when a notification occurs.
     */
    @Override
    public void update(Observable o, Object o1) {
        if (challengeModel.getChallenge() == null) {
            // Entrance
            entranceWindow.setVisible(true);
            configurationWindow.setVisible(false);
            gameModeWindow.setVisible(false);
            gameScoreWindow.setVisible(false);
        }
        else if (challengeModel.getChallenge().getCurrentState() instanceof WaitConfiguration) {
            // Configuration
            entranceWindow.setVisible(false);
            configurationWindow.setVisible(true);
            gameModeWindow.setVisible(false);
            gameScoreWindow.setVisible(false);
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
                gameScoreWindow.setVisible(false);
            }
        }
        else if (challengeModel.getChallenge().getCurrentState() instanceof WaitScore) {
            // Show final score
            entranceWindow.setVisible(false);
            configurationWindow.setVisible(false);
            gameModeWindow.setVisible(false);
            gameScoreWindow.setVisible(true);
        }
    }
}
