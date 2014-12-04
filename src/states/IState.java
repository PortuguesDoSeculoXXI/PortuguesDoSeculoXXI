package states;

import java.util.List;
import logic.Challenge;

/**
 * IState = InterfaceState
 * This interface contains all methods used in the state machine.
 * This methods should only be implemented if needed on each state.
 * All javadoc is declared on the State Adapter, because that is going to be
 * the class that every state will extend.
 * 
 * @author danilo
 */
public interface IState {
    /**
     * Start a new challenge.
     * @return WaitConfiguration
     */
    public IState newGame();
    
    /**
     * After challenge configurations are set, to start game use this method.
     * @param categoryList list of categories.
     * @param challengeMode this may be ChallengeEasy or ChallengeHard.
     * @return WaitAnswer if configurations are correct. Returns <b>this</b> if there's invalid configurations.
     */
    public IState startGame(List<Challenge.Categories> categoryList, Challenge challengeMode);
    
    /**
     * If user wants to quit challenge this method should be called.
     * No data is saved if this method is called.
     * @return WaitConfiguration
     */
    public IState quitGame();
    
    /**
     * Move to next answer.
     * @param answer Represents the answer chosen by the user.
     * @return <b>this</b> if challenge isn't over. WaitScore if challenge is over.
     */
    public IState nextAnswer(int answer);
    
    /**
     * This action represents the end of a challenge by answering all questions.
     * Challenge data is stored.
     * @return WaitScore
     */
    public IState end();
}
