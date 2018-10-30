package seedu.clinicio.model.analytics.data;

import java.util.List;

//@@author arsalanc-v2

/**
 *
 */
public class SummaryData {

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
     *
     */
    public void resetSummaryValues() {
        for (Tuple<String, Integer> summaryElement : summaryElements) {
            summaryElement.setValue(0);
        }
    }
}
