package seedu.address.model;

/**
 * Wrapper for all analytics functionality.
 */
public class Analytics {

    private Statistics averageStatistics;
    private Statistics totalStatistics;

    public Analytics() {
        averageStatistics = new AverageStatistics();
        totalStatistics =  new TotalStatistics();
    }

    public String getAverageStatistics() {
        return averageStatistics.toString();
    }

    public String getTotalStatistics() {
        return totalStatistics.toString();
    }
}
