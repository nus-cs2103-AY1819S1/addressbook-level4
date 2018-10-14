package seedu.address.model.ride.exceptions;

/**
 * Signals that the attribute type is invalid. Valid attribute classes would be WaitTime and Maintenance.
 */
public class InvalidNumericAttributeException extends RuntimeException {
    public InvalidNumericAttributeException() {
        super("No such Numeric Attribute Type. Please input a valid attribute type");
    }
}
