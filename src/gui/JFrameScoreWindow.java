package gui;

import javax.swing.JFrame;
import logic.ChallengeModel;
import logic.database.Controller;

/**
 *
 * @author PTXXI
 */
public final class JFrameScoreWindow extends JFrame {
    private final Controller controller;
    private final ChallengeModel challengeModel;

    public JFrameScoreWindow(Controller controller, ChallengeModel challengeModel) {
        this.controller = controller;
        this.challengeModel = challengeModel;
    }
}
