package seedu.address.model.analytics.data;

import java.util.List;

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
}
