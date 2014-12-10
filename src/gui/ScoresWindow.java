package gui;

import database.DataController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import resources.Resources;

/**
 * JFrame Score Window class. This window presents all scores per user profile.
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
    private final String[] COLUMN_NAMES = {"Medalhas", "Score", "Nível", "Tempo", "Data"};
    private final JLabel backLabel = new JLabel("Voltar");

    /**
     * Constructor. Initializes model variables and define layout parameters.
     *
     * @param controller
     * @param challengeModel
     */
    public ScoresWindow(Controller controller, ChallengeModel challengeModel) {
        this(controller, challengeModel, 350, 75, 600, 500);
    }

    /**
     * Constructor. Initializes model variables and define layout parameters.
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

    /**
     * Create layout for view components.
     */
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
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
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
        tabs.add(new JScrollPane(scoreTable));
        tabs.setTitleAt(0, "Gráfico de Scores");
        tabs.setTitleAt(1, "Tabela de Scores");
        tabs.setPreferredSize(new Dimension(380, 330));

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
            }
        });

        jc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                refreshChartAndTable();
            }
        });
    }

    /**
     * Chart needs to be refreshed this way so updates are done in runtime.
     */
    private void refreshChartAndTable() {
        tabs.removeAll();
        tabs.revalidate(); // This removes the old chart and table

        generateSeriesChart();
        generateScoreTable();

        tabs.add(new JScrollPane(seriesChartPanel));
        tabs.add(new JScrollPane(scoreTable));

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

        for (Score aux : scores) {
            easyModeSerie.add(new Day(aux.getDateTime()), (double)aux.getFloatScore());
        }

        for(Score aux : new DataController().getAvgScoreByPlayer(controller.getProfileOf(jc.getSelectedItem().toString()).getId(), Level.MODE_HARD))
                hardModeSerie.addOrUpdate(new Day(aux.getDateTime()), (double)aux.getFloatScore());

        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        // Add series
        dataset.addSeries(easyModeSerie);
        dataset.addSeries(hardModeSerie);
        // Format axis
        DateAxis dateAxis = new DateAxis("Dia");
        NumberAxis scoreAxis = new NumberAxis("Pontuação (Média diária)");
        scoreAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        // Define chart type
        XYSplineRenderer r = new XYSplineRenderer(1);
        XYPlot xyplot = new XYPlot(dataset, dateAxis, scoreAxis, r);
        // Generate chart
        JFreeChart chart = new JFreeChart(xyplot);
        seriesChartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };
    }

    /**
     * Generate a updated version of the score table, depending on user selected
     * on the combo box.
     */
    private void generateScoreTable() {
        List<Score> scores = controller.getScoreByPlayer((controller.getProfileOf(jc.getSelectedItem().toString())).getId());
        Object[][] tableContent = new Object[scores.size()][5];
        // Initialize panels cells
        for (int i = 0; i < tableContent.length; i++) {
            for (int j = 0; j < tableContent[i].length; j++) {
                tableContent[i][j] = new JPanel();
            }
        }

        for (int i = 0; i < tableContent.length; i++) {
            // Medals
            tableContent[i][0] = "" + scores.get(i).getGold() + scores.get(i).getSilver() + scores.get(i).getBronze();

            // Score
            tableContent[i][1] = (Integer)scores.get(i).getScore();

            // Level
            if (scores.get(i).getLevel() == Level.MODE_EASY) {
             tableContent[i][2] = "Fácil";
             } else
             tableContent[i][2] = "Difícil";

            // Duration
            tableContent[i][3] = "" + scores.get(i).getDuration();

            // Date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
            String date = sdf.format(scores.get(i).getDateTime());
            tableContent[i][4] = date;
        }

        scoreTable = new JTable(new MyTableModel(tableContent, COLUMN_NAMES));
        scoreTable.setRowHeight(32);
        scoreTable.getColumnModel().getColumn(scoreTable.getColumnCount()-1).setMinWidth(100);
        scoreTable.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        scoreTable.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        scoreTable.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        scoreTable.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        scoreTable.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );    
        scoreTable.setAutoCreateRowSorter(true);
        scoreTable.getRowSorter().toggleSortOrder(1);
        scoreTable.getRowSorter().toggleSortOrder(1);
        scoreTable.setEnabled(false);
        scoreTable.setFillsViewportHeight(true); // Uses the entire height even withou enough scores

    }
    
    /**
     * My Table Model.
     * 
     * This class is used so we're able to sort table contents from column 1 (Score)
     * as Integer values.
     */
    class MyTableModel extends DefaultTableModel implements TableModel {

        public MyTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }
        
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 1)
                return Integer.class;
            else
                return Object.class;
        }
    }
    
    /**
     * Image Renderer class.
     * 
     * To insert imageIcon on cells it's necessary to create a TableCellRenderer.
     * Draws medals according to number on cell: "111" OR "11" OR "1".
     */
    class ImageRenderer extends JPanel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
            JLabel label;
            this.removeAll();
            setPreferredSize(new Dimension(20, 20));
            setBackground(Color.WHITE);
            switch (Integer.parseInt((String)o)) {
                case 111: // Gold, Silver, Bronze
                    label = new JLabel(Resources.getImageResized(Resources.getImageMedalGold(), 16, 25));
                    add(label);
                case 11: // Silver, Bronze
                    label = new JLabel(Resources.getImageResized(Resources.getImageMedalSilver(), 16, 25));
                    add(label);
                case 1: // Bronze
                    label = new JLabel(Resources.getImageResized(Resources.getImageMedalBronze(), 16, 25));
                    add(label);
                    break;
                default: // None
                    break;
            }
            return this;
        }
    }

    /**
     * Update method - Observer. Defines this frame behavior according to the
     * current state on ChallengeModel
     *
     * @param o
     * @param o1
     */
    @Override
    public void update(Observable o, Object o1) {

    }
}
