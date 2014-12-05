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
    private JPanel jpanelNorth;
    private JPanel jpanelcenter;
    private JPanel jpanelsouth;
    private JLabel profile;
    private JLabel time;
    private JLabel lblQuestion;
    private JButton option1 = new JButton("Option1");
    private JButton option2 = new JButton("Option2");
    private JButton option3 = new JButton("Option3");
    private JLabel giveUp   = new JLabel("Give Up");
    
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
         this.jPanelNorth();
         this.jPanelCenter();
         this.jPanelSouth();
     }   
        
    private void jPanelNorth() {
        jpanelNorth = new JPanel(new BorderLayout());
        this.jpanelNorth.setPreferredSize(new Dimension(400, 50));
        this.profile = new JLabel("Perfil atual: ");
        this.time =  new JLabel("Time...");
        
        jpanelNorth.add(this.profile,BorderLayout.WEST);
        jpanelNorth.add(this.time,BorderLayout.EAST);
        this.mainContainer.add(this.jpanelNorth, BorderLayout.NORTH);
     }
     
    private void jPanelCenter() {
        this.jpanelcenter = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
        
        JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lblQuestion = new JLabel("Pergunta");
        lblQuestion.setFont(lblQuestion.getFont().deriveFont(42f));
        jp.add(lblQuestion);
        
        jpanelcenter.add(jp);
        //Questions
        Box verticalBox = Box.createVerticalBox();
        verticalBox.setPreferredSize(new Dimension(500, 200));
        verticalBox.add(Box.createVerticalStrut(10));
        
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(Box.createHorizontalStrut(10));
        
        horizontalBox.add(this.option1);
        horizontalBox.add(new JLabel("                    "));
        horizontalBox.add(this.option2);
        
        verticalBox.add(horizontalBox);
       
        this.option3.setAlignmentX(CENTER_ALIGNMENT);
        verticalBox.add(this.option3);
        this.jpanelcenter.add(verticalBox);
        
        this.mainContainer.add(this.jpanelcenter, BorderLayout.CENTER);
    }
    
    private void jPanelSouth() {
        this.jpanelsouth = new JPanel(new BorderLayout());

        this.giveUp.setText("<HTML><U>Desistir</U></HTML>");
        this.giveUp.setForeground(Color.BLUE);
        this.giveUp.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.jpanelsouth.add(this.giveUp, BorderLayout.WEST);

        this.jpanelsouth.setPreferredSize(new Dimension(400, 50));

        this.mainContainer.add(this.jpanelsouth, BorderLayout.SOUTH);
    }
    
    private void registerListeners() {
        
        this.giveUp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogExit();
            }
        });

        this.option1.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                verifyQuestion();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                verifyQuestion();
            }
        });

        this.option2.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                verifyQuestion();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                verifyQuestion();
            }
        });

        this.option3.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                verifyQuestion();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                verifyQuestion();
            }
        });
        
    }
    
    /**
     * Verify that hit the question.
     */
    private void  verifyQuestion() {
        dialogNext();
        scoreResultDialog();
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
    private void dialogNext() {
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
        if (challengeModel.getChallenge().getQuestionsList().isEmpty())
            return;
        
        lblQuestion.setText(challengeModel.getChallenge().getQuestionsList().get(0).getQuestion());
    }
}
