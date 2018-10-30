package seedu.clinicio.model.analytics;

//@@author arsalanc-v2

/**
 * Enum to represent the values of the different types of statistics.
 */
public enum StatisticType {
    PATIENT("patient"),
    APPOINTMENT("appointment"),
    DOCTOR("doctor"),
    MEDICINE("medicine"),
    CONSULTATION("consultation");

    private final String value;

    /**
     * @param value A string representing the value of the type of statistic.
     */
    StatisticType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
