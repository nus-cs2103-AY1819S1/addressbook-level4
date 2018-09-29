package seedu.address.model.person;

/**
 * Represents a Task's status in the address book.
 * There are two possible status: IN_PROGRESS and FINISHED.
 */
public enum Status {
    IN_PROGRESS("IN PROGRESS"), FINISHED("FINISHED");
    
    private String statusValue;
    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status should only have the value IN PROGRESS or FINISHED";
    
    /**
     * Constructs a {@code Status}.
     *
     * @param statusValue A valid statusValue.
     */
    Status(String statusValue) {
        this.statusValue = statusValue;
    }

    /**
     * Returns true if a given string is a valid Status value.
     */
    public static boolean isValidStatus(String value) {
        try {
            return value.equals("IN PROGRESS") || value.equals("FINISHED");
        } catch(NullPointerException ex) {
            return false;
        }
    }

    /**
     * Returns the corresponding status object of {@param value}.
     */
    public static Status getStatusFromValue(String value) {
        if(!isValidStatus(value)) {
            throw new IllegalArgumentException();
        }
        return value.equals("IN PROGRESS") ? Status.IN_PROGRESS : Status.FINISHED;
    }
    
    @Override
    public String toString() {
        return statusValue;
    }
}