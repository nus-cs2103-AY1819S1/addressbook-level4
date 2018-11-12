package seedu.address.logic.commands.exceptions;

import static seedu.address.commons.core.Messages.MESSAGE_USER_NOT_LOGGED_IN_FOR_COMMAND;

/**
 * Represents an error which occurs during execution of a {@link Command} that requires
 * the user to be logged in.
 */
public class NotLoggedInCommandException extends CommandException {

    /**
     * @param commandWord The commandWord of the command being called.
     */
    public NotLoggedInCommandException(String commandWord) {
        super(String.format(MESSAGE_USER_NOT_LOGGED_IN_FOR_COMMAND, commandWord));
    }

    /**
     * Constructs a new {@code NotLoggedInCommandException} with the specified detail {@code message} and {@code cause}.
     */
    public NotLoggedInCommandException(String message, Throwable cause) {
        super(message, cause);

    }
}
