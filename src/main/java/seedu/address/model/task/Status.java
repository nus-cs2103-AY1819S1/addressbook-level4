package seedu.address.model.task;

/**
 * Represents a Task's status in the task manager.
 * There are three possible status: IN_PROGRESS, COMPLETED and OVERDUE.
 */
public enum Status {
    IN_PROGRESS("IN PROGRESS"), COMPLETED("COMPLETED"), OVERDUE("OVERDUE");

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status should only have the value IN PROGRESS, COMPLETED or OVERDUE";
    private String statusName;

    /**
     * Constructs a {@code Status}.
     *
     * @param statusName A valid statusName.
     */
    Status(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Returns true if a given string is a valid Status name.
     */
    public static boolean isValidStatus(String value) {
        try {
            return value.equals("IN PROGRESS") || value.equals("COMPLETED") || value.equals("OVERDUE");
        } catch (NullPointerException ex) {
            return false;
        }
    }

    /**
     * Returns the corresponding status object of {@param name}.
     */
    public static Status fromString(String name) {
        if (!isValidStatus(name)) {
            throw new IllegalArgumentException();
        }
        return name.equals("IN PROGRESS") ? Status.IN_PROGRESS : Status.valueOf(name);
    }

    @Override
    public String toString() {
        return statusName;
    }

}
