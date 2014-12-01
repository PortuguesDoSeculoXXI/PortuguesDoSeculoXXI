package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
    private final JButton consoants = new JButton("Consoantes");
    private final JButton accents = new JButton("Acentos");
    private final JButton hyphen = new JButton("Hífen");
    private final JButton caseSensitive = new JButton("Maiúsculas e Minúsculas");
    private final JButton random = new JButton("Aleatório");
    private final JLabel currentProfile = new JLabel("");
    private final JLabel changeCurrentProfile = new JLabel("Mudar");
    private final String symbols[] = {"Fácil (Sem tempo)", " Difícil (Com tempo)"};
    private JComboBox jc;

    public JFrameChoosingWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 500);
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
        horizontalBox.add(jc);
        
        verticalBox.add(horizontalBox);
        
        horizontalBox = Box.createHorizontalBox();
        
        verticalBox.add(Box.createVerticalStrut(30));
        
        consoants.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        horizontalBox.add(consoants);
        horizontalBox.add(Box.createHorizontalStrut(50));
        accents.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        horizontalBox.add(accents);
        
        verticalBox.add(horizontalBox);
        
        verticalBox.add(Box.createVerticalStrut(30));
        
        horizontalBox = Box.createHorizontalBox();
        
        hyphen.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));
        horizontalBox.add(hyphen);
        horizontalBox.add(Box.createHorizontalStrut(50));
        caseSensitive.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 9));
        caseSensitive.setMaximumSize(new Dimension(248, Integer.MAX_VALUE));
        horizontalBox.add(caseSensitive);
        
        verticalBox.add(horizontalBox);
        
        verticalBox.add(Box.createVerticalStrut(30));
        
        random.setMaximumSize(new Dimension(250, Integer.MAX_VALUE));
        random.setAlignmentX(CENTER_ALIGNMENT);
        verticalBox.add(random);
        
        jpanelcenter.add(verticalBox);
        
        this.mainContainer.add(this.jpanelcenter, BorderLayout.CENTER);
    }

    //JPanelSouth
    private void jPanelSouth() {
        this.jpanelsouth = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel backLabel = new JLabel("<HTML><U>Voltar</U><HTML>");
        backLabel.setForeground(Color.BLUE);
        backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        backLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                challengeModel.backPressed();
            }
        });

        jpanelsouth.add(backLabel);
        this.mainContainer.add(this.jpanelsouth, BorderLayout.SOUTH);
    }

    private void registerListeners() {
        changeCurrentProfile.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogProfile();
            }
        });
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
