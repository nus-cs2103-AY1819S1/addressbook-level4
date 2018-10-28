package seedu.address.model.analytics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.analytics.data.SummaryData;
import seedu.address.model.analytics.data.Tuple;
import seedu.address.model.analytics.data.VisualizationData;

//@@author arsalanc-v2

/**
 * A data structure for statistics.
 * Exposes data to be displayed through convenient methods.
 */
public class StatData {

    public static final int NUM_SUMMARY_ELEMENTS = 4;
    public static final int DEFAULT_SUMMARY_VALUE = 0;

    // lists are used to indicate ordering and avoid mutating hashmap keys
    // a list of lists is used to allow multiple summary statistics for v2.0 and beyond
    private SummaryData summaryData;
    private List<VisualizationData> allVisualizationData;

    public StatData() {
        allVisualizationData = new ArrayList<>();
    }

    /**
     *
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
     *
     * @param summaryValues
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

    public void resetSummaryValues() {
        summaryData.resetSummaryValues();
    }

    /**
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
     *
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





//    public Map<String, List> getAllData() {
//        return new HashMap<>() {{
//            put("summary", summaryData);
//            put("visualization", visualizationData);
//        }};
//    }

}
