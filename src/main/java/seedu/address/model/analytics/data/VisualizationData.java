package seedu.address.model.analytics.data;

import java.util.List;

public class VisualizationData<T, U> {

    public static final int NUM_VISUALIZATION_ELEMENTS = 6;

    private String chartTitle;
    private String xTitle;
    private String yTitle;
    private List<T> xLabels;
    private List<U> yLabels;
    private List<Tuple<T, U>> dataPoints;

    public VisualizationData(String chartTitle, String xTitle, String yTitle, List<T> xLabels, List<U>
        yLabels, List<Tuple<T, U>> dataPoints) {
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.xLabels = xLabels;
        this.yLabels = yLabels;
        this.dataPoints = dataPoints;
    }

    public VisualizationData(List visualizationElements) {
        assert visualizationElements.size() == NUM_VISUALIZATION_ELEMENTS : "There must be six visualization " +
            "elements";
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.xLabels = xLabels;
        this.yLabels = yLabels;
        this.dataPoints = dataPoints;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public String getxTitle() {
        return xTitle;
    }

    public String getyTitle() {
        return yTitle;
    }

    public List<T> getxLabels() {
        return xLabels;
    }

    public List<U> getyLabels() {
        return yLabels;
    }

    public List<Tuple<T, U>> getDataPoints() {
        return dataPoints;
    }
}
