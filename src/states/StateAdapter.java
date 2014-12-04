package states;

import java.util.List;
import logic.Challenge;

/**
 * State Adapter.
 * This class implements all possible state actions.
 * Allowing states to only implement desired methods.
 * 
 * @author PTXXI
 */
public abstract class StateAdapter implements IState {
    /**
     * challenge variable represents the current challenge being played.
     */
    protected Challenge challenge;
    
    /**
     * StateAdapter Constructor.
     * StateAdapter should only be created in the context of a state that
     * extends from this class, therefor its constructor being protected.
     * @param challenge 
     */
    protected StateAdapter(Challenge challenge) {
        this.challenge = challenge;
    }
    
    /**
     * Start a new challenge.
     * @return WaitConfiguration
     */
    @Override
    public IState newGame() {
        return this;
    }

    /**
     * After challenge configurations are set, to start game use this method.
     * @param categoryList List of categories.
     * @param challengeMode this may be ChallengeEasy or ChallengeHard.
     * @return WaitAnswer if configurations are correct. Returns <b>this</b> if there's invalid configurations.
     */
    @Override
    public IState startGame(List<Challenge.Categories> categoryList, Challenge challengeMode) {
        return this;
    }

    /**
     * If user wants to quit challenge this method should be called.
     * No data is saved if this method is called.
     * @return WaitConfiguration
     */
    @Override
    public IState quitGame() {
        return this;
    }

    /**
     * Move to next answer.
     * @param answer Represents the answer chosen by the user.
     * @return <b>this</b> if challenge isn't over. WaitScore if challenge is over.
     */
    @Override
    public IState nextAnswer(int answer) {
        return this;
    }

    /**
     * This action represents the end of a challenge by answering all questions.
     * Challenge data is stored.
     * @return WaitScore
     */
    @Override
    public IState end() {
        return this;
    }
}
