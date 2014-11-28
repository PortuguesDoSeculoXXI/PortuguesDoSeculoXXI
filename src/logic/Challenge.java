package logic;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;
import states.IState;

/**
 * Challenge.
 * Each challenge the user play is represented by this class.
 * The interaction with the state machine is also made here.
 * Interface should use this class 
 * 
 * @author PTXXI
 */
public class Challenge extends Observable implements ChallengeInterface {
    /**
     * Current state of the state machine.
     */
    protected IState currentState;
    /**
     * List of all questions associated with this challenge.
     * This array list is filled when the challenge is created, with 15 Question objects.
     */
    protected ArrayList<Question> questionsList = new ArrayList<>(15);
    /**
     * Current question being asked to the user.
     */
    protected Question currentQuestion;
    /**
     * Represents current question number.
     * This is only to facilitate the determination of last question without 
     * having to compare which question is currentQuestion in the array list.
     * Has to be incremented whenever the user moves to next question.
     */
    protected int currentQuestionNumber = 1;
    /**
     * This variable saves the number of questions the user got right until now.
     */
    protected int numberOfQuestionsRight = 0;
    /**
     * All possible categories and the random mode.
     */
    public enum Categories { 
        CONSOANTS, 
        GRAPHIC_ACCENTS, 
        HYPHEN, 
        UPPERCASE_AND_LOWERCASE, 
        RANDOM
    }
    /**
     * Current profile selected to play the challenge.
     */
    private Player currentProfile;
    /**
     * The score of this challenge.
     */
    private Score score;
    /**
     * This variable has the time that took to complete the challenge.
     * this should be instantiated when the challenge starts.
     */
    long timer;

    /**
     * Challenge Constructor.
     * @param currentProfile Sets current profile for this challenge.
     */
    public Challenge(Player currentProfile) {
        this.currentProfile = currentProfile;
    }

    
    //  ______________________________________________________________________
    // |                                                                      |
    // |                        Getters and Setters                           |
    // |______________________________________________________________________|
    //
    public IState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public ArrayList<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void nextQuestion() {
        this.currentQuestion = questionsList.get((++currentQuestionNumber) - 1);
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public Player getCurrentProfile() {
        return currentProfile;
    }

    public void setCurrentProfile(Player currentProfile) {
        this.currentProfile = currentProfile;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public int getNumberOfQuestionsRight() {
        return numberOfQuestionsRight;
    }

    public void incrementNumberOfQuestionsRight() {
        this.numberOfQuestionsRight++;
    }
    
    
    //  ______________________________________________________________________
    // |                                                                      |
    // |                    ChallengeInterface methods                        |
    // |______________________________________________________________________|
    //
    
    @Override
    public void newGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void startGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nextAnswer(int answer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void quitGame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void end() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
