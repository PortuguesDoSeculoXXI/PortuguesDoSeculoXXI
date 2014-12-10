package logic;

/**
 * Player.
 * This class represents the profile of user
 * that will be add on the game.
 * 
 * @author PTXXI
 */
public class Player {
    
    private final int id;
    private final String name;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
