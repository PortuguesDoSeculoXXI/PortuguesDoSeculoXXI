package logic;

import java.util.Date;

/**
 * Score.
 * This class represents the final score
 * of the played games.
 *
 * @author PTXXI
 */
public class Score {

    private final int idPlayer;
    private final float score;
    private Date dateTime;
    private int duration; //Segundos
    private Level level;
    private int rightAnswers;
    private int gold;
    private int silver;
    private int bronze;

    public Score(int idPlayer, float score) {
        this.idPlayer = idPlayer;
        this.score = score;
    }

    public Score(int idPlayer, int score, Date dateTime, int duration, Level level, int gold, int silver, int bronze) {
        this.idPlayer = idPlayer;
        this.score = score;
        this.dateTime = dateTime;
        this.duration = duration;
        this.level = level;
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public int getIdPlayer() {
        return idPlayer;
    }
    
    public int getScore() {
        return (int) score;
    }

    public float getFloatScore() {
        return score;
    }

    public int getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(int rightAnswers) {
        this.rightAnswers = rightAnswers;
    }
    
    public int getScorePoints() {
        return new Double((50 / duration + rightAnswers) * 100).intValue();
    }
    
}
