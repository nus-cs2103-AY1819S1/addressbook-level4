package seedu.address.model.deck;

/**
 * Represents a card's performance.
 */
public enum Performance {
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard");

    public static final String MESSAGE_PERFORMANCE_CONSTRAINTS =
            "Performance must be one of the strings {easy|normal|hard}";
    private final String type;

    Performance(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
