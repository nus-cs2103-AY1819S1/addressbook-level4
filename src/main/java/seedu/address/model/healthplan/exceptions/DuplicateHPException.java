package seedu.address.model.healthplan.exceptions;

public class DuplicateHPException extends RuntimeException {

    public DuplicateHPException() {
        super("Operation would result in duplicate HealthPlanBook");
    }

}
