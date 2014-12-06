package logic;

import java.util.Date;
import logic.database.Controller;

/**
 * Challenge Hard.
 * Represent the game mode.
 * 
 * @author PTXXI
 */
public class ChallengeHard extends Challenge {
    
    private Date answerEndTime;

    public ChallengeHard(Controller controller, Player currentProfile) {
        super(controller, currentProfile);
    }
    
    @Override
    public void nextAnswer(Answer answer) {
        
        super.nextAnswer(answer);
    }
    
}
