package logic;

/**
 * Score.
 * This class represents the final score
 * of the played games.
 *
 * @author PTXXI
 */
public class Score {
    private final int gold;
    private final int silver;
    private final int bronze;

    public Score(int gold, int silver, int bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public int getGold() {
        return gold;
    }

    public int getSilver() {
        return silver;
    }

    public int getBronze() {
        return bronze;
    }
}
