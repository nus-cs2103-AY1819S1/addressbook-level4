package seedu.address.model.route.exceptions;

/**
 * Signals that the operation will result in duplicate Routes (Routes are considered duplicates if they have the same
 * identity).
 */
public class DuplicateRouteException extends RuntimeException {
    public DuplicateRouteException() {
        super("Operation would result in duplicate routes");
    }
}
