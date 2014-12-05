package states;

import java.util.List;
import logic.Challenge;
import logic.Question;

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
    public IState startGame(List<Challenge.Categories> categoryList) {
        // When game starts set current time in milliseconds
        challenge.setTimer(System.currentTimeMillis());
        
        List<Question> list = challenge.getController().getDataController().getQuestionsByCategory(categoryList, 15);
        
        challenge.setQuestionsList(list);
        
        return new WaitAnswer(challenge);
    }
}
