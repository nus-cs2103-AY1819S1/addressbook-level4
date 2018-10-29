package seedu.address.model.doctor.exceptions;

//@@author jjlee050
/**
 * Signals that the operation will result in duplicate Doctors (Doctors are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDoctorException extends RuntimeException {
    public DuplicateDoctorException() {
        super("Operation would result in duplicate doctors");
    }
}
