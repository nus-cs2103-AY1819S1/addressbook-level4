package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import seedu.clinicio.model.analytics.data.SummaryData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;

//@@author arsalanc-v2

/**
 *
 *
 */
public class Plot {
    private static final int NUM_SUMMARY_ELEMENTS = 4;
    private static final int NUM_VISUALIZATION_ELEMENTS = 6;

    /**
     *
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
     *
     * @param allVisualizationData
     */
    public static void updateVisualization(List<VisualizationData> allVisualizationData, Pane chartPane) {
        if (allVisualizationData.size() < 1) {
            chartPane.setStyle("-fx-background-color: #fff");
            return;
        }

        for (VisualizationData data : allVisualizationData) {
            plotChart(data, chartPane);
        }
    }

    /**
     *
     * @param type
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
     *
     * @param barsData
     */
    public static void plotStackedBar(VisualizationData<String> barsData, Pane chartPane) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(barsData.getXTitle());
        xAxis.setTickLabelFill(Paint.valueOf("#fff"));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(barsData.getYTitle());
        yAxis.setTickLabelFill(Paint.valueOf("#fff"));

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

        stackedBarChart.setStyle("-fx-text-fill: red");
        stackedBarChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(stackedBarChart);
    }

    /**
     * Plots a vertical bar chart with a single bar for categorical data.
     * @param barsData
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

        barChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(barChart);
    }

    /**
     * Plots a vertical bar chart with a single bar for categorical data.
     * Assumes a single bar for each category.
     * @param barsData
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

        barChart.setPrefSize(chartPane.getWidth(), chartPane.getHeight());
        chartPane.getChildren().add(barChart);
    }
}

