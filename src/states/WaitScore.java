package states;

import logic.Challenge;
import logic.database.Controller;

/**
 * Wait Score.
 * When the score board is shown, after a challenge being completed.
 * 
 * @author PTXXI
 */
public class WaitScore extends StateAdapter {
    /**
     * WaitScore Constructor.
     * @param challenge Sets challenge variable to super StateAdapter
     */
    public WaitScore(Challenge challenge) {
        super(challenge);
    }
    
    /**
     * To restart a new challenge call this method.
     * @return nextState
     */
    @Override
    public IState newGame() {
        return new WaitConfiguration(challenge);
    }
}
