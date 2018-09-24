package seedu.address.model;

public class TotalStatistics extends Statistics {

    public static final String STATISTICS_TYPE = "Total";

    private static final String NUMBER_DAYS = STATISTICS_TYPE + " number of days of operation (inclusive)";
    private static final String NUMBER_PATIENTS = STATISTICS_TYPE + " number of patients to date";
    private static final String NUMBER_MEDICINES = STATISTICS_TYPE + " number of medicines dispensed to date";

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
