package seedu.address.model.portation.exceptions;

/**
 * Represents an error of the file to be imported.
 */
public class InvalidImportFileException extends Exception {

    public InvalidImportFileException(String message) {
        super(message);
    }

    public InvalidImportFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
