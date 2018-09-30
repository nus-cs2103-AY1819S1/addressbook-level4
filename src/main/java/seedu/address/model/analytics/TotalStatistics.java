package seedu.address.model.analytics;

/**
 * Represents statistics that are total numbers.
 */
public class TotalStatistics extends Statistics {

    public static final StatisticType statisticType = StatisticType.TOTAL;
    public static final int NUMBER_STATISTICS = 3;
    private static final String NUMBER_DAYS = statisticType + " number of days of operation (inclusive)";
    private static final String NUMBER_PATIENTS = statisticType + " number of patients to date";
    private static final String NUMBER_MEDICINES = statisticType + " number of medicines dispensed to date";

    public TotalStatistics() {
        super();
        this.statistics.put(NUMBER_DAYS, 0);
        this.statistics.put(NUMBER_PATIENTS, 0);
        this.statistics.put(NUMBER_MEDICINES, 0);
    }

    @Override
    public void compute() {

    }

    public static String displayTypes() {
        return NUMBER_DAYS + "\n"
                + NUMBER_PATIENTS + "\n"
                + NUMBER_MEDICINES + "\n";
    }
}
