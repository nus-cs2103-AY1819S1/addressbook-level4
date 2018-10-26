package seedu.address.model.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// @@author arsalanc-v2

/**
 * Represents statistics of a general type.
 */
public abstract class Statistics {

    // summary fields
    public final String SUMMARY_TODAY = "Today";
    public final String SUMMARY_WEEK = "This Week";
    public final String SUMMARY_MONTH = "This Month";
    public final String SUMMARY_YEAR = "This Year";

    protected Map<String, Integer> summaryStatistics;
    protected Map<String, Integer> visualizationStatistics;
    protected StatData statData;

    /**
     * Initializes maps to store statistic names as keys and their corresponding values.
     */
    public Statistics() {
        summaryStatistics = new HashMap<>();
        visualizationStatistics = new HashMap<>();
        statData = new StatData();
    }

    /**
     *
     */
    public void resetAllStatistics() {
        statData.resetSummaryStatistics();
        statData.resetVisualizationStatistics();
    }

    /**
     * Resets all summary statistics to 0.
     */
    public void resetSummaryStatistics() {
        statData.resetSummaryStatistics();

    }

    /**
     *
     */
    public void resetVisualizationStatistics() {
        visualizationStatistics.clear();
        visualizationStatistics.replaceAll((key, value) -> 0);

    }

    public abstract void computeSummaryStatistics();
    public abstract void computeVisualizationStatistics();

    public Map<String, Map<String, Integer>> getAllStatistics() {
        Map<String, Map<String, Integer>> allStats = new HashMap<>();
        allStats.put("summary", getSummaryStatistics());
        allStats.put("visualization", getVisualizationStatistics());
        return allStats;
    }

    /**
     * @return The HashMap containing all statistics of a type.
     */
    public Map<String, Integer> getSummaryStatistics() {
        computeSummaryStatistics();
        return summaryStatistics;
    }

    /**
     *
     */
    public Map<String, Integer> getVisualizationStatistics() {
        computeVisualizationStatistics();
        return visualizationStatistics;
    }

    /**
     * @return The names of the different summary statistics.
     */
    public Set<String> getSummaryKeys() {
        return summaryStatistics.keySet();
    }

    /**
     * @return A String consisting of a statistic and its value, each on a separate line.
     */
    @Override
    public String toString() {
        StringBuilder toDisplay = new StringBuilder();
        for (Map.Entry<String, Integer> entry : summaryStatistics.entrySet()) {
            toDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }

        for (Map.Entry<String, Integer> entry : visualizationStatistics.entrySet()) {
            toDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }

        return toDisplay.toString();
    }
}
