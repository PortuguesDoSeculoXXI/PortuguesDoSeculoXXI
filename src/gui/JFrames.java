package gui;


public class JFrames {
    //Attributes
    private Controller controller = null;
    private FrameMain framemain   = null;
    
    
    
    // ########### Builder ###################
    public JFrames(){
        this.controller = new Controller();
    }
    // ########### Builder ###################
    
    //############ Methods ###################
    public void createFrames(){
        this.framemain = new FrameMain(this.controller);
    }
    
    
    
    
    //############ Methods ###################
}
