package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logic.Challenge;
import logic.ChallengeModel;
import logic.database.Controller;
import resources.Resources;

/**
 * Frame Main Class.
 *
 * This class represents the Entrance Window described on FR-1 Quote: "This is
 * the entrance window, that every user will see when opening the application."
 *
 * @author PTXXI
 */
public final class EntranceWindow extends JFrame {
    
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    private JPanel jpanel;
    private JPanel jpanelcenter;
    private JPanel jpanelsout;
    private final JButton play = new JButton("Jogar");
    private final JButton score = new JButton("Pontuações");
    private final JButton exit = new JButton("Sair");
    private final String[] simbolos = {"Novo perfil..."};
    private JComboBox jc = null;
    private JLabel click = null;   

    /**
     * Constructor.
     */
    public EntranceWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 500);
    }

    /**
     * Constructor.
     */
    private EntranceWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;

        init();

        setLocation(x, y);
        setSize(width, height);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Verify if there's profiles created
        verifyProfiles();
    }

    /**
     * Instantiates content panes and register component listeners
     */
    protected void init() {
        // Init components/panels
        this.mainContainer = getContentPane();
        this.mainContainer.setLayout(new BorderLayout());
        this.mainContainer.validate();

        // Layout
        createLayout();

        // Listeners
        registerListeners();
        addJComboBoxListenner();
    }

    /**
     * Create the main layout
     */
    protected void createLayout() {
        this.jPanelNorth();
        this.jPanelCenter();
    }

    /**
     * Method instantiates <b>North</b> components and its characteristics
     */
    private void jPanelNorth() {
        this.jpanel = new JPanel(new FlowLayout());
        this.jpanel.setBackground(Resources.getLogoColor());
        
        JLabel jb = new JLabel(Resources.getLogo());

        this.jpanel.add(jb);
        this.mainContainer.add(this.jpanel, BorderLayout.NORTH);
    }

    /**
     * Method instantiates <b>Center</b> components and its characteristics
     */
    private void jPanelCenter() {
        jpanelcenter = new JPanel(new BorderLayout());
        this.jpanelcenter.setBackground(Resources.getLogoColor());
        this.jpanelcenter.setAlignmentX(CENTER_ALIGNMENT);

        this.jc = new JComboBox(this.simbolos);

        JPanel jp = new JPanel(new GridLayout(1, 6));
        jp.setBackground(Resources.getLogoColor());
        jp.add(new JLabel(" "));
        jp.add(new JLabel("Perfil do utilizador:"));
        jp.add(this.jc);

        this.click = new JLabel("<HTML><U>Editar</U></HTML>");
        this.click.setForeground(Color.BLUE);
        this.click.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.click.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                dialogEditProfile();
            }
            
        });

        jp.add(this.click);
        this.jpanelcenter.add(jp,BorderLayout.NORTH);

        this.jpanelcenter.add(new JLabel(" "));

        Box buttonsBox = Box.createVerticalBox();
        buttonsBox.add(Box.createVerticalStrut(25));
        
        play.setMaximumSize(new Dimension(140, Integer.MAX_VALUE));
        play.setAlignmentX(CENTER_ALIGNMENT);
        buttonsBox.add(play);
        buttonsBox.add(Box.createVerticalStrut(25));
        
        score.setMaximumSize(new Dimension(140, Integer.MAX_VALUE));
        score.setAlignmentX(CENTER_ALIGNMENT);
        buttonsBox.add(score);
        buttonsBox.add(Box.createVerticalStrut(25));
        
        exit.setMaximumSize(new Dimension(140, Integer.MAX_VALUE));
        exit.setAlignmentX(CENTER_ALIGNMENT);
        buttonsBox.add(exit);
        jpanelcenter.add(buttonsBox);
        
        this.jpanelsout = new JPanel(new FlowLayout());
        this.jpanelsout.setBackground(Resources.getLogoColor());
        
        this.mainContainer.add(this.jpanelsout, BorderLayout.SOUTH);
        this.mainContainer.add(this.jpanelcenter, BorderLayout.CENTER);
    }

    /**
     * Static listeners
     */
    private void registerListeners() {
        // Play button
        play.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Challenge currentChallenge = new Challenge(controller, controller.getProfileOf((String)jc.getSelectedItem()));
                challengeModel.setChallenge(currentChallenge);
                challengeModel.newGame();
            }
            
        });
        
        score.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                challengeModel.setScoreWindow(true);
            }
        });
        
        // Exit Button
        this.exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
            
        });
    }

    /**
     * Dynamic listener.
     * Dynamic because the list used on the combo box may
     * change, and the listener has to be updated accordingly.
     */
    private void addJComboBoxListenner() {
        this.jc.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // Ignore ItemEvent.DESELECTED event
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (((String) e.getItem()).equals("Novo perfil...")) {
                        // Create new profile option.
                        if (jc.getSelectedIndex() == jc.getItemCount() - 1) {
                            createProfileDialog();
                        }
                    }
                }
            }
        });
    }

    /**
     * Verifies if there are any profile on database.
     * If not then show dialogProfile(). Also populates profile combo box. 
     * So it can be used to update combo box and its listener.
     */
    private void verifyProfiles() {
        if (controller.getPlayers().size() > 0) {
            jc.removeAllItems();
            for (String aux : controller.getPlayersAsStrings()) {
                jc.addItem(aux);
            }
            jc.addItem(simbolos[0]);
            addJComboBoxListenner();
        } else {
            createProfileDialog();
        }
    }

    /**
     * Launches Edit Profile Dialog, according to FR-3 (SRS Document)
     */
    private void createProfileDialog() {
        JPanel jp = new JPanel(new GridLayout(2, 2));

        jp.add(new JLabel("Nome"));

        final JTextField tx = new JTextField();
        tx.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                tx.setText("");
                tx.setForeground(Color.BLACK);
            }
        });
        tx.setText("Insira aqui o nome...");
        tx.setForeground(Color.LIGHT_GRAY);

        jp.add(tx);

        String options[] = {"Cancelar", "Gravar"};

        while (true) {
            int value = JOptionPane.showOptionDialog(null, jp, "Novo Perfil de Utilizador", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            // If user presses ok = 1; cancel != 1
            if (value == 1) {
                if (tx.getText().equals("") || tx.getText().equals("Insira aqui o nome...")) {
                    tx.setText("Insira aqui o nome...");
                    tx.setForeground(Color.RED);
                    continue;
                }
                if (controller.isValidName(tx.getText())) {
                    controller.insertPlayer(tx.getText());
                    break;
                } else {
                    tx.setForeground(Color.RED);
                    tx.setText("Nome já existente");
                }
            } else {
                // Cancel
                break;
            }
        }

        // Update JComboBox
        verifyProfiles();
        // Set selected value to the last inserted profile
        jc.setSelectedIndex(jc.getItemCount() - 2);
    }

    /**
     * Launches Edit Profile Dialog, according to FR-3 (SRS Document)
     */
    private void dialogEditProfile() {
        // If selected item is simbolos[0] return
        if (((String) jc.getSelectedItem()).equals(simbolos[0])) {
            return;
        }

        JPanel jp = new JPanel(new GridLayout(2, 6));

        // Create textfield with selected profile
        final JTextField tx = new JTextField((String) jc.getSelectedItem());
        tx.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                tx.setText("");
                tx.setForeground(Color.BLACK);
            }
        });

        jp.add(tx);
        jp.add(new JLabel(" "));
        String options[] = {"Cancelar", "Apagar", "Alterar"};

        while (true) {
            int value = JOptionPane.showOptionDialog(null, jp, "Alteração de Perfil", JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            if (value == 0) { // Cancel
                return;
            } else if (value == 1) { // Erase
                controller.deletePlayer(controller.getProfileOf((String) jc.getSelectedItem()));
                break;
            } else if (value == 2) { // Edit
                if (controller.isValidName(tx.getText())) {
                    controller.updatePlayer(controller.getProfileOf((String) jc.getSelectedItem()), tx.getText());
                    break;
                } else {
                    tx.setForeground(Color.RED);
                    tx.setText("Nome já existente");
                }
            }
        }

        // Update JComboBox
        verifyProfiles();
    }
}
