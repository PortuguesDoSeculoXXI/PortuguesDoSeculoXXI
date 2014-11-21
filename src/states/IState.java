package states;

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
    public IState newGame();
    public IState startGame(Challenge.Categories category, Challenge challengeMode);
    public IState quitGame();
    public IState nextAnswer(int answer);
    public IState end();
}
