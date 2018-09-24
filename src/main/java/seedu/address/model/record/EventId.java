package seedu.address.model.record;

/**
 * This is just a placeholder class for development purposes.
 * Actual record EventId should come from the event package
 */
public class EventId {
    public final String value;

    public EventId(String id) {
        this.value = id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventId // instanceof handles nulls
                && value.equals(((EventId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return value;
    }
}
