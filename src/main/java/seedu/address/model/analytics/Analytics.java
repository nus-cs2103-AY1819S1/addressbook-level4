package seedu.address.model.analytics;

//@@author arsalanc-v2
/**
 * Wrapper for all analytics functionality.
 */
public class Analytics {

    private Statistics averageStatistics;
    private Statistics totalStatistics;

    public Analytics() {
        averageStatistics = new AverageStatistics();
        totalStatistics = new TotalStatistics();
    }

    /**
     * @return The String representation for displaying of average statistics.
     */
    public String getAverageStatistics() {
        return averageStatistics.toString();
    }

    /**
     * @return The String representation for displaying total statistics.
     */
    public String getTotalStatistics() {
        return totalStatistics.toString();
    }
}
