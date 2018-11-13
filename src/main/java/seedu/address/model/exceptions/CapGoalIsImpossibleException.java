package seedu.address.model.exceptions;

/**
 * Flags that the CAP Goal is impossible to achieve.
 */
public class CapGoalIsImpossibleException extends RuntimeException {
    public CapGoalIsImpossibleException() {
        super("CAP Goal is not achievable!");
    }
}
