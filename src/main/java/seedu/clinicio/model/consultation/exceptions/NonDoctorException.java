package seedu.clinicio.model.consultation.exceptions;

/**
 * Signals that a Staff that was not a doctor was attempted to be assigned to a consultation.
 */
public class NonDoctorException extends RuntimeException {
    public NonDoctorException() {
        super("Only a doctor can be assigned to a consultation");
    }
}
