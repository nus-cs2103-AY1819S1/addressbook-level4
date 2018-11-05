package seedu.address.model.deck;

/**
 * Represents a card's performance.
 */
public enum Performance {
    DEFAULT("default"),
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard");

    public static final String MESSAGE_PERFORMANCE_CONSTRAINTS =
            "Performance must be one of the strings {easy|normal|hard}";
    private final String description;

    Performance(String description) {
        this.description = description;
    }

    public String toString() {
        return description;
    }
}
