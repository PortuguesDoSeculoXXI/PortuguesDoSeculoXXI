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
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import logic.Challenge;
import logic.ChallengeEasy;
import logic.ChallengeHard;
import logic.ChallengeModel;
import logic.database.Controller;
import states.WaitConfiguration;

/**
 * Choosing Window.
 * Configure game.
 * 
 * @author PTXXI
 */
public final class ConfigurationWindow extends JFrame implements Observer {

    private final Controller controller;
    private final ChallengeModel challengeModel;
    
    /**
     * UI Components
     */
    private Container mainContainer;
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
    private final JButton buttonPlay = new JButton("Jogar");
    private final JRadioButton easy  = new JRadioButton("Fácil (Sem tempo)");
    private final JRadioButton hard = new JRadioButton(" Difícil (Com tempo)");
    private JLabel backLabel;
 
    /**
     * Constructor.
     * @param controller
     * @param challengeModel
     */
    public ConfigurationWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 550);
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
    public ConfigurationWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;

        init();

        setLocation(x, y);
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Initialize the window.
     */
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

    /**
     * Create the main layout.
     */
    protected void createLayout() {
        this.jPanelNorth();
        this.jPanelCenter();
        this.jPanelSouth();
    }

    /**
     * Panel north configuration.
     */
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

    /**
     * Panel center configuration.
     */
    private void jPanelCenter() {
        jpanelcenter = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Box verticalBox = Box.createVerticalBox();
        Box horizontalBox = Box.createHorizontalBox();

        verticalBox.setPreferredSize(new Dimension(500, 200));

        horizontalBox.add(new JLabel("Dificuldade: "));
        horizontalBox.add(this.easy);
        horizontalBox.add(this.hard);

        verticalBox.add(horizontalBox);
        verticalBox.add(Box.createVerticalStrut(10));

        consoants.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        consoants.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        verticalBox.add(this.consoants);

        accents.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        accents.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        verticalBox.add(this.accents);

        hyphen.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        hyphen.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        verticalBox.add(this.hyphen);
        
        caseSensitive.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        caseSensitive.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));

        verticalBox.add(this.caseSensitive);

        random.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        random.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        verticalBox.add(random);
        verticalBox.add(this.buttonPlay);
        buttonPlay.setMaximumSize(new Dimension(160, Integer.MAX_VALUE));
        buttonPlay.setAlignmentX(CENTER_ALIGNMENT);

        jpanelcenter.add(verticalBox);

        this.mainContainer.add(this.jpanelcenter, BorderLayout.CENTER);
    }

    /**
     * Panel south configuration.
     */
    private void jPanelSouth() {
        backLabel = new JLabel("<HTML><U>Voltar</U><HTML>");
        backLabel.setForeground(Color.BLUE);
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jpanelsouth = new JPanel(new FlowLayout (FlowLayout.LEFT));
        jpanelsouth.add(backLabel);
        
        jpanelWest = new JPanel(new BorderLayout());
        
        this.jpaneleast = new JPanel(new BorderLayout());
        jpaneleast.add(backLabel,BorderLayout.SOUTH);
        
        this.mainContainer.add(this.jpaneleast, BorderLayout.WEST);
    }

    /**
     * Register listeners for each component.
     */
    private void registerListeners() {
        
        backLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                challengeModel.backPressed();
            }
        });

        changeCurrentProfile.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogProfile();
            }
        });

        buttonPlay.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Verifica se algum nível foi seleccionado
                if (!checkMode()) {
                    dialogGameMode();
                    return;
                }
                // Verifica se existe pelo menos uma categoria seleccionada
                if (!checkCategories()) {
                    dialogGameMode();
                    return;
                }

                startGame();
            }
        });

        this.easy.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                deselectMode(1);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                deselectMode(1);
            }
        });

        this.hard.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                deselectMode(2);
            }
           
            @Override
            public void mousePressed(MouseEvent e) {
                deselectMode(2);
            }
            
        });

        this.random.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                changeCategories();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                changeCategories();
            }

        });

        this.accents.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                removeRandom();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                removeRandom();
            }

        });

        this.consoants.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                removeRandom();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                removeRandom();
            }
        });

        this.hyphen.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                removeRandom();
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                removeRandom();
            }

        });

        this.caseSensitive.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                removeRandom();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                removeRandom();
            }
        }); 
    }

    /**
     * Select/Unselect game mode.
     */
    private void deselectMode(int gameMode) {
        if (gameMode == 1) {
            this.hard.setSelected(false);
        }
        else if( gameMode == 2){
            this.easy.setSelected(false);
        }
    }
    
    /**
     * Remove categories.
     */
    private void changeCategories() {
        this.consoants.setState(false);
        this.accents.setState(false);
        this.hyphen.setState(false);
        this.caseSensitive.setState(false);
    }

    /**
     * Verify that you have choosen a game mode.
     * 
     * @return true: user selected a mode, false: nothing selected.
     */
    private boolean checkMode() {
        return this.easy.isSelected() || this.hard.isSelected();
    } 

    /**
     * Remove categories.
     */
    private void removeRandom(){
        this.random.setState(false);
    }
    
    /**
     * Check categories.
     * 
     * @return true: user selected at least one category, 
     *  false: nothing selected.
     */
    private boolean checkCategories() {
        if (this.consoants.getState()) {
            return true;
        } 
        else if (this.accents.getState()) {
            return true;
        } 
        else if (this.hyphen.getState()) {
            return true;
        } 
        else if (this.caseSensitive.getState()) {
            return true;
        } 
        else if (this.random.getState()) {
            return true;
        }
        return false;
    }
    
    /**
     * Retrieve selected categories.
     * 
     * @return list with categories.
     */
    private List<Challenge.Categories> getSelectedCategories() {
        ArrayList<Challenge.Categories> list = new ArrayList<>();
        
        if (this.consoants.getState()) {
            list.add(Challenge.Categories.CONSOANTS);
        } 
        if (this.accents.getState()) {
            list.add(Challenge.Categories.GRAPHIC_ACCENTS);
        } 
        if (this.hyphen.getState()) {
            list.add(Challenge.Categories.HYPHEN);
        } 
        if (this.caseSensitive.getState()) {
            list.add(Challenge.Categories.UPPERCASE_AND_LOWERCASE);
        } 
        if (this.random.getState()) {
            list.add(Challenge.Categories.CONSOANTS);
            list.add(Challenge.Categories.GRAPHIC_ACCENTS);
            list.add(Challenge.Categories.HYPHEN);
            list.add(Challenge.Categories.UPPERCASE_AND_LOWERCASE);
        }
        return list;
    }
    
    /**
     * Choose a game mode.
     */
    private void dialogGameMode() {
        JPanel jp = new JPanel(new GridLayout(2, 6));
        jp.add(new JLabel("Por favor, escolha um modo de jogo."));

        String options[] = {"Continuar"};
        int value = JOptionPane.showOptionDialog(null, jp, "", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }
   
    /**
     * Shows profiles.
     */
    private void dialogProfile() {
        JPanel jp = new JPanel(new GridLayout(2, 6));
        jp.add(new JLabel("Escolha de perfil:"));

        JComboBox profilesList = new JComboBox();
        
        profilesList.removeAllItems();
        for (String aux : controller.getPlayersAsStrings()) {
            profilesList.addItem(aux);
        }
        
        profilesList.setSelectedItem(challengeModel.getCurrentPlayer().getName());
        
        jp.add(profilesList);
        
        String options[] = {"Cancelar", "Alterar"};
        int value = JOptionPane.showOptionDialog(null, jp, "Alteração de Perfil", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Change Current Profile
        if (value == 1) {
            challengeModel.setCurrentProfile(controller.getProfileOf((String) profilesList.getSelectedItem()));
        }
    }

    /**
     * Update state machine when a notification occurs.
     */
    @Override
    public void update(Observable t, Object o) {
        if (challengeModel.getChallenge() != null && !challengeModel.isScoreWindow()) {
            if (challengeModel.getChallenge().getCurrentState() instanceof WaitConfiguration) {
                currentProfile.setText("<HTML><B>" + challengeModel.getCurrentPlayer().getName() + "</B></HTML>");
            }
        }
    }
    
    /**
     * Start a new game with the current options.
     */
    public void startGame() {
        List<Challenge.Categories> listCategories = getSelectedCategories();
        if (listCategories.isEmpty()) {
            return;
        }

        Challenge currentChallenge;
        if (this.easy.isSelected())
            currentChallenge = new ChallengeEasy(controller, challengeModel.getCurrentPlayer());
        else
            currentChallenge = new ChallengeHard(controller, challengeModel.getCurrentPlayer());
        
        challengeModel.setChallenge(currentChallenge);
        challengeModel.startGame(listCategories);
    }
}