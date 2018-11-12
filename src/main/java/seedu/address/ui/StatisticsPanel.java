package seedu.address.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the statistics of orders.
 */
public class StatisticsPanel extends UiPart<Region> {

    private static final String FXML = "Statistics.fxml";
    private final Logger logger = LogsCenter.getLogger(StatisticsPanel.class);

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label orderCount;

    @FXML
    private ProgressBar orderProgress;

    @FXML
    private Label trendingFood;

    public StatisticsPanel() {
        super(FXML);
    }

    /**
     * Updates the bar graph in the component with orders over the past 7 days. It does this by calling
     * the pollLastEntry method from a balanced binary search tree
     *
     * @param historyDate TreeMap of order count arranged by their day/month
     */
    public void initialize(TreeMap<Date, Integer> historyDate) {
        xAxis.setLabel("Date");
        yAxis.setLabel("Order Count");
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);

        TreeMap<Date, Integer> history = new TreeMap<>(historyDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

        Stack<Map.Entry<Date, Integer>> storage = new Stack<>();
        for (int i = 0; i < 7; ++i) {

            if (history.isEmpty()) {
                break;
            }

            Map.Entry<Date, Integer> entry = history.pollLastEntry();
            storage.push(entry);
        }

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        while (!storage.empty()) {
            Map.Entry<Date, Integer> entry = storage.pop();
            series1.getData().add(new XYChart.Data<>(dateFormat.format(entry.getKey()), entry.getValue()));
        }

        barChart.getData().clear();
        barChart.getData().add(series1);
    }

    /**
     * Updates the labels in the statistics panel based on information given
     *
     * @param count Total count of orders
     * @param progress Percentage of orders that are pending
     *  @param food The most popular food item
     */
    public void updateLabels(int count, double progress, String food) {

        logger.info("PROGRESS " + progress);

        orderCount.setText("Total Orders: " + Integer.toString(count));
        orderProgress.setProgress(progress);
        trendingFood.setText("Trending: " + food);
    }

    public void clear() {
        barChart.getData().clear();
    }
}
