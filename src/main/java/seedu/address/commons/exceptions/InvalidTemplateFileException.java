package seedu.address.commons.exceptions;

/**
 * Signals that the specified file is not a valid template file.
 */
public class InvalidTemplateFileException extends Exception {
    /**
     * @param message should contain relevant information on the failed constraint(s)
     */
    public InvalidTemplateFileException(String message) {
        super(message);
    }

    /**
     * @param message should contain relevant information on the failed constraint(s)
     * @param cause of the main exception
     */
    public InvalidTemplateFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
