package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import logic.ChallengeModel;
import logic.Score;
import logic.database.Controller;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.title.Title;
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
public final class JFrameScoreWindow extends JFrame implements Observer {
    private final Controller controller;
    private final ChallengeModel challengeModel;
    private Container mainContainer;
    private final JComboBox jc;
    private JTabbedPane tabs;
    private ChartPanel seriesChartPanel;
    private JTable scoreTable;
    private final String[] COLUMN_NAMES = {"Medalhas", "Score", "Tempo", "Data"}; 

    
    /**
     * Constructor.
     * Initializes model variables and define layout parameters.
     * 
     * @param controller
     * @param challengeModel 
     */
    public JFrameScoreWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 550);
    }
    
    public JFrameScoreWindow(Controller controller, ChallengeModel challengeModel, int x, int y, int width, int height) {
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
        final JPanel jPanelCenter = new JPanel(new FlowLayout());

        jPanelCenter.add(new JLabel("<HTML><B><FONT SIZE=64>Pontuações</FONT></B></HTML>"));
        
        jPanelCenter.add(jc);
        
        tabs = new JTabbedPane();
        
        generateScoreTable();
        
        generateSeriesChart();
        
        tabs.add(seriesChartPanel);
        
        tabs.add(scoreTable);
        
        //jPanelCenter.add(this)
        jPanelCenter.add(tabs);
        mainContainer.add(jPanelCenter);
    }

    /**
     * Method instantiates <b>South</b> components and its characteristics.
     */
    private void jPanelSouth() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Static listeners.
     */
    private void registerListeners() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    /**
     * Generate graph accordingly to selected user on JComboBox.
     */
    private void generateSeriesChart() {
        XYSeries seriesChart = new XYSeries("Ola mundo");
        
        for (Score aux : controller.getScoreByPlayer(controller.getProfileOf(jc.getSelectedItem().toString()).getId())) {
            seriesChart.add(aux.getDateTime().getDay(), aux.getScore());
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(seriesChart);
        NumberAxis domain = new NumberAxis("x");
        NumberAxis range = new NumberAxis("f(x)");
        XYSplineRenderer r = new XYSplineRenderer(3);
        XYPlot xyplot = new XYPlot(dataset, domain, range, r);
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
        scoreTable = new JTable(new DefaultTableModel(COLUMN_NAMES, 10));
        scoreTable.setEnabled(false);
    }
}
