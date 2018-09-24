package seedu.address.model.record;

/**
 * This is just a placeholder class for development purposes.
 * Actual record VolunteerId should come from the volunteer package
 */
public class VolunteerId {
    public final String value;

    public VolunteerId(String id) {
        this.value = id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof VolunteerId // instanceof handles nulls
                && value.equals(((VolunteerId) other).value)); // state check
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
