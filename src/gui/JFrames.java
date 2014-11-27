package gui;

/**
 * 
 * @author PTXXI
 */
public class JFrames {
    //Attributes
    private Controller controller = null;
    private FrameMain framemain   = null;
    private JframeChoosingWindow ChoosingWindow = null;
    
    
    
    // ########### Builder ###################
    public JFrames(){
        this.controller = new Controller();
    }
    // ########### Builder ###################
    
    //############ Methods ###################
    public void createFrames(){
        this.framemain = new FrameMain(this.controller);
        this.ChoosingWindow = new JframeChoosingWindow(this.controller);
        
    }
    
    
    
    
    //############ Methods ###################
}
