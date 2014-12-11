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
        challenge.setStartTime(System.currentTimeMillis());
        
        if (challenge.getController().hasDataController()) {
            // List of questions based on selected categories
            List<Question> list = challenge.getController().getQuestionsByCategory(categoryList);
            challenge.setQuestionsList(list);
        }
        
        return new WaitAnswer(challenge);
    }
}
