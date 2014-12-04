package gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import logic.ChallengeModel;
import logic.database.Controller;
import states.WaitConfiguration;

/**
 *
 * @author PTXXI
 */ 

public final class JFrameChoosingWindow extends JFrame implements Observer {

    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    private JTextField textname;
    private JPanel jpanelcenter;
    private JPanel jpanelNorth;
    private JPanel jpanelsouth;
    private JPanel jpanelWest;
    private JPanel jpaneleast;
    private final Checkbox consoants = new Checkbox("Consoantes");
    private final Checkbox accents = new Checkbox("Acentos");
    private final Checkbox hyphen = new Checkbox("Hífen"); 
    private final Checkbox caseSensitive = new Checkbox("Maiúsculas e Minúsculas");
    private final Checkbox random = new Checkbox("Aleatório");
    private final JLabel currentProfile = new JLabel("");
    private final JLabel changeCurrentProfile = new JLabel("Mudar");
    private final String symbols[] = {"Fácil (Sem tempo)", " Difícil (Com tempo)"};
    private final JButton play = new JButton("Jogar");
    private final JRadioButton easy  = new JRadioButton("Fácil (Sem tempo)");
    private final JRadioButton hard = new JRadioButton(" Difícil (Com tempo)");
    private JComboBox jc;
    private HashMap<Integer,String> listcategories;
 
    public JFrameChoosingWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 550);
    }
    

    public JFrameChoosingWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;

        init();

        setLocation(x, y);
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void init() {
        // Init components/panels
        this.mainContainer = getContentPane();
        this.mainContainer.setLayout(new BorderLayout());
        this.mainContainer.validate();

        // Layout
        createLayout();

        // Observers
        this.controller.addObserver(this);
        this.challengeModel.addObserver(this);

        // Listeners
        registerListeners();
    }

    protected void createLayout() {
        this.jPanelNorth();
        this.jPanelCenter();
        this.jPanelSouth();
    }

    //JPanelNort 
    private void jPanelNorth() {
        jpanelNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));

        Box verticalBox = Box.createVerticalBox();
        JPanel horizontalFlow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        horizontalFlow.setPreferredSize(new Dimension(600, 30));
        horizontalFlow.add(new JLabel("Perfil atual: "));

        horizontalFlow.add(currentProfile);

        changeCurrentProfile.setText("<HTML><U>Mudar</U></HTML>");
        changeCurrentProfile.setForeground(Color.BLUE);
        changeCurrentProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        horizontalFlow.add(changeCurrentProfile);

        verticalBox.add(horizontalFlow);

        verticalBox.add(Box.createVerticalStrut(40));

        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel jb = new JLabel("<HTML><CENTER>Português<BR>do Século XXI</CENTER><HTML>");
        jb.setFont(jb.getFont().deriveFont(64f));

        jp.add(jb);

        verticalBox.add(jp);

        jpanelNorth.add(verticalBox);

        this.mainContainer.add(jpanelNorth, BorderLayout.NORTH);
    }

    //JPanelCenter
    private void jPanelCenter() {
        jpanelcenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        jc = new JComboBox(symbols);
        jc.setMaximumSize(new Dimension(170, Integer.MAX_VALUE));
        
        Box verticalBox = Box.createVerticalBox();
        Box horizontalBox = Box.createHorizontalBox();
        
        verticalBox.setPreferredSize(new Dimension(500, 200));
        //horizontalBox.setPreferredSize(new Dimension(600, 400));
        
        horizontalBox.add(new JLabel("Dificuldade: "));
        horizontalBox.add(this.easy);
        horizontalBox.add(this.hard);
        verticalBox.add(horizontalBox);
        
        horizontalBox = Box.createHorizontalBox();
        
        verticalBox.add(Box.createVerticalStrut(10));
      
        consoants.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        verticalBox.add(this.consoants);
        accents.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        verticalBox.add(this.accents);
        
        hyphen.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        verticalBox.add(this.hyphen);
        caseSensitive.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        caseSensitive.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));

        verticalBox.add(this.caseSensitive);

        random.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        //random.setAlignmentX(CENTER_ALIGNMENT);
        verticalBox.add(random);
        verticalBox.add(this.play);
        play.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        play.setAlignmentX(CENTER_ALIGNMENT);
        
        jpanelcenter.add(verticalBox);
       
        this.mainContainer.add(this.jpanelcenter, BorderLayout.CENTER);
    }

    //JPanelSouth
    private void jPanelSouth() {
      

        JLabel backLabel = new JLabel("<HTML><U>Voltar</U><HTML>");
        JLabel playlabel = new JLabel("<HTML><U>Jogar</U><HTML>");
        
        backLabel.setForeground(Color.BLUE);
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                challengeModel.backPressed();
            }
        });

        playlabel.setForeground(Color.BLUE);
        playlabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        playlabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //play game
            }
        
        });
        jpanelsouth = new JPanel(new FlowLayout (FlowLayout.LEFT));
        jpanelsouth.add(backLabel);
        jpanelWest = new JPanel(new BorderLayout());
        jpanelWest.add(playlabel,BorderLayout.SOUTH);
        this.jpaneleast = new JPanel(new BorderLayout());
        jpaneleast.add(backLabel,BorderLayout.SOUTH);
        this.mainContainer.add(this.jpaneleast, BorderLayout.WEST);
        //this.mainContainer.add(this.jpanelWest, BorderLayout.EAST);
    }

    private void registerListeners(){
        changeCurrentProfile.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogProfile();
            }
        });
        
        this.play.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                 if(checkMode()== true){
                     checkCategories();
                 }
                 else{
                     dialogGameMode();
                 }
