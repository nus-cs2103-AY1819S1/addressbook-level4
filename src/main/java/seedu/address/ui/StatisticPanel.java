package seedu.address.ui;

import java.util.LinkedHashMap;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

//@@author jonathantjm
/**
 * Panel containing the statistic information.
 */
public class StatisticPanel extends UiPart<Region> {
    private static final String FXML = "StatisticPanel.fxml";

    @FXML
    private Pane chartArea;

    public StatisticPanel (LinkedHashMap<String, Double> statsData) {
        super(FXML);
        setData(statsData);
    }

    public void setData(LinkedHashMap<String, Double> statsData) {
        chartArea.getChildren().clear();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");
        BarChart stats = new BarChart(xAxis, yAxis);
        stats.setTitle("Daily Expenditure");

        XYChart.Series series = new XYChart.Series();
        series.setName("Day Amounts");
        Set<String> keySet = statsData.keySet();
        for (String s : keySet) {
            series.getData().add(new XYChart.Data<>(s, statsData.get(s)));
        }

        stats.getData().add(series);

        chartArea.getChildren().add(stats);
    }

    public Pane getChartArea() {
        return chartArea;
    }
}
