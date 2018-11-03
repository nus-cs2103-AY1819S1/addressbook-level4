package seedu.clinicio.model.analytics.data;

import java.util.List;

import seedu.clinicio.model.analytics.ChartType;

//@@author arsalanc-v2

/**
 * Data structure for storing data needed to create visualizations.
 * Exposes data to be displayed through convenient methods.
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
     * Categorical Visualizations.
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param xLabels
     * @param dataGroups
     */
    public VisualizationData(String id, ChartType type, String chartTitle, String xTitle, String yTitle, List<String>
        xLabels, List<List<Tuple<T, Integer>>> dataGroups, List<String> dataGroupsLabels) {
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
     * Continuous visualizations.
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
     * @return the title for each group of data in this visualization.
     */
    public List<String> getDataGroupsLabels() {
        return dataGroupsLabels;
    }

    /**
     * @return all the groups of data to be displayed in this visualization.
     */
    public List<List<Tuple<T, Integer>>> getDataGroups() {
        return dataGroups;
    }
}
