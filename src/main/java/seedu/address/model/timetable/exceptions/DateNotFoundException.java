package seedu.address.model.timetable.exceptions;

/**
 * Signals that the operation is unable to find the specified date.
 */
public class DateNotFoundException extends RuntimeException {

    public DateNotFoundException() {
        super("The date does not exist on the schedule.");
    }
}
