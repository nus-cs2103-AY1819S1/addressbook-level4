package seedu.address.commons.exceptions;
//@@author winsonhys

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Exception that is thrown when user enters a negative value.
 */
public class TooRichException extends ParseException {
    public static final String TOO_RICH_EXCEPTION_MESSAGE = "Are you sure you are richer than Bill Gates?";

    public TooRichException () {
        super(TOO_RICH_EXCEPTION_MESSAGE);
    }
}
