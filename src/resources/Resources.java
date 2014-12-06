package resources;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Resources {
    
    /**
     * Get resource file.
     * Opens file with path relative to location of the Resources class.
     * 
     * @param name
     * @return url of resource.
     */
    public static final URL getResourceFile(String name) {
        URL url = Resources.class.getResource(name);
        return url;
    }
    
    public static final Color getLogoColor() {
        return new Color(225, 97, 94);
    }
    
    public static final ImageIcon getLogo() {
        return new ImageIcon("src/resources/logo.png");
    }
    
    public static final ImageIcon getImageCorrect() {
        return new ImageIcon("src/resources/correct.png");
    }
    
    public static final ImageIcon getImageIncorrect() {
        return new ImageIcon("src/resources/incorrect.png");
    }
    
    public static final ImageIcon getImageMedalGold() {
        return new ImageIcon("src/resources/Gold.png");
    }
    
    public static final ImageIcon getImageMedalSilver() {
        return new ImageIcon("src/resources/Silver.png");
    }
    
    public static final ImageIcon getImageMedalBronze() {
        return new ImageIcon("src/resources/Bronze.png");
    }
    
    public static final ImageIcon getImageResized(ImageIcon imageIcon, int width, int height) {
        Image img = imageIcon.getImage();
        Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        
        return new ImageIcon(newimg);
    }
}
