package logic;

/**
 * Category.
 * This class represents the categories
 * of questions in the game.
 * 
 * @author PTXXI
 */
public class Category {
    
    private final int id;
    private final String name;

    public Category(int id, String name) {
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
