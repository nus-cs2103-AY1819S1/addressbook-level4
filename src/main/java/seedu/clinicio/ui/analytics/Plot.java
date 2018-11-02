package seedu.clinicio.ui.analytics;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import seedu.clinicio.model.analytics.data.SummaryData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;

//@@author arsalanc-v2

/**
 * Contains methods to represent analytics data onto a user interface.
 */
public class Plot {
    private static final int NUM_SUMMARY_ELEMENTS = 4;
    private static final int NUM_VISUALIZATION_ELEMENTS = 6;

    /**
     * Edits ui labels to show summary texts and corresponding values.
     */
    public static void fillSummary(SummaryData summaryData, Label summaryBar, List<Tuple<Label, Label>> summaryLabels) {
        if (summaryData == null) {
            summaryBar.setText("No summary data available");
            return;
        }

        summaryBar.setText(summaryData.getTitle());
        for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
            Tuple<String, Integer> summaryElementI = summaryData.getSummaryElement(i);
            // set text for summary text label
            summaryLabels.get(i).getKey().setText(summaryElementI.getKey().toString());
            // set text for summary value label
            summaryLabels.get(i).getValue().setText(summaryElementI.getValue().toString());
            i++;
        }
    }

    /**
     * Updates {@code chartPane} with the appropriate visualizations, if they exist.
     */
    public static void updateVisualization(List<VisualizationData> allVisualizationData, Pane chartPane) {
        if (allVisualizationData.size() < 1) {
            chartPane.setStyle("-fx-background-color: #fff");
            return;
        }

        // TO CHANGE
        for (VisualizationData data : allVisualizationData) {
            plotChart(data, chartPane);
        }
    }

    /**
     * Decides which chart type to plot.
     * @param data The data to plot.
     * @param chartPane The pane to be plotted on.
     */
    public static void plotChart(VisualizationData data, Pane chartPane) {
        switch (data.getChartType()) {
        case VERTICAL_BAR:
            plotVBar((VisualizationData<String>) data, chartPane);
            break;
        case HORIZONTAL_BAR:
            plotHBar((VisualizationData<String>) data, chartPane);
            break;
        case STACKED_BAR:
            plotStackedBar((VisualizationData<String>) data, chartPane);
            break;
        case AREA:
            break;
        case LINE:
            break;
        default:
            break;
        }
    }

    /**
     * Plots a stacked bar chart.
     * Assumes a minimum of two groups of data.
     */
    public static void plotStackedBar(VisualizationData<String> barsData, Pane chartPane) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(barsData.getXTitle());

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(barsData.getYTitle());

        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<String, Number>(xAxis, yAxis);
        stackedBarChart.setTitle(barsData.getChartTitle());
        stackedBarChart.setId(barsData.getId());

        for (int i = 0; i < barsData.getDataGroups().size(); i++) {
            List<Tuple<String, Integer>> seriesData = barsData.getDataGroups().get(i);

            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(barsData.getDataGroupsLabels().get(i));
            for (Tuple<String, Integer> dataPoint : seriesData) {
                series.getData().add(new XYChart.Data<String, Number>(dataPoint.getKey(), dataPoint.getValue()));
            }

            stackedBarChart.getData().add(series);
        }

        stackedBarChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(stackedBarChart);
    }

    /**
     * Plots a vertical bar chart for categorical data.
     */
    public static void plotVBar(VisualizationData<String> barsData, Pane chartPane) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(barsData.getXTitle());

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(barsData.getYTitle());

        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(barsData.getChartTitle());
        barChart.setId(barsData.getId());

        for (int i = 0; i < barsData.getDataGroups().size(); i++) {
            List<Tuple<String, Integer>> seriesData = barsData.getDataGroups().get(i);

            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(barsData.getDataGroupsLabels().get(i));
            for (Tuple<String, Integer> dataPoint : seriesData) {
                series.getData().add(new XYChart.Data<String, Number>(dataPoint.getKey(), dataPoint.getValue()));
            }

            barChart.getData().add(series);
        }

        // remove the legend if there is only one group of data
        if (barsData.getDataGroups().size() < 2) {
            Node legend = barChart.lookup(".chart-legend");
            legend.setStyle("visibility: hidden");
            legend.setVisible(false);
            legend.setManaged(false);
        }

        barChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(barChart);
    }

    /**
     * Plots a horizontal bar chart for categorical data.
     */
    public static void plotHBar(VisualizationData<String> barsData, Pane chartPane) {
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel(barsData.getYTitle());

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(barsData.getXTitle());

        BarChart<Number, String> barChart = new BarChart<Number, String>(xAxis, yAxis);
        barChart.setTitle(barsData.getChartTitle());
        barChart.setId(barsData.getId());

        for (int i = 0; i < barsData.getDataGroups().size(); i++) {
            List<Tuple<String, Integer>> seriesData = barsData.getDataGroups().get(i);

            XYChart.Series<Number, String> series = new XYChart.Series<Number, String>();
            series.setName(barsData.getDataGroupsLabels().get(i));
            for (Tuple<String, Integer> dataPoint : seriesData) {
                series.getData().add(new XYChart.Data<Number, String>(dataPoint.getValue(), dataPoint.getKey()));
            }

            barChart.getData().add(series);
        }

        // remove the legend if there is only one group of data
        if (barsData.getDataGroups().size() < 2) {
            Node legend = barChart.lookup(".chart-legend");
            legend.setStyle("visibility: hidden");
            legend.setVisible(false);
            legend.setManaged(false);
        }

        barChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(barChart);
    }
}

