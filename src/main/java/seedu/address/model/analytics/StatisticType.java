package seedu.address.model.analytics;

public enum StatisticType {
    AVERAGE ("Average"),
    TOTAL ("Total");

    private final String value;

    StatisticType(String value) {
        this.value = value;
    }
}
