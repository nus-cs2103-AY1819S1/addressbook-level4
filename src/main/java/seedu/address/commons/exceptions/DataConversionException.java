package seedu.address.commons.exceptions;

/**
 * Represents an error during conversion of data from one FORMAT to another
 */
public class DataConversionException extends Exception {
    public DataConversionException(Exception cause) {
        super(cause);
    }

}
