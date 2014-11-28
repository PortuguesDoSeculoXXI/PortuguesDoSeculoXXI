/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

/**
 *
 * @author PTXXI
 */
public class JframeChoosingWindow extends JFrame implements Observer {
    private Controller controller;
    private Container mainContainer;
    private JTextField textname;
    private JPanel jpanel;
    private JPanel jpanelcenter;
    private JPanel jpanelWest;
    private JPanel jpaneleast;
    private JPanel jpanelsouth ;
    private JButton exit = null;
    private JButton consonants = null;
    private JButton accents = null;
    private JButton hyphen  = null;
    private JButton Casesensitive  = null;
    private JButton random = null;
   
    //temp
    private String symbols [] = {"Dificuldade"," 1","2"," 3"," 4"};
    private String symbols2 [] = {"Nome","1","2","3","4"}; 
    //temp
    private JComboBox jc = null;
    private JComboBox jc2 = null;
    private JLabel click = null;
    // private JDesktopPane desk = null;
   
    public JframeChoosingWindow(Controller controller){
               this(controller, 350, 75, 600, 500);
    }
    
    public JframeChoosingWindow(Controller controller, int x, int y, int width, int height) {
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
        this.jpanel = new JPanel(new GridLayout(1,2));
       // this.jpanel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, WIDTH));
       //this.jpanel.setPreferredSize(new Dimension(250,100));
        JPanel jp = new JPanel(new GridLayout(3,1));
        
        //jp.add(new JLabel("Perfil atual"));  
        //jp.add(new JLabel("Nome"));//name player
        
        this.click = new JLabel("Perfil atual User1 alterar");
       // this.click.setBackground(Color.RED);
        this.click.addMouseListener(new MouseListener(){
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
        
       
        JLabel jb = new JLabel("         Português");
        jb.setFont(jb.getFont().deriveFont(64f));
 
        this.jpanel.add(jb);
 
        JLabel jb2 = new JLabel("      do Século XXI");
        jb2.setFont(jb2.getFont().deriveFont(64f));
     
        jp.add(jb);
        jp.add(jb2);
        this.jpanel.add(jp);
        this.mainContainer.add(this.jpanel,BorderLayout.NORTH);
    } 
    
    //JPanelCenter
    private void jPanelCenter(){
        this.jpanelcenter = new JPanel(new GridLayout(10,1));
       
        this.jc = new JComboBox(this.symbols);
        JPanel jp = new JPanel(new GridLayout(1,6));
        jp.add(new JLabel(" "));
        jp.add(new JLabel("     Dificuldade"));
        jp.add(this.jc);
        jp.add(new JLabel(" ")); 
       
        this.jpanelcenter.add(jp);
       
        this.jpanelcenter.add(new JLabel(" "));
        JPanel jp1 = new JPanel(new GridLayout(1,6));
        jp1.add(new JLabel(" "));
        this.consonants = new JButton("Consoantes");
        jp1.add(this.consonants);
        jp1.add(new JLabel(" "));
        this.accents = new JButton("Acentos");
        jp1.add(this.accents);
        jp1.add(new JLabel(" "));
        this.jpanelcenter.add(jp1);
        
        JPanel jp2 = new JPanel(new GridLayout(1,3));
        jp2.add(new JLabel(" "));
        this.hyphen  = new JButton("Hífen");
        jp2.add(this.hyphen,BorderLayout.CENTER);
        jp2.add(new JLabel(" "));
        this.Casesensitive = new JButton("Maiúsculas E Minúsculas");
        jp2.add(this.Casesensitive);
        jp2.add(new JLabel(" "));
        this.jpanelcenter.add(new JLabel(" "));
        this.jpanelcenter.add(jp2);
//        
        JPanel jp3 = new JPanel(new GridLayout(1,6));
        jp3.add(new JLabel(" "));
        jp3.add(new JLabel(" "));
        this.random = new JButton("Aleatorio");
        jp3.add(this.random,BorderLayout.CENTER);
        jp3.add(new JLabel(" "));
        jp3.add(new JLabel(" "));
        
        this.jpanelcenter.add(new JLabel(" "));
        this.jpanelcenter.add(jp3);
//       
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
        this.jpanelWest = new JPanel(new GridLayout(10,1));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.jpanelWest.add(new JLabel("      "));
        this.exit = new JButton("");
        JLabel voltar = new JLabel("Voltar");
        this.jpanelWest.add(voltar);
        this.click = new JLabel("Alterar");
      
        voltar.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                //insertCode
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
    }
    
    //Create Profile
    private void dialogProfile(){
        JPanel jp = new JPanel(new GridLayout(2,6));
        // jp.add(new JLabel(" "));
        jp.add(new JLabel("EScolha de perfil"));
        
      
       this.jc2 = new JComboBox(this.symbols2);
       jp.add(this.jc2);
       String options[] = {"Alterar","Cancelar"};
       int value = JOptionPane.showOptionDialog(null, jp, "ALteracao de Perfil", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options , options[0]);
      
      
    }
    
    @Override
    public void update(Observable t, Object o) {
       
    }
   //############ Methods ###################
}
