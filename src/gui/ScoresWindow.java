package gui;

import database.DataController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import logic.ChallengeModel;
import logic.Level;
import logic.Score;
import logic.database.Controller;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.Title;
import org.jfree.data.Range;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * JFrame Score Window class.
 * This window presents all scores per user profile.
 * Is implemented with all FR characteristics described on SRS: FR-9
 * 
 * @author PTXXI
 */
public final class ScoresWindow extends JFrame implements Observer {
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    private final JComboBox jc;
    private JTabbedPane tabs;
    private ChartPanel seriesChartPanel;
    private JTable scoreTable;
    private final String[] COLUMN_NAMES = {"Medalhas", "Score", "Tempo", "Data"}; 
    private final JLabel backLabel = new JLabel("Voltar");

    
    /**
     * Constructor.
     * Initializes model variables and define layout parameters.
     * 
     * @param controller
     * @param challengeModel 
     */
    public ScoresWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 500);
    }
    /**
     * Constructor.
     * Initializes model variables and define layout parameters.
     * 
     * @param controller
     * @param challengeModel
     * @param x
     * @param y
     * @param width
     * @param height 
     */
    public ScoresWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
        super("Português do Século XXI");
        this.controller = controller;
        this.challengeModel = challengeModel;
        jc = new JComboBox(controller.getPlayersAsStrings());
        
        init();

        setLocation(x, y);
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Instantiates content panes and register component listeners
     */
    private void init() {
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

    private void createLayout() {
        jPanelCenter();
        jPanelSouth();
    }
    
    /**
     * Method instantiates <b>Center</b> components and its characteristics.
     */
    private void jPanelCenter() {
        final JPanel jPanelCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        jPanelCenter.setBackground(resources.Resources.getLogoColor());
        
        Box verticalBox = Box.createVerticalBox();
        verticalBox.setAlignmentX(CENTER_ALIGNMENT);
        
        //JLabel title = new JLabel("<HTML><B><FONT SIZE=64>Pontuações</FONT></B></HTML>");
        JLabel titleLabel = new JLabel("Pontuações");
        //titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(32f));
        verticalBox.add(titleLabel);
        
        verticalBox.add(Box.createVerticalStrut(10));
        
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(new JLabel("Visualizar pontuações de: "));
        horizontalBox.add(jc);
        verticalBox.add(horizontalBox);
        
        verticalBox.add(Box.createVerticalStrut(10));
        // Instantiate tabbed pane
        tabs = new JTabbedPane();
        // Generate score table
        generateScoreTable();
        // Generate series Chart, with scores
        generateSeriesChart();
        // Add to tab panel and configure it
        tabs.add(seriesChartPanel);
        tabs.add(scoreTable);
        tabs.setTitleAt(0, "Gráfico de Scores");
        tabs.setTitleAt(1, "Tabela de Scores");
        tabs.setPreferredSize(new Dimension(380, 380));
        
        //jPanelCenter.add(this)
        verticalBox.add(tabs);
        jPanelCenter.add(verticalBox);
        mainContainer.add(jPanelCenter);
    }

    /**
     * Method instantiates <b>South</b> components and its characteristics.
     */
    private void jPanelSouth() {
        final JPanel jPanelSouth = new JPanel(new BorderLayout());
        
        jPanelSouth.setBackground(resources.Resources.getLogoColor());

        this.backLabel.setText("<HTML><U>Voltar</U></HTML>");
        this.backLabel.setForeground(Color.BLUE);
        this.backLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jPanelSouth.add(this.backLabel, BorderLayout.WEST);

        jPanelSouth.setPreferredSize(new Dimension(400, 50));

        this.mainContainer.add(jPanelSouth, BorderLayout.SOUTH);
    }
    
    /**
     * Static listeners.
     */
    private void registerListeners() {
        backLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                challengeModel.setChallenge(null);
                challengeModel.setScoreWindow(false);
                
//                DataController a  = new DataController();
//                a.insertChallengeScore(1, new Date("12/2/2014"), 10, Level.MODE_HARD, 100, 1, 1, 1);
//                a.insertChallengeScore(1, new Date("12/1/2014"), 10, Level.MODE_HARD, 10, 1, 1, 1);
//                a.insertChallengeScore(1, new Date("12/6/2014"), 10, Level.MODE_HARD, 200, 1, 1, 1);
//                a.insertChallengeScore(1, new Date("12/5/2014"), 10, Level.MODE_HARD, 1, 1, 1, 1);
//                a.insertChallengeScore(1, new Date("12/5/2014"), 10, Level.MODE_HARD, 140, 1, 1, 1);
//                generateSeriesChart();
            }
        });
        
        jc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                refreshChart();
            }
        });
    }

    private void refreshChart() {
        tabs.removeAll();
        tabs.revalidate(); // This removes the old chart and table
        
        generateSeriesChart();
        generateScoreTable();

        tabs.add(seriesChartPanel);
        tabs.add(scoreTable);
        
        tabs.setTitleAt(0, "Gráfico de Scores");
        tabs.setTitleAt(1, "Tabela de Scores");
        
        tabs.repaint(); // This method makes the new chart appear
    }
    
    /**
     * Generate graph accordingly to selected user on JComboBox.
     */
    private void generateSeriesChart() {
        TimeSeries easyModeSerie = new TimeSeries("Fácil", Day.class);
        TimeSeries hardModeSerie = new TimeSeries("Difícil", Day.class);
        
        // Get avg score list by selected player
        List<Score> scores = new DataController().getAvgScoreByPlayer(controller.getProfileOf(jc.getSelectedItem().toString()).getId(), Level.MODE_EASY);
        //if (scores.get(0).getDateTime() != null)
            for(Score aux : scores)
                easyModeSerie.addOrUpdate(new Day(aux.getDateTime()), aux.getScore());
        
        scores = new DataController().getAvgScoreByPlayer(controller.getProfileOf(jc.getSelectedItem().toString()).getId(), Level.MODE_HARD);
        //if (scores.get(0).getDateTime() != null)
            for(Score aux : new DataController().getAvgScoreByPlayer(controller.getProfileOf(jc.getSelectedItem().toString()).getId(), Level.MODE_HARD))
                hardModeSerie.addOrUpdate(new Day(aux.getDateTime()), aux.getScore());
        
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        // Add series
        dataset.addSeries(easyModeSerie);
        dataset.addSeries(hardModeSerie);
        // Format axis
        DateAxis dateAxis = new DateAxis("Dia");
        NumberAxis scoreAxis = new NumberAxis("Pontuação (Média diária)");
        // Define chart type
        XYSplineRenderer r = new XYSplineRenderer(3);
        XYPlot xyplot = new XYPlot(dataset, dateAxis, scoreAxis, r);
        // Generate chart
        JFreeChart chart = new JFreeChart(xyplot);
        seriesChartPanel = new ChartPanel(chart){

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };
    }

    private void generateScoreTable() {
        //scoreTable = new JTable(new DefaultTableModel(COLUMN_NAMES, controller.getScoreByPlayer(controller.getProfileOf(jc.getSelectedItem().toString()).getId()).size()));
        scoreTable = new JTable(new Object[][] {}, COLUMN_NAMES);
        scoreTable.setEnabled(false);
        scoreTable.setFillsViewportHeight(true); // Uses the entire height even withou enough scores
    }
    
    /**
     * Update method - Observer.
     * Defines this frame behavior according to the current state on ChallengeModel
     * 
     * @param o
     * @param o1 
     */
    @Override
    public void update(Observable o, Object o1) {
        
    }
}
