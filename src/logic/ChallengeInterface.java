package logic;

/**
 * Challenge Interface.
 * Describes the interface for model classes.
 * 
 * @author PTXXI
 */
public interface ChallengeInterface {
    public void newGame();
    public void startGame();
    public void nextAnswer(int answer);
    public void quitGame();
    public void end();
}
