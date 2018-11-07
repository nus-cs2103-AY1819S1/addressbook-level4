package seedu.clinicio.model.analytics.data;

import java.util.List;

//@@author arsalanc-v2

/**
 * Data structure to store data required to create summaries.
 */
public class SummaryData {

    public static final int DEFAULT_SUMMARY_VALUE = 0;

    private String title;
    private List<Tuple<String, Integer>> summaryElements;

    public SummaryData(String title, List<Tuple<String, Integer>> summaryElements) {
        this.title = title;
        this.summaryElements = summaryElements;
    }

    public String getTitle() {
        return title;
    }

    public List<Tuple<String, Integer>> getSummaryElements() {
        return summaryElements;
    }

    public Tuple<String, Integer> getSummaryElement(int i) {
        return summaryElements.get(i);
    }

    /**
     * Reset summary values to their default of zero.
     */
    public void resetSummaryValues() {
        for (Tuple<String, Integer> summaryElement : summaryElements) {
            summaryElement.setValue(DEFAULT_SUMMARY_VALUE);
        }
    }
}
