package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logic.Answer;
import logic.Challenge;
import logic.ChallengeModel;
import logic.database.Controller;

/**
 * Game Window.
 * Game mode.
 * 
 * @author PTXXI
 */
public class GameModeWindow extends JFrame implements Observer{
    
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JLabel labelProfile;
    private JLabel labelTime;
    private JLabel labelQuestion;
    private JLabel labelGiveUp = new JLabel("Give Up");
    private JButton buttonOptionA = new JButton("Option1");
    private JButton buttonOptionB = new JButton("Option2");
    private JButton buttonOptionBoth = new JButton("Option3");
    
    public GameModeWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 450);
    }

    public GameModeWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;

        this.init();
        
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
        
        // First time
        refreshGame();
    }
     
    private void createLayout() {
         jPanelNorth();
         jPanelCenter();
         jPanelSouth();
     }   
        
    private void jPanelNorth() {
        panelNorth = new JPanel(new BorderLayout());
        this.panelNorth.setPreferredSize(new Dimension(400, 50));
        this.labelProfile = new JLabel("Perfil atual: ");
        this.labelTime = new JLabel("Time...");
        
        panelNorth.add(this.labelProfile,BorderLayout.WEST);
        panelNorth.add(this.labelTime,BorderLayout.EAST);
        this.mainContainer.add(this.panelNorth, BorderLayout.NORTH);
     }
     
    private void jPanelCenter() {
        this.panelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelQuestion = new JLabel("Pergunta");
        labelQuestion.setFont(labelQuestion.getFont().deriveFont(20f));
        jp.add(labelQuestion);
        
        panelCenter.add(jp);
        //Questions
        Box verticalBox = Box.createVerticalBox();
        verticalBox.setPreferredSize(new Dimension(500, 200));
        verticalBox.add(Box.createVerticalStrut(10));
        
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalStrut(10));
        
        horizontalBox.add(this.buttonOptionA);
        horizontalBox.add(new JLabel("                    "));
        horizontalBox.add(this.buttonOptionB);
        
        verticalBox.add(horizontalBox);
       
        this.buttonOptionBoth.setAlignmentX(CENTER_ALIGNMENT);
        verticalBox.add(this.buttonOptionBoth);
        this.panelCenter.add(verticalBox);
        
        this.mainContainer.add(this.panelCenter, BorderLayout.CENTER);
    }
    
    private void jPanelSouth() {
        this.panelSouth = new JPanel(new BorderLayout());

        this.labelGiveUp.setText("<HTML><U>Desistir</U></HTML>");
        this.labelGiveUp.setForeground(Color.BLUE);
        this.labelGiveUp.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.panelSouth.add(this.labelGiveUp, BorderLayout.WEST);

        this.panelSouth.setPreferredSize(new Dimension(400, 50));

        this.mainContainer.add(this.panelSouth, BorderLayout.SOUTH);
    }
    
    private void registerListeners() {
        
        this.labelGiveUp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogExit();
            }
        });

        this.buttonOptionA.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                verifyQuestion();
                challengeModel.nextAnswer(Answer.OPTION_A);
            }
            
        });

        this.buttonOptionB.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                verifyQuestion();
                challengeModel.nextAnswer(Answer.OPTION_B);
            }
            
        });

        this.buttonOptionBoth.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                verifyQuestion();
                challengeModel.nextAnswer(Answer.OPTION_BOTH);
            }

        });
        
    }
    
    /**
     * Verify that hit the question.
     */
    private void  verifyQuestion() {
        
    }
    
    /**
     * Quit game confimation.
     */
    private void dialogExit() {
        JPanel jp = new JPanel(new GridLayout(2, 6));
        jp.add(new JLabel("Deseja desistir?"));

        String options[] = {"Cancelar", "Confirmar"};
        int value = JOptionPane.showOptionDialog(null, jp, "Desistir", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        //Is ready to give up
        if (value == 1) {

        }
    }
    
    /**
     * Show clarification.
     */
    private void dialogClarification() {
        JPanel jp = new JPanel(new GridLayout(2, 2));
        // jp.add(new JLabel(" "));
        jp.add(new JLabel("Esclarecimento......"));
  
        String options[] = {"Nova pergunta"};
        int value = JOptionPane.showOptionDialog(null, jp, "Resposta errada", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
    
    /**
     * Show Score result.
     */
    private void scoreResultDialog() {
        JPanel jp = new JPanel(new GridLayout(2, 6));
        JLabel jb = new JLabel("Parabens");
        jb.setFont(jb.getFont().deriveFont(42f));
        jp.add(jb);

        String options[] = {"Sair", "Novo Desafio"};
        int value = JOptionPane.showOptionDialog(null, jp, "Fim do desafio", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
 
    @Override
    public void update(Observable t, Object o) {
        refreshGame();
    }
    
    public void refreshGame() {
        if (challengeModel.getChallenge().getCurrentQuestion() == null)
            return;
        
        labelQuestion.setText(challengeModel.getChallenge().getCurrentQuestion().getQuestion());
    }
}
