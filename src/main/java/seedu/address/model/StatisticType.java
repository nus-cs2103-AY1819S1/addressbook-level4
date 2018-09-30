package seedu.address.model;

public enum StatisticType {
    AVERAGE ("Average"),
    TOTAL ("Total");

    private final String value;

    private StatisticType(String value) {
        this.value = value;
    }
}
