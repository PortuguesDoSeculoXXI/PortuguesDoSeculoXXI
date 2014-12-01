package logic;

import java.util.Observable;

/**
 * Model class.
 * Interface should interact with this model to perform any operation.
 * This class is the bridge between interface and logic.
 *
 * @author PTXXI
 */
public class ChallengeModel extends Observable implements ChallengeInterface {
    Challenge challenge;

    public ChallengeModel(Challenge challenge) {
        this.challenge = challenge;
    }
    
    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
        sendNotification();
    }
    
    /**
     * Notify registered observers classes. 
     */
    public void sendNotification() {
        setChanged();
        notifyObservers();
    }
    
    @Override
    public void newGame() {
        challenge.newGame();
        sendNotification();
    }

    @Override
    public void startGame(Challenge.Categories category, Challenge challengeMode) {
        challenge.startGame(category, challengeMode);
        sendNotification();
    }

    @Override
    public void nextAnswer(int answer) {
        challenge.nextAnswer(answer);
        sendNotification();
    }

    @Override
    public void quitGame() {
        challenge.quitGame();
        sendNotification();
    }

    @Override
    public void end() {
        challenge.end();
        sendNotification();
    }
    
    /**
     * Sets current profile for challenge.
     * There's no need for notify observers here.
     * @param player 
     */
    public void setCurrentProfile(Player player) {
        challenge.setCurrentProfile(player);
        sendNotification();
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public Player getCurrentPlayer() {
        return challenge.getCurrentProfile();
    }

    public void backPressed() {
        setChallenge(null);
    }
}
