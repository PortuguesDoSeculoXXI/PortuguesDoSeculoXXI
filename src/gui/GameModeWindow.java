package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import logic.Answer;
import logic.ChallengeModel;
import logic.Question;
import logic.database.Controller;
import resources.Resources;

/**
 * Game Window.
 * Game mode.
 * 
 * @author PTXXI
 */
public class GameModeWindow extends JFrame implements Observer{
    
    private final Controller controller;
    private final ChallengeModel challengeModel;
    
    /**
     * UI Components
     */
    private Container mainContainer;
    private JPanel panelNorth;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JLabel labelProfile;
    private JLabel labelTimeQuestion;
    private JLabel labelQuestion;
    private JLabel labelClarification;
    private JLabel labelAnswerResult;
    private JLabel labelGiveUp;
    private JLabel imgCenter;
    private JButton buttonDismiss;
    private JButton buttonOptionA = new JButton("Resposta A");
    private JButton buttonOptionB = new JButton("Resposta B");
    private JButton buttonOptionBoth = new JButton("Ambas estão corretas");
    
    private static final String captionCurrentProfile = " Perfil atual: ";
    
    // Teste
    private Timer timerAnswer;
    private Timer timerQuestion;
    
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
        getContentPane().setLayout(new BorderLayout());
        this.mainContainer = new JPanel(new BorderLayout());
        this.mainContainer.validate();
        getContentPane().add(mainContainer, BorderLayout.CENTER);

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
        initComponents();
        initMargins();
        jPanelNorth();
        jPanelCenter();
        jPanelSouth();
    }
    
    private void initMargins() {
        // Margin Left
        JPanel marginLeft = new JPanel(new BorderLayout());
        marginLeft.setBackground(Resources.getLogoColor());
        marginLeft.setMinimumSize(new Dimension(10, 400));
        marginLeft.setPreferredSize(new Dimension(10, 400));
        getContentPane().add(marginLeft, BorderLayout.WEST);
        // Margin Right
        JPanel marginRight = new JPanel(new BorderLayout());
        marginRight.setBackground(Resources.getLogoColor());
        marginRight.setMinimumSize(new Dimension(10, 400));
        marginRight.setPreferredSize(new Dimension(10, 400));
        getContentPane().add(marginRight, BorderLayout.EAST);
    }
    
    private void initComponents() {
        labelProfile = new JLabel();
        labelTimeQuestion = new JLabel();
        labelQuestion = new JLabel("Pergunta");
        labelClarification = new JLabel();        
        labelAnswerResult = new JLabel();
        
        imgCenter = new JLabel();
        imgCenter.setIcon(Resources.getImageCorrect());
        imgCenter.setVisible(false);
        
        labelGiveUp = new JLabel("<HTML><U>Desistir</U></HTML>");
        labelGiveUp.setForeground(Color.BLUE);
        labelGiveUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonDismiss = new JButton("Continuar");
    }
        
    private void jPanelNorth() {
        panelNorth = new JPanel(new BorderLayout());
        panelNorth.setPreferredSize(new Dimension(400, 50));
        panelNorth.setBackground(Resources.getLogoColor());

        panelNorth.add(labelProfile,BorderLayout.WEST);
        panelNorth.add(labelTimeQuestion,BorderLayout.EAST);
        
        mainContainer.add(panelNorth, BorderLayout.NORTH);
     }
     
    private void jPanelCenter() {
        panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter,BoxLayout.Y_AXIS));
        panelCenter.setBackground(Resources.getLogoColor());
        
        JLabel labelCaption = new JLabel("Pergunta");
        Font labelFont = labelCaption.getFont();
        labelCaption.setFont(new Font(labelFont.getName(), Font.PLAIN, 24));
        labelCaption.setAlignmentX(CENTER_ALIGNMENT);
        labelCaption.setAlignmentY(CENTER_ALIGNMENT);
        panelCenter.add(labelCaption);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,15)));
        
        labelQuestion.setAlignmentX(CENTER_ALIGNMENT);
        labelQuestion.setAlignmentY(CENTER_ALIGNMENT);
        panelCenter.add(labelQuestion);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,15)));
        
        JPanel group;
        
        group = new JPanel();
        group.setBackground(Resources.getLogoColor());
        group.setLayout(new BoxLayout(group,BoxLayout.X_AXIS));
        group.add(this.buttonOptionA);
        group.add(Box.createRigidArea(new Dimension(5,5)));
        group.add(this.buttonOptionB);        
        panelCenter.add(group);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,5)));
       
        group = new JPanel();
        group.setBackground(Resources.getLogoColor());
        group.setLayout(new BoxLayout(group,BoxLayout.X_AXIS));
        buttonOptionBoth.setAlignmentX(CENTER_ALIGNMENT);
        group.add(this.buttonOptionBoth);
        panelCenter.add(group);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,15)));
        
        labelAnswerResult.setAlignmentX(CENTER_ALIGNMENT);
        panelCenter.add(labelAnswerResult);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,5)));
        
        labelClarification.setAlignmentX(CENTER_ALIGNMENT);
        panelCenter.add(labelClarification);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,5)));
        
        group = new JPanel();
        group.setBackground(Resources.getLogoColor());
        group.setPreferredSize(new Dimension(100,120));
        group.setMinimumSize(new Dimension(100,120));
        // Image: Correct/Incorrect
        group.add(imgCenter);
        panelCenter.add(group);
        
        mainContainer.add(panelCenter, BorderLayout.CENTER);
    }
    
    private void jPanelSouth() {
        panelSouth = new JPanel(new BorderLayout());
        panelSouth.setBackground(Resources.getLogoColor());
        panelSouth.setPreferredSize(new Dimension(400, 50));

        panelSouth.add(labelGiveUp, BorderLayout.WEST);

        mainContainer.add(panelSouth, BorderLayout.SOUTH);
    }
    
    private void registerListeners() {
        
        labelGiveUp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogExit();
            }
        });

        buttonOptionA.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (timerAnswer != null && timerAnswer.isRunning())
                    return;

                verifyQuestion();
                challengeModel.nextAnswer(Answer.OPTION_A);
            }
            
        });

        buttonOptionB.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (timerAnswer != null && timerAnswer.isRunning())
                    return;

                verifyQuestion();
                challengeModel.nextAnswer(Answer.OPTION_B);
            }
            
        });

        buttonOptionBoth.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (timerAnswer != null && timerAnswer.isRunning())
                    return;

                verifyQuestion();
                challengeModel.nextAnswer(Answer.OPTION_BOTH);
            }

        });

    }
    
    /**
     * Verify the answer of question.
     */
    private void  verifyQuestion() {
        if (challengeModel.getChallenge().getCurrentCorrectAnswer()) {
            imgCenter.setIcon(Resources.getImageCorrect());
            labelAnswerResult.setText("Correto ");
            labelClarification.setText("");
        }
        else {
            imgCenter.setIcon(Resources.getImageIncorrect());
            // Show clarification
            labelAnswerResult.setText("");
            labelClarification.setText("<HTML><B>Esclarecimento: </B>"+challengeModel.getChallenge().getCurrentRuleClarification()+"</HTML>");
        }
        
        showAnswerResult();
    }
    
    public void showAnswerResult() {
        imgCenter.setVisible(true);
        imgCenter.invalidate();
        
        buttonOptionA.setEnabled(false);
        buttonOptionB.setEnabled(false);
        buttonOptionBoth.setEnabled(false);
        
        // Refresh in thread
        SwingUtilities.invokeLater(new Runnable() {
            
                                @Override
                                public void run() { 
                                   imgCenter.repaint();
                                }
                                
                            });
        
        int answerDuration = 1000;
        if (!challengeModel.getChallenge().getCurrentCorrectAnswer())
            answerDuration = 5000;
        
        // Timer for next answer
        timerAnswer = new Timer(answerDuration, new ActionListener() {
            
                @Override
                public void actionPerformed(ActionEvent event) {
                    hideAnswerResult();
                }

            });
        
        timerAnswer.setRepeats(false);
        timerAnswer.start();
    }
    
    public void hideAnswerResult() {
        imgCenter.setVisible(false);
        imgCenter.invalidate();

        labelAnswerResult.setText("");
        labelClarification.setText("");

        buttonOptionA.setEnabled(true);
        buttonOptionB.setEnabled(true);
        buttonOptionBoth.setEnabled(true);

        refreshGame();
        if (timerAnswer != null)
            timerAnswer.stop();
    }
    
    /**
     * Quit game confimation.
     */
    private void dialogExit() {
        JPanel jp = new JPanel(new GridLayout(2, 6));
        jp.add(new JLabel("Deseja realmente desistir?"));

        String options[] = {"Confirmar", "Cancelar"};
        int value = JOptionPane.showOptionDialog(null, jp, "Desistir", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        //Is ready to give up
        if (value == 0) {
            challengeModel.quitGame();
        }
    }
    
    /**
     * Update event.
     */
    @Override
    public void update(Observable t, Object o) {
        refreshGame();
    }
    
    /**
     * Refresh interface data.
     */
    public void refreshGame() {
        if (challengeModel.getChallenge() == null)
            return;
        
        Question currentQuestion = challengeModel.getChallenge().getCurrentQuestion();
        if (currentQuestion == null)
            return;
        
        labelProfile.setText("<HTML><B>"+captionCurrentProfile+"</B>"+challengeModel.getChallenge().getCurrentProfile().getName()+"</HTML>");
        
        if (timerAnswer != null && timerAnswer.isRunning())
            return;
        
        buttonOptionA.setText(currentQuestion.getOptionA());
        buttonOptionB.setText(currentQuestion.getOptionB());
        
        labelQuestion.setText("<HTML><B>"+currentQuestion.getQuestion()+"</B></HTML>");
    }
}
