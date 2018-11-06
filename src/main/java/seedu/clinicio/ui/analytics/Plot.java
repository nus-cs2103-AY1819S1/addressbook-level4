package seedu.clinicio.ui.analytics;

import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
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

        case LINE:
            plotLine(data, chartPane);
            break;

        case AREA:
            break;

        default:
            break;
        }
    }

    /**
     * Plots a stacked bar chart.
     * Assumes a minimum of two groups of data.
     */
    public static void plotStackedBar(VisualizationData<String> data, Pane chartPane) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(data.getXTitle());

        NumberAxis yAxis = new NumberAxis(0, 25, 5);
        yAxis.setLabel(data.getYTitle());

        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<String, Number>(xAxis, yAxis);
        stackedBarChart.setTitle(data.getChartTitle());
        stackedBarChart.setId(data.getId());

        for (int i = 0; i < data.getDataGroups().size(); i++) {
            List<Tuple<String, Integer>> seriesData = data.getDataGroups().get(i);

            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(data.getDataGroupsLabels().get(i));
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
    public static void plotVBar(VisualizationData<String> data, Pane chartPane) {
        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(data.getXTitle());

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(data.getYTitle());

        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(data.getChartTitle());
        barChart.setId(data.getId());

        for (int i = 0; i < data.getDataGroups().size(); i++) {
            List<Tuple<String, Integer>> seriesData = data.getDataGroups().get(i);

            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(data.getDataGroupsLabels().get(i));
            for (Tuple<String, Integer> dataPoint : seriesData) {
                series.getData().add(new XYChart.Data<String, Number>(dataPoint.getKey(), dataPoint.getValue()));
            }

            barChart.getData().add(series);
        }

        barChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(barChart);
    }

    /**
     * Plots a horizontal bar chart for categorical data.
     */
    public static void plotHBar(VisualizationData<String> data, Pane chartPane) {
        final CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel(data.getYTitle());

        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(data.getXTitle());

        final BarChart<Number, String> barChart = new BarChart<Number, String>(xAxis, yAxis);
        barChart.setTitle(data.getChartTitle());
        barChart.setId(data.getId());

        for (int i = 0; i < data.getDataGroups().size(); i++) {
            XYChart.Series<Number, String> series = new XYChart.Series<Number, String>();
            series.setName(data.getDataGroupsLabels().get(i));

            List<Tuple<String, Integer>> seriesData = data.getDataGroups().get(i);
            for (Tuple<String, Integer> dataPoint : seriesData) {
                series.getData().add(new XYChart.Data<Number, String>(dataPoint.getValue(), dataPoint.getKey()));
            }

            barChart.getData().add(series);
        }

        barChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(barChart);
    }

    /**
     * Plots a line chart for continuous data.
     * May have String or Integer labels on x axis.
     * For String labels, data points must consist of String, Integer pairs. The String components of all the pairs that
     * match the supplied labels will be shown as the  x axis tick labels while those that don't match will be removed.
     */
    public static void plotLine(VisualizationData data, Pane chartPane) {
        // check x axis is to have String labels.
        if (data.getXLabels() != null) {
            data = (VisualizationData<String>) data;

            // hide axis, keep match
            final CategoryAxis xAxis = new CategoryAxis();
            xAxis.setLabel(data.getXTitle());

            final NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel(data.getYTitle());

            final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
            lineChart.setTitle(data.getChartTitle());

            for (int i = 0; i < data.getDataGroups().size(); i++) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName((String) data.getDataGroupsLabels().get(i));

                List<Tuple<String, Integer>> seriesData = (List<Tuple<String, Integer>>) data.getDataGroups().get(i);
                for (Tuple<String, Integer> dataPoint : seriesData) {
                    String xAxisTick = dataPoint.getKey();
                    if (!data.getXLabels().contains(xAxisTick)) {
                        xAxisTick = "";
                    }
                    series.getData().add(new XYChart.Data<String, Number>(xAxisTick, dataPoint.getValue()));
                }

                lineChart.getData().add(series);
            }

            lineChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
            chartPane.getChildren().add(lineChart);
        }
    }
}

