package seedu.souschef.model.healthplan.exceptions;

/**
 * Exception thrown when duplicate health plan is found
 */
public class DuplicateHpException extends RuntimeException {

    public DuplicateHpException() {
        super("Operation would result in duplicate HealthPlanBook");
    }

}
