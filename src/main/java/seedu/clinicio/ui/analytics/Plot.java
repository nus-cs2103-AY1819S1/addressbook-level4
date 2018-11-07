package seedu.clinicio.ui.analytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javafx.scene.chart.Axis;
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
            plotVerticalBar((VisualizationData<String>) data, chartPane);
            break;
        case HORIZONTAL_BAR:
            plotHorizontalBar((VisualizationData<String>) data, chartPane);
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
    public static void plotStackedBar(VisualizationData data, Pane chartPane) {
        Tuple<Axis, Axis> axes = getAxes(data.getXTitle(), data.getYTitle(), false);
        StackedBarChart<String, Number> stackedBarChart = getStackedBarChart(axes, data.getId(), data.getChartTitle());
        List<XYChart.Series> allSeries = getSeries(data.getDataGroups(), data.getDataGroupsLabels());

        for (XYChart.Series series : allSeries) {
            stackedBarChart.getData().add(series);
        }
        stackedBarChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(stackedBarChart);
    }

    /**
     * Plots a vertical bar chart for categorical data.
     */
    public static void plotVerticalBar(VisualizationData data, Pane chartPane) {
        Tuple<Axis, Axis> axes = getAxes(data.getXTitle(), data.getYTitle(), false);
        BarChart<String, Number> verticalBarChart = getVerticalBarChart(axes, data.getId(), data.getChartTitle());
        List<XYChart.Series> allSeries = getSeries(data.getDataGroups(), data.getDataGroupsLabels());

        for (XYChart.Series series : allSeries) {
            verticalBarChart.getData().add(series);
        }
        verticalBarChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(verticalBarChart);
    }

    /**
     * Plots a horizontal bar chart for categorical data.
     */
    public static void plotHorizontalBar(VisualizationData data, Pane chartPane) {
        Tuple<Axis, Axis> axes = getAxes(data.getXTitle(), data.getYTitle(), false);
        BarChart<Number, String> horizontalBarChart = getHorizontalBarChart(axes, data.getId(), data.getChartTitle());
        List<XYChart.Series> allSeries = getSeries(data.getDataGroups(), data.getDataGroupsLabels());

        for (XYChart.Series series : allSeries) {
            horizontalBarChart.getData().add(series);
        }
        horizontalBarChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(horizontalBarChart);
    }

    /**
     * @return a pair of axes for a chart.
     */
    public static Tuple<Axis, Axis> getAxes(String xTitle, String yTitle, boolean isContinuous) {
        Axis xAxis;
        if (isContinuous) {
            xAxis = new NumberAxis();
            xAxis.setLabel(xTitle);
        } else {
            xAxis = new CategoryAxis();
            xAxis.setLabel(xTitle);
        }

        NumberAxis yAxis = new NumberAxis(0, 25, 5);
        yAxis.setLabel(yTitle);
        return new Tuple<>(xAxis, yAxis);
    }

    /**
     * @return A stacked bar chart with the appropriate axes, id and title.
     */
    public static StackedBarChart<String, Number> getStackedBarChart(Tuple<Axis, Axis> axes, String id, String title) {
        StackedBarChart<String, Number> stackedBarChart = new StackedBarChart<String, Number>(axes.getKey(), axes.getValue());
        stackedBarChart.setTitle(title);
        stackedBarChart.setId(id);
        return stackedBarChart;
    }

    /**
     * @return a vertical bar chart with the appropriate axes, id and title.
     */
    public static BarChart<String, Number> getVerticalBarChart(Tuple<Axis, Axis> axes, String id, String title) {
        BarChart<String, Number> verticalBarChart = new BarChart<>(axes.getKey(), axes.getValue());
        verticalBarChart.setTitle(title);
        verticalBarChart.setId(id);
        return verticalBarChart;
    }

    /**
     * @return a horizontal bar chart with the appropriate axes, id and title.
     */
    public static BarChart<Number, String> getHorizontalBarChart(Tuple<Axis, Axis> axes, String id, String title) {
        BarChart<Number, String> horizontalBarChart = new BarChart<>(axes.getValue(), axes.getKey());
        horizontalBarChart.setTitle(title);
        horizontalBarChart.setId(id);
        return horizontalBarChart;
    }

    /**
     * @return one or more series of dat to be plotted on a chart.
     */
    public static List<XYChart.Series> getSeries(List<List<Tuple<?, Integer>>> dataGroups, List<String> dataGroupLabels) {
        List<XYChart.Series> allSeries = new ArrayList<>();

        for (int i = 0; i < dataGroups.size(); i++) {
            List<Tuple<?, Integer>> seriesData = dataGroups.get(i);

            XYChart.Series series = new XYChart.Series();
            series.setName(dataGroupLabels.get(i));
            for (Tuple dataPoint : seriesData) {
                series.getData().add(new XYChart.Data(dataPoint.getKey(), dataPoint.getValue()));
            }

            allSeries.add(series);
        }

        return allSeries;
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
                    // remove data point if the list of labels does not contain the data point's x value.
                    if (!data.getXLabels().contains(xAxisTick)) {
                       continue;
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

