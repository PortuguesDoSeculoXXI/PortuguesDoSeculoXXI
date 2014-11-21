package states;

import logic.Challenge;
import logic.Score;

/**
 * Wait Answer.
 * This state waits for the user to choose an answer.
 * 
 * @author PTXXI
 */
public class WaitAnswer extends StateAdapter {
    /**
     * WaitAnswer Constructor.
     * @param challenge Sets challenge variable to super StateAdapter
     */
    public WaitAnswer(Challenge challenge) {
        super(challenge);
    }

    @Override
    public IState nextAnswer(int answer) {
        // If user got the answer right increment number of qestions right
        if (challenge.getCurrentQuestion().getAnswer() == answer)
            challenge.incrementNumberOfQuestionsRight();
        
        // If it's not the last question, move to next question and return this state
        if (challenge.getCurrentQuestionNumber() < challenge.getQuestionsList().size()) {
            challenge.nextQuestion();
            return this;
        }
        // if it's last then return WaitScore
        else
            return end();
    }
    
    @Override
    public IState end() {
        // Set score
        challenge.setScore(calculateScore(challenge.getNumberOfQuestionsRight()));
        // Stop counting time
        long timeEnd = System.currentTimeMillis();
        long timeDelta = timeEnd - challenge.getTimer();
        challenge.setTimer((long) (timeDelta / 1000.0)); // CONFIRMAR SE ISTO NAO PERDE VALORES
        
        // TO DO - Save data to database HERE
        
        return new WaitScore(challenge);
    }

    @Override
    public IState quitGame() {
        // Returns to state WaitConfiguration with a new challenge without saving any data.
        return new WaitConfiguration(new Challenge(challenge.getCurrentProfile()));
    }

    /**
     * Calculate the score according to the number of questions the user got right.
     * @param numberOfQuestionsRight
     * @return Score with the respective result.
     */
    private Score calculateScore(int numberOfQuestionsRight) {
        if (numberOfQuestionsRight == 15)
            return new Score(1, 1, 1); // Gold, Silver, Bronze
        else if (numberOfQuestionsRight <= 14 && numberOfQuestionsRight >= 11)
            return new Score(0, 1, 1); // Silver, Bronze
        else if (numberOfQuestionsRight <= 10 && numberOfQuestionsRight >= 8)
            return new Score(0, 0, 1); // Bronze
        else
            return new Score(0, 0, 0); // No medals
    }
}
