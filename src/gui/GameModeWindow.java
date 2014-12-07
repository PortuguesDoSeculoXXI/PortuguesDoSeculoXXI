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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import logic.ChallengeHard;
import logic.ChallengeModel;
import logic.Question;
import logic.database.Controller;
import resources.Resources;
import states.WaitScore;

/**
 * Game Window.
 * Game mode.
 * 
 * @author PTXXI
 */
public class GameModeWindow extends JFrame implements Observer{
    
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Date questionStartTime;
    private Date questionAnswerTime;
    
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
    
    /**
     * Timers
     */
    private Timer timerAnswer;
    private Timer timerQuestion;
    
    /**
     * Constructor.
     * @param controller
     * @param challengeModel 
     */
    public GameModeWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 450);
    }

    /**
     * Constructor.
     * @param controller
     * @param challengeModel
     * @param x
     * @param y
     * @param width
     * @param height 
     */
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
        // Timer
        prepareQuestionTimer();
        
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
    
    private void prepareQuestionTimer() {
        timerQuestion = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if (questionStartTime == null)
                    return;
                DateFormat df = new SimpleDateFormat("mm:ss");
                Date currentDate = new Date();
                // Time elapsed
                long timeElapsed = currentDate.getTime() - questionStartTime.getTime();
                // Show timer
                String timeStr = df.format(new Date(timeElapsed));
                try {
                    labelTimeQuestion.setText("" + (Integer.parseInt(labelTimeQuestion.getText()) - 1));
                } catch(NumberFormatException e) {
                    labelTimeQuestion.setText("15");
                }
                labelTimeQuestion.repaint();
                if (labelTimeQuestion.getText().equals("0")) {
                    verifyQuestion(Answer.NO_ANSWER);
                    challengeModel.nextAnswer(null);
                    labelTimeQuestion.setText("15");
                }
            }
            
        });
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
        if (challengeModel.getChallenge() instanceof ChallengeHard)
            labelTimeQuestion = new JLabel("15");
        else
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
        buttonDismiss.setVisible(false);
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
        // Answer Result
        labelAnswerResult.setAlignmentX(CENTER_ALIGNMENT);
        panelCenter.add(labelAnswerResult);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,5)));
        // Clarification
        labelClarification.setAlignmentX(CENTER_ALIGNMENT);
        panelCenter.add(labelClarification);
        
        panelCenter.add(Box.createRigidArea(new Dimension(5,5)));
        // Dimsiss the answer message with clarification
        buttonDismiss.setAlignmentX(CENTER_ALIGNMENT);
        panelCenter.add(buttonDismiss);

        panelCenter.add(Box.createRigidArea(new Dimension(5,5)));
        
        group = new JPanel();
        group.setBackground(Resources.getLogoColor());
        group.setPreferredSize(new Dimension(100,130));
        group.setMinimumSize(new Dimension(100,130));
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
                if (timerAnswer != null)
                    return;

                verifyQuestion(Answer.OPTION_A);
                challengeModel.nextAnswer(Answer.OPTION_A);
            }
            
        });

        buttonOptionB.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (timerAnswer != null)
                    return;

                verifyQuestion(Answer.OPTION_B);
                challengeModel.nextAnswer(Answer.OPTION_B);
            }
            
        });

        buttonOptionBoth.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (timerAnswer != null)
                    return;

                verifyQuestion(Answer.OPTION_BOTH);
                challengeModel.nextAnswer(Answer.OPTION_BOTH);
            }

        });

        buttonDismiss.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                hideAnswerResult();
            }

        });
        
    }
    
    /**
     * Verify the answer of question.
     */
    private void  verifyQuestion(Answer answer) {
        // If is running, stop the question timer
        //(Hard mode)
        timerQuestion.stop();
        
        //if (challengeModel.getChallenge().getCurrentCorrectAnswer()) {
        if (challengeModel.getChallenge().getQuestionsList().get(challengeModel.getChallenge().getCurrentQuestionNumber()-1).getAnswer() == answer) {
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
        
        showAnswerResult(answer);
    }
    
    public void showAnswerResult(Answer answer) {
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
        if (challengeModel.getChallenge().getQuestionsList().get(challengeModel.getChallenge().getCurrentQuestionNumber()-1).getAnswer() != answer) {
            answerDuration = 10000;
            buttonDismiss.setVisible(true);
        }
        
        // Timer for next answer
        timerAnswer = new Timer(answerDuration, new ActionListener() {
            
                @Override
                public void actionPerformed(ActionEvent event) {
                    hideAnswerResult();
                }

            });
        
        timerAnswer.setRepeats(false);
        timerAnswer.start();
        if (challengeModel.getChallenge() instanceof ChallengeHard)
            labelTimeQuestion.setText("15");
    }
    
    public void hideAnswerResult() {
        imgCenter.setVisible(false);
        imgCenter.invalidate();

        labelAnswerResult.setText("");
        labelClarification.setText("");

        buttonDismiss.setVisible(false);
        buttonOptionA.setEnabled(true);
        buttonOptionB.setEnabled(true);
        buttonOptionBoth.setEnabled(true);

        if (timerAnswer != null) {
            timerAnswer.stop();
            timerAnswer = null;
        }
        
        refreshGame();
    }
    
    /**
     * Quit game confirmation.
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
     * Refresh interface data.
     */
    public void refreshGame() {
        if (challengeModel.getChallenge() == null)
            return;
        
        // Waiting when ready
        Question currentQuestion = challengeModel.getChallenge().getCurrentQuestion();
        if (currentQuestion == null)
            return;
        
        labelProfile.setText("<HTML><B>"+captionCurrentProfile+"</B>"+challengeModel.getChallenge().getCurrentProfile().getName()+"</HTML>");
        
        // Waiting for clarification dismiss
        if (timerAnswer != null)
            return;
                
        // Hard mode
        if (challengeModel.getChallenge() instanceof ChallengeHard) {
            questionStartTime = new Date();
            timerQuestion.start();
        }
        
        buttonOptionA.setText(currentQuestion.getOptionA());
        buttonOptionB.setText(currentQuestion.getOptionB());
        
        labelQuestion.setText("<HTML><B>"+currentQuestion.getQuestion()+"</B></HTML>");
    }
    
    /**
     * Presents score dialog.
     * Where the score of the current challenge is shown.
     * This dialog should only be called when the user reaches the end of a challenge,
     * by answering all questions.
     */
    private void scoreDialog() {
        JPanel jp = new JPanel();
        
        Box verticalBox = Box.createVerticalBox();
        
        JLabel scoreTitle = new JLabel("<html><font size=13><center>Score: " + challengeModel.getChallengeScore(challengeModel.getChallenge().getDuration()) + "</center></font></html>");
        scoreTitle.setAlignmentX(CENTER_ALIGNMENT);
        verticalBox.add(scoreTitle);
        
        Box horizontalBox = Box.createHorizontalBox();
        String scoreResult = "" + challengeModel.getChallenge().getScore().getGold() + challengeModel.getChallenge().getScore().getSilver() + challengeModel.getChallenge().getScore().getBronze();
        switch(Integer.parseInt(scoreResult)) {
            case 111: // Gold, Silver, Bronze
                horizontalBox.add(new JLabel(Resources.getImageMedalGold()));
            case 11: // Silver, Bronze
                horizontalBox.add(new JLabel(Resources.getImageMedalSilver()));
            case 1: // Bronze
                horizontalBox.add(new JLabel(Resources.getImageMedalBronze()));
                break;
            default:
                break;
        }
        
        verticalBox.add(horizontalBox);
        
        jp.add(verticalBox);
        
        int value = JOptionPane.showOptionDialog(null, jp, "Fim do desafio", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[] {"Sair", "Novo Desafio"}, 1);
        
        if (value == 0)
            System.exit(0);
        else
            challengeModel.newGame();
    }
    
    /**
     * Update event.
     */
    @Override
    public void update(Observable t, Object o) {
        refreshGame();
        if (challengeModel.getChallenge() != null && challengeModel.getChallenge().getCurrentState() instanceof WaitScore) {
            scoreDialog();
        }
        if (challengeModel.getChallenge() != null && challengeModel.getChallenge().getCurrentState() instanceof WaitScore && challengeModel.getChallenge() instanceof ChallengeHard) {
            labelTimeQuestion.setText("15");
        }
    }
}
