package states;

import java.util.List;
import logic.Challenge;

/**
 * Wait Configurations.
 * This state represents when the user is asked to select the challenge 
 * configurations.
 * 
 * @author PTXXI
 */
public class WaitConfiguration extends StateAdapter {
    /**
     * WaitAnswer Constructor.
     * @param challenge Sets challenge variable to super StateAdapter
     */
    public WaitConfiguration(Challenge challenge) {
        super(challenge);
    }

    @Override
    public IState startGame(List<Challenge.Categories> categoryList, Challenge challengeMode) {
        // RP: rever este ponto!
        // Set challenge mode using polimorphism
        //challenge = challengeMode; 

        // When game starts set current time in milliseconds
        challenge.setTimer(System.currentTimeMillis());
        
        // TO DO - fetch questions from database and fill questionsList in Challenge
        // challenge.
        
        return new WaitAnswer(challenge);
    }
}
