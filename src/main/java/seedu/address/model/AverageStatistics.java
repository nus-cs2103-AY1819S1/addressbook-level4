package seedu.address.model;

public class AverageStatistics extends Statistics {

    public static final String STATISTICS_TYPE = "Average";
    public static final int NUMBER_STATISTICS = 4;
    private static final String AVERAGE_TYPE_DAY = "per day";
    private static final String AVERAGE_TYPE_PATIENT = "per patient";

    private static final String NUMBER_PATIENTS_DAY = STATISTICS_TYPE + " number of patients " + AVERAGE_TYPE_DAY;
    private static final String TIME_WAITING_PATIENT = STATISTICS_TYPE + " waiting time " + AVERAGE_TYPE_PATIENT;
    private static final String NUMBER_MEDICINES_DAY = STATISTICS_TYPE + " number of medicines dispensed " + AVERAGE_TYPE_DAY;
    private static final String NUMBER_MEDICINES_PATIENT = STATISTICS_TYPE + " number of medicines dispensed " + AVERAGE_TYPE_PATIENT;

    public AverageStatistics() {
        super();
        this.statistics.put(NUMBER_PATIENTS_DAY, 0);
        this.statistics.put(TIME_WAITING_PATIENT, 0);
        this.statistics.put(NUMBER_MEDICINES_DAY, 0);
        this.statistics.put(NUMBER_MEDICINES_PATIENT, 0);
    }

    @Override
    public void compute() {

    }

    public static String displayTypes() {
        return NUMBER_PATIENTS_DAY + "\n"
                + TIME_WAITING_PATIENT + "\n"
                + NUMBER_MEDICINES_DAY + "\n"
                + NUMBER_MEDICINES_PATIENT + "\n";
    }
}
