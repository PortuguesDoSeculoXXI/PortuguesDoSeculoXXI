package states;

import logic.Answer;
import logic.Challenge;
import logic.ChallengeHard;
import logic.Level;
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
    public IState nextAnswer(Answer answer) {
        // If user got the answer right increment number of qestions right
        if (challenge.getCurrentQuestion().getAnswer() == answer) {
            challenge.incrementNumberOfQuestionsRight();
            challenge.madeCorrectAnswer();
        }
        else {
            challenge.madeIncorrectAnswer();
        }
        
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
        challenge.setScore(calculateMedals(challenge.getNumberOfQuestionsRight()));
        // Stop counting time
        long timeEnd = System.currentTimeMillis();
        long timeDelta = timeEnd - challenge.getStartTime();
        
        challenge.getScore().setDuration(new Double((long) (timeDelta / 1000.0)).intValue());
                        
        Level level = challenge instanceof ChallengeHard ? Level.MODE_HARD : Level.MODE_EASY;
        
        challenge.getController().insertChallengeScore(challenge.getCurrentProfile(), level, challenge.getScore());
        
        return new WaitScore(challenge);
    }

    @Override
    public IState quitGame() {
        // Returns to state WaitConfiguration with a new challenge without saving any data.
        return new WaitConfiguration(challenge);
    }

    /**
     * Calculate the score according to the number of questions the user got right.
     * @param numberOfQuestionsRight
     * @return Score with the respective result.
     */
    private Score calculateMedals(int numberOfQuestionsRight) {
        Score challengeScore;
        
        // Calculate final score
        if (numberOfQuestionsRight == 15) {
            challengeScore = new Score(0, 0); // Gold, Silver, Bronze
            challengeScore.setGold(1);
            challengeScore.setSilver(1);
            challengeScore.setBronze(1);
            challengeScore.setRightAnswers(numberOfQuestionsRight);
            return challengeScore;
        }
        else if (numberOfQuestionsRight <= 14 && numberOfQuestionsRight >= 11) {
            challengeScore = new Score(0, 0); // Silver, Bronze
            challengeScore.setGold(0);
            challengeScore.setSilver(1);
            challengeScore.setBronze(1);
            challengeScore.setRightAnswers(numberOfQuestionsRight);
            return challengeScore;
        }
        else if (numberOfQuestionsRight <= 10 && numberOfQuestionsRight >= 8) {
            challengeScore = new Score(0, 0); // Bronze
            challengeScore.setGold(0);
            challengeScore.setSilver(0);
            challengeScore.setBronze(1);
            challengeScore.setRightAnswers(numberOfQuestionsRight);
            return challengeScore;
        }
        else {
            challengeScore = new Score(0, 0); // No medals
            challengeScore.setRightAnswers(numberOfQuestionsRight);
            return challengeScore;
        }
    }
}