//                challengeModel.setChallenge(new Challenge(controller.getProfileOf((String)jc.getSelectedItem())));
//                challengeModel.newGame();
            }
        });
        
        
    }

    /**
     * 
     * check categories 
     */
    private void checkCategories(){
        this.listcategories = new HashMap<Integer,String>();

        if(this.consoants.getState()== true){
            this.listcategories.put(1,"consoants");
        }
        else if(this.accents.getState()== true){
            this.listcategories.put(2,"accents");
        }
        else if(this.hyphen.getState()== true){
            this.listcategories.put(3,"hyphen");
        }
        else if(this.caseSensitive.getState()== true){
            this.listcategories.put(4,"caseSensitive");
        }
        else if(this.random.getState()== true){
            this.listcategories.put(5,"random");
        }
        
        //Depois aqui pode fazer start game 
        
    } 
    /*
        verify that you have chosen a game mode
    */
    private boolean checkMode(){
        if(this.easy.isSelected()== true){
            return true;//easy mode
        }
        else if (this.hard.isSelected()== true){
            return true;
        }
        else{
            return false;
        }
    } 
    
    /**
     * choose a game mode
     */
    private void dialogGameMode(){
        JPanel jp = new JPanel(new GridLayout(2, 6));
        // jp.add(new JLabel(" "));
        jp.add(new JLabel("Atencao tem que escolher um modo de jogo"));

        String options[] = {"Continuar"};
        int value = JOptionPane.showOptionDialog(null, jp, "", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

    }
   
    //Create Profile
    private void dialogProfile() {
        JPanel jp = new JPanel(new GridLayout(2, 6));
        // jp.add(new JLabel(" "));
        jp.add(new JLabel("Escolha de perfil:"));

        JComboBox jc2 = new JComboBox();
        
        jc2.removeAllItems();
        for (String aux : controller.getPlayersAsStrings()) {
            jc2.addItem(aux);
        }
        
        jc2.setSelectedItem(challengeModel.getCurrentPlayer().getName());
        
        jp.add(jc2);
        
        String options[] = {"Cancelar", "Alterar"};
        int value = JOptionPane.showOptionDialog(null, jp, "ALteracao de Perfil", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (value == 1) { // Change Current Profile
            challengeModel.setCurrentProfile(controller.getProfileOf((String)jc2.getSelectedItem()));
        }
    }

    @Override
    public void update(Observable t, Object o) {
        if (challengeModel.getChallenge() != null) {
            if (challengeModel.getChallenge().getCurrentState() instanceof WaitConfiguration) {
                currentProfile.setText("<HTML><B>" + challengeModel.getCurrentPlayer().getName() + "</B></HTML>");
            }
        }
    }    
}