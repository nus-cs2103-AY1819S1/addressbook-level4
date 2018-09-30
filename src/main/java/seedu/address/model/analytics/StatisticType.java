package seedu.address.model.analytics;

/**
 * Enum to represent the values of the different types of statistics.
 */
public enum StatisticType {
    AVERAGE ("Average"),
    TOTAL ("Total");

    private final String value;

    StatisticType(String value) {
        this.value = value;
    }

    @Override public String toString() {
        return this.value;
    }
}
