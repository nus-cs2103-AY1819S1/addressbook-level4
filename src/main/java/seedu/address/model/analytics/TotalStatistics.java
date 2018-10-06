package seedu.address.model.analytics;

//@@author arsalanc-v2
/**
 * Represents statistics that are total numbers.
 */
public class TotalStatistics extends Statistics {

    public static final StatisticType STATISTIC_TYPE = StatisticType.TOTAL;
    public static final int NUMBER_STATISTICS = 3;

    // Different statistics fields.
    private static final String NUMBER_DAYS = STATISTIC_TYPE
            + " number of days of operation (inclusive)";
    private static final String NUMBER_PATIENTS = STATISTIC_TYPE
            + " number of patients to date";
    private static final String NUMBER_MEDICINES = STATISTIC_TYPE
            + " number of medicines prescribed to date";

    // Sets statistics to 0 and stores them in a HashMap.
    public TotalStatistics() {
        super();
        this.statistics.put(NUMBER_DAYS, 0);
        this.statistics.put(NUMBER_PATIENTS, 0);
        this.statistics.put(NUMBER_MEDICINES, 0);
    }

    @Override
    public void compute() {
        // placeholder
    }
}
