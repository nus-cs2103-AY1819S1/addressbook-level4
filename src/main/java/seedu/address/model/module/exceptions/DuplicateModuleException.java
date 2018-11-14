package seedu.address.model.module.exceptions;

/**
 * Signals that the operation would result in duplicated modules within the addressbook.
 * Modules are considered identical iff two modules are positive given the equals method.
 *
 * @author Ahan
 */
public class DuplicateModuleException extends RuntimeException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate modules");
    }
}
