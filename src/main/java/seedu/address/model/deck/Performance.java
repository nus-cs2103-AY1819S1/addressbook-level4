package seedu.address.model.deck;

/**
 * Represents a card's performance.
 */
public enum Performance {
    EASY,
    NORMAL,
    HARD;

    public static final String MESSAGE_PERFORMANCE_CONSTRAINTS =
            "Performance must be one of the strings {easy|normal|hard}";

    public static Performance type(String type) {
        return Performance.valueOf(type.toUpperCase());
    }
}
