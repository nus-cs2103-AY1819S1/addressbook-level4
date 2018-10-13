package seedu.souschef.model.healthplan.exceptions;

/**
 *  class for hp exception
 */
public class DuplicateHpException extends RuntimeException {

    /**
     * Signals that the operation will result in duplicate Recipes
     * (Recipes are considered duplicates if they have the same
     * identity).
     */
    public DuplicateHpException() {
        super("Operation would result in duplicate plans");

    }

}
