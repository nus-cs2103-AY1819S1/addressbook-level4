package seedu.address.model.analytics.data;

import java.util.List;

import seedu.address.model.analytics.ChartType;

//@@author arsalanc-v2

/**
 *
 */
public class VisualizationData<T> {

    public static final int NUM_VISUALIZATION_ELEMENTS = 6;

    // fields for a chart
    private String id;
    private ChartType type;
    private String chartTitle;
    private String xTitle;
    private String yTitle;
    private List<String> xLabels;
    private List<List<Tuple<T, Integer>>> dataGroups;
    private List<String> dataGroupsLabels;

    /**
     * Categorical
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param xLabels
     * @param dataGroups
     */
    public VisualizationData(String id, ChartType type, String chartTitle, String xTitle, String yTitle, List<String>
        xLabels,
        List<List<Tuple<T, Integer>>> dataGroups, List<String> dataGroupsLabels) {

        this.id = id;
        this.type = type;
        this.chartTitle = chartTitle;
        this.xTitle = xTitle;
        this.yTitle = yTitle;
        this.xLabels = xLabels;
        this.dataGroups = dataGroups;
        this.dataGroupsLabels = dataGroupsLabels;
    }

    /**
     * Continuous
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param dataGroups
     */
    public VisualizationData(String id, ChartType type, String chartTitle, String xTitle, String yTitle,
        List<List<Tuple<T, Integer>>> dataGroups, List<String> dataGroupsLabels) {

            this.id = id;
            this.type = type;
            this.chartTitle = chartTitle;
            this.xTitle = xTitle;
            this.yTitle = yTitle;
            this.dataGroups = dataGroups;
            this.dataGroupsLabels = dataGroupsLabels;
    }

    public String getId() {
        return id;
    }

    public ChartType getChartType() {
        return type;
    }

    public String getChartTitle() {
        return chartTitle;
    }

    public String getXTitle() {
        return xTitle;
    }

    public String getYTitle() {
        return yTitle;
    }

    public List<?> getXLabels() {
        return xLabels;
    }

    /**
     *
     */
    public List<String> getDataGroupsLabels() {
        return dataGroupsLabels;
    }

    public List<List<Tuple<T, Integer>>> getDataGroups() {
        return dataGroups;
    }
}
