package seedu.restaurant.ui.sales;

import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.ui.UiPart;

/**
 * Controller for a sales chart window. The sales data to plot in the line chart is given as a Map with Key = Date,
 * Value = Double
 */
public class SalesChartWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SalesChartWindow.class);
    private static final String FXML = "SalesChartWindow.fxml";

    private Map<Date, Double> salesData;

    @FXML
    private LineChart<String, Double> salesChart;


    /**
     * Creates a new SalesChartWindow
     *
     * @param root Stage to use as the root of the SalesChartWindow.
     * @param salesData the sales data to plot in the sales line chart represented by a Map.
     */
    public SalesChartWindow(Stage root, Map<Date, Double> salesData) {
        super(FXML, root);
        root.setTitle("Sales Chart");
        this.salesData = salesData;
    }

    /**
     * Creates a new SalesChartWindow.
     */
    public SalesChartWindow(Map<Date, Double> salesData) {
        this(new Stage(), salesData);
    }

    /**
     * Shows the SalesChartWindow.
     */
    public void show() {
        logger.fine("Displaying Sales Chart.");
        initialize();
        getRoot().show();
    }

    /**
     * Plots the sales line chart according to the sales data.
     */
    private void initialize() {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for (Entry<Date, Double> entry : salesData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        salesChart.getData().add(series);
    }

    /**
     * Returns true if the SalesChartWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
