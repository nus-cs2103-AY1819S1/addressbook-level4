package seedu.address.model.exceptions;

public class CapGoalIsImpossibleException extends RuntimeException {
    public CapGoalIsImpossibleException() {
        super("CAP Goal is not achievable!");
    }
}
