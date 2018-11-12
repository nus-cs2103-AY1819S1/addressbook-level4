package seedu.address.logic.parser.fileparser.exceptions;

/**
 * Represents a parse error encountered by a file parser.
 */
public class FileParseException extends IllegalArgumentException {

    public FileParseException(String message) {
        super(message);
    }

    public FileParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
