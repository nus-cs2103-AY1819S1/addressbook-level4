package seedu.clinicio.model.analytics.data;

import java.util.ArrayList;
import java.util.List;

import seedu.clinicio.model.analytics.ChartType;

//@@author arsalanc-v2

/**
 * A convenient wrapper for summary and visualization data for a particular class of statistics.
 * There can only be one set of summary data.
 * There can be multiple visualizations.
 * Lists of tuples are used to indicate ordering and avoid mutating {@code HashMap} keys
 */
public class StatData {

    public static final int NUM_SUMMARY_ELEMENTS = 4;
    public static final int DEFAULT_SUMMARY_VALUE = 0;

    private SummaryData summaryData;
    private List<VisualizationData> allVisualizationData;

    public StatData() {
        allVisualizationData = new ArrayList<>();
    }

    /**
     * Initializes summary data to default texts and values.
     * @param title
     * @param summaryTexts
     */
    public void initializeSummary(String title, List<String> summaryTexts) {
        assert summaryTexts.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary texts";

        List<Tuple<String, Integer>> newSummaryElements = new ArrayList<>();
        for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
            // create a pair of text and value, and add it to the list
            newSummaryElements.add(new Tuple<String, Integer>(summaryTexts.get(i), DEFAULT_SUMMARY_VALUE));
        }

        summaryData = new SummaryData(title, newSummaryElements);
    }

    /**
     * Updates summary data with supplied texts and values.
     */
    public void updateSummary(String title, List<String> summaryTexts, List<Integer> summaryValues) {
        assert summaryTexts.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary texts";
        assert summaryValues.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary values";

        List<Tuple<String, Integer>> newSummaryElements = new ArrayList<>();
        for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
            // create a pair of text and value, and add it to the list
            newSummaryElements.add(new Tuple<String, Integer>(summaryTexts.get(i), summaryValues.get(i)));
        }

        summaryData = new SummaryData(title, newSummaryElements);
    }

    /**
     * Resets all summary values to zero.
     */
    public void resetSummaryValues() {
        summaryData.resetSummaryValues();
    }

    /**
     * Creates a categorical visualization.
     * Requires the minimum amount of information from statistics classes to represent data to be visualized.
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param xLabels
     * @param dataGroups
     */
    public void addCategoricalVisualization(String id, ChartType type, String chartTitle, String xTitle, String yTitle,
        List<String> xLabels, List<List<Tuple<String, Integer>>> dataGroups, List<String> dataGroupsLabels) {

        allVisualizationData.add(new VisualizationData<String>(id, type, chartTitle, xTitle,
            yTitle, xLabels, dataGroups, dataGroupsLabels));
    }

    /**
     * Creates a continuous visualization.
     * Requires the minimum amount of information from statistics classes to represent data to be visualized.
     * @param type
     * @param chartTitle
     * @param xTitle
     * @param yTitle
     * @param dataGroups
     */
    public void addContinuousVisualization(String id, ChartType type, String chartTitle, String xTitle, String yTitle,
        List<List<Tuple<Integer, Integer>>> dataGroups, List<String> dataGroupsLabels) {

        allVisualizationData.add(new VisualizationData<Integer>(id, type, chartTitle, xTitle,
            yTitle, dataGroups, dataGroupsLabels));
    }

    public SummaryData getSummaryData() {
        return summaryData;
    }

    public List<VisualizationData> getVisualizationData() {
        return allVisualizationData;
    }
}
