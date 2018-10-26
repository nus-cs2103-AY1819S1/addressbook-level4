package seedu.address.model.analytics;

import java.util.ArrayList;
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


    // lists are used to indicate ordering and avoid mutating hashmap keys
    // a list of lists is used to allow multiple summary statistics for v2.0 and beyond
    private List<SummaryData> allSummaryData;
    private List<VisualizationData> allVisualizationData;

    public StatData() {
        allSummaryData = new ArrayList<>();
        allVisualizationData = new ArrayList<>();
    }

    /**
     *
     * @param summaryValues
     */
    public void addSummary(String title, List<String> summaryTexts, List<Integer> summaryValues) {
        assert summaryValues.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary texts";
        assert summaryValues.size() == NUM_SUMMARY_ELEMENTS : "There must be four summary values";

        List<Tuple<String, Integer>> newSummaryElements = new ArrayList<>();
       for (int i = 0; i < NUM_SUMMARY_ELEMENTS; i++) {
           // create a pair of text and value, and add it to the list
           newSummaryElements.add(new Tuple<String, Integer>(summaryTexts.get(i), summaryValues.get(i)));
       }

        allSummaryData.add(new SummaryData(title, newSummaryElements));
    }

    /**
     *
     * @param visualizationElements
     */
    public void addVisualization(String chartTitle, String xTitle, String yTitle, List<T> xLabels, List<?>
        yLabels, List<Tuple<?, ?>> dataPoints) {
        assert

            xLabels.getClass();
        allVisualizationData.add(new VisualizationData<, yLabels.getClass()>(chartTitle, xTitle,
            yTitle, xLabels, yLabels, dataPoints));
    }

    public List<SummaryData> getAllSummaryData() {
        return allSummaryData;
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
