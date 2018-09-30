package seedu.address.model.analytics;


public class AverageStatistics extends Statistics {

    public static final StatisticType statisticType = StatisticType.AVERAGE;
    public static final int NUMBER_STATISTICS = 4;
    private static final String AVERAGE_TYPE_DAY = "per day";
    private static final String AVERAGE_TYPE_PATIENT = "per patient";

    private static final String NUMBER_PATIENTS_DAY = statisticType + " number of patients " + AVERAGE_TYPE_DAY;
    private static final String TIME_WAITING_PATIENT = statisticType + " waiting time " + AVERAGE_TYPE_PATIENT;
    private static final String NUMBER_MEDICINES_DAY = statisticType + " number of medicines dispensed " + AVERAGE_TYPE_DAY;
    private static final String NUMBER_MEDICINES_PATIENT = statisticType + " number of medicines dispensed " + AVERAGE_TYPE_PATIENT;

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
