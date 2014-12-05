package logic;

import java.util.List;

/**
 * Challenge Interface.
 * Describes the interface for model classes.
 * 
 * @author PTXXI
 */
public interface ChallengeInterface {
    public void newGame();
    public void startGame(List<Challenge.Categories> categoryList);
    public void nextAnswer(int answer);
    public void quitGame();
    public void end();
}
