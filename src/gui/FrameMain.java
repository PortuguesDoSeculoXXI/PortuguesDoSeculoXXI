package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrameMain extends JFrame implements Observer {
    //Atributos
    private Controller controller;
    private Container mainContainer;
    private JTextField textname;
    private JPanel jpanel;
    private JPanel jpanelcenter;
    private JPanel jpanelWest;
    private JPanel jpaneleast;
    private JPanel jpanelsouth ;
    private JButton play = null;
    private JButton change = null;
    private JButton score = null;
    private JButton exit = null;
    private String Simbolos [] = {"Novo","User1","User2","User3","Novo Utilizador"}; 
    private JComboBox jc = null;
    private JLabel click = null;
    private String novo = null;
    private boolean flag = true; 
   // private JDesktopPane desk = null;
      
    
    public FrameMain(Controller controller) {
        this(controller, 350, 75, 600, 500);
    }
    
    public FrameMain(Controller controller, int x, int y, int width, int height) {
        // Cabeçalho
        super("Português do Século XXI");
        this.controller = controller;
      
        init();
        this.registerListeners();
        validate();
        
        
        setLocation(x,y);
        setSize(width, height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // READY
        controller.notifyObservers();
    }
   
    //############ Methods ###################
    protected void init() {
        // Init components/panels
        this.mainContainer = getContentPane();
        this.mainContainer.setLayout(new BorderLayout());
        this.mainContainer.validate();
        
        // Layout
        createLayout();

        // Observers
        this.controller.addObserver(this);// adicionar esta vista ao  controler
       
        // Listeners
        registerListeners();
    }
    
    //JPanelNort
    private void jPanelNorth(){
        this.jpanel = new JPanel(new FlowLayout());
        JPanel jp = new JPanel(new GridLayout(3,1));
        jp.add(new JLabel(" "));
        JLabel jb = new JLabel("   Português");
        jb.setFont(jb.getFont().deriveFont(64f));
        
        jp.add(jb);
        //
        //this.jpanel.add(jb);
        
        JLabel jb2 = new JLabel("do Século XXI");
        jb2.setFont(jb2.getFont().deriveFont(64f));
        jp.add(jb2);
        //
        this.jpanel.add(jp);
        //this.jpanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, WIDTH));
        //add Atributos
        this.mainContainer.add(this.jpanel,BorderLayout.NORTH);
    } 
    
    //JPanelCenter
    private void jPanelCenter(){
        this.jpanelcenter = new JPanel(new GridLayout(10,1));
        //this.jpanelcenter.setBorder(BorderFactory.createLineBorder(Color.RED, WIDTH));
        // this.textname = new JTextField("Perfil do utilizador");
        
        this.jc = new JComboBox(this.Simbolos);
        JPanel jp = new JPanel(new GridLayout(1,6));
        jp.add(new JLabel(" "));
        jp.add(new JLabel("Perfil do utilizador"));
        jp.add(this.jc);
        
       
        
        this.click = new JLabel("Editar");
        this.click.setBackground(Color.RED);
        this.click.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogProfile();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
               // dialogProfile();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            
            
            
        });
        
        jp.add(this.click);
        this.jpanelcenter.add(jp);
       
        this.jpanelcenter.add(new JLabel(" "));
        JPanel jp1 = new JPanel(new GridLayout(1,6));
        jp1.add(new JLabel(" "));
        jp1.add(new JLabel(" "));
        this.play  = new JButton("Jogar");
        jp1.add(this.play,BorderLayout.CENTER);
        jp1.add(new JLabel(" "));
        jp1.add(new JLabel(" "));
        
        this.jpanelcenter.add(jp1);
        
        JPanel jp2 = new JPanel(new GridLayout(1,6));
        jp2.add(new JLabel(" "));
        jp2.add(new JLabel(" "));
        this.score  = new JButton("Pontuacoes");
        jp2.add(this.score,BorderLayout.CENTER);
        jp2.add(new JLabel(" "));
        jp2.add(new JLabel(" "));
        
        this.jpanelcenter.add(new JLabel(" "));
        this.jpanelcenter.add(jp2);
        
        JPanel jp3 = new JPanel(new GridLayout(1,6));
        jp3.add(new JLabel(" "));
        jp3.add(new JLabel(" "));
        this.exit  = new JButton("Sair");
        jp3.add(this.exit,BorderLayout.CENTER);
        jp3.add(new JLabel(" "));
        jp3.add(new JLabel(" "));
        
        this.jpanelcenter.add(new JLabel(" "));
        this.jpanelcenter.add(jp3);
       
        //centro
        this.mainContainer.add(this.jpanelcenter,BorderLayout.CENTER);
    } 
    
    protected void createLayout(){
        //
        this.jPanelNorth();
        this.jPanelCenter();
        this.jPanelSouth();
        this.jPanelWest();
        this.jPanelEast();
    }
    
    //JPanelSouth
    private void jPanelSouth(){
        //south
        this.jpanelsouth = new JPanel(new FlowLayout());
         this.mainContainer.add(this.jpanelsouth,BorderLayout.SOUTH);
    }
    
    //JPanelWest
    private void jPanelWest(){
        //weast 
        this.jpanelWest = new JPanel(new FlowLayout());
        this.mainContainer.add(this.jpanelWest,BorderLayout.WEST);
    }

    //JPanelEast
    private void jPanelEast(){
         //East
        this.jpaneleast = new JPanel(new FlowLayout());
        this.mainContainer.add(this.jpaneleast,BorderLayout.EAST);
    }
    
    
    private void registerListeners() {
        //Exit
        this.exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.gc();
                System.exit(0);
            }
        });
       
//        this.jc.addActListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(e.getActionCommand()== ActionEvent. ){
//                if(jc.getSelectedIndex()== jc.getItemCount()-1){
//                    ddddd();
//                }
//            }
//        });

        //TODO - Ricardo esta a duplicar duas vezes esta acc
        this.jc.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1 && ( (( String )e.getItem()).equals("Novo Utilizador"))){
                     if(jc.getSelectedIndex()== jc.getItemCount()-1){
                        ddddd();
                     }
                }
            }
        });
}
    
    private void ddddd(){
     
        JPanel jp = new JPanel(new GridLayout(2,2));
        System.out.println(novo);
                  

        jp.add(new JLabel("Nome"));

        JTextField tx = new JTextField();
        jp.add(tx); 
        String options[] = {"Cancelar","Gravar"};
        int value = JOptionPane.showOptionDialog(null, jp, "Novo Perfil de Utilizador", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);  

        if(value == 0){// ok
            //System.exit(0);
        }
        else{//cancel

        }
            
    }
  
    //Create Profile
    private void dialogProfile(){
        JPanel jp = new JPanel(new GridLayout(2,6));
        // jp.add(new JLabel(" "));
        
        
        JTextField tx = new JTextField();
        jp.add(tx); 
        jp.add(new JLabel(" "));
        String options[] = {"Cancelar","Apagar","Alterar"};
        int value = JOptionPane.showOptionDialog(null, jp, "Alteracao de Perfil", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);  

    }
    
    @Override
    public void update(Observable t, Object o) {
       
    }
    
   
   //############ Methods ###################
}
